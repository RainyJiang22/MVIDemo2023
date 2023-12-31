package com.volcengine.service;

import com.alibaba.fastjson.JSON;
import com.volcengine.model.ApiInfo;
import com.volcengine.model.Header;
import com.volcengine.model.NameValuePair;
import com.volcengine.auth.ISignerV4;
import com.volcengine.auth.impl.SignerV4Impl;
import com.volcengine.error.SdkError;
import com.volcengine.helper.Const;
import com.volcengine.http.DynamicTimeoutInterceptor;
import com.volcengine.http.OkHttpClientFactory;
import com.volcengine.http.VolcengineInterceptor;
import com.volcengine.model.Credentials;
import com.volcengine.model.RequestParam;
import com.volcengine.model.ServiceInfo;
import com.volcengine.model.SignRequest;
import com.volcengine.model.response.RawResponse;

import okhttp3.*;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.Proxy;
import java.util.*;

public abstract class BaseServiceImpl implements IBaseService {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    public static final MediaType MEDIA_TYPE_PROTOBUF = MediaType.parse(Const.APPLICATION_X_PROTOBUF);

    protected ServiceInfo serviceInfo;
    protected Map<String, ApiInfo> apiInfoList;
    private OkHttpClient httpClient;
    private ISignerV4 ISigner;

    private BaseServiceImpl() {
    }

    public BaseServiceImpl(ServiceInfo info, Proxy proxy, Map<String, ApiInfo> apiInfoList) {
        initBean(info, proxy, apiInfoList);
    }

    public BaseServiceImpl(ServiceInfo info, Map<String, ApiInfo> apiInfoList) {
        initBean(info, null, apiInfoList);
    }

    private void initBean(ServiceInfo info, Proxy proxy, Map<String, ApiInfo> apiInfoList) {
        this.serviceInfo = info;
        this.apiInfoList = apiInfoList;
        this.ISigner = new SignerV4Impl();
        VolcengineInterceptor volcengineInterceptor = new VolcengineInterceptor(this.ISigner, serviceInfo.getCredentials());

        DynamicTimeoutInterceptor.DynamicTimeoutConfig defaultTimeout = new DynamicTimeoutInterceptor.DynamicTimeoutConfig(info.getConnectionTimeout(), info.getSocketTimeout());
        Map<String, DynamicTimeoutInterceptor.DynamicTimeoutConfig> apiTimeoutMap = new HashMap<String, DynamicTimeoutInterceptor.DynamicTimeoutConfig>();
        for (Map.Entry<String, ApiInfo> entry : apiInfoList.entrySet()) {
            ApiInfo apiInfo = entry.getValue();

            if (apiInfo.getConnectionTimeout() == 0 && apiInfo.getSocketTimeout() == 0) {
                continue;
            }
            if (apiInfo.getConnectionTimeout() == defaultTimeout.getConnectTimeout() && apiInfo.getSocketTimeout() == defaultTimeout.getReadTimeout()) {
                continue;
            }
            apiTimeoutMap.put(entry.getKey(), new DynamicTimeoutInterceptor.DynamicTimeoutConfig(apiInfo.getConnectionTimeout(), apiInfo.getSocketTimeout()));
        }

        DynamicTimeoutInterceptor dynamicTimeoutInterceptor = new DynamicTimeoutInterceptor(defaultTimeout, apiTimeoutMap);
        if (proxy == null) {
            this.httpClient = OkHttpClientFactory.create(volcengineInterceptor, dynamicTimeoutInterceptor);
        } else {
            this.httpClient = OkHttpClientFactory.create(proxy, volcengineInterceptor, dynamicTimeoutInterceptor);
        }

        init(info);
    }


    private void init(ServiceInfo info) {
        String accessKey = System.getenv(Const.ACCESS_KEY);
        String secretKey = System.getenv(Const.SECRET_KEY);

        if (accessKey != null && !accessKey.equals("") && secretKey != null && !secretKey.equals("")) {
            info.getCredentials().setAccessKeyID(accessKey);
            info.getCredentials().setSecretAccessKey(secretKey);
        } else {
            File file = new File(System.getenv("HOME") + "/.volc/config");
            if (file.exists()) {
                try {
                    long length = file.length();
                    byte[] content = new byte[(int) length];
                    FileInputStream in = new FileInputStream(file);
                    in.read(content);
                    in.close();
                    com.volcengine.model.Credentials credentials = JSON.parseObject(content, Credentials.class);
                    if (credentials.getAccessKeyID() != null) {
                        info.getCredentials().setAccessKeyID(credentials.getAccessKeyID());
                    }
                    if (credentials.getSecretAccessKey() != null) {
                        info.getCredentials().setSecretAccessKey(credentials.getSecretAccessKey());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Read file " + file.getName() + " fail.");
                }
            }
        }
    }

    @Override
    public String getSignUrl(String api, List<NameValuePair> params) throws Exception {
        ApiInfo apiInfo = apiInfoList.get(api);

        if (apiInfo == null) {
            throw new Exception(SdkError.getErrorDesc(SdkError.ENOAPI));
        }

        List<NameValuePair> mergedNV = mergeQuery(params, apiInfo.getQuery());

        RequestParam requestParam = RequestParam.builder().isSignUrl(true)
                .body(new byte[0])
                .host(serviceInfo.getHost())
                .path(apiInfo.getPath())
                .method(apiInfo.getMethod().toUpperCase())
                .date(new Date())
                .queryList(mergedNV)
                .build();
        SignRequest signRequest = ISigner.getSignRequest(requestParam, serviceInfo.getCredentials());

        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.scheme(serviceInfo.getScheme()).host(serviceInfo.getHost()).encodedPath(apiInfo.getPath());

        for (NameValuePair pair : mergedNV) {
            urlBuilder.addQueryParameter(pair.getName(), pair.getValue());
        }

        urlBuilder.addQueryParameter(Const.XDate, signRequest.getXDate());
        urlBuilder.addQueryParameter(Const.XNotSignBody, signRequest.getXNotSignBody());
        urlBuilder.addQueryParameter(Const.XCredential, signRequest.getXCredential());
        urlBuilder.addQueryParameter(Const.XAlgorithm, signRequest.getXAlgorithm());
        urlBuilder.addQueryParameter(Const.XSignedHeaders, signRequest.getXSignedHeaders());
        urlBuilder.addQueryParameter(Const.XSignedQueries, signRequest.getXSignedQueries());
        urlBuilder.addQueryParameter(Const.XSignature, signRequest.getXSignature());
        if (StringUtils.isNotEmpty(signRequest.getXSecurityToken())) {
            urlBuilder.addQueryParameter(Const.XSecurityToken, signRequest.getXSecurityToken());
        }
        return StringUtils.defaultString(urlBuilder.build().query(), StringUtils.EMPTY);
    }

    @Override
    public RawResponse query(String api, List<NameValuePair> params) {
        ApiInfo apiInfo = apiInfoList.get(api);
        if (apiInfo == null) {
            return new RawResponse(null, SdkError.ENOAPI.getNumber(), new Exception(SdkError.getErrorDesc(SdkError.ENOAPI)), null, 0);
        }

        Request request = prepareRequestBuilder(api, params).get().build();
        return makeRequest(api, request);
    }

    /**
     * Origin put method which without volcengine signer
     *
     * @param url     url
     * @param data    data
     * @param headers headers
     * @return true if the http response code is 200, otherwise false
     */
    protected boolean originPutData(String url, byte[] data, Map<String, String> headers) {
        RequestBody body = RequestBody.create(data);
        return doOriginPut(url, body, headers).getCode() == 200;
    }

    /**
     * Origin put method which without volcengine signer
     *
     * @param url     url
     * @param data    data
     * @param headers headers
     * @return the raw response
     */
    protected RawResponse originPutDataWithResponse(String url, byte[] data, Map<String, String> headers) {
        return doOriginPut(url, RequestBody.create(data), headers);
    }

    private RawResponse doOriginPut(String url, RequestBody entity, Map<String, String> headers) {
        Request.Builder httpPut = new Request.Builder();
        httpPut.url(url);
        httpPut.put(entity);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.addHeader(entry.getKey(), entry.getValue());
            }
        }
        OkHttpClient client;
        Response response = null;
        RawResponse rawResponse = new RawResponse();
        try {
            client = OkHttpClientFactory.create();
            if (client == null) {
                return new RawResponse(null, SdkError.UNKNOWN.getNumber(), new IllegalStateException(""), null, 0);
            }
            Call call = client.newCall(httpPut.build());
            response = call.execute();
            int statusCode = response.code();
            rawResponse.setCode(statusCode);
            ResponseBody body = response.body();
            if (body != null) {

                rawResponse.setData(body.bytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            rawResponse.setException(e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return rawResponse;
    }

    @Override
    public RawResponse json(String api, List<NameValuePair> params, String body) {
        ApiInfo apiInfo = apiInfoList.get(api);
        if (apiInfo == null) {
            return new RawResponse(null, SdkError.ENOAPI.getNumber(), new Exception(SdkError.getErrorDesc(SdkError.ENOAPI)), null, 0);
        }

        Request.Builder requestBuilder = prepareRequestBuilder(api, params);

        RequestBody requestBody = RequestBody.create(body, MEDIA_TYPE_JSON);

        requestBuilder.header(Const.ContentType, requestBody.contentType().toString());
        requestBuilder.post(requestBody);
        return makeRequest(api, requestBuilder.build());
    }

    @Override
    public RawResponse post(String api, List<NameValuePair> query, List<NameValuePair> form) {
        ApiInfo apiInfo = apiInfoList.get(api);
        if (apiInfo == null) {
            return new RawResponse(null, SdkError.ENOAPI.getNumber(), new Exception(SdkError.getErrorDesc(SdkError.ENOAPI)), null, 0);
        }

        Request.Builder requestBuilder = prepareRequestBuilder(api, query);
        List<NameValuePair> mergedForm = mergeQuery(form, apiInfo.getForm());
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (NameValuePair pair : mergedForm) {
            bodyBuilder.add(pair.getName(), pair.getValue());
        }

        FormBody formBody = bodyBuilder.build();

        requestBuilder.header(Const.ContentType, formBody.contentType().toString());
        return makeRequest(api, requestBuilder.post(formBody).build());
    }

    private RawResponse makeRequest(String api, Request request) {
        OkHttpClient client;
        Response response = null;
        try {
            client = getHttpClient();
            if (client == null) {
                return new RawResponse(null, SdkError.UNKNOWN.getNumber(), new IllegalStateException(""), null, 0);
            }

            Call call = client.newCall(request);
            response = call.execute();
            ResponseBody body = response.body();
            byte[] bytes = null;
            int statusCode = response.code();
            if (statusCode >= 300) {
                if (body != null) {
                    return new RawResponse(null, SdkError.EHTTP.getNumber(), new Exception(body.string()), null, 0);
                }
            }
            if (body != null) {
                bytes = body.bytes();
            }
            return new RawResponse(bytes, SdkError.SUCCESS.getNumber(), null, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new RawResponse(null, SdkError.EHTTP.getNumber(), new Exception(SdkError.getErrorDesc(SdkError.EHTTP)), null, 0);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private Request.Builder prepareRequestBuilder(String api, List<NameValuePair> params) {
        Request.Builder requestBuilder = new Request.Builder();
        ApiInfo apiInfo = apiInfoList.get(api);

        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();

        Collection<Header> mergedH = mergeHeader(serviceInfo.getHeader(), apiInfo.getHeader());
        for (Header header : mergedH) {
            requestBuilder.addHeader(header.getName(), header.getValue());
        }
        List<NameValuePair> mergedNV = mergeQuery(params, apiInfo.getQuery());


        urlBuilder.scheme(serviceInfo.getScheme());
        urlBuilder.host(serviceInfo.getHost());
        urlBuilder.encodedPath(apiInfo.getPath());
        for (NameValuePair pair : mergedNV) {
            urlBuilder.addQueryParameter(pair.getName(), pair.getValue());
        }

        requestBuilder.url(urlBuilder.build());
        return requestBuilder;
    }

    private Collection<Header> mergeHeader(List<Header> header1, List<Header> header2) {
        Set<Header> set = new HashSet<>();
        if (header1 != null) {
            set.addAll(header1);
        }
        if (header2 != null) {
            set.addAll(header2);
        }
        return set;
    }

    private List<NameValuePair> mergeQuery(List<NameValuePair> query1, List<NameValuePair> query2) {
        List<NameValuePair> res = new ArrayList<NameValuePair>();
        if (query1 != null) {
            res.addAll(query1);
        }
        if (query2 != null) {
            res.addAll(query2);
        }
        return res;
    }

    @Override
    public void setClientNoReuse() {
        this.httpClient = null;
    }

    @Override
    public String getAccessKey() {
        return serviceInfo.getCredentials().getAccessKeyID();
    }

    @Override
    public void setAccessKey(String accessKey) {
        serviceInfo.getCredentials().setAccessKeyID(accessKey);
    }

    @Override
    public String getSecretKey() {
        return serviceInfo.getCredentials().getSecretAccessKey();
    }

    @Override
    public void setSecretKey(String secretKey) {
        serviceInfo.getCredentials().setSecretAccessKey(secretKey);
    }

    @Override
    public String getSessionToken() {
        return serviceInfo.getCredentials().getSessionToken();
    }

    @Override
    public void setSessionToken(String sessionToken) {
        serviceInfo.getCredentials().setSessionToken(sessionToken);
    }

    @Override
    public void setRegion(String region) {
        serviceInfo.getCredentials().setRegion(region);
    }


    @Override
    public String getRegion() {
        return serviceInfo.getCredentials().getRegion();
    }

    @Override
    public void setHost(String host) {
        serviceInfo.setHost(host);
    }

    @Override
    public void setScheme(String scheme) {
        serviceInfo.setScheme(scheme);
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    @Override
    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public Map<String, ApiInfo> getApiInfoList() {
        return apiInfoList;
    }

    public ISignerV4 getISigner() {
        return ISigner;
    }

}

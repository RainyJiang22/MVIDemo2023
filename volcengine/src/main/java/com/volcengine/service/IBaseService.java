package com.volcengine.service;

import com.volcengine.model.NameValuePair;
import com.volcengine.model.ServiceInfo;
import com.volcengine.model.response.RawResponse;

import okhttp3.OkHttpClient;
import java.util.List;

/**
 * The interface Service.
 */
public interface IBaseService {

    /**
     * Sets client no reuse.
     */
    void setClientNoReuse();

    /**
     * Gets access key.
     *
     * @return the access key
     */
    String getAccessKey();

    /**
     * Sets access key.
     *
     * @param accessKey the access key
     */
    void setAccessKey(String accessKey);

    /**
     * Gets secret key.
     *
     * @return the secret key
     */
    String getSecretKey();

    /**
     * Sets secret key.
     *
     * @param secretKey the secret key
     */
    void setSecretKey(String secretKey);

    /**
     * Gets session token.
     *
     * @return the session token
     */
    String getSessionToken();

    /**
     * Sets session token.
     *
     * @param sessionToken the session token
     */
    void setSessionToken(String sessionToken);

    /**
     * Sets region.
     *
     * @param region the region
     */
    void setRegion(String region);

    /**
     * Gets region.
     *
     * @return the region
     */
    String getRegion();

    /**
     * Sets host.
     *
     * @param host the host
     */
    void setHost(String host);

    /**
     * Sets scheme.
     *
     * @param scheme the scheme
     */
    void setScheme(String scheme);

    /**
     * Sets http client.
     *
     * @param httpClient the http client
     */
    void setHttpClient(OkHttpClient httpClient);

    /**
     * Sets service info.
     *
     * @param serviceInfo the service info
     */
    void setServiceInfo(ServiceInfo serviceInfo);

    /**
     * Query raw response.
     *
     * @param api    the api
     * @param params the params
     * @return the raw response
     * @throws Exception the exception
     */
    RawResponse query(String api, List<NameValuePair> params) throws Exception;

    /**
     * Gets sign url.
     *
     * @param api    the api
     * @param params the params
     * @return the sign url
     * @throws Exception the exception
     */
    String getSignUrl(String api, List<NameValuePair> params) throws Exception;

    /**
     * Json raw response.
     *
     * @param api    the api
     * @param params the params
     * @param body   the body
     * @return the raw response
     * @throws Exception the exception
     */
    RawResponse json(String api, List<NameValuePair> params, String body) throws Exception;

    /**
     * Post raw response.
     *
     * @param api    the api
     * @param params the params
     * @param form   the form
     * @return the raw response
     * @throws Exception the exception
     */
    RawResponse post(String api, List<NameValuePair> params, List<NameValuePair> form) throws Exception;

}

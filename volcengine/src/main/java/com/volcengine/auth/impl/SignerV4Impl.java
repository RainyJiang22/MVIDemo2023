package com.volcengine.auth.impl;

import android.annotation.SuppressLint;
import android.os.Build;

import com.volcengine.auth.ISignerV4;
import com.volcengine.helper.Const;
import com.volcengine.helper.Hex;
import com.volcengine.helper.Utils;
import org.apache.commons.lang3.StringUtils;
import com.volcengine.model.Credentials;
import com.volcengine.model.Header;
import com.volcengine.model.MetaData;
import com.volcengine.model.NameValuePair;
import com.volcengine.model.RequestParam;
import com.volcengine.model.SignRequest;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 签名方法实现
 */
public class SignerV4Impl implements ISignerV4 {
    private static final TimeZone tz = TimeZone.getTimeZone("UTC");
    private static final Set<String> H_INCLUDE = new HashSet<String>();
    private static final BitSet URLENCODER = new BitSet(256);
    private static final String CONST_ENCODE = "0123456789ABCDEF";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    static {
        H_INCLUDE.add(Const.ContentType);
        H_INCLUDE.add(Const.ContentMd5);
        H_INCLUDE.add(Const.Host);
        int i;
        for (i = 97; i <= 122; ++i) {
            URLENCODER.set(i);
        }

        for (i = 65; i <= 90; ++i) {
            URLENCODER.set(i);
        }

        for (i = 48; i <= 57; ++i) {
            URLENCODER.set(i);
        }
        URLENCODER.set('-');
        URLENCODER.set('_');
        URLENCODER.set('.');
        URLENCODER.set('~');
    }

    @Override
    public SignRequest getSignRequest(RequestParam requestParam, Credentials credentials) throws Exception {
        if (requestParam == null || credentials == null) {
            throw new Exception("requestParam and credentials is null");
        }
        if (requestParam.getIsSignUrl() == null || requestParam.getDate() == null || requestParam.getQueryList() == null) {
            throw new Exception("requestParam's isSignUrl or date or queryList is null");
        }
        String formatDate = getAppointFormatDate(requestParam.getDate());
        MetaData meta = getMetaDate(credentials, toDate(formatDate));

        Map<String, String> requestSignMap = new HashMap<>();
        String bodyHash;
        SignRequest signRequest = SignRequest.builder().xDate(formatDate).xSecurityToken(credentials.getSessionToken()).build();
        if (StringUtils.isNotEmpty(credentials.getSessionToken())) {
            requestSignMap.put(Const.XSecurityToken, credentials.getSessionToken());
        }

        if (requestParam.getIsSignUrl()) {
            for (NameValuePair nv : requestParam.getQueryList()) {
                requestSignMap.put(nv.getName(), nv.getValue());
            }
            requestSignMap.put(Const.XDate, formatDate);
            requestSignMap.put(Const.XNotSignBody, "");
            requestSignMap.put(Const.XCredential, credentials.getAccessKeyID() + "/" + meta.getCredentialScope());
            requestSignMap.put(Const.XAlgorithm, meta.getAlgorithm());
            requestSignMap.put(Const.XSignedHeaders, meta.getSignedHeaders());
            requestSignMap.put(Const.XSignedQueries, "");

            List<String> keys = new ArrayList<>(requestSignMap.keySet());
            Collections.sort(keys);
            requestSignMap.put(Const.XSignedQueries, StringUtils.join(keys, ";"));

            signRequest.setXNotSignBody("");
            signRequest.setXCredential(credentials.getAccessKeyID() + "/" + meta.getCredentialScope());
            signRequest.setXAlgorithm(meta.getAlgorithm());
            signRequest.setXSignedHeaders(meta.getSignedHeaders());
            signRequest.setXSignedQueries(StringUtils.join(keys, ";"));

            bodyHash = Utils.hashSHA256(new byte[0]);
        } else {
            for (Header header : requestParam.getHeaders()) {
                requestSignMap.put(header.getName(), header.getValue());
            }
            if (requestSignMap.get(Const.ContentType) == null) {
                signRequest.setContentType(Const.ContentTypeValue);
            } else {
                signRequest.setContentType(requestSignMap.get(Const.ContentType));
            }

            requestSignMap.put(Const.XDate, formatDate);
            requestSignMap.put(Const.Host, requestParam.getHost());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                requestSignMap.putIfAbsent(Const.ContentType, Const.ContentTypeValue);
            }
            bodyHash = Utils.hashSHA256(requestParam.getBody() == null ? new byte[0] : requestParam.getBody());
            requestSignMap.put(Const.XContentSha256, bodyHash);

            signRequest.setHost(requestParam.getHost());
            signRequest.setXContentSha256(bodyHash);
        }

        String signature = getSignatureStr(requestParam, meta, credentials.getSecretAccessKey(),
                formatDate, requestSignMap, bodyHash);
        if (requestParam.getIsSignUrl()) {
            signRequest.setXSignature(signature);
        } else {
            signRequest.setAuthorization(buildAuthHeaderV4(signature, meta, credentials));
        }
        return signRequest;
    }

    private String getSignatureStr(RequestParam requestParam, MetaData meta, String secreteAccessKey,
                                   String formatDate, Map<String, String> requestSignMap, String bodyHash) throws Exception {


        /*
         fixme 签名过程
         * 1.构建规范请求字符串
         * 2.构建待签名字符串
         * 3.计算签名密钥
         * 4.构建Authorization
         */
        //step1
        String hashedCanonReq = hashedCanonicalRequestV4(requestParam, meta, requestSignMap, bodyHash);

        // step 2
        String stringToSign = StringUtils.join(new String[]{meta.getAlgorithm(), formatDate, meta.getCredentialScope(), hashedCanonReq}, "\n");

        // step 3
        byte[] signingKey = genSigningSecretKeyV4(secreteAccessKey, meta.getDate(), meta.getRegion(), meta.getService());
        return signatureV4(signingKey, stringToSign);
    }

    private MetaData getMetaDate(Credentials credentials, String date) {
        MetaData meta = new MetaData();
        meta.setDate(date);
        meta.setService(credentials.getService());
        meta.setRegion(credentials.getRegion());
        meta.setAlgorithm("HMAC-SHA256");
        meta.setSignedHeaders("");
        meta.setCredentialScope(StringUtils.join(new String[]{meta.getDate(), meta.getRegion(), meta.getService(), "request"}, "/"));
        return meta;
    }

    private String hashedCanonicalRequestV4(RequestParam requestParam, MetaData meta,
                                            Map<String, String> requestSignMap, String bodyHash) throws Exception {
        List<NameValuePair> queryList = new ArrayList<>();
        String canonicalRequest;
        if (requestParam.getIsSignUrl()) {
            for (String key : requestSignMap.keySet()) {
                queryList.add(new NameValuePair(key, requestSignMap.get(key)));
            }
            canonicalRequest = StringUtils.join(new String[]{requestParam.getMethod(), this.normUri(requestParam.getPath()),
                    this.normQuery(queryList), "\n", meta.getSignedHeaders(), bodyHash}, "\n");
        } else {
            String canonicalHeaders = getCanonicalHeaders(requestParam, meta, requestSignMap);

            canonicalRequest = StringUtils.join(new String[]{requestParam.getMethod(), normUri(requestParam.getPath()),
                    normQuery(requestParam.getQueryList()), canonicalHeaders, meta.getSignedHeaders(), bodyHash}, "\n");
        }
        return Utils.hashSHA256(canonicalRequest.getBytes());
    }

    private String getCanonicalHeaders(RequestParam requestParam, MetaData meta, Map<String, String> requestSignMap) {
        Map<String, String> signMap = new HashMap<>();
        List<String> signedHeaders = sortHeaders(requestSignMap, signMap);
        if (!requestParam.getIsSignUrl()) {
            meta.setSignedHeaders(StringUtils.join(signedHeaders, ";"));
        }
        if (StringUtils.isEmpty(requestParam.getPath())) {
            requestParam.setPath("/");
        }

        StringBuilder signedHeadersToSignStr = new StringBuilder();
        for (String h : signedHeaders) {
            String value = signMap.get(h).trim();
            if (h.equals("host")) {
                if (value.contains(":")) {
                    String[] split = value.split(":");
                    String port = split[1];
                    if (port.equals("80") || port.equals("443")) {
                        value = split[0];
                    }
                }
            }
            signedHeadersToSignStr.append(h).append(":").append(value).append("\n");
        }
        return signedHeadersToSignStr.toString();
    }

    private List<String> sortHeaders(Map<String, String> requestSignMap, Map<String, String> signMap) {
        List<String> signedHeaders = new ArrayList<>();
        for (Map.Entry<String, String> entry : requestSignMap.entrySet()) {
            signMap.put(entry.getKey().toLowerCase(), entry.getValue());
            if (H_INCLUDE.contains(entry.getKey()) || entry.getKey().startsWith("X-")) {
                signedHeaders.add(entry.getKey().toLowerCase());
            }
        }
        Collections.sort(signedHeaders);
        return signedHeaders;
    }

    private String signatureV4(byte[] signingKey, String stringToSign) throws Exception {
        return new String(Hex.encodeHex(Utils.hmacSHA256(signingKey, stringToSign)));
    }

    private byte[] genSigningSecretKeyV4(String secretKey, String date, String region, String service) throws Exception {
        byte[] kDate = Utils.hmacSHA256((secretKey).getBytes(), date);
        byte[] kRegion = Utils.hmacSHA256(kDate, region);
        byte[] kService = Utils.hmacSHA256(kRegion, service);
        return Utils.hmacSHA256(kService, "request");
    }

    private String buildAuthHeaderV4(String signature, MetaData meta, Credentials credentials) {
        String credential = credentials.getAccessKeyID() + "/" + meta.getCredentialScope();

        return meta.getAlgorithm() +
                " Credential=" + credential +
                ", SignedHeaders=" + meta.getSignedHeaders() +
                ", Signature=" + signature;
    }

    @SuppressLint("SimpleDateFormat")
    private String getCurrentFormatDate() {
        DateFormat df = new SimpleDateFormat(Const.TIME_FORMAT_V4);
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    private String getAppointFormatDate(Date date) {
         DateFormat df = new SimpleDateFormat(Const.TIME_FORMAT_V4);
        df.setTimeZone(tz);
        return df.format(date);
    }

    private String toDate(String timestamp) {
        return timestamp.substring(0, 8);
    }


    private String normUri(String path) {
        String[] parts = path.split("/", -1);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = signStringEncoder(parts[i]);
        }
        return StringUtils.join(parts, "/");
    }

    /**
     * 与golang的标准对齐
     *
     * @param params query kv pair
     * @return query
     */
    private String normQuery(List<NameValuePair> params) {
        Collections.sort(params);
        return signQueryEncoder(params);
    }

    /**
     * 与golang的标准对齐，
     *
     * @param params kv pair
     * @return query string
     */
    private String signQueryEncoder(List<NameValuePair> params) {
        StringBuilder result = new StringBuilder();
        for (NameValuePair pair : params) {
            String encodedName = signStringEncoder(pair.getName());
            String encodedValue = signStringEncoder(pair.getValue());
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(encodedName);
            if (encodedValue != null) {
                result.append("=");
                result.append(encodedValue);
            }
        }
        return result.toString();
    }

    /**
     * 与golang的标准对齐，URLENCODER中的字符不转换，空格转换为%20(仅签名使用)
     *
     * @param source 原文
     * @return 转换后的编码字符串
     */
    private String signStringEncoder(String source) {
        if (source == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(source.length());
        ByteBuffer bb = UTF_8.encode(source);
        while (bb.hasRemaining()) {
            int b = bb.get() & 255;
            if (URLENCODER.get(b)) {
                buf.append((char) b);
            } else if (b == 32) {
                buf.append("%20");
            } else {
                buf.append("%");
                char hex1 = CONST_ENCODE.charAt(b >> 4);
                char hex2 = CONST_ENCODE.charAt(b & 15);
                buf.append(hex1);
                buf.append(hex2);
            }
        }

        return buf.toString();
    }
}

package com.volcengine.helper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Const {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String ACCESS_KEY = "VOLC_ACCESSKEY";
    public static final String SECRET_KEY = "VOLC_SECRETKEY";

    public static final String REGION_CN_NORTH_1 = "cn-north-1";
    public static final String REGION_AP_SINGAPORE_1 = "ap-singapore-1";
    public static final String REGION_US_EASE_1 = "us-east-1";

    public static final String TIME_FORMAT_V4 = "yyyyMMdd'T'HHmmss'Z'";

    public static final String CONNECTION_TIMEOUT = "ConnectionTimeout";
    public static final String SOCKET_TIMEOUT = "SocketTimeout";
    public static final String Scheme = "Scheme";
    public static final String Host = "Host";
    public static final String Header = "Header";
    public static final String Credentials = "Credentials";

    public static final String Method = "Method";
    public static final String Path = "Path";
    public static final String Query = "Query";
    public static final String Form = "Form";

    public static final String Action = "Action";



    //sign
    public static final String XDate = "X-Date";
    public static final String XNotSignBody = "X-NotSignBody";
    public static final String XCredential = "X-Credential";
    public static final String XAlgorithm = "X-Algorithm";
    public static final String XSignedHeaders = "X-SignedHeaders";
    public static final String XSignedQueries = "X-SignedQueries";
    public static final String XSignature = "X-Signature";

    public static final String ContentType = "Content-Type";
    public static final String ContentTypeValue = "application/x-www-form-urlencoded; charset=utf-8";
    public static final String XContentSha256 = "X-Content-Sha256";
    public static final String Authorization = "Authorization";
    public static final String XSecurityToken = "X-Security-Token";

    public static final String ContentMd5 = "Content-Md5";

    public static final String USERAGENT = "User-Agent";



    //request method
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String POST = "POST";
    public static final String PUT = "PUT";

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_ALL = "*/*";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String GZIP_DEFLATE_BR = "gzip, deflate, br";
    public static final String SERVICE_NAME = "ServiceName";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_X_PROTOBUF = "application/x-protobuf";

    // visual
    public static final String JPCartoon = "JPCartoon";
    public static final String AllAgeGeneration = "AllAgeGeneration";
    public static final String HairStyle = "HairStyle";
    public static final String HumanSegment = "HumanSegment";
}

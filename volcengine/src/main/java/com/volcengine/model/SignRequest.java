package com.volcengine.model;

/**
 * @author jiangshiyu
 * @date 2023/10/30
 */
public class SignRequest {
    private String xDate;
    private String xNotSignBody;
    private String xCredential;
    private String xAlgorithm;
    private String xSignedHeaders;
    private String xSignedQueries;
    private String xSignature;
    private String xSecurityToken;
    private String host;
    private String contentType;
    private String xContentSha256;
    private String authorization;

    SignRequest(String xDate, String xNotSignBody, String xCredential, String xAlgorithm, String xSignedHeaders, String xSignedQueries, String xSignature, String xSecurityToken, String host, String contentType, String xContentSha256, String authorization) {
        this.xDate = xDate;
        this.xNotSignBody = xNotSignBody;
        this.xCredential = xCredential;
        this.xAlgorithm = xAlgorithm;
        this.xSignedHeaders = xSignedHeaders;
        this.xSignedQueries = xSignedQueries;
        this.xSignature = xSignature;
        this.xSecurityToken = xSecurityToken;
        this.host = host;
        this.contentType = contentType;
        this.xContentSha256 = xContentSha256;
        this.authorization = authorization;
    }

    public static SignRequestBuilder builder() {
        return new SignRequestBuilder();
    }

    public String getXDate() {
        return this.xDate;
    }

    public String getXNotSignBody() {
        return this.xNotSignBody;
    }

    public String getXCredential() {
        return this.xCredential;
    }

    public String getXAlgorithm() {
        return this.xAlgorithm;
    }

    public String getXSignedHeaders() {
        return this.xSignedHeaders;
    }

    public String getXSignedQueries() {
        return this.xSignedQueries;
    }

    public String getXSignature() {
        return this.xSignature;
    }

    public String getXSecurityToken() {
        return this.xSecurityToken;
    }

    public String getHost() {
        return this.host;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getXContentSha256() {
        return this.xContentSha256;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setXDate(String xDate) {
        this.xDate = xDate;
    }

    public void setXNotSignBody(String xNotSignBody) {
        this.xNotSignBody = xNotSignBody;
    }

    public void setXCredential(String xCredential) {
        this.xCredential = xCredential;
    }

    public void setXAlgorithm(String xAlgorithm) {
        this.xAlgorithm = xAlgorithm;
    }

    public void setXSignedHeaders(String xSignedHeaders) {
        this.xSignedHeaders = xSignedHeaders;
    }

    public void setXSignedQueries(String xSignedQueries) {
        this.xSignedQueries = xSignedQueries;
    }

    public void setXSignature(String xSignature) {
        this.xSignature = xSignature;
    }

    public void setXSecurityToken(String xSecurityToken) {
        this.xSecurityToken = xSecurityToken;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setXContentSha256(String xContentSha256) {
        this.xContentSha256 = xContentSha256;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public static class SignRequestBuilder {
        private String xDate;
        private String xNotSignBody;
        private String xCredential;
        private String xAlgorithm;
        private String xSignedHeaders;
        private String xSignedQueries;
        private String xSignature;
        private String xSecurityToken;
        private String host;
        private String contentType;
        private String xContentSha256;
        private String authorization;

        SignRequestBuilder() {
        }

        public SignRequestBuilder xDate(String xDate) {
            this.xDate = xDate;
            return this;
        }

        public SignRequestBuilder xNotSignBody(String xNotSignBody) {
            this.xNotSignBody = xNotSignBody;
            return this;
        }

        public SignRequestBuilder xCredential(String xCredential) {
            this.xCredential = xCredential;
            return this;
        }

        public SignRequestBuilder xAlgorithm(String xAlgorithm) {
            this.xAlgorithm = xAlgorithm;
            return this;
        }

        public SignRequestBuilder xSignedHeaders(String xSignedHeaders) {
            this.xSignedHeaders = xSignedHeaders;
            return this;
        }

        public SignRequestBuilder xSignedQueries(String xSignedQueries) {
            this.xSignedQueries = xSignedQueries;
            return this;
        }

        public SignRequestBuilder xSignature(String xSignature) {
            this.xSignature = xSignature;
            return this;
        }

        public SignRequestBuilder xSecurityToken(String xSecurityToken) {
            this.xSecurityToken = xSecurityToken;
            return this;
        }

        public SignRequestBuilder host(String host) {
            this.host = host;
            return this;
        }

        public SignRequestBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public SignRequestBuilder xContentSha256(String xContentSha256) {
            this.xContentSha256 = xContentSha256;
            return this;
        }

        public SignRequestBuilder authorization(String authorization) {
            this.authorization = authorization;
            return this;
        }

        public SignRequest build() {
            return new SignRequest(this.xDate, this.xNotSignBody, this.xCredential, this.xAlgorithm, this.xSignedHeaders, this.xSignedQueries, this.xSignature, this.xSecurityToken, this.host, this.contentType, this.xContentSha256, this.authorization);
        }

        public String toString() {
            return "SignRequest.SignRequestBuilder(xDate=" + this.xDate + ", xNotSignBody=" + this.xNotSignBody + ", xCredential=" + this.xCredential + ", xAlgorithm=" + this.xAlgorithm + ", xSignedHeaders=" + this.xSignedHeaders + ", xSignedQueries=" + this.xSignedQueries + ", xSignature=" + this.xSignature + ", xSecurityToken=" + this.xSecurityToken + ", host=" + this.host + ", contentType=" + this.contentType + ", xContentSha256=" + this.xContentSha256 + ", authorization=" + this.authorization + ")";
        }
    }
}
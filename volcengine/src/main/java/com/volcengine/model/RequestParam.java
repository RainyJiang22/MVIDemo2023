
package com.volcengine.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author jiangshiyu
 * @date 2023/10/30
 */
public class RequestParam {
    private Boolean isSignUrl;
    private byte[] body;
    private String method;
    private Date date;
    private String path;
    private String host;
    private List<NameValuePair> queryList;
    private List<Header> headers;

    RequestParam(Boolean isSignUrl, byte[] body, String method, Date date, String path, String host, List<NameValuePair> queryList, List<Header> headers) {
        this.isSignUrl = isSignUrl;
        this.body = body;
        this.method = method;
        this.date = date;
        this.path = path;
        this.host = host;
        this.queryList = queryList;
        this.headers = headers;
    }

    public static RequestParamBuilder builder() {
        return new RequestParamBuilder();
    }

    public void setIsSignUrl(Boolean isSignUrl) {
        this.isSignUrl = isSignUrl;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setQueryList(List<NameValuePair> queryList) {
        this.queryList = queryList;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public Boolean getIsSignUrl() {
        return this.isSignUrl;
    }

    public byte[] getBody() {
        return this.body;
    }

    public String getMethod() {
        return this.method;
    }

    public Date getDate() {
        return this.date;
    }

    public String getPath() {
        return this.path;
    }

    public String getHost() {
        return this.host;
    }

    public List<NameValuePair> getQueryList() {
        return this.queryList;
    }

    public List<Header> getHeaders() {
        return this.headers;
    }

    public static class RequestParamBuilder {
        private Boolean isSignUrl;
        private byte[] body;
        private String method;
        private Date date;
        private String path;
        private String host;
        private List<NameValuePair> queryList;
        private List<Header> headers;

        RequestParamBuilder() {
        }

        public RequestParamBuilder isSignUrl(Boolean isSignUrl) {
            this.isSignUrl = isSignUrl;
            return this;
        }

        public RequestParamBuilder body(byte[] body) {
            this.body = body;
            return this;
        }

        public RequestParamBuilder method(String method) {
            this.method = method;
            return this;
        }

        public RequestParamBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public RequestParamBuilder path(String path) {
            this.path = path;
            return this;
        }

        public RequestParamBuilder host(String host) {
            this.host = host;
            return this;
        }

        public RequestParamBuilder queryList(List<NameValuePair> queryList) {
            this.queryList = queryList;
            return this;
        }

        public RequestParamBuilder headers(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        public RequestParam build() {
            return new RequestParam(this.isSignUrl, this.body, this.method, this.date, this.path, this.host, this.queryList, this.headers);
        }

        public String toString() {
            return "RequestParam.RequestParamBuilder(isSignUrl=" + this.isSignUrl + ", body=" + Arrays.toString(this.body) + ", method=" + this.method + ", date=" + this.date + ", path=" + this.path + ", host=" + this.host + ", queryList=" + this.queryList + ", headers=" + this.headers + ")";
        }
    }
}

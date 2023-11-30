package com.volcengine.model.response;

import com.volcengine.model.Header;
public class RawResponse {

    private byte[] data;
    private int code;
    private Exception exception;

    private Header[] headers;

    //response header and origin http response code
    private int httpCode;

    public RawResponse() {

    }

    public RawResponse(byte[] data, int code, Exception e) {
        this.data = data;
        this.code = code;
        this.exception = e;
    }

    public RawResponse(byte[] data, int code, Exception exception, Header[] headers) {
        this.data = data;
        this.code = code;
        this.exception = exception;
        this.headers = headers;
    }

    public RawResponse(byte[] data, int code, Exception exception, Header[] headers, int httpCode) {
        this.data = data;
        this.code = code;
        this.exception = exception;
        this.headers = headers;
        this.httpCode = httpCode;
    }

    public String getFirstHeader(String key) {
        if (key != null && headers != null) {
            for (Header header : headers) {
                if (header.getName().equalsIgnoreCase(key)) {
                    return header.getValue();
                }
            }
        }
        return null;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}

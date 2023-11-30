package com.volcengine.model.response;

import com.alibaba.fastjson.annotation.JSONField;


public class VisualBaseResponse {
    
    @JSONField(name = "ResponseMetadata")
    ResponseMetaData responseMetadata;

    @JSONField(name = "code")
    int code;

    @JSONField(name = "message")
    String message;

    @JSONField(name = "request_id")
    String requestId;

    @JSONField(name = "time_elapsed")
    String timeElapsed;

    public ResponseMetaData getResponseMetadata() {
        return responseMetadata;
    }

    public void setResponseMetadata(ResponseMetaData responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
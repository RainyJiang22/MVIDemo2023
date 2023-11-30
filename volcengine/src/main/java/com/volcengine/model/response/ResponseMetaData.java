package com.volcengine.model.response;


import com.alibaba.fastjson.annotation.JSONField;

public class ResponseMetaData {
    @JSONField(name = "RequestId")
    private String requestId;
    @JSONField(name = "Action")
    private String action;
    @JSONField(name = "Version")
    private String version;
    @JSONField(name = "Service")
    private String service;
    @JSONField(name = "Region")
    private String region;
    @JSONField(name = "Error")
    private Error error;
    
    
    public static class Error {
        @JSONField(name = "CodeN")
        private int codeN;
        @JSONField(name = "Code")
        private String code;
        @JSONField(name = "Message")
        private String message;

        public int getCodeN() {
            return codeN;
        }

        public void setCodeN(int codeN) {
            this.codeN = codeN;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
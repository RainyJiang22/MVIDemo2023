package com.volcengine.model.request;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * @author jiangshiyu
 * @date 2024/12/10
 * 图像风格化
 */

@Keep
public class VisualImageStylizationRequest {

    @JSONField(name = "req_key")
    String reqKey = "img2img_disney_3d_style";

    @JSONField(name = "sub_req_key")
    String subReqKey;

    @JSONField(name = "binary_data_base64")
    ArrayList<String> imageList;

    @JSONField(name = "return_url")
    Boolean returnUrl = false;

    @JSONField(name = "logo_info")
    LogoInfo logoInfo;

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public String getSubReqKey() {
        return subReqKey;
    }

    public void setSubReqKey(String subReqKey) {
        this.subReqKey = subReqKey;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public Boolean getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(Boolean returnUrl) {
        this.returnUrl = returnUrl;
    }

    public LogoInfo getLogoInfo() {
        return logoInfo;
    }

    public void setLogoInfo(LogoInfo logoInfo) {
        this.logoInfo = logoInfo;
    }
}

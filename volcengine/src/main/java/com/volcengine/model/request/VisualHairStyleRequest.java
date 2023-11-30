package com.volcengine.model.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * @author jiangshiyu
 * @date 2023/11/14
 */
public class VisualHairStyleRequest {
    @JSONField(name = "req_key")
    String reqKey = "hair_style";

    @JSONField(name = "binary_data_base64")
    ArrayList<String> imageList;


    @JSONField(name = "hair_type")
    Integer hairType = 101;

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public Integer getHairType() {
        return hairType;
    }

    public void setHairType(Integer hairType) {
        this.hairType = hairType;
    }
}

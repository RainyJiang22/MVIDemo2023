package com.volcengine.model.request;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;


@Keep
public class VisualAllAgeGenerationRequest {

    @JSONField(name = "req_key")
    String reqKey = "all_age_generation";

    @JSONField(name = "binary_data_base64")
    ArrayList<String> imageList;

    @JSONField(name = "target_age")
    Integer targetAge = 5;

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

    public Integer getTargetAge() {
        return targetAge;
    }

    public void setTargetAge(Integer targetAge) {
        this.targetAge = targetAge;
    }
}
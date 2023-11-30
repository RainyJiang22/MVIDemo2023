package com.volcengine.model.request;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;

@Keep
public class VisualJPCartoonRequest {
    
    @JSONField(name = "image_base64")
    String imageBase64;

    @JSONField(name = "cartoon_type")
    String cartoonType;

    @JSONField(name = "rotation")
    Integer rotation = 0;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getCartoonType() {
        return cartoonType;
    }

    public void setCartoonType(String cartoonType) {
        this.cartoonType = cartoonType;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }
}
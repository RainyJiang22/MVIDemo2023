package com.volcengine.model.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author jiangshiyu
 * @date 2024/7/16
 */
public class VisualHumanSegmentRequest {

    @JSONField(name = "image_base64")
    String imageBase64;

    //对推向边缘进行精细抠图
    @JSONField(name = "refine")
    Integer refine = 0;

    //是否返回前景人像图(透明背景)
    @JSONField(name = "return_foreground_image")
    Integer returnForegroundImage = 0;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getRefine() {
        return refine;
    }

    public void setRefine(int refine) {
        this.refine = refine;
    }

    public int getReturnForegroundImage() {
        return returnForegroundImage;
    }

    public void setReturnForegroundImage(int returnForegroundImage) {
        this.returnForegroundImage = returnForegroundImage;
    }
}

package com.volcengine.model.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author jiangshiyu
 * @date 2024/7/16
 */
public class VisualHumanSegmentResponse {

    @JSONField(name = "data")
    HumanSegmentData data;

    public static class HumanSegmentData {

        @JSONField(name = "mask")
        String image;

        @JSONField(name = "foreground_image")
        String foreGroundImage;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getForeGroundImage() {
            return foreGroundImage;
        }

        public void setForeGroundImage(String foreGroundImage) {
            this.foreGroundImage = foreGroundImage;
        }
    }
}

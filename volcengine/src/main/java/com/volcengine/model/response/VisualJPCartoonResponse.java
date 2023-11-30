package com.volcengine.model.response;

import com.alibaba.fastjson.annotation.JSONField;


public class VisualJPCartoonResponse extends VisualBaseResponse {
    
    @JSONField(name = "data")
    JPCartoonData data;


    public static class JPCartoonData {
        
        @JSONField(name = "image")
        String image;

        @JSONField(name = "clip")
        String clip;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getClip() {
            return clip;
        }

        public void setClip(String clip) {
            this.clip = clip;
        }
    }

    public JPCartoonData getData() {
        return data;
    }

    public void setData(JPCartoonData data) {
        this.data = data;
    }
}
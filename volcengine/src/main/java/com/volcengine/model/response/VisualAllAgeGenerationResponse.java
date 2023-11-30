package com.volcengine.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;


public class VisualAllAgeGenerationResponse extends VisualBaseResponse {

    @JSONField(name = "data")
    AllAgeGenerationData data;


    public static class AllAgeGenerationData {

        @JSONField(name = "binary_data_base64")
        ArrayList<String> imageList;

        public ArrayList<String> getImageList() {
            return imageList;
        }

        public void setImageList(ArrayList<String> imageList) {
            this.imageList = imageList;
        }
    }

    public AllAgeGenerationData getData() {
        return data;
    }

    public void setData(AllAgeGenerationData data) {
        this.data = data;
    }
}
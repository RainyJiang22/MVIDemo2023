package com.volcengine.model.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * @author jiangshiyu
 * @date 2024/12/10
 */
public class VisualImageStylizationResponse {

    @JSONField(name = "data")
    ImageStylizationData data;


    public static class ImageStylizationData {

        @JSONField(name = "binary_data_base64")
        ArrayList<String> image64List;

        @JSONField(name = "image_urls")
        ArrayList<String> imageUrls;

        public ArrayList<String> getImage64List() {
            return image64List;
        }

        public void setImage64List(ArrayList<String> image64List) {
            this.image64List = image64List;
        }

        public ArrayList<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(ArrayList<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
}

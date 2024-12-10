package com.volcengine.model.request;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author jiangshiyu
 * @date 2024/12/10
 * 水印 可选
 */

@Keep
public class LogoInfo {

    //是否添加水印
    @JSONField(name = "add_logo")
    Boolean addLogo = false;

    //水印位置
    @JSONField(name = "position")
    Integer position = 0;

    //水印语言
    @JSONField(name = "language")
    Integer language = 0;

    //水印的不透明度，取值范围0-1，1表示完全不透明，默认0.3
    @JSONField(name = "opacity")
    Float opacity = 0.3f;

    //水印文本内容
    @JSONField(name = "logo_text_content")
    String logoTextContent = "";

    public Boolean getAddLogo() {
        return addLogo;
    }

    public void setAddLogo(Boolean addLogo) {
        this.addLogo = addLogo;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Float getOpacity() {
        return opacity;
    }

    public void setOpacity(Float opacity) {
        this.opacity = opacity;
    }

    public String getLogoTextContent() {
        return logoTextContent;
    }

    public void setLogoTextContent(String logoTextContent) {
        this.logoTextContent = logoTextContent;
    }
}

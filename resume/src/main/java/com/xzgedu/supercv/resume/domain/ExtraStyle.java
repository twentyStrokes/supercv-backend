package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtraStyle {
    // specific setting
    private Integer pageMarginHorizontal; // 左右页边距
    private Integer pageMarginVertical; // 上下页边距
    private Integer moduleMargin; // 模块间距
    private String themeColor; // 主题色
    private String fontFamily; // 字体
    private Integer contentFontSize; // 正文字体大小
    private Double contentLineHeight; // 正文行高
}

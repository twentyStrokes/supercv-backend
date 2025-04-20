package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzgedu.supercv.common.anotation.ViewData;
import lombok.Data;

@Data
public class Resume {
    private Long id;

    private Long uid;
    @ViewData
    private String nickName;
    @ViewData
    private String headImgUrl;

    private String name;

    private Long templateId;
    @ViewData
    private Template template; // 模版

    private String rawFile; // 简历文件url(pdf/word等)

    @JsonIgnore
    private String rawDataJson; // 简历json数据
    @ViewData
    private RawData rawData;

    @JsonIgnore
    private String extraStyleJson; // 用户额外设置的样式
    @ViewData
    private ExtraStyle extraStyle;

    private boolean isPublic;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public RawData getRawData() {
        // 将json格式的rawDataJson转化成对象ResumeRawData
        if (rawData == null && rawDataJson != null) {
            try {
                rawData = objectMapper.readValue(rawDataJson, RawData.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse rawDataJson", e);
            }
        }
        return rawData;
    }

    public ExtraStyle getExtraStyle() {
        // 将json格式的extraStyleJson转化成对象ResumeExtraStyle
        if (extraStyle == null && extraStyleJson != null) {
            try {
                extraStyle = objectMapper.readValue(extraStyleJson, ExtraStyle.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse extraStyleJson", e);
            }
        }
        return extraStyle;
    }

    public String getRawDataJson() {
        if (rawDataJson == null && rawData != null) {
            try {
                rawDataJson = objectMapper.writeValueAsString(rawData);
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert rawData to json string.", e);
            }
        }
        return rawDataJson;
    }

    public String getExtraStyleJson() {
        if (extraStyleJson == null && extraStyle != null) {
            try {
                extraStyleJson = objectMapper.writeValueAsString(extraStyle);
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert extraStyle to json string.", e);
            }
        }
        return extraStyleJson;
    }
}

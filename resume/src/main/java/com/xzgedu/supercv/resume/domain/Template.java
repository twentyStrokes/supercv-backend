package com.xzgedu.supercv.resume.domain;

import com.xzgedu.supercv.common.anotation.ViewData;
import lombok.Data;

@Data
public class Template {
    private Long id;
    private String name;

    private String pageFrame; //vue页面结构
    private String pageStyle; //css格式

    private String thumbnailUrl;
    private Long demoResumeId;

    private boolean isPublic;
}

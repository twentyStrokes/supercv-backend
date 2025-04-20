package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleItem {
    private Boolean titleEnabled;

    private String titleMajorName;
    private String titleMinorName;
    private String titleOtherName;
    private String titleDateName;
    private String titleMajor; // 主标题
    private String titleMinor; // 次标题
    private String titleOther;
    private String titleDate; // 时间

    private String contentHint; //输入内容提示
    private String content; // 内容
}


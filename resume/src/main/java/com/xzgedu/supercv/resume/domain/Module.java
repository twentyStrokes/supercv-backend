package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xzgedu.supercv.resume.domain.ModuleItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Module {
    private String key; // profile education等
    private String title; // 模块标题
    private Boolean defaultModule; // 是否是默认模块，默认模块不可删除
    private Boolean enabled; // 是否启用

    private List<ModuleItem> items = new ArrayList<>();
}

package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
    private String title; // 标题:基本信息
    private String photoUrl; // 头像
    private Boolean photoEnabled; // 是否显示头像
    private Integer photoLayout; // 头像布局，默认居右，暂时只支持：居左、居右
    private Integer itemLayout; // 条目布局，默认居中，取值左对齐，右对齐，居中对齐
    private Boolean enabled; // 是否启用

    private List<KeyValuePair>  items = new ArrayList<>();
}

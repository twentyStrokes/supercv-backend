package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyValuePair {
    private String key;
    private String label;
    private String value;
    private String placeholder;
    private String valueType; //图片、短文本、长文本等
}

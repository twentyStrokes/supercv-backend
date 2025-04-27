package com.xzgedu.supercv.llm.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LLMLog {
    private long id;
    private long uid;
    private int modelType;
    private int promptType;
    private String input;
    private String output;
    private int inputToken;
    private int outputToken;
    private long costTime;
    private Boolean applied;
    private Date createTime;
}

package com.xzgedu.supercv.llm.domain;

import lombok.Data;

@Data
public class LLMResult {
    private String input;
    private String output;
    private int inputToken;
    private int outputToken;
    private long costTime;
}

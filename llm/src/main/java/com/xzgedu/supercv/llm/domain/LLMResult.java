package com.xzgedu.supercv.llm.domain;

import com.xzgedu.supercv.llm.enums.ModelType;
import lombok.Data;

@Data
public class LLMResult {
    private ModelType modelType;
    private String input;
    private String output;
    private int inputToken;
    private int outputToken;
    private long costTime;
}

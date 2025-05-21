package com.xzgedu.supercv.llm.domain;

import lombok.Data;

@Data
public class LLMModel {
    private long id;
    private int llmType;
    private String modelName;
    private String apiKey;
    private String endpoint;
    private boolean enabled;
}

package com.xzgedu.supercv.llm.domain;

import com.xzgedu.supercv.llm.enums.ModelType;
import com.xzgedu.supercv.llm.enums.PromptType;
import lombok.Data;

@Data
public class LLMLogFilter {
    private Long uid;
    private ModelType modelType;
    private PromptType promptType;
}

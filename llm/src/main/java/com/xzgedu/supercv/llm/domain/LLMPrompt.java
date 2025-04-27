package com.xzgedu.supercv.llm.domain;

import com.xzgedu.supercv.llm.enums.PromptType;
import lombok.Data;

@Data
public class LLMPrompt {
    private PromptType promptType;
    private String text;
}

package com.xzgedu.supercv.llm;

import com.xzgedu.supercv.llm.enums.LLMType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LLMFactory {

    @Autowired
    private QianWen qianWen;

    public LLM getLLM(LLMType llmType) {
        if (LLMType.QIANWEN.getValue() == llmType.getValue()) {
            return qianWen;
        }
        return null;
    }
}

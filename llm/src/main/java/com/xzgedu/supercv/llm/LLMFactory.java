package com.xzgedu.supercv.llm;

import com.xzgedu.supercv.llm.domain.LLMModel;
import com.xzgedu.supercv.llm.enums.LLMType;
import com.xzgedu.supercv.llm.service.LLMModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LLMFactory {

    @Autowired
    private QianWen qianWen;

    @Autowired
    private LLMModelService llmModelService;

    public LLM getEnabledLLM() {
        List<LLMModel> enabledLLMModels = llmModelService.getEnabledLLMModels();
        if (enabledLLMModels == null || enabledLLMModels.isEmpty()) return null;
        LLMModel llmModel = enabledLLMModels.get(0);
        if (llmModel.getLlmType() == LLMType.QIANWEN.getValue()) {
            qianWen.setLlmModel(llmModel);
            return qianWen;
        }
        return null;
    }
}

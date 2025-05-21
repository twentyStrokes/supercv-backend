package com.xzgedu.supercv.llm;

import com.xzgedu.supercv.llm.domain.LLMLog;
import com.xzgedu.supercv.llm.domain.LLMModel;
import com.xzgedu.supercv.llm.domain.LLMPrompt;
import com.xzgedu.supercv.llm.domain.LLMResult;
import com.xzgedu.supercv.llm.service.LLMLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractLLM implements LLM {

    @Autowired
    private LLMLogService llmLogService;

    protected LLMModel llmModel;

    public void setLlmModel(LLMModel llmModel) {
        this.llmModel = llmModel;
    }

    @Override
    public String call(long uid, LLMPrompt prompt) {
        LLMResult result = doCall(prompt.getText());
        LLMLog llmLog = new LLMLog();
        llmLog.setUid(uid);
        llmLog.setModelId(llmModel.getId());
        llmLog.setPromptType(prompt.getPromptType().getValue());
        llmLog.setInput(prompt.getText());
        llmLog.setOutput(result.getOutput());
        llmLog.setInputToken(result.getInputToken());
        llmLog.setOutputToken(result.getOutputToken());
        llmLog.setCostTime(result.getCostTime());
        llmLogService.insertLLMLog(llmLog);
        return result.getOutput();
    }

    protected abstract LLMResult doCall(String prompt);
}

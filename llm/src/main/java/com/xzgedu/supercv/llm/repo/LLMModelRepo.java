package com.xzgedu.supercv.llm.repo;

import com.xzgedu.supercv.llm.domain.LLMModel;
import com.xzgedu.supercv.llm.mapper.LLMModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LLMModelRepo {

    @Autowired
    private LLMModelMapper llmModelMapper;

    public LLMModel getLLMModelById(long id) {
        return llmModelMapper.getLLMModelById(id);
    }

    public List<LLMModel> getAllLLMModels() {
        return llmModelMapper.getAllLLMModels();
    }

    public List<LLMModel> getEnabledLLMModels() {
        return llmModelMapper.getEnabledLLMModels();
    }

    public boolean insertLLMModel(LLMModel llmModel) {
        return llmModelMapper.insertLLMModel(llmModel) == 1;
    }

    public boolean updateLLMModel(LLMModel llmModel) {
        return llmModelMapper.updateLLMModel(llmModel) == 1;
    }

    public boolean deleteLLMModelById(long id) {
        return llmModelMapper.deleteLLMModelById(id) == 1;
    }
}

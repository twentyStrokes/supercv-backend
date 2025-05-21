package com.xzgedu.supercv.llm.service;

import com.xzgedu.supercv.llm.domain.LLMModel;
import com.xzgedu.supercv.llm.repo.LLMModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LLMModelService {

    @Autowired
    private LLMModelRepo llmModelRepo;

    public LLMModel getLLMModelById(long id) {
        return llmModelRepo.getLLMModelById(id);
    }

    public List<LLMModel> getAllLLMModels() {
        return llmModelRepo.getAllLLMModels();
    }

    public List<LLMModel> getEnabledLLMModels() {
        return llmModelRepo.getEnabledLLMModels();
    }

    public boolean insertLLMModel(LLMModel llmModel) {
        return llmModelRepo.insertLLMModel(llmModel);
    }

    public boolean updateLLMModel(LLMModel llmModel) {
        return llmModelRepo.updateLLMModel(llmModel);
    }

    public boolean deleteLLMModelById(long id) {
        return llmModelRepo.deleteLLMModelById(id);
    }
}

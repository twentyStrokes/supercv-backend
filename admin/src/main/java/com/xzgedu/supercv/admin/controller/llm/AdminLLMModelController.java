package com.xzgedu.supercv.admin.controller.llm;

import com.xzgedu.supercv.common.exception.GenericBizException;
import com.xzgedu.supercv.llm.domain.LLMModel;
import com.xzgedu.supercv.llm.service.LLMModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/llm/model")
@RestController
public class AdminLLMModelController {

    @Autowired
    private LLMModelService llmModelService;

    @GetMapping("/list")
    public List<LLMModel> listAllLLMModels() {
        return llmModelService.getAllLLMModels();
    }

    @PostMapping("/add")
    public LLMModel addLLMModel(@RequestParam("llm_type") int llmType,
                            @RequestParam("model_name") String modelName,
                            @RequestParam(value = "api_key", required = false) String apiKey,
                            @RequestParam(value = "endpoint", required = false) String endpoint,
                            @RequestParam("enabled") boolean enabled) throws GenericBizException {
        LLMModel llmModel = new LLMModel();
        llmModel.setLlmType(llmType);
        llmModel.setModelName(modelName);
        llmModel.setApiKey(apiKey);
        llmModel.setEndpoint(endpoint);
        llmModel.setEnabled(enabled);
        if (!llmModelService.insertLLMModel(llmModel)) {
            throw new GenericBizException("Failed to add LLM model: " + llmModel);
        }
        return llmModel;
    }

    @PostMapping("/update")
    public void updateLLMModel(@RequestParam("id") long id,
                              @RequestParam("llm_type") int llmType,
                              @RequestParam("model_name") String modelName,
                              @RequestParam(value = "api_key", required = false) String apiKey,
                              @RequestParam(value = "endpoint", required = false) String endpoint,
                              @RequestParam("enabled") boolean enabled) throws GenericBizException {
        LLMModel llmModel = new LLMModel();
        llmModel.setId(id);
        llmModel.setLlmType(llmType);
        llmModel.setModelName(modelName);
        llmModel.setApiKey(apiKey);
        llmModel.setEndpoint(endpoint);
        llmModel.setEnabled(enabled);
        if (!llmModelService.updateLLMModel(llmModel)) {
            throw new GenericBizException("Failed to update LLM model: " + llmModel);
        }
    }

    @PostMapping("/delete")
    public void deleteLLMModelById(@RequestParam("id") long id) throws GenericBizException {
        if (!llmModelService.deleteLLMModelById(id)) {
            throw new GenericBizException("Failed to delete LLM model by id: " + id);
        }
    }
}

package com.xzgedu.supercv.resume.controller;

import com.xzgedu.supercv.llm.LLM;
import com.xzgedu.supercv.llm.LLMFactory;
import com.xzgedu.supercv.llm.domain.LLMPrompt;
import com.xzgedu.supercv.llm.domain.LLMPromptBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "简历优化")
@RequestMapping("/v1/resume/optimize")
@RestController
public class ResumeOptimizeController {

    @Autowired
    private LLMFactory llmFactory;

    @Operation(summary = "优化简历模块内容")
    @PostMapping("/module-item-content")
    public String optimizeModuleItemContent(@RequestHeader ("uid") long uid,
                                            @RequestParam("module_name") String moduleName,
                                            @RequestParam("content") String content) {
        LLMPrompt prompt = LLMPromptBuilder.generateOptimizeResumePrompt(moduleName, content);
        LLM llm = llmFactory.getEnabledLLM();
        return llm.call(uid, prompt);
    }
}

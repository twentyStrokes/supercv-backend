package com.xzgedu.supercv.llm;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.xzgedu.supercv.llm.domain.LLMResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QianWen extends AbstractLLM {

    @Override
    public LLMResult doCall(String prompt) {
        try {
            long startTime = System.currentTimeMillis();
            Generation gen = new Generation();
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你是一名程序员简历优化助手.")
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(prompt)
                    .build();
            GenerationParam param = GenerationParam.builder()
                    .apiKey(llmModel.getApiKey())
                    .model(llmModel.getModelName())
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();
            GenerationResult result = gen.call(param);
            LLMResult llmResult = new LLMResult();
            llmResult.setInput(prompt);
            llmResult.setOutput(result.getOutput().getChoices().get(0).getMessage().getContent());
            llmResult.setInputToken(result.getUsage().getInputTokens());
            llmResult.setOutputToken(result.getUsage().getOutputTokens());
            llmResult.setCostTime(System.currentTimeMillis() - startTime);
            return llmResult;
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("错误信息：" + e.getMessage());
            System.out.println("请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code");
            throw new RuntimeException(e);
        }
    }

}

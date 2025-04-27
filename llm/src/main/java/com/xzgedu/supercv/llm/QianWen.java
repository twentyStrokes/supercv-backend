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
import com.xzgedu.supercv.llm.enums.ModelType;
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
                    // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                    .apiKey("sk-a97a45fc6dd6438da345ac1f7407306e")
//                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                    // 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                    //TODO: qwen-plus 太慢了，1000tokens要1分钟，qwen-turbo大于20秒
                    .model(ModelType.QIANWEN_PLUS.getName())
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();
            GenerationResult result = gen.call(param);
            LLMResult llmResult = new LLMResult();
            llmResult.setModelType(ModelType.QIANWEN_PLUS);
            llmResult.setInput(prompt);
            llmResult.setOutput(result.getOutput().getChoices().get(0).getMessage().getContent());
            llmResult.setInputToken(result.getUsage().getInputTokens());
            llmResult.setOutputToken(result.getUsage().getOutputTokens());
            llmResult.setCostTime(System.currentTimeMillis() - startTime);
            return llmResult;
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("错误信息："+e.getMessage());
            System.out.println("请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code");
            throw new RuntimeException(e);
        }
    }

}

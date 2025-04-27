package com.xzgedu.supercv.llm.domain;

import com.xzgedu.supercv.llm.enums.PromptType;

public class LLMPromptBuilder {

    public static LLMPrompt generateText2JsonPrompt(String rawText, String targetJson) {
        LLMPrompt llmPrompt = new LLMPrompt();
        String text = "以下是从简历pdf文件中抽取出来的纯文本内容，将对应信息填充到targetJson中。\n" +
                "注意: 1.输出不需要markdown格式，纯文本即可。\n" +
                "2.输出的json中添加必要的回车换行以及缩进，方便查看\n" +
                "3.如果在简历文本中存在的信息，在targetJson中找不到对应的存储位置，可以参照targetJson的结构，扩展添加在targetJson中\n" +
                "4.对于targetJson中的content字段，从纯文本中解析出来的内容，用<p></p>将每段文字标记出来\n" +
                "简历文本：\n" +
                rawText + "\n" +
                "targetJson:\n" +
                targetJson;
        llmPrompt.setPromptType(PromptType.IMPORT_RESUME);
        llmPrompt.setText(text);
        return llmPrompt;
    }

    public static LLMPrompt generateOptimizeResumePrompt(String moduleName, String content) {
        LLMPrompt llmPrompt = new LLMPrompt();
        String text = "以下文本是某个简历中" + moduleName + "模块的内容，请结合招聘要求，优化此内容，让其更加专业、详细、措辞准确等。\n" +
                "注意：1.保留原有的文本格式。\n" +
                "2.返回的结果只包含优化后的内容\n" +
                "原简历内容：\n" +
                content;
        llmPrompt.setPromptType(PromptType.OPTIMIZE_RESUME);
        llmPrompt.setText(text);
        return llmPrompt;
    }
}

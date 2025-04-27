package com.xzgedu.supercv.llm;

import com.xzgedu.supercv.llm.domain.LLMPrompt;

public interface LLM {
    String call(long uid, LLMPrompt prompt);
}

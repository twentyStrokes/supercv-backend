package com.xzgedu.supercv.llm.enums;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public enum LLMType {
    QIANWEN(1, "阿里千问");

    private final int value;
    private final String name;

    private LLMType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static LLMType of(Integer value) {
        if (value == null) return null;
        for (LLMType modelType : values()) {
            if (modelType.getValue() == value) {
                return modelType;
            }
        }
        return null;
    }

    @Data
    public static final class LLMTypeBo {
        private int value;
        private String name;
    }

    public static List<LLMType.LLMTypeBo> getAllLLMTypeBo() {
        List<LLMType.LLMTypeBo> modelTypeBoList = new ArrayList<>();
        for (LLMType modelType : values()) {
            LLMType.LLMTypeBo modelTypeBo = new LLMType.LLMTypeBo();
            modelTypeBo.setValue(modelType.getValue());
            modelTypeBo.setName(modelType.getName());
            modelTypeBoList.add(modelTypeBo);
        }
        return modelTypeBoList;
    }
}

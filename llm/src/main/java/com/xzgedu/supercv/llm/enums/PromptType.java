package com.xzgedu.supercv.llm.enums;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public enum PromptType {
    IMPORT_RESUME(1, "导入简历"),
    OPTIMIZE_RESUME(2, "优化简历");

    private final int value;
    private final String name;

    private PromptType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static PromptType of(Integer value) {
        if (value == null) return null;
        for (PromptType promptType : PromptType.values()) {
            if (promptType.getValue() == value) {
                return promptType;
            }
        }
        return null;
    }

    @Data
    public static final class PromptTypeBo {
        private int value;
        private String name;
    }

    public static List<PromptTypeBo> getAllPromptTypeBo() {
        List<PromptTypeBo> promptTypeBoList = new ArrayList<>();
        for (PromptType promptType : PromptType.values()) {
            PromptTypeBo promptTypeBo = new PromptTypeBo();
            promptTypeBo.setValue(promptType.getValue());
            promptTypeBo.setName(promptType.getName());
            promptTypeBoList.add(promptTypeBo);
        }
        return promptTypeBoList;
    }
}

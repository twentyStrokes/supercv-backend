package com.xzgedu.supercv.llm.enums;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public enum ModelType {
    QIANWEN_PLUS(11, "qwen-plus"),
    QIANWEN_TURBO(12, "qwen-turbo");

    private final int value;
    private final String name;

    private ModelType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ModelType of(Integer value) {
        if (value == null) return null;
        for (ModelType modelType : values()) {
            if (modelType.getValue() == value) {
                return modelType;
            }
        }
        return null;
    }

    @Data
    public static final class ModelTypeBo {
        private int value;
        private String name;
    }

    public static List<ModelTypeBo> getAllModelTypeBo() {
        List<ModelTypeBo> modelTypeBoList = new ArrayList<>();
        for (ModelType modelType : values()) {
            ModelTypeBo modelTypeBo = new ModelTypeBo();
            modelTypeBo.setValue(modelType.getValue());
            modelTypeBo.setName(modelType.getName());
            modelTypeBoList.add(modelTypeBo);
        }
        return modelTypeBoList;
    }
}

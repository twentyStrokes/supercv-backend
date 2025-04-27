package com.xzgedu.supercv.llm.enums;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public enum SortType {
    BY_CREATE_TIME(1, "按照创建时间排序"),
    BY_COST_TIME(2, "按照耗时排序"),
    BY_INPUT_TOKEN(3, "按照输入大小排序");

    private final int value;
    private final String name;

    private SortType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static SortType of(Integer value) {
        if (value == null) return null;
        for (SortType sortType : values()) {
            if (sortType.getValue() == value) {
                return sortType;
            }
        }
        return null;
    }

    @Data
    public static final class SortTypeBo {
        private int value;
        private String name;
    }

    public static List<SortTypeBo> getAllSortTypeBo() {
        List<SortTypeBo> sortTypeBoList = new ArrayList<>();
        for (SortType sortType : values()) {
            SortTypeBo sortTypeBo = new SortTypeBo();
            sortTypeBo.setValue(sortType.getValue());
            sortTypeBo.setName(sortType.getName());
            sortTypeBoList.add(sortTypeBo);
        }
        return sortTypeBoList;
    }

}

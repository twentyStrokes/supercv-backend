package com.xzgedu.supercv.resume.enums;

public enum Alignment {
    LEFT(1, "左对齐"),
    RIGHT(2, "右对齐"),
    CENTER(3, "居中对齐");

    private final int value;
    private final String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    Alignment(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Alignment getAlignment(int value) {
        switch (value) {
            case 1:
                return LEFT;
            case 2:
                return RIGHT;
            case 3:
                return CENTER;
            default:
                return null;
        }
    }
}

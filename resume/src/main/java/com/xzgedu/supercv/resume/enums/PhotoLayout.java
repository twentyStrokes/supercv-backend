package com.xzgedu.supercv.resume.enums;

public enum PhotoLayout {
    LEFT(1, "居左"),
    RIGHT(2, "居右"),
    ABOVE(3, "居上"),
    BELOW(4, "居下");

    private final int value;
    private final String name;

    PhotoLayout(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static PhotoLayout getLayout(int value) {
        for (PhotoLayout layout : PhotoLayout.values()) {
            if (layout.getValue() == value) {
                return layout;
            }
        }
        return null;
    }
}

package com.xzgedu.supercv.resume.enums;

public enum ResumeFileParsedStatus {
    PARSING(1, "解析中"),
    SUCCESS(2, "解析成功"),
    FAILED(3, "解析失败");

    private final int value;
    private final String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    ResumeFileParsedStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }
}

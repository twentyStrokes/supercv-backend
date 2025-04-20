package com.xzgedu.supercv.resume.enums;

public enum DefaultProfileItem {
    NAME("name", "姓名"),
    TELEPHONE("telephone", "电话"),
    EMAIL("email", "邮箱"),
    WECHAT("wechat", "微信"),
    WORK_YEARS("workYears", "工作年限"),
    WORK_PLACE("workPlace", "工作地点"),
    EXPECT_SALARY("expectSalary", "期望薪资"),
    BLOG("blog", "博客"),
    GITHUB("github", "GitHub");

    private final String key;
    private final String name;

    private DefaultProfileItem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

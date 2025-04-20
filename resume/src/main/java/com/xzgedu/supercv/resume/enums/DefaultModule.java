package com.xzgedu.supercv.resume.enums;

public enum DefaultModule {
    SKILLS("skill", "专业技能"),
    EDUCATIONS("education", "教育经历"),
    JOBS("job", "工作经历"),
    PROJECTS("project", "项目经历"),
    ASSESSMENT("assessment", "个人评价");

    private final String key;
    private final String name;

    DefaultModule(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static DefaultModule keyOf(String key) {
        for (DefaultModule module : DefaultModule.values()) {
            if (module.key.equals(key)) {
                return module;
            }
        }
        return null;
    }
}

package com.xzgedu.supercv.resume.service;

import com.xzgedu.supercv.resume.domain.*;
import com.xzgedu.supercv.resume.domain.Module;
import com.xzgedu.supercv.resume.enums.Alignment;
import com.xzgedu.supercv.resume.enums.DefaultModule;
import com.xzgedu.supercv.resume.enums.DefaultProfileItem;
import com.xzgedu.supercv.resume.enums.PhotoLayout;

public class BlankResumeFactory {
    public static final Resume create(long uid, long templateId, String resumeName) {
        Resume resume = new Resume();
        resume.setUid(uid);
        resume.setName(resumeName);
        resume.setTemplateId(templateId);
        resume.setRawData(new RawData());
        resume.setExtraStyle(new ExtraStyle());

        // default specific setting
        ExtraStyle extraStyle = resume.getExtraStyle();
        extraStyle.setPageMarginHorizontal(30);
        extraStyle.setPageMarginVertical(30);
        extraStyle.setModuleMargin(20);
        extraStyle.setThemeColor("#000000");
        extraStyle.setFontFamily("Microsoft YaHei");
        extraStyle.setContentFontSize(15);
        extraStyle.setContentLineHeight(2.0);

        // default profile
        Profile profile = new Profile();
        profile.setTitle("基本信息");
        profile.setPhotoUrl("https://static.supercv.cn/image/default_profile_photo.png");
        profile.setPhotoLayout(PhotoLayout.RIGHT.getValue());
        profile.setPhotoEnabled(true);
        profile.setItemLayout(Alignment.CENTER.getValue());
        profile.setEnabled(true);
        for (DefaultProfileItem defaultItem : DefaultProfileItem.values()) {
            KeyValuePair keyValuePair = new KeyValuePair();
            keyValuePair.setKey(defaultItem.getKey());
            keyValuePair.setLabel(defaultItem.getName());
            profile.getItems().add(keyValuePair);
        }
        resume.getRawData().setProfile(profile);

        // default modules
        for (DefaultModule defaultModule : DefaultModule.values()) {
            Module module = new Module();
            module.setKey(defaultModule.getKey());
            module.setTitle(defaultModule.getName());
            module.setDefaultModule(true);
            module.setEnabled(true);
            module.getItems().add(createBlankResumeModuleItem(defaultModule)); //添加一条moduleItem数据
            resume.getRawData().getModules().add(module);
        }

        return resume;
    }

    public static final ModuleItem createBlankResumeModuleItem(DefaultModule moduleType) {
        ModuleItem item = new ModuleItem();
        item.setTitleEnabled(true);
        item.setTitleMajorName("主标题");
        item.setTitleMinorName("次标题");
        item.setTitleOtherName("其他");
        item.setTitleDateName("时间");
        item.setContentHint("在此输入内容");
        item.setContent("");

        // SKILL、EDUCATION、JOB、PROJECT、ASSESSMENT
        if (DefaultModule.SKILLS.equals(moduleType)) {
            item.setTitleEnabled(false);
            item.setContentHint("分条目输入掌握的专业技能");
        } else if (DefaultModule.EDUCATIONS.equals(moduleType)) {
            item.setTitleEnabled(true);
            item.setTitleMajorName("学校");
            item.setTitleMinorName("专业");
            item.setTitleOtherName("学历");
            item.setTitleDateName("时间");
            item.setContentHint("输入GPA、奖学金情况、证书或者所学课程等信息，当然也可以不填写");
        } else if (DefaultModule.JOBS.equals(moduleType)) {
            item.setTitleEnabled(true);
            item.setTitleMajorName("公司");
            item.setTitleMinorName("部门");
            item.setTitleOtherName("职位");
            item.setTitleDateName("时间");
            item.setContentHint("输入公司介绍或者职责说明");
        } else if (DefaultModule.PROJECTS.equals(moduleType)) {
            item.setTitleEnabled(true);
            item.setTitleMajorName("项目名称");
            item.setTitleMinorName("职位");
            item.setTitleOtherName("其他");
            item.setTitleDateName("时间");
            item.setContentHint("输入项目核心功能、核心技术点、技术挑战等");
        } else if (DefaultModule.ASSESSMENT.equals(moduleType)) {
            item.setTitleEnabled(false);
            item.setContentHint("输入个人评价");
        }

        return item;
    }

}

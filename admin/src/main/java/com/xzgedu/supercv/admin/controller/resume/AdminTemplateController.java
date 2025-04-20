package com.xzgedu.supercv.admin.controller.resume;

import com.xzgedu.supercv.common.exception.GenericBizException;
import com.xzgedu.supercv.resume.domain.Template;
import com.xzgedu.supercv.resume.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "简历模板管理")
@RequestMapping("/admin/resume/template")
@RestController
public class AdminTemplateController {

    @Autowired
    private TemplateService resumeTemplateService;

    @Operation(summary = "创建简历模板")
    @PostMapping("/add")
    public Template addTemplate(@RequestParam("name") String name,
                                @RequestParam("page_frame") String pageFrame,
                                @RequestParam(value = "page_style", required = false) String pageStyle,
                                @RequestParam("is_public") boolean isPublic) throws GenericBizException {
        Template template = new Template();
        template.setName(name);
        template.setPageFrame(pageFrame);
        template.setPageStyle(pageStyle);
        template.setPublic(isPublic);
        if (!resumeTemplateService.addTemplate(template)) {
            throw new GenericBizException("Failed to add resume template: " + template);
        }
        return template;
    }

    @Operation(summary = "更新简历模板")
    @PostMapping("/update")
    public void updateTemplate(@RequestParam("template_id") long id,
                               @RequestParam("name") String name,
                               @RequestParam("page_frame") String pageFrame,
                               @RequestParam(value = "page_style", required = false) String pageStyle,
                               @RequestParam("is_public") boolean isPublic)
            throws GenericBizException {
        Template template = new Template();
        template.setId(id);
        template.setName(name);
        template.setPageFrame(pageFrame);
        template.setPageStyle(pageStyle);
        template.setPublic(isPublic);
        if (!resumeTemplateService.updateTemplate(template)) {
            throw new GenericBizException("Failed to update resume template: " + template);
        }
    }

    @PostMapping("/update/thumbnail-url")
    public void updateTemplateThumbnailUrl(@RequestParam("template_id") long id,
                                           @RequestParam(value = "thumbnail_url", required = false) String thumbnailUrl)
            throws GenericBizException {
        if (!resumeTemplateService.updateTemplateThumbnailUrl(id, thumbnailUrl)) {
            throw new GenericBizException("Failed to update thumbnail url for template: " + id + "; " + thumbnailUrl);
        }
    }

    @Operation(summary = "更新示例简历")
    @PostMapping("/update/demo-resume")
    public void updateTemplateDemoResumeId(@RequestParam("template_id") long id,
                                           @RequestParam(value = "demo_resume_id", required = false) Long demoResumeId)
            throws GenericBizException {
        if (!resumeTemplateService.updateDemoResumeId(id, demoResumeId)) {
            throw new GenericBizException("Failed to add demo resume for template: " + id + "; " + demoResumeId);
        }
    }

    @Operation(summary = "删除简历模板")
    @PostMapping("/delete")
    public void deleteTemplate(@RequestParam("template_id") long resumeTemplateId) throws GenericBizException {
        if (!resumeTemplateService.deleteTemplate(resumeTemplateId)) {
            throw new GenericBizException("Failed to delete resume template: " + resumeTemplateId);
        }
    }
}

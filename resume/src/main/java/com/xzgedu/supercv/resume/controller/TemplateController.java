package com.xzgedu.supercv.resume.controller;

import com.xzgedu.supercv.resume.domain.Template;
import com.xzgedu.supercv.resume.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "简历模板")
@RestController
@RequestMapping("/v1/resume/template")
public class TemplateController {

    @Autowired
    private TemplateService resumeTemplateService;

    @Operation(summary = "查询模板详情")
    @GetMapping("/detail")
    public Template getTemplateDetail(@RequestParam("id") long id) {
        return resumeTemplateService.getTemplateById(id);
    }

    @Operation(summary = "分页查询模板")
    @GetMapping("/list")
    public Map<String, Object> listTemplates(@RequestParam(value = "is_public", required = false) Boolean isPublic,
                                             @RequestParam("page_no") int pageNo,
                                             @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        int count = resumeTemplateService.countTemplates(isPublic);
        List<Template> resumeTemplates =
                resumeTemplateService.getTemplatesPagination(isPublic, limitOffset, limitSize);
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", count);
        resp.put("templates", resumeTemplates);
        return resp;
    }

    @Operation(summary = "分页查询模板(mock)，用于前端开发首页时临时调用")
    @GetMapping("/list/mock")
    public Map<String, Object> listTemplatesMock(@RequestParam("page_no") int pageNo,
                                                  @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        List<Template> mockTemplates = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Template template = new Template();
            template.setId(1625L + i);
            template.setName("简历模板-" + i);
            template.setPageStyle("css_" + i);
            template.setThumbnailUrl("https://static.supercv.cn/image/defaut_resume_thumbnail.png");
            mockTemplates.add(template);
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", 11);
        if (limitSize < 11) {
            resp.put("templates", mockTemplates.subList(limitOffset, limitSize));
        } else {
            resp.put("templates", mockTemplates);
        }
        return resp;
    }
}

package com.xzgedu.supercv.resume.controller;

import com.xzgedu.supercv.common.exception.GenericBizException;
import com.xzgedu.supercv.common.exception.ResumeFileParsedFailedException;
import com.xzgedu.supercv.common.exception.ResumeTemplateNotFoundException;
import com.xzgedu.supercv.resume.domain.Resume;
import com.xzgedu.supercv.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "简历")
@RequestMapping("/v1/resume/")
@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Operation(summary = "获取用户创建的所有简历（非detail）")
    @GetMapping("/list/mine")
    public Map<String, Object> getMyResumes(@RequestHeader("uid") long uid,
                                            @RequestParam("page_no") int pageNo,
                                            @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        int count = resumeService.countResumesByUid(uid);
        List<Resume> resumes = resumeService.getResumesByUid(uid, limitOffset, limitSize);
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", count);
        resp.put("resumes", resumes);
        return resp;
    }

    @Operation(summary = "获取简历详情")
    @GetMapping("/detail")
    public Resume getResumeDetail(@RequestParam("resume_id") long resumeId) {
        return resumeService.getResumeById(resumeId);
    }

    @Operation(summary = "删除简历")
    @PostMapping("/delete")
    public void deleteResume(@RequestParam("resume_id") long resumeId) {
        resumeService.deleteResume(resumeId);
    }

    @Operation(summary = "更新简历")
    @PostMapping("/update")
    public boolean updateResume(@RequestParam("resume_id") long resumeId,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "template_id", required = false) Long templateId,
                                @RequestParam(value = "raw_data_json", required = false) String rawDataJson,
                                @RequestParam(value = "extra_style_json", required = false) String extraStyleJson,
                                @RequestParam(value = "is_public", required = false) Boolean isPublic) {
        Resume resume = resumeService.getResumeById(resumeId);
        if (StringUtils.isNotBlank(name)) {
            resume.setName(name);
        }
        if (templateId != null) {
            resume.setTemplateId(templateId);
        }
        if (rawDataJson != null) {
            resume.setRawDataJson(rawDataJson);
        }
        if (extraStyleJson != null) {
            resume.setExtraStyleJson(extraStyleJson);
        }
        if (isPublic != null) {
            resume.setPublic(isPublic);
        }
        return resumeService.updateResume(resume);
    }

    @Operation(summary = "创建一个包含空白数据的简历，简历中有基本信息和默认模块")
    @PostMapping("/create-blank-resume")
    public Resume createBlankResume(@RequestHeader("uid") long uid,
                                    @RequestParam("template_id") long templateId,
                                    @RequestParam(value = "resume_name", required = false) String resumeName)
            throws GenericBizException {
        return resumeService.createBlankResume(uid, templateId, resumeName);
    }

    @Operation(summary = "拷贝一份简历")
    @PostMapping("/create-from-copying")
    public Resume createResumeFromCopying(@RequestHeader("uid") long uid,
                                          @RequestParam("resume_id") long resumeId,
                                          @RequestParam(value = "resume_name", required = false) String newResumeName)
            throws GenericBizException {
        return resumeService.copyResume(uid, resumeId, newResumeName);
    }

    @Operation(summary = "根据模板，以及用户上传的简历文件，创建一个简历")
    @PostMapping("/create-from-file")
    public Resume createResumeFromFile(@RequestHeader("uid") long uid,
                                       @RequestParam("template_id") long templateId,
                                       @RequestParam("resume_file_url") String resumeFileUrl,
                                       @RequestParam(value = "resume_name", required = false) String resumeName)
            throws ResumeFileParsedFailedException, GenericBizException {
        return resumeService.createResumeFromFile(uid, templateId, resumeFileUrl, resumeName);
    }
}

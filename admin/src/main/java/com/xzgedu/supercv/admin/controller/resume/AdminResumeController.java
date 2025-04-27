package com.xzgedu.supercv.admin.controller.resume;

import com.xzgedu.supercv.common.exception.GenericBizException;
import com.xzgedu.supercv.resume.domain.Resume;
import com.xzgedu.supercv.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "简历管理")
@RequestMapping("/admin/resume")
@RestController
public class AdminResumeController {

    @Autowired
    private ResumeService resumeService;

    @Operation(summary = "分页查询简历")
    @GetMapping("/list")
    public Map<String, Object> listResumes(@RequestParam("page_no") int pageNo,
                                           @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        List<Resume> resumes = resumeService.getResumesPagination(limitOffset, limitSize);
        int count = resumeService.countResumes();
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", count);
        resp.put("resumes", resumes);
        return resp;
    }

    @Operation(summary = "分页查询简历(按用户)")
    @GetMapping("/list/user")
    public Map<String, Object> listResumesByUid(@RequestParam("filter_uid") long uid,
                                                @RequestParam("page_no") int pageNo,
                                                @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        List<Resume> resumes = resumeService.getResumesByUid(uid, limitOffset, limitSize);
        int count = resumeService.countResumesByUid(uid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", count);
        resp.put("resumes", resumes);
        return resp;
    }

    @Operation(summary = "删除简历")
    @PostMapping("/delete")
    public void deleteResume(@RequestParam("resume_id") long resumeId) throws GenericBizException {
        if (!resumeService.deleteResume(resumeId)) {
            throw new GenericBizException("Failed to delete resume: " + resumeId);
        }
    }

    @Operation(summary = "更新简历是否公开")
    @PostMapping("/update/public")
    public void updateResumePublic(@RequestParam("resume_id") long resumeId,
                                   @RequestParam("is_public") boolean isPublic) throws GenericBizException {
        Resume resume = resumeService.getResumeById(resumeId);
        resume.setPublic(isPublic);
        if (!resumeService.updateResume(resume)) {
            throw new GenericBizException("Failed to update resume: " + resumeId);
        }
    }
}

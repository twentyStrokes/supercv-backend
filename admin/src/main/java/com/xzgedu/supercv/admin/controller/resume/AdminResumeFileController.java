package com.xzgedu.supercv.admin.controller.resume;

import com.xzgedu.supercv.resume.domain.Resume;
import com.xzgedu.supercv.resume.domain.ResumeFile;
import com.xzgedu.supercv.resume.service.ResumeFileService;
import com.xzgedu.supercv.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "简历文件管理")
@RequestMapping("/admin/resume/file")
@RestController
public class AdminResumeFileController {

    @Autowired
    private ResumeFileService resumeFileService;

    @Autowired
    private ResumeService resumeService;

    @Operation(summary = "分页查询简历文件")
    @GetMapping("/list")
    public Map<String, Object> getResumeFiles(@RequestParam(value = "filter_uid", required = false) Long filterUid,
                              @RequestParam("page_no") int pageNo,
                              @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        List<ResumeFile> files = null;
        int totalCount = 0;
        if (filterUid == null) {
            files = resumeFileService.getResumeFiles(limitOffset, limitSize);
            totalCount = resumeFileService.countResumeFiles();
        } else {
            files = resumeFileService.getResumeFilesByUid(filterUid, limitOffset, limitSize);
            totalCount = resumeFileService.countResumeFilesByUid(filterUid);
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("resumeFiles", files);
        resp.put("count", totalCount);
        return resp;
    }

    @Operation(summary = "获取简历文件详情")
    @GetMapping("/detail")
    public Map<String, Object> getResumeFileById(@RequestParam("resume_file_id") long resumeFileId) {
        ResumeFile resumeFile = resumeFileService.getResumeFileById(resumeFileId);
        Resume resume = resumeService.getResumeByFileId(resumeFileId);
        resume.setRawDataJson(resumeFile.getParsedJson());
        Map<String, Object> resp = new HashMap<>();
        resp.put("resumeFile", resumeFile);
        resp.put("resume", resume);
        return resp;
    }
}

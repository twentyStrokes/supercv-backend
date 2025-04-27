package com.xzgedu.supercv.resume.controller;

import com.xzgedu.supercv.resume.domain.ResumeFile;
import com.xzgedu.supercv.resume.enums.ResumeFileParsedStatus;
import com.xzgedu.supercv.resume.service.ResumeFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "简历文件")
@Slf4j
@RequestMapping("/v1/resume/file")
@RestController
public class ResumeFileController {

    @Autowired
    private ResumeFileService resumeFileService;

    @Operation(summary = "解析简历文件")
    @PostMapping("/parse")
    public void parseResumeFile(@RequestHeader("uid") long uid,
                                @RequestParam("resume_file_url") String resumeFileUrl) {
        ResumeFile resumeFile = new ResumeFile();
        resumeFile.setUid(uid);
        resumeFile.setFileUrl(resumeFileUrl);
        resumeFile.setParsedStatus(ResumeFileParsedStatus.PARSING.getValue());
        resumeFileService.addResumeFile(resumeFile);
        resumeFileService.asyncParseResume(uid, resumeFile);
    }

    @Operation(summary = "获取简历文件解析状态")
    @GetMapping("/parse/status")
    public int getResumeFileParsedStatus(@RequestParam("resume_file_url") String resumeFileUrl) {
        Integer status = resumeFileService.getParsedStatus(resumeFileUrl);
        if (status == null) {
            return ResumeFileParsedStatus.PARSING.getValue();
        }
        return status;
    }
}

package com.xzgedu.supercv.resume.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzgedu.supercv.llm.LLMFactory;
import com.xzgedu.supercv.llm.PdfParser;
import com.xzgedu.supercv.llm.domain.LLMPrompt;
import com.xzgedu.supercv.llm.domain.LLMPromptBuilder;
import com.xzgedu.supercv.resume.domain.RawData;
import com.xzgedu.supercv.resume.domain.ResumeFile;
import com.xzgedu.supercv.resume.enums.ResumeFileParsedStatus;
import com.xzgedu.supercv.resume.repo.ResumeFileRepo;
import com.xzgedu.supercv.user.domain.User;
import com.xzgedu.supercv.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ResumeFileService {

    @Autowired
    private ResumeFileRepo resumeFileRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private LLMFactory llmFactory;

    public ResumeFile getResumeFileById(long id) {
        return resumeFileRepo.getResumeFileById(id);
    }

    public ResumeFile getResumeFileByFileUrl(String fileUrl) {
        return resumeFileRepo.getResumeFileByFileUrl(fileUrl);
    }

    public List<ResumeFile> getResumeFilesByUid(long uid, int limitOffset, int limitSize) {
        return fillUserInfos(resumeFileRepo.getResumeFilesByUid(uid, limitOffset, limitSize));
    }

    public int countResumeFilesByUid(long uid) {
        return resumeFileRepo.countResumeFilesByUid(uid);
    }

    public List<ResumeFile> getResumeFiles(int limitOffset, int limitSize) {
        return fillUserInfos(resumeFileRepo.getResumeFiles(limitOffset, limitSize));
    }

    public int countResumeFiles() {
        return resumeFileRepo.countResumeFiles();
    }

    public Integer getParsedStatus(String fileUrl) {
        return resumeFileRepo.getParsedStatus(fileUrl);
    }

    public boolean addResumeFile(ResumeFile resumeFile) {
        return resumeFileRepo.insertResumeFile(resumeFile);
    }

    public boolean updateParsedResult(ResumeFile resumeFile) {
        return resumeFileRepo.updateParsedResult(resumeFile);
    }

    @Async("asyncTaskExecutor")
    public void asyncParseResume(long uid, ResumeFile resumeFile) {
        try {
            // 1.下载解析PDF
            String parsedText = PdfParser.parsePdfFromUrl(resumeFile.getFileUrl());
            resumeFile.setParsedText(parsedText);

            // 2.调用大模型转换JSON
            RawData blankResumeData = ResumeFactory.createDefaultRawData();
            ObjectMapper objectMapper = new ObjectMapper();
            String blankResumeDataJson = objectMapper.writeValueAsString(blankResumeData);
            LLMPrompt prompt = LLMPromptBuilder.generateText2JsonPrompt(parsedText, blankResumeDataJson);
            String parsedJson = llmFactory.getEnabledLLM().call(uid, prompt);
            resumeFile.setParsedJson(parsedJson);

            // 3.校验JSON格式
            objectMapper.readValue(parsedJson, RawData.class);

            // 4.更新成功状态
            resumeFile.setParsedStatus(ResumeFileParsedStatus.SUCCESS.getValue());
            resumeFile.setParsedJsonValid(true);
        } catch (Exception e) {
            // 异常处理
            resumeFile.setParsedStatus(ResumeFileParsedStatus.FAILED.getValue());
            resumeFile.setParsedJsonValid(false);
            resumeFile.setParsedErrorMsg(e.getMessage());
            log.warn("Failed to parse resume file: " + resumeFile.getFileUrl(), e);
        } finally {
            updateParsedResult(resumeFile); // 确保最终更新状态
        }
    }

    private List<ResumeFile> fillUserInfos(List<ResumeFile> files) {
        if (files == null || files.isEmpty()) return files;
        Set<Long> uidSet = new HashSet<>();
        for (ResumeFile file : files) {
            uidSet.add(file.getUid());
        }
        List<Long> uids = new ArrayList<>(uidSet);
        List<User> users = userService.getUsersByUids(uids);
        Map<Long, User> userMap = new HashMap<>();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }

        for (ResumeFile file : files) {
            User user = userMap.get(file.getUid());
            if (user != null) {
                file.setNickName(user.getNickName());
                file.setHeadImgUrl(user.getHeadImgUrl());
            }
        }

        return files;
    }
}

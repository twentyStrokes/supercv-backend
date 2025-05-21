package com.xzgedu.supercv.resume.service;

import com.xzgedu.supercv.common.exception.GenericBizException;
import com.xzgedu.supercv.common.exception.ResumeFileParsedFailedException;
import com.xzgedu.supercv.resume.domain.Resume;
import com.xzgedu.supercv.resume.domain.ResumeFile;
import com.xzgedu.supercv.resume.domain.Template;
import com.xzgedu.supercv.resume.enums.ResumeFileParsedStatus;
import com.xzgedu.supercv.resume.repo.ResumeFileRepo;
import com.xzgedu.supercv.resume.repo.ResumeRepo;
import com.xzgedu.supercv.resume.repo.TemplateRepo;
import com.xzgedu.supercv.user.domain.User;
import com.xzgedu.supercv.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class ResumeService {

    @Autowired
    private ResumeRepo resumeRepo;

    @Autowired
    private ResumeFileRepo resumeFileRepo;

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    private UserService userService;

    public Resume getResumeById(long id) {
        return fillTemplate(resumeRepo.getResumeById(id));
    }

    public Resume getResumeByFileId(long fileId) {
        return fillTemplate(resumeRepo.getResumeByFileId(fileId));
    }

    public List<Resume> getResumesByUid(long uid, int limitOffset, int limitSize) {
        return fillUserInfos(fillTemplates(resumeRepo.selectResumesByUid(uid, limitOffset, limitSize)));
    }

    public int countResumesByUid(long uid) {
        return resumeRepo.countResumesByUid(uid);
    }

    public List<Resume> getResumesPagination(int limitOffset, int limitSize) {
        return fillUserInfos(fillTemplates(resumeRepo.selectResumesPagination(limitOffset, limitSize)));
    }

    public int countResumes() {
        return resumeRepo.countResumes();
    }

    public boolean deleteResume(Long id) {
        return resumeRepo.deleteResume(id);
    }

    public boolean insertResume(Resume resume) {
        return resumeRepo.insertResume(resume);
    }

    public boolean updateResume(Resume resume) {
        return resumeRepo.updateResume(resume);
    }

    public List<Resume> fillUserInfos(List<Resume> resumes) {
        if (resumes == null || resumes.isEmpty()) return resumes;

        Set<Long> uidSet = new HashSet<>();
        for (Resume resume : resumes) {
            uidSet.add(resume.getUid());
        }
        if (!uidSet.isEmpty()) {
            List<Long> uids = new ArrayList<>(uidSet);
            List<User> users = userService.getUsersByUids(uids);
            Map<Long, User> userMap = new HashMap<>();
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
            for (Resume resume : resumes) {
                User user = userMap.get(resume.getUid());
                if (user != null) {
                    resume.setNickName(user.getNickName());
                    resume.setHeadImgUrl(user.getHeadImgUrl());
                }
            }
        }

        return resumes;
    }

    public Resume fillTemplate(Resume resume) {
        if (resume == null) return null;
        List<Resume> resumes = new ArrayList<>();
        resumes.add(resume);
        return fillTemplates(resumes).get(0);
    }

    public List<Resume> fillTemplates(List<Resume> resumes) {
        if (resumes == null || resumes.isEmpty()) return resumes;
        Set<Long> templateIdSet = new HashSet<>();
        for (Resume resume : resumes) {
            templateIdSet.add(resume.getTemplateId());
        }
        Map<Long, Template> templateMap = new HashMap<>();
        if (!templateIdSet.isEmpty()) {
            List<Long> templateIds = new ArrayList<>(templateIdSet);
            List<Template> templates = templateRepo.getTemplatesByIds(templateIds);
            for (Template template : templates) {
                templateMap.put(template.getId(), template);
            }
        }

        for (Resume resume : resumes) {
            resume.setTemplate(templateMap.get(resume.getTemplateId()));
        }
        return resumes;
    }

    /**
     * 创建一个数据是空白的简历，但简历的基本模块是包含的
     */
    @Transactional(rollbackFor = Exception.class)
    public Resume createBlankResume(long uid, long templateId, String newResumeName) throws GenericBizException {
        if (StringUtils.isBlank(newResumeName)) {
            newResumeName = "未命名简历";
        }
        Resume resume = ResumeFactory.create(uid, templateId, newResumeName);
        Template template = templateRepo.getTemplateById(templateId);
        if (template.getDemoResumeId() != null) {
            Resume demoResume = getResumeById(template.getDemoResumeId());
            resume.setExtraStyle(demoResume.getExtraStyle());
        }
        if (!resumeRepo.insertResume(resume)) {
            throw new GenericBizException("Failed to insert resume: " + resume);
        }
        return getResumeById(resume.getId());
    }

    /**
     * 拷贝简历
     */
    @Transactional(rollbackFor = Exception.class)
    public Resume copyResume(long uid, long copiedResumeId, String newResumeName) throws GenericBizException {
        Resume resume = getResumeById(copiedResumeId);
        resume.setUid(uid);
        if (StringUtils.isBlank(newResumeName)) {
            newResumeName = resume.getName() + "_副本";
        }
        resume.setName(newResumeName);
        if (!resumeRepo.insertResume(resume)) {
            throw new GenericBizException("Failed to insert resume: " + resume);
        }
        return getResumeById(resume.getId());
    }

    /**
     * 根据模板，以及用户上传的简历文件，创建一个简历
     */
    @Transactional(rollbackFor = Exception.class)
    public Resume createResumeFromFile(long uid, long templateId, String resumeFileUrl, String newResumeName)
            throws ResumeFileParsedFailedException, GenericBizException {
        ResumeFile resumeFile = resumeFileRepo.getResumeFileByFileUrl(resumeFileUrl);
        if (resumeFile.getParsedJsonValid() != true
                || resumeFile.getParsedStatus() == ResumeFileParsedStatus.FAILED.getValue()) {
            throw new ResumeFileParsedFailedException("Failed to parse resume file: " + resumeFile.getId());
        }
        if (resumeFile.getParsedStatus() == ResumeFileParsedStatus.PARSING.getValue()) {
            throw new GenericBizException("Resume file is parsing: " + resumeFile.getId());
        }

        Resume resume = new Resume();
        resume.setUid(uid);
        if (StringUtils.isBlank(newResumeName)) {
            newResumeName = "未命名简历";
        }
        resume.setName(newResumeName);
        resume.setTemplateId(templateId);
        resume.setFileId(resumeFile.getId());
        resume.setFileUrl(resumeFile.getFileUrl());
        Template template = templateRepo.getTemplateById(templateId);
        if (template.getDemoResumeId() != null) {
            Resume demoResume = getResumeById(template.getDemoResumeId());
            resume.setExtraStyle(demoResume.getExtraStyle());
        } else {
            resume.setExtraStyle(ResumeFactory.createDefaultExtraStyle());
        }

        resume.setRawDataJson(resumeFile.getParsedJson());
        resume.setPublic(false);
        if (!resumeRepo.insertResume(resume)) {
            throw new GenericBizException("Failed to insert resume: " + resume);
        }

        return getResumeById(resume.getId());
    }
}
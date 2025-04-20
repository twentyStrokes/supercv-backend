package com.xzgedu.supercv.resume.service;

import com.xzgedu.supercv.resume.domain.Template;
import com.xzgedu.supercv.resume.repo.TemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepo resumeTemplateRepo;

    public boolean addTemplate(Template template) {
        return resumeTemplateRepo.addTemplate(template);
    }

    public boolean deleteTemplate(long id) {
        return resumeTemplateRepo.deleteTemplate(id);
    }

    public boolean updateTemplate(Template template) {
        return resumeTemplateRepo.updateTemplate(template);
    }


    public boolean updateTemplateThumbnailUrl(long id, String thumbnailUrl) {
        return resumeTemplateRepo.updateTemplateThumbnailUrl(id, thumbnailUrl);
    }

    public boolean updateDemoResumeId(long id, Long demoResumeId) {
        return resumeTemplateRepo.updateDemoResumeId(id, demoResumeId);
    }

    public List<Template> getTemplatesPagination(Boolean isPublic, int limitOffset, int limitSize) {
        return resumeTemplateRepo.getTemplatesPagination(isPublic, limitOffset, limitSize);
    }

    public int countTemplates(Boolean isPublic) {
        return resumeTemplateRepo.countTemplates(isPublic);
    }

    public Template getTemplateById(long id) {
        return resumeTemplateRepo.getTemplateById(id);
    }

    public List<Template> getTemplatesByIds(List<Long> ids) {
        return resumeTemplateRepo.getTemplatesByIds(ids);
    }
}

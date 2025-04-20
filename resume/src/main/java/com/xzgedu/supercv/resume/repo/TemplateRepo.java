package com.xzgedu.supercv.resume.repo;

import com.xzgedu.supercv.resume.domain.Template;
import com.xzgedu.supercv.resume.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemplateRepo {

    @Autowired
    private TemplateMapper resumeTemplateMapper;

    public boolean addTemplate(Template template) {
        return resumeTemplateMapper.insertTemplate(template) == 1;
    }

    public boolean deleteTemplate(long id) {
        return resumeTemplateMapper.deleteTemplate(id) == 1;
    }

    public boolean updateTemplate(Template template) {
        return resumeTemplateMapper.updateTemplate(template) == 1;
    }

    public boolean updateTemplateThumbnailUrl(long id, String thumbnailUrl) {
        return resumeTemplateMapper.updateTemplateThumbnailUrl(id, thumbnailUrl) == 1;
    }

    public boolean updateDemoResumeId(long id, Long demoResumeId) {
        return resumeTemplateMapper.updateDemoResumeId(id, demoResumeId) == 1;
    }

    public List<Template> getTemplatesPagination(Boolean isPublic ,int limitOffset, int limitSize) {
        return resumeTemplateMapper.selectTemplatesPagination(isPublic, limitOffset, limitSize);
    }

    public int countTemplates(Boolean isPublic) {
        return resumeTemplateMapper.countTemplates(isPublic);
    }

    public Template getTemplateById(long id) {
        return resumeTemplateMapper.selectTemplateById(id);
    }

    public List<Template> getTemplatesByIds(List<Long> ids) {
        return resumeTemplateMapper.selectTemplatesByIds(ids);
    }
}

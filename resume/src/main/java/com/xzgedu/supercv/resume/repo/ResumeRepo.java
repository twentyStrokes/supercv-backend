package com.xzgedu.supercv.resume.repo;

import com.xzgedu.supercv.resume.domain.Resume;
import com.xzgedu.supercv.resume.mapper.ResumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResumeRepo {

    @Autowired
    private ResumeMapper resumeMapper;

    public Resume getResumeById(long id) {
        return resumeMapper.getResumeById(id);
    }

    public Resume getResumeByFileId(long fileId) {
        return resumeMapper.getResumeByFileId(fileId);
    }

    public List<Resume> selectResumesByUid(long uid, int limitOffset, int limitSize) {
        return resumeMapper.selectResumesByUid(uid, limitOffset, limitSize);
    }

    public int countResumesByUid(long uid) {
        return resumeMapper.countResumesByUid(uid);
    }

    public List<Resume> selectResumesPagination(int limitOffset, int limitSize) {
        return resumeMapper.selectResumesPagination(limitOffset, limitSize);
    }

    public int countResumes() {
        return resumeMapper.countResumes();
    }

    public boolean deleteResume(Long id) {
        return resumeMapper.deleteResume(id) == 1;
    }

    public boolean insertResume(Resume resume) {
        return resumeMapper.insertResume(resume) == 1;
    }

    public boolean updateResume(Resume resume) {
        return resumeMapper.updateResume(resume) == 1;
    }
}

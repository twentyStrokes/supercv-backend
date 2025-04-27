package com.xzgedu.supercv.resume.repo;

import com.xzgedu.supercv.resume.domain.ResumeFile;
import com.xzgedu.supercv.resume.mapper.ResumeFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResumeFileRepo {

    @Autowired
    private ResumeFileMapper resumeFileMapper;

    public ResumeFile getResumeFileById(long id) {
        return resumeFileMapper.getResumeFileById(id);
    }

    public ResumeFile getResumeFileByFileUrl(String fileUrl) {
        return resumeFileMapper.getResumeFileByFileUrl(fileUrl);
    }

    public List<ResumeFile> getResumeFilesByUid(long uid, int limitOffset, int limitSize) {
        return resumeFileMapper.getResumeFilesByUid(uid, limitOffset, limitSize);
    }

    public int countResumeFilesByUid(long uid) {
        return resumeFileMapper.countResumeFilesByUid(uid);
    }

    public List<ResumeFile> getResumeFiles(int limitOffset, int limitSize) {
        return resumeFileMapper.getResumeFiles(limitOffset, limitSize);
    }

    public int countResumeFiles() {
        return resumeFileMapper.countResumeFiles();
    }

    public Integer getParsedStatus(String fileUrl) {
        return resumeFileMapper.getParsedStatus(fileUrl);
    }

    public boolean insertResumeFile(ResumeFile resumeFile) {
        return resumeFileMapper.insertResumeFile(resumeFile) == 1;
    }

    public boolean updateParsedResult(ResumeFile resumeFile) {
        return resumeFileMapper.updateParsedResult(resumeFile) == 1;
    }
}

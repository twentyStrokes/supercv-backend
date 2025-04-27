package com.xzgedu.supercv.llm.service;

import com.xzgedu.supercv.llm.domain.LLMLog;
import com.xzgedu.supercv.llm.domain.LLMLogFilter;
import com.xzgedu.supercv.llm.enums.SortType;
import com.xzgedu.supercv.llm.repo.LLMLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LLMLogService {

    @Autowired
    private LLMLogRepo llmLogRepo;

    public LLMLog selectLLMLogById(long id) {
        return llmLogRepo.selectLLMLogById(id);
    }

    public List<LLMLog> selectLLMLogs(LLMLogFilter llmLogFilter, SortType sortType, int limitOffset, int limitSize) {
        return llmLogRepo.selectLLMLogs(llmLogFilter, sortType, limitOffset, limitSize);
    }

    public int countLLMLogs(LLMLogFilter llmLogFilter) {
        return llmLogRepo.countLLMLogs(llmLogFilter);
    }

    public boolean insertLLMLog(LLMLog llmLog) {
        return llmLogRepo.insertLLMLog(llmLog);
    }
}

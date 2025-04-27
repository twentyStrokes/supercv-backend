package com.xzgedu.supercv.llm.repo;

import com.xzgedu.supercv.llm.domain.LLMLog;
import com.xzgedu.supercv.llm.domain.LLMLogFilter;
import com.xzgedu.supercv.llm.enums.SortType;
import com.xzgedu.supercv.llm.mapper.LLMLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LLMLogRepo {

    @Autowired
    private LLMLogMapper llmLogMapper;

    public LLMLog selectLLMLogById(long id) {
        return llmLogMapper.selectLLMLogById(id);
    }

    public List<LLMLog> selectLLMLogs(LLMLogFilter llmLogFilter, SortType sortType, int limitOffset, int limitSize) {
        return llmLogMapper.selectLLMLogs(llmLogFilter, sortType, limitOffset, limitSize);
    }

    public int countLLMLogs(LLMLogFilter llmLogFilter) {
        return llmLogMapper.countLLMLogs(llmLogFilter);
    }

    public boolean insertLLMLog(LLMLog llmLog) {
        return llmLogMapper.insertLLMLog(llmLog) == 1;
    }
}

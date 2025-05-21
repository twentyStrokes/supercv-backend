package com.xzgedu.supercv.llm.mapper;

import com.xzgedu.supercv.llm.domain.LLMLogFilter;
import com.xzgedu.supercv.llm.enums.SortType;

import java.util.Map;

public class LLMLogSql {

    public String selectLLMLogs(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("select id, uid, model_id, prompt_type, " +
                "CASE WHEN input IS NOT NULL THEN SUBSTRING(input, 1, 100) ELSE input END as input, " +
                "CASE WHEN output IS NOT NULL THEN SUBSTRING(output, 1, 100) ELSE output END as output, " +
                "input_token, output_token, cost_time, applied, create_time " +
                "from llm_log where 1=1 ");
        LLMLogFilter filter = (LLMLogFilter) params.get("llmLogFilter");
        appendFilter(sb, filter);
        SortType sortType = (SortType) params.get("sortType");
        appendSortType(sb, sortType);
        sb.append(" limit #{limitOffset}, #{limitSize}");
        return sb.toString();
    }

    public String countLLMLogs(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("select count(1) from llm_log where 1=1 ");
        LLMLogFilter filter = (LLMLogFilter) params.get("llmLogFilter");
        appendFilter(sb, filter);
        return sb.toString();
    }

    private void appendFilter(StringBuilder sb, LLMLogFilter filter) {
        if (filter.getUid() != null) {
            sb.append(" and uid = #{llmLogFilter.uid}");
        }
        if (filter.getModelId() != null) {
            sb.append(" and model_id = " + filter.getModelId());
        }
        if (filter.getPromptType() != null) {
            sb.append(" and prompt_type = " + filter.getPromptType().getValue());
        }
    }

    private void appendSortType(StringBuilder sb, SortType sortType) {
        if (sortType == null) {
            sb.append(" order by create_time desc");
        } else if (SortType.BY_CREATE_TIME.getValue() == sortType.getValue()) {
            sb.append(" order by create_time desc");
        } else if (SortType.BY_COST_TIME.getValue() == sortType.getValue()) {
            sb.append(" order by cost_time desc");
        } else if (SortType.BY_INPUT_TOKEN.getValue() == sortType.getValue()) {
            sb.append(" order by input_token desc");
        } else {
            sb.append(" order by create_time desc");
        }
    }
}

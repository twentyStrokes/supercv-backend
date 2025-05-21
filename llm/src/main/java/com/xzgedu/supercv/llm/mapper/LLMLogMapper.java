package com.xzgedu.supercv.llm.mapper;

import com.xzgedu.supercv.llm.domain.LLMLog;
import com.xzgedu.supercv.llm.domain.LLMLogFilter;
import com.xzgedu.supercv.llm.enums.SortType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LLMLogMapper {
    @Results(id="LLMLog", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "modelId", column = "model_id"),
            @Result(property = "promptType", column = "prompt_type"),
            @Result(property = "input", column = "input"),
            @Result(property = "output", column = "output"),
            @Result(property = "inputToken", column = "input_token"),
            @Result(property = "outputToken", column = "output_token"),
            @Result(property = "costTime", column = "cost_time"),
            @Result(property = "applied", column = "applied"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("select * from llm_log where id=#{id}")
    LLMLog selectLLMLogById(@Param("id") long id);

    @ResultMap("LLMLog")
    @SelectProvider(type = LLMLogSql.class, method = "selectLLMLogs")
    List<LLMLog> selectLLMLogs(@Param("llmLogFilter") LLMLogFilter llmLogFilter,
                               @Param("sortType") SortType sortType,
                               @Param("limitOffset") int limitOffset,
                               @Param("limitSize") int limitSize);

    @SelectProvider(type = LLMLogSql.class, method = "countLLMLogs")
    int countLLMLogs(@Param("llmLogFilter") LLMLogFilter llmLogFilter);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO llm_log (uid, model_id, prompt_type, input, output, input_token, output_token, cost_time, applied) " +
            "VALUES (#{uid}, #{modelId}, #{promptType}, #{input}, #{output}, #{inputToken}, #{outputToken}, #{costTime}, #{applied})")
    int insertLLMLog(LLMLog llmLog);
}

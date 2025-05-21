package com.xzgedu.supercv.llm.mapper;

import com.xzgedu.supercv.llm.domain.LLMModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LLMModelMapper {

    @Results(id = "LLMModel", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "llm_type", property = "llmType"),
            @Result(column = "model_name", property = "modelName"),
            @Result(column = "api_key", property = "apiKey"),
            @Result(column = "endpoint", property = "endpoint"),
            @Result(column = "enabled", property = "enabled")
    })
    @Select("SELECT * FROM llm_model WHERE id = #{id}")
    LLMModel getLLMModelById(@Param("id") long id);

    @ResultMap("LLMModel")
    @Select("SELECT * FROM llm_model")
    List<LLMModel> getAllLLMModels();

    @ResultMap("LLMModel")
    @Select("SELECT * FROM llm_model WHERE enabled = true")
    List<LLMModel> getEnabledLLMModels();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO llm_model (llm_type, model_name, api_key, endpoint, enabled) " +
            "VALUES (#{llmType}, #{modelName}, #{apiKey}, #{endpoint}, #{enabled})")
    int insertLLMModel(LLMModel llmModel);

    @Update("UPDATE llm_model SET llm_type = #{llmType}, model_name = #{modelName}, " +
            "api_key = #{apiKey}, endpoint = #{endpoint}, enabled = #{enabled} " +
            "WHERE id = #{id}")
    int updateLLMModel(LLMModel llmModel);

    @Delete("DELETE FROM llm_model WHERE id = #{id}")
    int deleteLLMModelById(@Param("id") long id);
}

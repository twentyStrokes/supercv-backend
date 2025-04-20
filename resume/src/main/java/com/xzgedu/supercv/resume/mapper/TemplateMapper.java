package com.xzgedu.supercv.resume.mapper;

import com.xzgedu.supercv.resume.domain.Template;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TemplateMapper {

    // 增加模板
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO resume_template (name, page_frame, page_style, is_public) " +
            "VALUES (#{name}, #{pageFrame}, #{pageStyle}, #{isPublic})")
    int insertTemplate(Template template);

    // 删除模板
    @Update("UPDATE resume_template SET is_deleted = TRUE WHERE id = #{id}")
    int deleteTemplate(@Param("id") long id);

    // 更新模板
    @Update("UPDATE resume_template " +
            "SET name = #{name}, " +
            "page_frame=#{pageFrame}, " +
            "page_style = #{pageStyle}, " +
            "is_public=#{isPublic} " +
            "WHERE id = #{id}")
    int updateTemplate(Template template);

    // 更新模板缩略图
    @Update("UPDATE resume_template SET thumbnail_url = #{thumbnailUrl} WHERE id = #{id}")
    int updateTemplateThumbnailUrl(@Param("id") long id, @Param("thumbnailUrl") String thumbnailUrl);

    // 更新示例简历
    @Update("UPDATE resume_template SET demo_resume_id = #{demoResumeId} WHERE id = #{id}")
    int updateDemoResumeId(@Param("id") long id, @Param("demoResumeId") Long demoResumeId);

    // 根据ID查询模板
    @Results(id = "Template", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "pageFrame", column = "page_frame"),
            @Result(property = "pageStyle", column = "page_style"),
            @Result(property = "thumbnailUrl", column = "thumbnail_url"),
            @Result(property = "demoResumeId", column = "demo_resume_id"),
            @Result(property = "isPublic", column = "is_public")
    })
    @Select("select * from resume_template where id=#{id}")
    Template selectTemplateById(@Param("id") long id);

    // 分页查询模板
    @ResultMap("Template")
    @Select("<script>" +
            "SELECT * " +
            "FROM resume_template " +
            "WHERE is_deleted = FALSE " +
            "<if test='isPublic != null'>AND is_public = #{isPublic}</if> " +
            "LIMIT #{limitOffset}, #{limitSize}" +
            "</script>")
    List<Template> selectTemplatesPagination(@Param("isPublic") Boolean isPublic,
                                             @Param("limitOffset") int limitOffset,
                                             @Param("limitSize") int limitSize);

    @Select("<script> " +
            "SELECT count(*) " +
            "FROM resume_template " +
            "WHERE is_deleted = FALSE " +
            "<if test='isPublic != null'>AND is_public = #{isPublic}</if>" +
            "</script>")
    int countTemplates(@Param("isPublic") Boolean isPublic);

    @ResultMap("Template")
    @SelectProvider(type = TemplateSql.class, method = "selectTemplatesByIds")
    List<Template> selectTemplatesByIds(@Param("ids") List<Long> ids);
}
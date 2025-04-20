package com.xzgedu.supercv.resume.mapper;

import com.xzgedu.supercv.resume.domain.Resume;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResumeMapper {
    @Results(id = "Resume", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "name", column = "name"),
            @Result(property = "templateId", column = "template_id"),
            @Result(property = "rawFile", column = "raw_file"),
            @Result(property = "rawDataJson", column = "raw_data_json"),
            @Result(property = "extraStyleJson", column = "extra_style_json"),
            @Result(property = "isPublic", column = "is_public")
    })
    @Select("select * from resume where id=#{id} and is_deleted=false")
    Resume getResumeById(@Param("id") long id);

    @ResultMap("Resume")
    @Select("select * from resume where uid=#{uid} and is_deleted=false " +
            "order by update_time desc limit #{limitOffset}, #{limitSize}")
    List<Resume> selectResumesByUid(@Param("uid") long uid,
                                    @Param("limitOffset") int limitOffset,
                                    @Param("limitSize") int limitSize);

    @Select("select count(*) from resume where is_deleted=false and uid=#{uid}")
    int countResumesByUid(@Param("uid") long uid);

    @ResultMap("Resume")
    @Select("select * from resume where is_deleted=false order by update_time desc limit #{limitOffset}, #{limitSize}")
    List<Resume> selectResumesPagination(@Param("limitOffset") int limitOffset, @Param("limitSize") int limitSize);

    @Select("select count(*) from resume where is_deleted=false")
    int countResumes();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO resume(uid, name, template_id, raw_file, raw_data_json, extra_style_json, is_public) " +
            "values(#{uid}, #{name}, #{templateId}, #{rawFile}, #{rawDataJson}, #{extraStyleJson}, #{isPublic})")
    int insertResume(Resume resume);

    @Update("update resume set is_deleted=true where id=#{id}")
    int deleteResume(@Param("id") long id);

    @Update("UPDATE resume " +
            "SET uid = #{uid}, " +
            "    name = #{name}, " +
            "    template_id = #{templateId}, " +
            "    raw_file = #{rawFile}, " +
            "    raw_data_json = #{rawDataJson}, " +
            "    extra_style_json = #{extraStyleJson}, " +
            "    is_public = #{isPublic} " +
            "WHERE id = #{id}")
    int updateResume(Resume resume);
}

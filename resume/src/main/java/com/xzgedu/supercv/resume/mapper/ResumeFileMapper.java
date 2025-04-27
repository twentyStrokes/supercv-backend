package com.xzgedu.supercv.resume.mapper;

import com.xzgedu.supercv.resume.domain.ResumeFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResumeFileMapper {
    @Results(id = "ResumeFile", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "fileUrl", column = "file_url"),
            @Result(property = "parsedText", column = "parsed_text"),
            @Result(property = "parsedJson", column = "parsed_json"),
            @Result(property = "parsedJsonValid", column = "parsed_json_valid"),
            @Result(property = "parsedErrorMsg", column = "parsed_error_msg"),
            @Result(property = "parsedStatus", column = "parsed_status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("select * from resume_file where id = #{id}")
    ResumeFile getResumeFileById(@Param("id") long id);

    @ResultMap("ResumeFile")
    @Select("select id, uid, file_url, " +
            "CASE WHEN parsed_text IS NOT NULL THEN SUBSTRING(parsed_text, 1, 100) ELSE parsed_text END as parsed_text, " +
            "CASE WHEN parsed_json IS NOT NULL THEN SUBSTRING(parsed_json, 1, 100) ELSE parsed_json END as parsed_json, " +
            "CASE WHEN parsed_error_msg IS NOT NULL THEN SUBSTRING(parsed_error_msg, 1, 100) ELSE parsed_error_msg END as parsed_error_msg, " +
            "parsed_json_valid, parsed_status, create_time, update_time " +
            "from resume_file where uid = #{uid} order by create_time desc limit #{limitOffset}, #{limitSize}")
    List<ResumeFile> getResumeFilesByUid(@Param("uid") long uid,
                                         @Param("limitOffset") int limitOffset,
                                         @Param("limitSize") int limitSize);

    @Select("select count(*) from resume_file where uid = #{uid}")
    int countResumeFilesByUid(@Param("uid") long uid);

    @ResultMap("ResumeFile")
    @Select("select id, uid, file_url, " +
            "CASE WHEN parsed_text IS NOT NULL THEN SUBSTRING(parsed_text, 1, 100) ELSE parsed_text END as parsed_text, " +
            "CASE WHEN parsed_json IS NOT NULL THEN SUBSTRING(parsed_json, 1, 100) ELSE parsed_json END as parsed_json, " +
            "CASE WHEN parsed_error_msg IS NOT NULL THEN SUBSTRING(parsed_error_msg, 1, 100) ELSE parsed_error_msg END as parsed_error_msg, " +
            "parsed_json_valid, parsed_status, create_time, update_time " +
            "from resume_file order by create_time desc limit #{limitOffset}, #{limitSize}")
    List<ResumeFile> getResumeFiles(@Param("limitOffset") int limitOffset,
                                    @Param("limitSize") int limitSize);

    @Select("select count(*) from resume_file")
    int countResumeFiles();

    @ResultMap("ResumeFile")
    @Select("select * from resume_file where file_url = #{fileUrl}")
    ResumeFile getResumeFileByFileUrl(@Param("fileUrl") String fileUrl);

    @Select("select parsed_status from resume_file where file_url = #{fileUrl}")
    Integer getParsedStatus(@Param("fileUrl") String fileUrl);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into resume_file(uid, file_url, parsed_status) values(#{uid}, #{fileUrl}, #{parsedStatus})")
    int insertResumeFile(ResumeFile resumeFile);

    @Update("update resume_file set parsed_text=#{parsedText}, parsed_json=#{parsedJson}," +
            "parsed_json_valid=#{parsedJsonValid}, parsed_error_msg=#{parsedErrorMsg}, parsed_status=#{parsedStatus} " +
            "where id=#{id}")
    int updateParsedResult(ResumeFile resumeFile);
}

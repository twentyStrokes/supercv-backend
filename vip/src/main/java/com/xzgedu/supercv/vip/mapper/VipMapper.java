package com.xzgedu.supercv.vip.mapper;

import com.xzgedu.supercv.vip.domain.Vip;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface VipMapper {
    @Results(id = "Vip", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "expireTime", column = "expire_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "resumeImportLeftNum", column = "resume_import_left_num"),
            @Result(property = "resumeExportLeftNum", column = "resume_export_left_num"),
            @Result(property = "resumeCreateLeftNum", column = "resume_create_left_num"),
            @Result(property = "resumeAnalyzeLeftNum", column = "resume_analyze_left_num"),
            @Result(property = "resumeOptimizeLeftNum", column = "resume_optimize_left_num"),
            @Result(property = "isTrial", column = "is_trial"),
    })
    @Select("SELECT * FROM vip WHERE uid = #{uid}")
    Vip selectByUid(@Param("uid") long uid);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO vip(uid, expire_time, resume_analyze_left_num, resume_optimize_left_num, " +
            "resume_import_left_num, resume_export_left_num, resume_create_left_num, is_trial) " +
            "VALUES(#{uid}, #{expireTime}, #{resumeAnalyzeLeftNum}, #{resumeOptimizeLeftNum}, " +
            "#{resumeImportLeftNum}, #{resumeExportLeftNum}, #{resumeCreateLeftNum}, #{isTrial})")
    int insertVip(Vip vip);

    @Update("UPDATE vip SET expire_time = #{expireTime}, resume_analyze_left_num = #{resumeAnalyzeLeftNum}, " +
            "resume_optimize_left_num = #{resumeOptimizeLeftNum}, " +
            "resume_import_left_num = #{resumeImportLeftNum}, resume_export_left_num = #{resumeExportLeftNum}, " +
            "resume_create_left_num = #{resumeCreateLeftNum}, is_trial = #{isTrial} " +
            "WHERE uid = #{uid}")
    int updateVip(Vip vip);

    @Update("UPDATE vip SET resume_import_left_num = resume_import_left_num - 1 " +
            "WHERE uid = #{uid} AND resume_import_left_num > 0")
    int decreaseResumeImportLeftNum(@Param("uid") long uid);

    @Update("UPDATE vip SET resume_export_left_num = resume_export_left_num - 1 " +
            "WHERE uid = #{uid} AND resume_export_left_num > 0")
    int decreaseResumeExportLeftNum(@Param("uid") long uid);

    @Update("UPDATE vip SET resume_create_left_num = resume_create_left_num - 1 " +
            "WHERE uid = #{uid} AND resume_create_left_num > 0")
    int decreaseResumeCreateLeftNum(@Param("uid") long uid);

    @Update("UPDATE vip SET resume_analyze_left_num = resume_analyze_left_num - 1 " +
            "WHERE uid = #{uid} AND resume_analyze_left_num > 0")
    int decreaseResumeAnalyzeLeftNum(@Param("uid") long uid);

    @Update("UPDATE vip SET resume_optimize_left_num = resume_optimize_left_num - 1 " +
            "WHERE uid = #{uid} AND resume_optimize_left_num > 0")
    int decreaseResumeOptimizeLeftNum(@Param("uid") long uid);

    @ResultMap("Vip")
    @Select("select * from vip order by update_time desc limit #{limitOffset}, #{limitSize}")
    List<Vip> selectVips(@Param("limitOffset") int limitOffset,
                         @Param("limitSize") int limitSize);

    @SelectProvider(type = VipProvider.class, method = "countVips")
    int countVips(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}

package com.xzgedu.supercv.product.mapper;

import com.xzgedu.supercv.product.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Results(id = "Product", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "originalPrice", column = "original_price"),
            @Result(property = "discountPrice", column = "discount_price"),
            @Result(property = "durationDays", column = "duration_days"),
            @Result(property = "resumeImportNum", column = "resume_import_num"),
            @Result(property = "resumeExportNum", column = "resume_export_num"),
            @Result(property = "resumeCreateNum", column = "resume_create_num"),
            @Result(property = "resumeAnalyzeNum", column = "resume_analyze_num"),
            @Result(property = "resumeOptimizeNum", column = "resume_optimize_num"),
            @Result(property = "sortValue", column = "sort_value")
    })
    @Select("select * from product where id=#{id}")
    Product selectProductById(@Param("id") long id);

    @ResultMap("Product")
    @Select("select * from product where is_deleted=false order by sort_value asc")
    List<Product> getAllProducts();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into product(name, original_price, discount_price, duration_days, " +
            "resume_import_num, resume_export_num, resume_create_num, resume_analyze_num, resume_optimize_num, sort_value)" +
            "values(#{name},#{originalPrice},#{discountPrice},#{durationDays}," +
            "#{resumeImportNum},#{resumeExportNum},#{resumeCreateNum},#{resumeAnalyzeNum},#{resumeOptimizeNum}, #{sortValue})")
    int insertProduct(Product product);

    @Update("update product set name=#{name}" +
            ",original_price=#{originalPrice}" +
            ",discount_price=#{discountPrice}" +
            ",duration_days=#{durationDays}" +
            ",resume_import_num=#{resumeImportNum}" +
            ",resume_export_num=#{resumeExportNum}" +
            ",resume_create_num=#{resumeCreateNum}" +
            ",resume_analyze_num=#{resumeAnalyzeNum}" +
            ",resume_optimize_num=#{resumeOptimizeNum} " +
            ",sort_value=#{sortValue} " +
            "where id=#{id}")
    int updateProduct(Product product);

    @Update("update product set is_deleted=true where id=#{id}")
    int deleteProduct(@Param("id") long id);
}

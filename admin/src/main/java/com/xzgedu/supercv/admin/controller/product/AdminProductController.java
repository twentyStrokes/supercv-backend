package com.xzgedu.supercv.admin.controller.product;

import com.xzgedu.supercv.common.exception.GenericBizException;
import com.xzgedu.supercv.product.domain.Product;
import com.xzgedu.supercv.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "产品管理")
@RequestMapping("/admin/product")
@RestController
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "添加产品")
    @PostMapping("/add")
    public Product addProduct(@RequestParam("name") String productName,
                              @RequestParam("original_price") BigDecimal originalPrice,
                              @RequestParam("discount_price") BigDecimal discountPrice,
                              @RequestParam("duration_days") int durationDays,
                              @RequestParam("resume_import_num") int resumeImportNum,
                              @RequestParam("resume_export_num") int resumeExportNum,
                              @RequestParam("resume_create_num") int resumeCreateNum,
                              @RequestParam("resume_analyze_num") int resumeAnalyzeNum,
                              @RequestParam("resume_optimize_num") int resumeOptimizeNum,
                              @RequestParam("sort_value") int sortValue)
            throws GenericBizException {
        Product product = new Product();
        product.setName(productName);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setDurationDays(durationDays);
        product.setResumeImportNum(resumeImportNum);
        product.setResumeExportNum(resumeExportNum);
        product.setResumeCreateNum(resumeCreateNum);
        product.setResumeAnalyzeNum(resumeAnalyzeNum);
        product.setResumeOptimizeNum(resumeOptimizeNum);
        product.setSortValue(sortValue);
        if (productService.addProduct(product)) return product;
        throw new GenericBizException("Failed to add product: " + product.toString());
    }

    @Operation(summary = "更新产品")
    @PostMapping("/update")
    public void updateProduct(@RequestParam("product_id") int productId,
                              @RequestParam("name") String productName,
                              @RequestParam("original_price") BigDecimal originalPrice,
                              @RequestParam("discount_price") BigDecimal discountPrice,
                              @RequestParam("duration_days") int durationDays,
                              @RequestParam("resume_import_num") int resumeImportNum,
                              @RequestParam("resume_export_num") int resumeExportNum,
                              @RequestParam("resume_create_num") int resumeCreateNum,
                              @RequestParam("resume_analyze_num") int resumeAnalyzeNum,
                              @RequestParam("resume_optimize_num") int resumeOptimizeNum,
                              @RequestParam("sort_value") int sortValue)
            throws GenericBizException {
        Product product = new Product();
        product.setId(productId);
        product.setName(productName);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setDurationDays(durationDays);
        product.setResumeImportNum(resumeImportNum);
        product.setResumeExportNum(resumeExportNum);
        product.setResumeCreateNum(resumeCreateNum);
        product.setResumeAnalyzeNum(resumeAnalyzeNum);
        product.setResumeOptimizeNum(resumeOptimizeNum);
        product.setSortValue(sortValue);
        if (productService.updateProduct(product)) return;
        throw new GenericBizException("Failed to update product: " + product.toString());
    }

    @Operation(summary = "删除产品")
    @PostMapping("/delete")
    public void deleteProduct(@RequestParam("product_id") int productId)
            throws GenericBizException {
        if (productService.deleteProduct(productId)) return;
        throw new GenericBizException("Failed to delete product with id: " + productId);
    }
}

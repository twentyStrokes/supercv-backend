package com.xzgedu.supercv.product.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private long id;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;
    private int durationDays;
    private int resumeImportNum;
    private int resumeExportNum;
    private int resumeCreateNum;
    private int resumeAnalyzeNum;
    private int resumeOptimizeNum;
    private int sortValue;
}

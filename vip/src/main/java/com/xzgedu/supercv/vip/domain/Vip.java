package com.xzgedu.supercv.vip.domain;

import com.xzgedu.supercv.common.anotation.ViewData;
import lombok.Data;

import java.util.Date;

@Data
public class Vip {
    private long id;
    private Long uid;
    @ViewData
    private String nickName;
    @ViewData
    private String headImgUrl;

    private Date expireTime;
    private int resumeImportLeftNum;
    private int resumeExportLeftNum;
    private int resumeCreateLeftNum;
    private int resumeAnalyzeLeftNum;
    private int resumeOptimizeLeftNum;

    private boolean isTrial;

    private Date updateTime;
}
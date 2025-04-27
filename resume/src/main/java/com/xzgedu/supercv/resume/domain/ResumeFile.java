package com.xzgedu.supercv.resume.domain;

import com.xzgedu.supercv.common.anotation.ViewData;
import lombok.Data;

import java.util.Date;

@Data
public class ResumeFile {
    private Long id;
    private Long uid;
    @ViewData
    private String nickName;
    @ViewData
    private String headImgUrl;

    private String fileUrl;
    private String parsedText;
    private String parsedJson;
    private Boolean parsedJsonValid;
    private String parsedErrorMsg;
    private Integer parsedStatus;
    private Date createTime;
    private Date updateTime;
    @ViewData
    private long parsedCostTime; //单位毫秒

    public long getParsedCostTime() {
        if (createTime != null && updateTime != null) {
            return updateTime.getTime() - createTime.getTime();
        }
        return -1;
    }
}

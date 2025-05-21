package com.xzgedu.supercv.vip.controller;

import com.xzgedu.supercv.vip.domain.Vip;
import com.xzgedu.supercv.vip.service.VipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//TODO: 优化这个类的代码
@Tag(name = "会员")
@RequestMapping("/v1/vip")
@RestController
public class VipController {

    @Autowired
    private VipService vipService;

    @Operation(summary = "查询用户的会员信息")
    @GetMapping("/info")
    public Vip getVipInfo(@RequestHeader("uid") long uid) {
        return vipService.getVipInfo(uid);
    }

    @Operation(summary = "检查是否为合法会员")
    @GetMapping("/check-if-valid")
    public boolean checkIfValidVip(@RequestHeader("uid") long uid) {
        return vipService.permitValidVip(uid);
    }

    @Operation(summary = "检查是否为合法会员，并且剩余导入简历次数大于0")
    @GetMapping("/permit/resume-import")
    public Map<String, Object> permitResumeImport(@RequestHeader("uid") long uid) {
        Vip vip = vipService.getVipInfo(uid);
        if (vip == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("permit", false);
            resp.put("description", "非会员用户");
            return resp;
        }
        return permit(vip, vip.getResumeImportLeftNum());
    }

    @Operation(summary = "检查是否为合法会员，并且剩余导出简历次数大于0")
    @GetMapping("/permit/resume-export")
    public Map<String, Object> permitResumeExport(@RequestHeader("uid") long uid) {
        Vip vip = vipService.getVipInfo(uid);
        if (vip == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("permit", false);
            resp.put("description", "非会员用户");
            return resp;
        }
        return permit(vip, vip.getResumeExportLeftNum());
    }

    @Operation(summary = "检查是否为合法会员，并且剩余创建简历次数大于0")
    @GetMapping("/permit/resume-create")
    public Map<String, Object> permitResumeCreate(@RequestHeader("uid") long uid) {
        Vip vip = vipService.getVipInfo(uid);
        if (vip == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("permit", false);
            resp.put("description", "非会员用户");
            return resp;
        }
        return permit(vip, vip.getResumeCreateLeftNum());
    }

    @Operation(summary = "检查是否为合法会员，并且剩余AI分析次数大于0")
    @GetMapping("/permit/resume-analyze")
    public Map<String, Object> permitAiAnalysis(@RequestHeader("uid") long uid) {
        Vip vip = vipService.getVipInfo(uid);
        if (vip == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("permit", false);
            resp.put("description", "非会员用户");
            return resp;
        }
        return permit(vip, vip.getResumeAnalyzeLeftNum());
    }

    @Operation(summary = "检查是否为合法会员，并且剩余AI优化次数大于0")
    @GetMapping("/permit/resume-optimize")
    public Map<String, Object> permitAiOptimize(@RequestHeader("uid") long uid) {
        Vip vip = vipService.getVipInfo(uid);
        if (vip == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("permit", false);
            resp.put("description", "非会员用户");
            return resp;
        }
        return permit(vip, vip.getResumeOptimizeLeftNum());
    }

    private Map<String, Object> permit(Vip vip, int leftNum) {
        Map<String, Object> resp = new HashMap<>();
        String vipType = "会员";
        if (vip.isTrial()) {
            vipType = "试用期";
        }

        //拥有VIP并且没有过期
        boolean isValidVip = vipService.checkIfValidVip(vip);
        if (!isValidVip) {
            resp.put("permit", false);
            resp.put("description", vipType + "已过期");
            return resp;
        }

        //检查是否还有剩余次数
        if (leftNum <= 0) {
            resp.put("permit", false);
            resp.put("description", vipType+"权益已用光");
            return resp;
        }

        resp.put("permit", true);
        return resp;
    }
}

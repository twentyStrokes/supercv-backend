package com.xzgedu.supercv.vip.controller;

import com.xzgedu.supercv.event.UserRegEvent;
import com.xzgedu.supercv.vip.domain.VipPrivilege;
import com.xzgedu.supercv.vip.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TrialVipGrantListener {

    @Autowired
    private VipService vipService;

    @EventListener(UserRegEvent.class)
    public void grantTrialVip(UserRegEvent event) {
        long uid = event.getUserId();
        VipPrivilege privilege = new VipPrivilege();
        privilege.setResumeImportNum(1);
        privilege.setResumeExportNum(10);
        privilege.setResumeCreateNum(5);
        privilege.setResumeAnalyzeNum(1);
        privilege.setResumeOptimizeNum(5);
        vipService.renewVip(uid, 3, privilege, true);
    }
}

package com.xzgedu.supercv;

import com.xzgedu.supercv.user.domain.AuthToken;
import com.xzgedu.supercv.vip.domain.VipPrivilege;
import com.xzgedu.supercv.vip.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VipAccountSetUp {

    @Autowired
    private UserAccountSetUp userAccountSetUp;

    @Autowired
    private VipService vipService;

    public AuthToken createVipAccountAndLogin()
    {
        AuthToken authToken = userAccountSetUp.createRandomUserAndLogin();
        VipPrivilege vipPrivilege = new VipPrivilege();
        vipPrivilege.setResumeCreateNum(5);
        vipPrivilege.setResumeExportNum(5);
        vipPrivilege.setResumeImportNum(5);
        vipPrivilege.setResumeAnalyzeNum(5);
        vipPrivilege.setResumeOptimizeNum(5);
        vipService.renewVip(authToken.getUid(), 30, vipPrivilege, false);
        return authToken;
    }
}

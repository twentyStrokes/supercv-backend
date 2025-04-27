package com.xzgedu.supercv.vip.service;

import com.xzgedu.supercv.vip.domain.Vip;
import com.xzgedu.supercv.vip.domain.VipPrivilege;
import com.xzgedu.supercv.vip.repo.VipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VipService {

    @Autowired
    private VipRepo vipRepo;

    public boolean permitValidVip(long uid) {
        Vip vip = vipRepo.getVipByUid(uid);
        return this.checkIfValidVip(vip);
    }

    public boolean permitResumeImport(long uid) {
        Vip vip = vipRepo.getVipByUid(uid);

        //拥有VIP并且没有过期
        boolean isValidVip = checkIfValidVip(vip);
        if (!isValidVip) return false;

        //检查是否还有剩余次数
        return vip.getResumeImportLeftNum() > 0;
    }

    public boolean permitResumeExport(long uid) {
        Vip vip = vipRepo.getVipByUid(uid);

        //拥有VIP并且没有过期
        boolean isValidVip = checkIfValidVip(vip);
        if (!isValidVip) return false;

        //检查是否还有剩余次数
        return vip.getResumeExportLeftNum() > 0;
    }

    public boolean permitResumeCreate(long uid) {
        Vip vip = vipRepo.getVipByUid(uid);

        //拥有VIP并且没有过期
        boolean isValidVip = checkIfValidVip(vip);
        if (!isValidVip) return false;

        //检查是否还有剩余次数
        return vip.getResumeCreateLeftNum() > 0;
    }

    public boolean permitAiAnalysis(long uid) {
        Vip vip = vipRepo.getVipByUid(uid);

        //拥有VIP并且没有过期
        boolean isValidVip = checkIfValidVip(vip);
        if (!isValidVip) return false;

        //检查是否还有剩余次数
        return vip.getResumeAnalyzeLeftNum() > 0;
    }

    public boolean permitAiOptimize(long uid) {
        Vip vip = vipRepo.getVipByUid(uid);

        //拥有VIP并且没有过期
        boolean isValidVip = checkIfValidVip(vip);
        if (!isValidVip) return false;

        //检查是否还有剩余次数
        return vip.getResumeOptimizeLeftNum() > 0;
    }

    public boolean decreaseResumeImportLeftNum(long uid) {
        return vipRepo.decreaseResumeImportLeftNum(uid);
    }

    public boolean decreaseResumeExportLeftNum(long uid) {
        return vipRepo.decreaseResumeExportLeftNum(uid);
    }

    public boolean decreaseResumeCreateLeftNum(long uid) {
        return vipRepo.decreaseResumeCreateLeftNum(uid);
    }

    public boolean decreaseAiAnalysisLeftNum(long uid) {
        return vipRepo.decreaseAiAnalysisLeftNum(uid);
    }

    public boolean decreaseAiOptimizeLeftNum(long uid) {
        return vipRepo.decreaseAiOptimizeLeftNum(uid);
    }

    public Vip getVipInfo(Long uid) {
        if (uid == null) return null;
        return vipRepo.getVipByUid(uid);
    }

    public boolean renewVip(long uid, int days, VipPrivilege vipPrivilege, boolean isTrial) {
        int resumeImportLeftNum = 0;
        int resumeExportLeftNum = 0;
        int resumeCreateLeftNum = 0;
        int aiAnalysisLeftNum = 0;
        int aiOptimizeLeftNum = 0;
        long expireTimeInMillis = System.currentTimeMillis();
        Vip oldVip = vipRepo.getVipByUid(uid);
        if (oldVip != null && oldVip.getExpireTime().after(new Date()) && !oldVip.isTrial()) {
            resumeImportLeftNum  = oldVip.getResumeImportLeftNum();
            resumeExportLeftNum = oldVip.getResumeExportLeftNum();
            resumeCreateLeftNum = oldVip.getResumeCreateLeftNum();
            aiAnalysisLeftNum = oldVip.getResumeAnalyzeLeftNum();
            aiOptimizeLeftNum = oldVip.getResumeOptimizeLeftNum();
            expireTimeInMillis = oldVip.getExpireTime().getTime();
        }
        Vip vip = new Vip();
        vip.setUid(uid);
        vip.setResumeImportLeftNum(resumeImportLeftNum + vipPrivilege.getResumeImportNum());
        vip.setResumeExportLeftNum(resumeExportLeftNum + vipPrivilege.getResumeExportNum());
        vip.setResumeCreateLeftNum(resumeCreateLeftNum + vipPrivilege.getResumeCreateNum());
        vip.setResumeAnalyzeLeftNum(aiAnalysisLeftNum + vipPrivilege.getResumeAnalyzeNum());
        vip.setResumeOptimizeLeftNum(aiOptimizeLeftNum + vipPrivilege.getResumeOptimizeNum());
        vip.setTrial(isTrial);
        long millis = days * 3600l * 24l * 1000l;
        vip.setExpireTime(new Date(expireTimeInMillis + millis));
        if (oldVip == null) {
            return vipRepo.addVip(vip);
        } else {
            return vipRepo.updateVip(vip);
        }
    }

    public boolean checkIfValidVip(Vip vip) {
        //检查是否拥有vip
        if (vip == null) return false;

        //检查是否过期
        Date expireTime = vip.getExpireTime();
        if (expireTime == null || expireTime.before(new Date())) {
            return false;
        }

        return true;
    }

   public List<Vip> getVipsByPagination(int limitOffset, int limitSize) {
        return vipRepo.getVipsByPagination(limitOffset, limitSize);
    }

    public int countVips(Date startTime, Date endTime) {
        return vipRepo.countVips(startTime, endTime);
    }
}
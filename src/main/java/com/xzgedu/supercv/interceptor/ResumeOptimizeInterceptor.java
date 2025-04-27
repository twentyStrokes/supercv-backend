package com.xzgedu.supercv.interceptor;

import com.xzgedu.supercv.admin.service.AdminService;
import com.xzgedu.supercv.common.exception.ErrorCode;
import com.xzgedu.supercv.vip.service.VipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class ResumeOptimizeInterceptor  implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VipService vipService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        //权限检查和权益消耗
        Long selfUid = InterceptorUtils.parseLong(request.getHeader("uid"));
        if (vipService.permitAiOptimize(selfUid) && vipService.decreaseAiOptimizeLeftNum(selfUid)) {
            return true;
        }

        //没有权限
        log.warn("No permission to optimize resume by uid={}", selfUid);
        InterceptorUtils.writeResponse(response, ErrorCode.NO_PERMISSION);
        return false;
    }
}
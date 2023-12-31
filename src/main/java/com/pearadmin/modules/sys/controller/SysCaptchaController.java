package com.pearadmin.modules.sys.controller;

import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.response.Result;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码控制器
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@RestController
@RequestMapping("system/captcha")
public class SysCaptchaController extends BaseController {

    /**
     * 生成验证
     *
     * @param request  请求报文
     * @param response 响应报文
     */
    @RequestMapping("generate")
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 远程验证
     *
     * @param request 请求报文
     * @param captcha 验证码
     * @return 验证结果
     */
    @RequestMapping("verify")
    public Result verify(HttpServletRequest request, String captcha) {
        if (CaptchaUtil.ver(captcha, request)) {
            return success("验证成功");
        }
        return failure("验证失败");
    }
}

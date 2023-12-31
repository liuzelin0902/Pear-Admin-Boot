package com.pearadmin.modules.sys.controller;

import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.tools.server.CpuInfo;
import com.pearadmin.common.tools.server.SystemUtil;
import com.pearadmin.common.web.base.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Describe: 服务器控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "monitor")
public class SysMonitorController extends BaseController {

    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/monitor/main','sys:monitor:main')")
    public ModelAndView main(Model model) {
        CpuInfo cpu = SystemUtil.getCpu();
        model.addAttribute("cpu", cpu);
        return jumpPage("system/monitor/main");
    }
}

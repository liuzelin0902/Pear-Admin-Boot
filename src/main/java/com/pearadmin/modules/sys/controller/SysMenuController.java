package com.pearadmin.modules.sys.controller;

import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.context.UserContext;
import com.pearadmin.modules.sys.domain.SysMenu;
import com.pearadmin.modules.sys.domain.SysUser;
import com.pearadmin.modules.sys.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 邮箱控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "menu/")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * Describe: 根据 username 获取菜单数据
     * Param SysRole
     * Return 执行结果
     */
    @GetMapping("/data")
    public List<SysMenu> getUserMenu() {
        SysUser sysUser = UserContext.currentUser();
        List<SysMenu> menus = sysMenuService.getUserMenu(sysUser.getUsername());
        return sysMenuService.toUserMenu(menus, "0");
    }
}

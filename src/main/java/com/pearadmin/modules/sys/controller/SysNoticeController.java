package com.pearadmin.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.context.UserContext;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.tools.string.Convert;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.modules.sys.domain.SysNotice;
import com.pearadmin.modules.sys.service.SysNoticeService;
import com.pearadmin.modules.sys.service.SysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe: 消息控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    private String prefix = "system/notice";

    @Resource
    private SysNoticeService sysNoticeService;

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/main")
    @PreAuthorize("hasPermission('/system/notice/main','system:notice:main')")
    public ModelAndView main() {
        return jumpPage(prefix + "/main");
    }

    /**
     * 查询notice列表
     */
    @ResponseBody
    @GetMapping("/data")
    @PreAuthorize("hasPermission('/system/notice/data','system:notice:data')")
    public ResultTable list(@ModelAttribute SysNotice sysNotice, PageDomain pageDomain) {
        PageInfo<SysNotice> pageInfo = sysNoticeService.selectSysNoticePage(sysNotice, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 查询消息
     */
    @ResponseBody
    @GetMapping("notice")
    public List<Map> notice() {

        List<Map> result = new ArrayList<>();

        SysNotice publicParam = new SysNotice();
        publicParam.setType("public");

        SysNotice privateParam = new SysNotice();
        privateParam.setType("private");
        privateParam.setAccept(UserContext.currentUser().getUserId());

        SysNotice noticeParam = new SysNotice();
        noticeParam.setType("notice");

        Map<String, Object> publicArray = new HashMap<>(3);
        publicArray.put("id", "1");
        publicArray.put("title", "公告");
        publicArray.put("children", sysNoticeService.selectSysNoticeList(publicParam));

        Map<String, Object> privateArray = new HashMap<>(3);
        privateArray.put("id", "2");
        privateArray.put("title", "私信");
        privateArray.put("children", sysNoticeService.selectSysNoticeList(privateParam));

        Map<String, Object> noticeArray = new HashMap<>(3);
        noticeArray.put("id", "3");
        noticeArray.put("title", "通知");
        noticeArray.put("children", sysNoticeService.selectSysNoticeList(noticeParam));

        result.add(publicArray);
        result.add(privateArray);
        result.add(noticeArray);

        return result;
    }

    /**
     * 新增notice
     */
    @GetMapping("/add")
    @PreAuthorize("hasPermission('/system/notice/add','system:notice:add')")
    public ModelAndView add(Model model) {
        model.addAttribute("users", sysUserService.list(null));
        return jumpPage(prefix + "/add");
    }

    /**
     * 新增保存notice
     */
    @ResponseBody
    @PostMapping("/save")
    @PreAuthorize("hasPermission('/system/notice/add','system:notice:add')")
    public Result save(@RequestBody SysNotice sysNotice) {
        sysNotice.setId(SequenceUtil.makeStringId());
        return decide(sysNoticeService.insertSysNotice(sysNotice));
    }

    /**
     * 修改notice
     */
    @GetMapping("/edit")
    @PreAuthorize("hasPermission('/system/notice/edit','system:notice:edit')")
    public ModelAndView edit(String id, ModelMap mmap) {
        SysNotice sysNotice = sysNoticeService.selectSysNoticeById(id);
        mmap.put("sysNotice", sysNotice);
        return jumpPage(prefix + "/edit");
    }

    /**
     * 修改保存notice
     */
    @ResponseBody
    @PutMapping("/update")
    @PreAuthorize("hasPermission('/system/notice/edit','system:notice:edit')")
    public Result update(@RequestBody SysNotice sysNotice) {
        sysNotice.setCreateTime(LocalDateTime.now());
        return decide(sysNoticeService.updateSysNotice(sysNotice));
    }

    /**
     * 删除notice
     */
    @ResponseBody
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/system/notice/remove','system:notice:remove')")
    public Result batchRemove(String ids) {
        return decide(sysNoticeService.deleteSysNoticeByIds(Convert.toStrArray(ids)));
    }

    /**
     * 删除
     */
    @ResponseBody
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasPermission('/system/notice/remove','system:notice:remove')")
    public Result remove(@PathVariable("id") String id) {
        return decide(sysNoticeService.deleteSysNoticeById(id));
    }
}

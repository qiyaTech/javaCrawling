package com.qiya.boss.fwapi.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.qiya.boss.menu.annotation.Menu;
import com.qiya.framework.coreservice.SysService;
import com.qiya.framework.middletier.dao.PageUIDao;
import com.qiya.framework.middletier.model.PageUI;
import com.qiya.framework.middletier.service.ScurityRoleService;
import com.qiya.framework.middletier.service.ScurityUserRoleService;
import com.qiya.framework.middletier.service.ScurityUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class EntityPageController  {

    @Autowired
    SysService sysService;

    @Autowired
    protected PageUIDao pageUIDao;

    protected Map<String, String> postMap = Maps.newHashMap();

    @Autowired
    private ScurityRoleService roleService;

    @Autowired
    private ScurityUserService userService;

    @Autowired
    private ScurityUserRoleService userRoleService;

    private static Logger log = LoggerFactory.getLogger(EntityPageController.class);

    // post执行的js语句
    private final String ACTION_CREATE = "create";
    private final String ACTION_READ = "read";
    private final String ACTION_UPDATE = "update";
    private final String ACTION_DELETE = "delete";
    private final String ACTION_SEARCH = "search";

    @PostConstruct
    private void init() {
        this.postMap.put(ACTION_CREATE, "ui.title = _pageTitle;\r\n" + "ui.submitUrl = projectConfig.urlRule.getApiUrl(_entity, _action);\r\n" + "heituFramework.renderCreateUI(ui, ds);");
        this.postMap.put(ACTION_READ, "ui.title = _pageTitle;\r\n" + "ui.apiUrl = projectConfig.urlRule.getApiUrl(_entity, _action, _objId);\r\n" + "heituFramework.renderReadUI(ui, ds);");
        this.postMap.put(ACTION_UPDATE, "ui.title = _pageTitle;\r\n" + "ui.apiUrl = projectConfig.urlRule.getApiUrl(_entity, 'read', _objId);\r\n" + "ui.submitUrl = projectConfig.urlRule.getApiUrl(_entity, _action, _objId);\r\n" + "heituFramework.renderUpdateUI(ui, ds);");
        this.postMap.put(ACTION_DELETE, ""); // 不存在
        this.postMap.put(ACTION_SEARCH, "ui.title = _pageTitle;\r\n" + "ui.entity = _entity;\r\n" + "tableOpts.entity = _entity;\r\n" + "ui.apiUrl = projectConfig.urlRule.getApiUrl(_entity, _action);\r\n" + "heituFramework.renderPage({\r\n" + "    navBarMessage: _navBarMessage,\r\n" + "    navBarNotifications: _navBarNotifications,\r\n" + "    navBarMega: _navBarMega,\r\n" + "    navBarUser: {\r\n" + "        headPortrait: _headImg,\r\n" + "        userName: _userName\r\n" + "    },\r\n" + "    navMainData: _navMainMenu\r\n" + "});\r\n" + "var ui_opts = heituFramework.getSearchUI(ui, ds);\r\n" + "var table = heituFramework.renderSearchUI(ui_opts, tableOpts, toolBarOpts);\r\n" + "heituFramework.search(ui_opts, table, _searchFirst);");
    }

    @Menu
    @RequestMapping(value = { "/{entity}/search" })
    ModelAndView search(@PathVariable String entity, ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
        log.debug(ACTION_SEARCH);

        PageUI pu = this.pageUIDao.getByEntityAndAction(entity, ACTION_SEARCH);
        if (pu == null) {
            model.setViewName("404");
            return model;
        } else {
            this.render(pu, model);
            model.addObject("searchFirst", true);
            model.setViewName("entity");
            return model;
        }
    }

    @Menu
    @RequestMapping(value = { "/{entity}/create" })
    ModelAndView create(@PathVariable String entity, ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
        PageUI pu = this.pageUIDao.getByEntityAndAction(entity, ACTION_CREATE);
        if (pu == null) {
            model.setViewName("404");
            return model;
        }else {
            this.render(pu, model);
            model.setViewName("entity");
            return model;
        }
    }

    @Menu
    @RequestMapping(value = { "/{entity}/read/{id}" })
    ModelAndView read(@PathVariable String entity, @PathVariable String id, ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
        PageUI pu = this.pageUIDao.getByEntityAndAction(entity, ACTION_READ);
        if (pu == null)
        {
            model.setViewName("404");
            return model;
        }
        else {
            this.render(pu, model);
            model.addObject("objId", id);
            model.setViewName("entity");
            return model;
        }
    }

    @Menu
    @RequestMapping(value = { "/{entity}/update/{id}" })
    ModelAndView update(@PathVariable String entity, @PathVariable String id, ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
        PageUI pu = this.pageUIDao.getByEntityAndAction(entity, ACTION_UPDATE);
        if (pu == null)
        {
            model.setViewName("404");
            return model;
        }
        else {
            this.render(pu, model);
            model.addObject("objId", id);
            model.setViewName("entity");
            return model;
        }
    }

    // 生成js文件的内容，返回
    @RequestMapping(value = { "/jsui/{entity}_{action}" })
    @ResponseBody
    String searchJs(@PathVariable String entity, @PathVariable String action, Model model, HttpServletRequest request, HttpServletResponse response) {
        PageUI pu = this.pageUIDao.getByEntityAndAction(entity, action);
        if (pu == null)
            return "404";
        else {
            String post = this.postMap.get(action);
            return "var ds={};" + pu.getPageText() + post ;
        }
    }
    // 通用参数赋值
    protected void render(PageUI pu, ModelAndView model) {

        model.addObject("env", this.sysService.getActiveProfile());

        model.addObject("title", pu.getPageTitle());
        model.addObject("entity", pu.getEntity());
        model.addObject("action", pu.getAction());
        model.addObject("pageType", pu.getPageType());
        model.addObject("jsFile", this.getJsFile(pu.getEntity(), pu.getAction()));
        model.addObject("_navMainMenuData", ""); // 当前用户

        //String phone= SecurityContextHolder.getContext().getAuthentication().getName();
//        List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(userService.getUserInfo(phone).getId());
//
//
//        List<Map<String,Object>> mapList=userRoleService.getMenu(roleList);
//
//        model.addAttribute("menu",mapList);

        model.addObject("userName", "");
    }
    protected String getJsFile(String entity, String action) {
        return String.format("/jsui/%s_%s.js", entity, action);
    }

}

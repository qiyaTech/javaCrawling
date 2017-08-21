package com.qiya.boss.fwapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Preconditions;
import com.qiya.boss.menu.annotation.Menu;
import com.qiya.framework.baselib.web.WebUtils;
import com.qiya.framework.middletier.model.ScurityUser;
import com.qiya.framework.middletier.service.ScurityResourceService;
import com.qiya.framework.middletier.service.ScurityRoleService;
import com.qiya.framework.middletier.service.ScurityUserRoleService;
import com.qiya.framework.middletier.service.ScurityUserService;
import com.qiya.framework.model.ApiOutput;


/**
 * Created by qiyalm on 16/6/17.
 */
@Controller
public class ScurityHomeController {

    @Autowired
    private ScurityRoleService roleService;

    @Autowired
    private ScurityUserService userService;

    @Autowired
    private ScurityResourceService resourceService;

    @Autowired
    private ScurityUserRoleService userRoleService;

    // 登录
    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    public String login(){
        return "login";
    }

    //首页
    @Menu
    @RequestMapping(value={"","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView res(ModelAndView model,
                      HttpServletRequest request, HttpServletResponse response,
                      HttpSession session){
//        String phone= SecurityContextHolder.getContext().getAuthentication().getName();
//        List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(userService.getUserInfo(phone).getId());
//        if (roleList.size()<=0){
//            return "errorPage";
//        }
//
//        List<Map<String, Object>> mapList =userRoleService.getMenu(roleList);
//
//        Collection<? extends GrantedAuthority> authorities = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
////        System.out.println("角色角色:" + authorities);   //[admin,user]
//
//        model.addAttribute("menu", mapList);
        model.setViewName("index");
        return model;
    }

    //没有权限页面
    @RequestMapping("/403")
    public ModelAndView forbidden(ModelAndView model) {
        model.setViewName("errorPage");
        return model;
    }
    //修改密码
    @RequestMapping(value = "/updatePwd",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public ApiOutput updateUser(@RequestBody String json) throws Exception{
        Map map = WebUtils.getJsonParams(json);

        Preconditions.checkNotNull(map.get("oldPwd"), "旧密码不能为空!");
        Preconditions.checkNotNull(map.get("pwd"), "密码不能为空!");
        Preconditions.checkNotNull(map.get("phone"), "手机号不能为空!");

        String oldPwd=map.get("oldPwd").toString();
        String pwd=map.get("pwd").toString();
        String phone=map.get("phone").toString();

        Map<String,Object> data = new HashMap<>();
        ScurityUser scurityUser = userService.getUserInfo(phone);
        if (!scurityUser.getPassword().equals(oldPwd)){
            data.put("isSuccess",2);
            return new ApiOutput(data);
        }

        if (scurityUser!=null) {
            scurityUser.setPassword(pwd);
            int count = userService.update(scurityUser);
            if (count != 1) {
                data.put("isSuccess", 1);  // 失败
            } else {
                data.put("isSuccess", 0);  // 成功
            }
        }

        return new ApiOutput(data);
    }
}

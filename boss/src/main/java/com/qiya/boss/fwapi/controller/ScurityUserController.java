package com.qiya.boss.fwapi.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qiya.boss.menu.MenuService;
import com.qiya.boss.menu.annotation.Menu;
import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.middletier.dao.ScurityUserDao;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.model.ScurityRole;
import com.qiya.framework.middletier.model.ScurityUser;
import com.qiya.framework.middletier.service.BizConfigService;
import com.qiya.framework.middletier.service.ScurityRoleService;
import com.qiya.framework.middletier.service.ScurityUserRoleService;
import com.qiya.framework.middletier.service.ScurityUserService;
import com.qiya.middletier.bizenum.BizConfigTypeEnum;


/**
 * Created by qiyalm on 16/6/12.
 */
@Controller
@RequestMapping(value = "/userManage")
public class ScurityUserController{
    @Autowired
    private ScurityUserService userService;

    @Autowired
    private ScurityRoleService roleService;

    @Autowired
    private ScurityUserDao userDao;

    @Autowired
    private BizConfigService bizConfigService;


    @Autowired
    private ScurityUserRoleService userRoleService;

    @Autowired
    MenuService menuService;

    //用户管理
    @Menu
    @RequestMapping(value = "")
    public ModelAndView userManage(ModelAndView model) {
//        String phone= SecurityContextHolder.getContext().getAuthentication().getName();
//        List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(userService.getUserInfo(phone).getId());
//
//        if (roleList.size()<=0){
//            return "errorPage";
//        }
//
//        List<Map<String,Object>> mapList=userRoleService.getMenu(roleList);

//        model.addObject("menu",mapList);
        model.setViewName("userManage");
        return  model ;
    }

    //修改用户信息
    @RequestMapping(value = "/updateUser",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String updateUser(HttpServletRequest request, HttpServletResponse response,
                             HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer uid=Integer.parseInt(request.getParameter("userId"));
        String name=request.getParameter("name");
        String phone=request.getParameter("phone");
      //  String username=request.getParameter("username");
//        String areaDesc=request.getParameter("areaDesc");
//        String district=request.getParameter("district");
        String gender=request.getParameter("gender");

        int count=userService.updateUser(uid,name,phone,gender);

        jsonObject.put("count",count);
        return jsonObject.toString();
    }

    //重置密码
    @RequestMapping(value = "/updatePwd",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String updatePwd(HttpServletRequest request, HttpServletResponse response,
                             HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer uid=Integer.parseInt(request.getParameter("userId"));
        String pwd = "";

        List<Config> configList = bizConfigService.getByType(BizConfigTypeEnum.USER_PWD.getValue());
        if (configList.size()>0){
            Config config = configList.get(0);
            pwd = config.getValue();
        }else {
            jsonObject.put("count",0);
            return jsonObject.toString();
        }

        int count=userService.updatePwd(uid,pwd);

        jsonObject.put("count",count);
        return jsonObject.toString();
    }


    //获取所有用户信息
    @RequestMapping(value = "/getAllUsers",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String getAllUsers() {
        JSONObject json=new JSONObject();
        List<Map<String,Object>> mapList=new ArrayList<>();

        List<ScurityUser> userList=userService.getAllUsers();
        if (userList.size()<=0){
            json.put("data",null);
            return json.toJSONString();
        }
        for (ScurityUser user:userList) {
            Map<String,Object> map=new HashMap<>();
            map.put("id",user.getId());
            map.put("name",user.getNickName());
            map.put("phone",user.getPhone());
            map.put("areaDesc",user.getAreaDesc());
            map.put("district",user.getDistrict());
            map.put("username",user.getUserName());
            map.put("gender",user.getGender());
            mapList.add(map);
        }

        json.put("data",mapList);

        return json.toJSONString();
    }

    //删除用户
    @RequestMapping(value = "/delUser",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String delUser(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session){

        JSONObject jsonObject=new JSONObject();
        Integer uid=Integer.parseInt(request.getParameter("userId"));

        try{
            ScurityUser user = userService.getUserById(uid);
            user.setStatus(StatusEnum.INVALID.getValue());
            user = userService.updateUser(user);
            if (user !=null){
                jsonObject.put("flag",true);
            }else {
                jsonObject.put("flag",false);
            }
            return jsonObject.toString();

        }catch (Exception e){

            jsonObject.put("flag",false);
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    //添加用户信息
    @RequestMapping(value = "/addUser",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session){
        JSONObject jsonObject=new JSONObject();

        String name=request.getParameter("name");
        String username=request.getParameter("username");
        String phone=request.getParameter("phone");
//        String areaDesc=request.getParameter("areaDesc");
//        String district=request.getParameter("district");
        String gender=request.getParameter("gender");
        String password=request.getParameter("password");

        ScurityUser user=new ScurityUser();
        user.setNickName(name);
        user.setUserName(username);
        user.setPhone(phone);
//        user.setAreaDesc(areaDesc);
//        user.setDistrict(district);
        user.setGender(gender);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setStatus(StatusEnum.VALID.getValue());
        userDao.save(user);

        jsonObject.put("user",user);
        return jsonObject.toString();
    }

    // 验证用户手机号是否已存在
    @RequestMapping(value = "/existUser",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String isExistUser(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();
        String phone=request.getParameter("phone");
        ScurityUser user=userService.getUserInfo(phone);
        if (user!=null){
            jsonObject.put("result",false);
        }else {
            jsonObject.put("result",true);
        }

        return jsonObject.toString();
    }

    @RequestMapping(value = "/existUserName",method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String isExistUserName(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();
        String loginName=request.getParameter("username");
        ScurityUser user=userService.getUserByUserName(loginName);
        if (user!=null){
            jsonObject.put("result",false);
        }else {
            jsonObject.put("result",true);
        }

        return jsonObject.toString();
    }

    //获取用户角色
    @RequestMapping(value ="/getUserRoleByUser",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String getUserRoleByUser(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer userId=Integer.parseInt(request.getParameter("userId"));

        List<Map<String,Object>> mapList=userRoleService.getUserRolesByUser(userId);
        jsonObject.put("map",mapList);

        return jsonObject.toJSONString();
    }

    //修改用户角色
    @RequestMapping(value ="/updateUserRoleByUser/{userId}/{roles}",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String delUserRoleByUser(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session, @PathVariable("userId") Integer userId, @PathVariable("roles")List<Integer> roles){
        JSONObject jsonObject=new JSONObject();

        userRoleService.delUserRoleByUser(userId);

        if (roles.size()>0){
            for (Integer roleId:roles) {
                userRoleService.addUserRole(userId,roleId);
            }
        }
        menuService.clealRedisMenu();
        return jsonObject.toJSONString();
    }

    //修改用户角色
    @RequestMapping(value ="/deleteUserRoleByUser/{userId}",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String delUserRoleByUser(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session, @PathVariable("userId") Integer userId){
        JSONObject jsonObject=new JSONObject();

        userRoleService.delUserRoleByUser(userId);
        menuService.clealRedisMenu();
        return jsonObject.toJSONString();
    }

    //获取角色信息
    @RequestMapping(value ="/getRole",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String getRoleInfo() {
        JSONObject jsonObject = new JSONObject();

        List<Map<String, Object>> mapList = new ArrayList<>();

        List<ScurityRole> roleList = roleService.getAllRoles();
        if (roleList.size() <= 0) {
            jsonObject.put("data", null);
            return jsonObject.toJSONString();
        }
        for (ScurityRole role : roleList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", role.getRoleId());
            map.put("roleName", role.getRoleName());
            map.put("remark", role.getRemark());
            mapList.add(map);
        }

        jsonObject.put("data", mapList);
        return jsonObject.toJSONString();
    }
}

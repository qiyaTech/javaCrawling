package com.qiya.boss.fwapi.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qiya.boss.menu.MenuService;
import com.qiya.boss.menu.annotation.Menu;
import com.qiya.boss.scurity.MyInvocationSecurityMetadataSource;
import com.qiya.framework.coreservice.RedisService;
import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.middletier.model.ScurityResource;
import com.qiya.framework.middletier.model.ScurityRole;
import com.qiya.framework.middletier.service.*;


/**
 * Created by qiyalm on 16/6/7.
 */
@Controller
@RequestMapping(value ="/roleManage")
public class ScurityRoleController {
    private Logger logger= LoggerFactory.getLogger(ScurityRoleController.class);

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ScurityRoleService roleService;

    @Autowired
    private ScurityUserService userService;

    @Autowired
    private ScurityUserRoleService userRoleService;

    @Autowired
    private ScurityResourceService resourceService;

    @Autowired
    private ScurityRoleResourceService roleResourceService;

    @Autowired
    private RedisService redisService;

    private String redisMenuKey = "MenuResource:";

    @Autowired
    private MenuService menuService;

    @Autowired
    private MyInvocationSecurityMetadataSource securityMetadataSource;

    //角色页面
    @Menu
    @RequestMapping(value ="")
    public ModelAndView showRole(ModelAndView model){
//        String phone= SecurityContextHolder.getContext().getAuthentication().getName();
//        List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(userService.getUserInfo(phone).getId());
//        if (roleList.size()<=0){
//            return "errorPage";
//        }
//        List<Map<String,Object>> mapList=userRoleService.getMenu(roleList);
//
//        model.addAttribute("menu",mapList);
        model.setViewName("roleManage");
        return model;
    }

    //获取角色信息
    @RequestMapping(value ="/getRole",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String getRoleInfo(){
        JSONObject jsonObject=new JSONObject();

        List<Map<String,Object>> mapList=new ArrayList<>();

        List<ScurityRole> roleList=roleService.getAllRoles();
        if (roleList.size()<=0){
            jsonObject.put("data",null);
            return jsonObject.toJSONString();
        }
        for (ScurityRole role:roleList) {
            Map<String,Object> map=new HashMap<>();
            map.put("id",role.getRoleId());
            map.put("roleName",role.getRoleName());
            map.put("remark",role.getRemark());
            mapList.add(map);
        }

        jsonObject.put("data",mapList);
        return jsonObject.toJSONString();
    }

    //修改角色
    @RequestMapping(value ="/updateRole",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String updateRoleInfo(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer roleId=Integer.parseInt(request.getParameter("roleId"));
        String roleName=request.getParameter("roleName");
        String remark=request.getParameter("remark");
        Date date=null;
        try {
            date=sdf.parse(sdf.format(new Date()));
        }catch (Exception ex){
            jsonObject.put("flag",false);
            logger.error(ex.getMessage());
        }

        roleService.updateRoleInfo(roleId,roleName,remark,date);
        securityMetadataSource.loadResourceDefine();
        jsonObject.put("flag",true);
        return jsonObject.toJSONString();
    }

    //添加角色
    @RequestMapping(value ="/addRole",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String addRoleInfo(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();

        String roleName=request.getParameter("roleName");
        String remark=request.getParameter("remark");
        Date date=null;
        try {
            date=sdf.parse(sdf.format(new Date()));
        }catch (Exception ex){
            jsonObject.put("flag",false);
            logger.error(ex.getMessage());
        }

        ScurityRole role=roleService.addRoleInfo(roleName,remark,date);
        if (role!=null){
            jsonObject.put("flag",true);
            securityMetadataSource.loadResourceDefine();
        }

        return jsonObject.toJSONString();
    }

    //删除角色
    @RequestMapping(value ="/delRole",method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String delRoleInfo(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer roleId=Integer.parseInt(request.getParameter("roleId"));

        //先判断有没有关联
        Boolean isHasUserRole = userRoleService.isHasRoleUser(roleId);
        Boolean isHasResRole = roleResourceService.isHasResByRole(roleId);

        if (isHasUserRole == true && isHasResRole == true){
            jsonObject.put("flag",false);
            jsonObject.put("showMsg","请先解除与该角色关联的用户与菜单!");
            return jsonObject.toJSONString();
        }

        if (isHasUserRole == true){
            jsonObject.put("flag",false);
            jsonObject.put("showMsg","请先解除与该角色关联的用户!");
            return jsonObject.toJSONString();
        }

        if (isHasResRole == true){
            jsonObject.put("flag",false);
            jsonObject.put("showMsg","请先解除与该角色关联的菜单!");
            return jsonObject.toJSONString();
        }


        ScurityRole scurityRole = roleService.getById(roleId);
        scurityRole.setStatus(StatusEnum.INVALID.getValue());
        scurityRole = roleService.updateRole(scurityRole);

        if (scurityRole != null){
            jsonObject.put("flag",true);
            securityMetadataSource.loadResourceDefine();
            menuService.clealRedisMenu();
        }

        return jsonObject.toJSONString();
    }

    //获取所有菜单信息
    @RequestMapping(value={"/getAllRes"},method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String getAllResInfo(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer roleId=Integer.parseInt(request.getParameter("roleId"));
        List<Map<String,Object>> mapList=resourceService.getResJsTree(roleId);

        jsonObject.put("map",mapList);
        jsonObject.put("flag",true);

        return jsonObject.toJSONString();
    }

    //给角色分配资源
    @RequestMapping(value={"/updateResByRole/{roleId}/{resList}"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String getResByRole(Model model,
                               HttpServletRequest request, HttpServletResponse response,
                               HttpSession session, @PathVariable("roleId") Integer roleId, @PathVariable("resList") List<String> res){
        JSONObject jsonObject=new JSONObject();

        int count=roleResourceService.delRoleResByRole(roleId);
        int parentId=0;

        if (res.size()>0){
            for (String str:res) {
                Integer resId=Integer.parseInt(str);
                ScurityResource resource=resourceService.getResourceById(resId);
                parentId=resource.getFatherId();
                roleResourceService.saveRoleRes(roleId,resId);

                if (roleResourceService.getRoleResById(roleId,parentId)==null){
                    roleResourceService.saveRoleRes(roleId,parentId);
                }

            }
//            Set<String>  set= redisService.keys(redisMenuKey+"*");
//            Iterator<String> it = set.iterator();
//            while(it.hasNext()){
//                String keyStr = it.next();
//                redisService.del(keyStr);
//            }
        }
        jsonObject.put("result","true");
        securityMetadataSource.loadResourceDefine();
        menuService.clealRedisMenu();
        return jsonObject.toJSONString();
    }

    //给角色分配资源
    @RequestMapping(value={"/delResByRole/{roleId}"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String delResByRole(Model model,
                               HttpServletRequest request, HttpServletResponse response,
                               HttpSession session, @PathVariable("roleId") Integer roleId){
        JSONObject jsonObject=new JSONObject();

        int count=roleResourceService.delRoleResByRole(roleId);

        jsonObject.put("result","true");
        securityMetadataSource.loadResourceDefine();
        menuService.clealRedisMenu();
        return jsonObject.toJSONString();
    }

}

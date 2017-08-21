package com.qiya.boss.fwapi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.qiya.boss.menu.MenuService;
import com.qiya.boss.menu.annotation.Menu;
import com.qiya.framework.baselib.web.WebUtils;
import com.qiya.framework.middletier.model.ScurityResource;
import com.qiya.framework.middletier.service.ScurityResourceService;
import com.qiya.framework.middletier.service.ScurityRoleService;
import com.qiya.framework.middletier.service.ScurityUserRoleService;
import com.qiya.framework.middletier.service.ScurityUserService;
import com.qiya.framework.model.ApiOutput;

/**
 * Created by qiyalm on 16/5/31.
 */
@Controller
@RequestMapping(value = "/resourceManage")
public class ScurityResourceController {
    private Logger logger= LoggerFactory.getLogger(ScurityResourceController.class);

    @Autowired
    private ScurityResourceService resourceService;

    @Autowired
    private ScurityRoleService roleService;

    @Autowired
    private ScurityUserService userService;

    @Autowired
    private ScurityUserRoleService userRoleService;

    @Autowired
    MenuService menuService;

    //菜单管理页面
    @Menu
    @RequestMapping(value={""}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getResourceManage(ModelAndView model,
                                    HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session){
//        String phone= SecurityContextHolder.getContext().getAuthentication().getName();
//        List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(userService.getUserInfo(phone).getId());
//
//        if (roleList.size()<=0){
//            return "errorPage";
//        }
//        List<Map<String, Object>> mapList =userRoleService.getMenu(roleList);;
//
//        model.addAttribute("menu",mapList);
        model.setViewName("resourceManage");
        return model;
    }

    //获取菜单信息
    @RequestMapping(value={"/getTreeGrid"},method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String getTreeGrid(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();

        List<Map<String,Object>> mapList=resourceService.getTreeGridOrderNum();

        jsonObject.put("map",mapList);

        return jsonObject.toJSONString();
    }

    //对添加的resourceCode验证是否已存在
    @RequestMapping(value={"/existCode"},method={RequestMethod.GET, RequestMethod.POST}, produces="application/json")
    @ResponseBody
    public String existCode(HttpServletRequest request, HttpServletResponse response,
                            HttpSession session){
        JSONObject jsonObject=new JSONObject();

        String code=request.getParameter("code");
        ScurityResource resource=resourceService.getResourceByCode(code);

        if (resource!=null){
            jsonObject.put("result",false);
        }else {
            jsonObject.put("result",true);
        }

        return jsonObject.toJSONString();
    }

    //增加菜单
    @RequestMapping(value={"/addResource"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String addResource(Model model,
                              HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();

        String name=request.getParameter("name");
        String code=request.getParameter("code");
        String url=request.getParameter("url");
        Integer fatherId=Integer.parseInt(request.getParameter("fatherId"));

        int num = resourceService.getMaxNumByFather(fatherId);

        ScurityResource resource=new ScurityResource();
        resource.setResourceName(name);
        resource.setResourceUrl(url);
        resource.setResourceCode(code);
        resource.setFatherId(fatherId);
        resource.setOrderNumber(num+1);
        resource.setCreateTime(new Date());

        ScurityResource resource1= resourceService.addResource(resource);

        if (resource1!=null){
            jsonObject.put("isSuccess","true");
        }else {
            jsonObject.put("isSuccess","false");
        }
        return jsonObject.toJSONString();
    }

    //修改菜单信息
    @RequestMapping(value={"/updateResource"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String updateResource(Model model,
                                 HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer resId=Integer.parseInt(request.getParameter("resId"));
        String name=request.getParameter("name");
        String code=request.getParameter("code");
        String url=request.getParameter("url");
        Integer fatherId=Integer.parseInt(request.getParameter("fatherId"));

        ScurityResource resource=new ScurityResource();
        resource.setResourceName(name);
        resource.setResourceUrl(url);
        resource.setResourceCode(code);
        resource.setFatherId(fatherId);
        resource.setLastUpdateTime(new Date());
        resource.setResourceId(resId);

        int count=resourceService.updateResourceByRes(resource);

        jsonObject.put("isSuccess","true");
        menuService.clealRedisMenu();
        return jsonObject.toJSONString();
    }

    //删除菜单
    @RequestMapping(value={"/delResource"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
    @ResponseBody
    public String delResource(Model model,
                              HttpServletRequest request, HttpServletResponse response,
                              HttpSession session){
        JSONObject jsonObject=new JSONObject();

        Integer resId=Integer.parseInt(request.getParameter("resId"));

        int count=resourceService.delResourceByRes(resId);

        jsonObject.put("isSuccess","true");
        menuService.clealRedisMenu();
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/updateOrderNumById",method = RequestMethod.POST, consumes ="application/json")
    @ResponseBody
    public ApiOutput updateOrderNumById(@RequestBody String json) throws Exception {
        Map map = WebUtils.getJsonParams(json);
        Preconditions.checkNotNull(map.get("resId"), "resId不能为空!");
        Integer resId =Integer.parseInt(map.get("resId").toString());
        Preconditions.checkNotNull(map.get("isUp"), "isUp不能为空!");
        Integer isUp = Integer.parseInt(map.get("isUp").toString());
        int index = -1;

        ScurityResource resource = resourceService.getResourceById(resId);
        List<ScurityResource> scurityResources = resourceService.getResourceByFathId(resource.getFatherId());

        Map<String,Object> data = new HashMap<>();

        for (int i = 0; i<scurityResources.size();i++) {
            ScurityResource res = scurityResources.get(i);
            if (res.getResourceId() == resId){
                if (isUp==0){  //向上
                    index = (i-1);
                }else {
                    index = (i+1);
                }

                ScurityResource otherRes = scurityResources.get(index);

                if (otherRes!=null){
                    int resOrderNum = res.getOrderNumber();

                    res.setOrderNumber(otherRes.getOrderNumber());
                    res = resourceService.update(res);

                    otherRes.setOrderNumber(resOrderNum);
                    otherRes = resourceService.update(otherRes);

                    if (res == null || otherRes == null){
                        data.put("isSuccess",1);
                        return new ApiOutput(data);
                    }
                }
            }
        }

        menuService.clealRedisMenu();
        data.put("isSuccess",0);
        return new ApiOutput(data);
    }

}

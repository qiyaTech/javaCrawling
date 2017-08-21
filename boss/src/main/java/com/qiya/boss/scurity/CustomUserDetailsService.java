package com.qiya.boss.scurity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.middletier.dao.ScurityUserDao;
import com.qiya.framework.middletier.model.ScurityUser;
import com.qiya.framework.middletier.service.ScurityRoleService;


/**
 * Created by qiyalm on 16/6/15.
 */
@Service("UserDetailService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ScurityUserDao userDao;
    @Autowired
    private ScurityRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(phone)){
            throw new UsernameNotFoundException("not found");
        }
        ScurityUser user = userDao.findUserByPhoneNandUserName(phone, StatusEnum.VALID.getValue());

        if(user == null){
            throw new UsernameNotFoundException("not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(user.getId());
        for (Map<String, Object> map :roleList) {
            authorities.add(new SimpleGrantedAuthority(map.get("roleName").toString()));
        }

        return new org.springframework.security.core.userdetails.User(user.getPhone(),
                user.getPassword(), authorities);
    }


}

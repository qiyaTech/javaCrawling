package com.qiya.middletier.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.qiya.middletier.model.SysMessage;

/**
 * Created by qiyamac on 16/6/16.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface SysMessageDao extends CrudRepository<SysMessage, Long>, JpaSpecificationExecutor<SysMessage> {



    @Query(" from SysMessage t where  t.receiverId=?2 and t.createtime > ?3 and status = ?1   ")
    public List<SysMessage>  getuserTopMessage(Integer status, Long userid, Date userDate, Pageable pageable);

}

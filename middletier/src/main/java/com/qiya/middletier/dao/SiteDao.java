package com.qiya.middletier.dao;

import com.qiya.middletier.model.Site;
import com.qiya.middletier.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by qiyalm on 17/3/24.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface SiteDao extends CrudRepository<Site, Long>, JpaSpecificationExecutor<Site> {

    @Query("FROM Site WHERE siteName=:name")
    public Site getSiteBYName(@Param("name") String name);

    @Query("select t from Site t where t.status = ?1")
    public List<Site> getSites(Integer status);

}

package com.qiya.middletier.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qiya.middletier.model.UserSource;

/**
 * Created by qiyamac on 16/6/15.
 */

@Repository
@RepositoryRestResource(exported = false)
@Transactional
public interface UserSourceDao extends CrudRepository<UserSource, Long>, JpaSpecificationExecutor<UserSource> {

	@Query("FROM UserSource t WHERE t.openId=:openId AND t.sourceFrom = :sourceFrom ")
	public UserSource getUserSourceByOpenId(@Param("openId") String openId, @Param("sourceFrom") String sourceFrom);

	@Query("FROM UserSource t WHERE t.userId=:userId  ")
	public List<UserSource> findUserSourceByUserId(@Param("userId") Long userId);

}

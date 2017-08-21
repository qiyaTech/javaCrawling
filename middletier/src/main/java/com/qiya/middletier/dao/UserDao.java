package com.qiya.middletier.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qiya.middletier.model.User;

/**
 * Created by qiyamac on 16/6/15.
 */

@Repository
@RepositoryRestResource(exported = false)
@Transactional
public interface UserDao extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Query("FROM User t WHERE t.mobile=:mobile ")
	public User getUserByMobile(@Param("mobile") String mobile);

	@Query("FROM User t WHERE t.mobile=:mobile AND t.password=:password")
	public User getUserByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);


	@Query(value = "select t from User t where t.status = :status and t.name like %:name% ",nativeQuery = false)
	public Page<User> getUserByPage(@Param("status") Integer status,@Param("name") String name,Pageable pageable);

	@Query(value = "select count(1) from User t where t.status = :status ",nativeQuery = false)
	public int getAllUserCount(@Param("status") Integer status);

	@Modifying
	@Query("update User u set u.password=:newPassowrd where u.mobile=:mobile")
	public void updateUserPassword(@Param("newPassowrd") String newPassowrd, @Param("mobile") String mobile);

	@Query("select t FROM User t,UserSource u WHERE u.openId=:openId AND u.sourceFrom = :sourceFrom AND status =:status  And t.id=u.userId")
	public User getUserByOpenId(@Param("openId") String openId, @Param("sourceFrom") String sourceFrom, @Param("status") int status);

}

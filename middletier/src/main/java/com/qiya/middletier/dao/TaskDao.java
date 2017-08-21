package com.qiya.middletier.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.qiya.middletier.model.Task;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qiyalm on 17/3/28.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface TaskDao extends CrudRepository<Task,Long>,JpaSpecificationExecutor<Task>{

    @Query("select t FROM Task t WHERE spiderUUID=:spiderUUID ")
    public Task getByUUID(@Param("spiderUUID") String spiderUUID);

    @Query("select t FROM Task t WHERE status=:status ")
    public Page<Task> getTasks(@Param("status") Integer status,Pageable pageable);

    @Query("select t FROM Task t WHERE status=:status ")
    public List<Task> getTaskInfo(@Param("status") Integer status);

    @Query("select t FROM Task t WHERE status=:status  and timerTask= :timerTask ")
    public List<Task> getTaskTimerTasks(@Param("status") Integer status,@Param("timerTask") Integer timerTask);

    @Query(" select t from Site s,Task t where s.id=t.siteId  and  s.domain=:domain and s.status =:status and t.status=:status  ")
    public List<Task> getTaskByDomain(@Param("domain") String domain,@Param("status") Integer status,Pageable pageable);


}

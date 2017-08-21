package com.qiya.middletier.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiya.framework.baselib.util.generate.RandomUtil;
import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.middletier.base.IService;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.bizenum.TaskRunStateEnum;
import com.qiya.middletier.dao.TaskDao;
import com.qiya.middletier.model.Task;
import com.qiya.middletier.model.TaskResult;
import com.qiya.middletier.webmagic.comm.configmodel.SpiderConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.monitor.MySpiderStatus;

import us.codecraft.webmagic.Spider;

/**
 * Created by qiyalm on 17/3/28.
 */
@Service
public class TaskService implements IService<Task, TaskResult> {

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private SiteService siteService;

	public Task getByUUID(String uuid) {
		return taskDao.getByUUID(uuid);
	}
    public List<Task> getTaskTimerTasks(Integer timerTask) {
        return taskDao.getTaskTimerTasks(StatusEnum.VALID.getValue(),timerTask);
    }


	public Page<TaskResult> runSearch(SearchCondition sc) {
		Pageable pageable = new PageRequest(sc.getIndex(), sc.getSize(), Sort.Direction.DESC, "createTime");
		Specification<Task> spec = new Specification<Task>() {
			@Override
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate bp = builder.equal(root.get("status"), StatusEnum.VALID.getValue());
				bp = builder.and(bp);
				return sc.getPredicate(root, builder, bp);
			}
		};

		Page<Task> result = this.taskDao.findAll(spec, pageable);

		MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();

		Map<String, MySpiderStatus> spiderStatuses = spiderMonitor.getSpiderStatuses();
		// 转换到可导出对象(IExportable)
		return result.map(new Converter<Task, TaskResult>() {
			@Override
			public TaskResult convert(Task source) {

				try {
					ObjectMapper objectMapper = new ObjectMapper();
					WebmagicConfig webmagicConfig = objectMapper.readValue(source.getTaskRuleJson(), WebmagicConfig.class);

					SpiderConfig spiderConfig = webmagicConfig.getSpider();
					MySpiderStatus spiderStatus = spiderStatuses.get(source.getSpiderUUID());
					if (spiderStatus == null) {
						return TaskResult.toTaskResult(source, siteService.read(source.getSiteId().toString()).getSiteName(), spiderConfig.getProcesser(), spiderConfig.getPipeline().toString(), Spider.Status.Stopped.name());
					} else {
						return TaskResult.toTaskResult(source, siteService.read(source.getSiteId().toString()).getSiteName(), spiderConfig.getProcesser(), spiderConfig.getPipeline().toString(), spiderStatus.getStatus());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}
		});

	}

	public Page<TaskResult> search(SearchCondition sc, Long siteId) {
		Pageable pageable = new PageRequest(sc.getIndex(), sc.getSize(), Sort.Direction.DESC, "createTime");
		Specification<Task> spec = new Specification<Task>() {
			@Override
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate bp = builder.equal(root.get("status"), StatusEnum.VALID.getValue());
				if (siteId == null)
					bp = builder.and(bp);
				else if (siteId > 0)
					bp = builder.and(bp, builder.equal(root.get("siteId"), siteId));
				return sc.getPredicate(root, builder, bp);
			}
		};

		Page<Task> result = this.taskDao.findAll(spec, pageable);
		MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();

		Map<String, MySpiderStatus> spiderStatuses = spiderMonitor.getSpiderStatuses();

		// 转换到可导出对象(IExportable)
		return result.map(new Converter<Task, TaskResult>() {
			@Override
			public TaskResult convert(Task source) {

				try {
					ObjectMapper objectMapper = new ObjectMapper();
					WebmagicConfig webmagicConfig = objectMapper.readValue(source.getTaskRuleJson(), WebmagicConfig.class);

					SpiderConfig spiderConfig = webmagicConfig.getSpider();

					MySpiderStatus spiderStatus = spiderStatuses.get(source.getSpiderUUID());
					if (spiderStatus == null) {
						return TaskResult.toTaskResult(source, siteService.read(source.getSiteId().toString()).getSiteName(), spiderConfig.getProcesser(), spiderConfig.getPipeline().toString(), Spider.Status.Stopped.name());
					} else {
						return TaskResult.toTaskResult(source, siteService.read(source.getSiteId().toString()).getSiteName(), spiderConfig.getProcesser(), spiderConfig.getPipeline().toString(), spiderStatus.getStatus());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}
		});

	}

	@Override
	public Page<TaskResult> search(SearchCondition sc) {
		return search(sc, 0L);
	}

	@Override
	public Task create(Task obj) {
		obj.setCreateTime(new Date());
		obj.setStatus(StatusEnum.VALID.getValue());
		obj.setSpiderUUID(obj.getTaskName() + RandomUtil.getUUID());
		obj.setRunState(TaskRunStateEnum.TASK_STOP.getCode());

		return taskDao.save(obj);
	}

	@Override
	public Task read(String id) {

		return taskDao.findOne(Long.valueOf(id));
	}

	@Override
	public Task update(Task obj) {
		return taskDao.save(obj);
	}

	@Override
	public Task delete(Task obj) {
		obj.setStatus(StatusEnum.DELETE.getValue());
		return taskDao.save(obj);
	}

	public Task getTaskByDomain(String domain) {
		List<Task> tasks = taskDao.getTaskByDomain(domain, StatusEnum.VALID.getValue(), new PageRequest(0, 1));
		if (tasks != null && tasks.size() > 0) {
			return tasks.get(0);
		} else {
			return null;
		}

	}

}

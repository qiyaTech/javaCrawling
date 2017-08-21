package com.qiya.middletier.model;

import org.springframework.beans.BeanUtils;

import com.qiya.middletier.bizenum.TaskStatusEnum;

/**
 * Created by qiyalm on 17/3/31.
 */
public class TaskResult extends Task {

	private String siteName;

	private String processName;

	private String pipeName;

	private String run;

	private String runTaskResult;

	private String timerTaskResult;

	public static TaskResult toTaskResult(Task source, String siteName, String processName, String pipeName, String run) {
		TaskResult result = new TaskResult();
		BeanUtils.copyProperties(source, result);
		result.setSiteName(siteName);
		result.setPipeName(pipeName);
		result.setProcessName(processName);
		result.setRun(run);
		result.setRunTaskResult(result.getRunTask() == null ? "" : TaskStatusEnum.getEnumByValue(result.getRunTask()).getName());
		result.setTimerTaskResult(result.getTimerTask() == null ? "" : TaskStatusEnum.getEnumByValue(result.getTimerTask()).getName());
		return result;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getPipeName() {
		return pipeName;
	}

	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getRunTaskResult() {
		return runTaskResult;
	}

	public void setRunTaskResult(String runTaskResult) {
		this.runTaskResult = runTaskResult;
	}

	public String getTimerTaskResult() {
		return timerTaskResult;
	}

	public void setTimerTaskResult(String timerTaskResult) {
		this.timerTaskResult = timerTaskResult;
	}
}

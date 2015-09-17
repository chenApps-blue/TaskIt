package com.chenapps.TaskIt.core;

import com.chenapps.TaskIt.core.enums.TaskType;

public class TaskTypeKey implements ITaskTypeKey{
	private TaskType taskType;
	private String keyString;

	public TaskTypeKey(TaskType taskType,String keyString){
		this.taskType = taskType;
		this.keyString = keyString;	
	}

	@Override
	public String getTaskTypeString() {
		return keyString;
	}

	@Override
	public TaskType getTaskType() {
		return taskType;
	}
	
}

package com.chenapps.TaskIt.core;

import com.chenapps.TaskIt.core.enums.TaskType;

public interface ITaskTypeKey{
	String getTaskTypeString();
	TaskType getTaskType();
}
package com.chenapps.TaskIt.core;

import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

public interface ITaskTypeDetector{
	TaskType getTaskType();
	List<String> getTaskStrings();
	boolean processTextStrings(List<String> taskStrings);	
}
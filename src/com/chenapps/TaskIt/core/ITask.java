package com.chenapps.TaskIt.core;

import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

public interface ITask{
	TaskType getTaskType();
	
	boolean ParsText(List<String> text);
}
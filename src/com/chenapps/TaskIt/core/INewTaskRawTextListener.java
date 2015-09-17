package com.chenapps.TaskIt.core;

import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

public interface INewTaskRawTextListener{
	void onRawTextReady(TaskType taskType, List<String> taskStrings);
}
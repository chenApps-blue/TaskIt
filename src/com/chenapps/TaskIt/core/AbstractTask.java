package com.chenapps.TaskIt.core;

import java.util.ArrayList;
import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

public abstract class AbstractTask  implements ITask {

	protected TaskType taskType;
	public AbstractTask(TaskType taskType) {
		this.taskType = taskType;
	}
	
	@Override
	public TaskType getTaskType() {
		return taskType;
	}
	
	protected List<String[]> prepareStrings(String[] stringArray,
			String stringRemains) {
		List<String[]> rv = new ArrayList<String[]>();
		for (String text : stringArray) {
			if (stringRemains != null)
				text = stringRemains + text;
			rv.add(text.split(" "));
		}
		return rv;
	}
}

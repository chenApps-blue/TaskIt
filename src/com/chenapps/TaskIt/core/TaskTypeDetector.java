package com.chenapps.TaskIt.core;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.chenapps.TaskIt.core.enums.TaskType;

public class TaskTypeDetector implements ITaskTypeDetector {
	private List<String> taskStrings;
	private TaskType taskType;
	
	@Override
	public List<String> getTaskStrings() {
		return taskStrings;
	}

	@Override
	public TaskType getTaskType() {		
		return taskType;
	}	

	@Override
	public boolean processTextStrings(List<String> rawStrings) {
		taskType = getTaskTypeAndRemoveFromText(rawStrings);
		if (taskType == TaskType.NotSet)
			return false;
		
		return true;
	}
	 
	private TaskType getTaskTypeAndRemoveFromText(List<String> textArray) {
		List<TaskType> taskTypeList = new ArrayList<TaskType>();
		List<String> textArrayTaskKeysRemoved = new ArrayList<String>();
		for (String text : textArray) {
			for (ITaskTypeKey taskTypeKey : ApplicationManager.getInstance()
					.getTaskTypeKeys()) {
				if (text.contains(taskTypeKey.getTaskTypeString())){
					text.replace(taskTypeKey.getTaskTypeString(), "");
					taskTypeList.add(taskTypeKey.getTaskType());
					textArrayTaskKeysRemoved.add(text);
					break;
				}			
			}
			textArrayTaskKeysRemoved.add(text);
		}
		taskStrings = textArrayTaskKeysRemoved;
		return getMostProbableTaskType(taskTypeList);
	}

	private TaskType getMostProbableTaskType(List<TaskType> taskTypeList) {
		int[] recurrences = new int[taskTypeList.size()];
		for(TaskType taskType:taskTypeList){
			recurrences[taskType.getValue()] += 1;	
		}
		int maxValueIndex = getMaxValueIndex(recurrences);
		if (maxValueIndex == -1)
			return TaskType.NotSet;
		
		return TaskType.values()[maxValueIndex];
	}

	private int getMaxValueIndex(int[] recurrences) {
		int rv = 0;
		for (int i=1; i< recurrences.length;i++){
			if(recurrences[rv] < recurrences[i])
				rv = i;
		}
		return rv;
	}
	
}
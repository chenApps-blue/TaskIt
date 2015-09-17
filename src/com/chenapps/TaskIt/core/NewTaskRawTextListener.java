package com.chenapps.TaskIt.core;

import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

import android.content.Context;

public class NewTaskRawTextListener implements INewTaskRawTextListener{

	private Context context;
	
	public NewTaskRawTextListener(Context context) {
		this.context = context;
	}
	

	@Override
	public void onRawTextReady(TaskType taskType, List<String> taskRawStrings) {
		ITaskTypeDetector taskTypeDetector = new TaskTypeDetector();
		
		if (!taskTypeDetector.processTextStrings(taskRawStrings)){
			fireTaskDetectionFailEvent();
			return;
		}
		
		ITask task = TaskCreator.creatNewTask(context, taskType);
		if(!task.ParsText(taskRawStrings)){
			fireTaskDetectionFailEvent();
			return;
		}
		
			
	}


	private void fireTaskDetectionFailEvent() {
		// TODO Auto-generated method stub	
	}
	
}
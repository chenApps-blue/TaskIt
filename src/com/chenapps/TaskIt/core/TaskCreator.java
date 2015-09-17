package com.chenapps.TaskIt.core;

import android.content.Context;

import com.chenapps.TaskIt.core.enums.TaskType;

public class TaskCreator{

	public static ITask creatNewTask(Context context,TaskType taskType) {
		switch(taskType){
		case Call:
			return new CallTask(context);
		case Meeting:
			return new MeetingTask();
		case Note:
			return new NoteTask();
		default:
			return null;			
		}
	}	
}
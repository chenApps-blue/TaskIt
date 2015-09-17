package com.chenapps.TaskIt.core;

import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

public class MeetingTask extends AbstractTask{
	
	public MeetingTask() {
		super(TaskType.Meeting); 
	}

	@Override
	public boolean ParsText(List<String> text) {
		return false;
		// TODO Auto-generated method stub
		
	}	
}
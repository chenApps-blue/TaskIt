package com.chenapps.TaskIt.core;

import java.util.List;

import com.chenapps.TaskIt.core.enums.TaskType;

public class NoteTask extends AbstractTask{
	
	public NoteTask() {
		super(TaskType.Note);
	}

	@Override
	public boolean ParsText(List<String> text) {
		return false;
	}
}
package com.chenapps.TaskIt.core.enums;

public enum TaskDetectorState{
	Idle(1), detectTaskType(2), RecordTask(3);
	
	private final int id;
	
	private TaskDetectorState(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return id;
	}
}
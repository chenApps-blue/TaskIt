package com.chenapps.TaskIt.core.enums;

public enum TaskType {
	NotSet(0), Call(1), Meeting(2), Note(3), GeneralRemainder(4);
	
	private final int id;
	
	private TaskType(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return id;
	}
}
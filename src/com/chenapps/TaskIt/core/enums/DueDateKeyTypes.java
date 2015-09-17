package com.chenapps.TaskIt.core.enums;

public enum DueDateKeyTypes {
Relative(1), Absolute(2);
	
	private final int id;
	
	private DueDateKeyTypes(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return id;
	}
}
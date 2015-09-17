package com.chenapps.TaskIt.core.enums;

public enum CallTaskActionKeysType {
	To(1), PhoneNumber(2), DueDate(3), Subject(4);
	
	private final int id;
	
	private CallTaskActionKeysType(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return id;
	}
}
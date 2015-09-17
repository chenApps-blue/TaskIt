package com.chenapps.TaskIt.core.enums;

public enum MeetingTaskSubCommands {
	participants(1), location(2), when(4), remainder(3), subject(4);
	
	private final int id;
	
	private MeetingTaskSubCommands(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return id;
	}
}
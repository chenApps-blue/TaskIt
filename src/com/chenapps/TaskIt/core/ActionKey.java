package com.chenapps.TaskIt.core;

import com.chenapps.TaskIt.core.enums.CallTaskActionKeysType;

public class ActionKey implements IActionKey{

	private CallTaskActionKeysType actionKeyType;
	private String actoinKeyString;
	

	public ActionKey(String actoinKeyString,CallTaskActionKeysType actionKeyType) {
		this.actionKeyType = actionKeyType;
		this.actoinKeyString = actoinKeyString;	
	}
	@Override
	public String getActoinKeyString() {
		return actoinKeyString;
	}

	@Override
	public CallTaskActionKeysType getActionKeyType() {
		return actionKeyType;
	}
	
}
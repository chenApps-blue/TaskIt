package com.chenapps.TaskIt.core;

import com.chenapps.TaskIt.core.enums.CallTaskActionKeysType;
public interface IActionKey{
	String getActoinKeyString();
	CallTaskActionKeysType getActionKeyType();
}
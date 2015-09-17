package com.chenapps.TaskIt.core;

import com.chenapps.TaskIt.core.enums.DueDateKeyTypes;

public interface IDueDateKey{
	String getKey();
	DueDateKeyTypes getDateType();	
	long getDateOffsetInMillis();
	long getDateInMillis();
}
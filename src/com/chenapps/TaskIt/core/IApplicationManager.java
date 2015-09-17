package com.chenapps.TaskIt.core;

import java.util.List;

public interface IApplicationManager {
	void setInsertNewTaskActivationKeys(List<String> insertNewTaskActivationKeys);
	List<String> getInsertNewTaskActivationKeys();
	
	void setInsertNewTaskDeactivationKeys(List<String> insertNewTaskDeactivationKeys);
	List<String> getInsertNewTaskDeactivationKeys();
	
	void setTaskTypeKeysList(List<ITaskTypeKey> taskTypesKeys);
	List<ITaskTypeKey> getTaskTypeKeys();
	
	List<String> getCallToActionKeys();
	List<String> getCallPhoneActionKeys();
	List<String> getCallDueDateActionKeys();
	List<String> getCallSubjectActionKeys();
	String getPhoneNumberRegexpString();
	
}

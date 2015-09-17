package com.chenapps.TaskIt.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chenapps.TaskIt.core.enums.CallTaskActionKeysType;
import com.chenapps.TaskIt.core.enums.TaskType;
import com.chenapps.TaskIt.core.textAnalysis.StringValue;

public class ApplicationManager implements IApplicationManager {

	private static final IApplicationManager instance = new ApplicationManager();

	private ApplicationManager() {
	}

	public static IApplicationManager getInstance() {
		return instance;
	}

	public static final String[] INSERT_TASK_ACTIVATION_KEY_DEFAULT_VALUE = {
			"insert new event", "insert event", "new event","הכנס אירוע חדש","הכנסת אירוע חדש","אירוע חדש","הכנס אירוע","הכנסת אירוע" };

	public static final String[] INSERT_TASK_DEACTIVATION_KEY_DEFAULT_VALUE = {
			"end event", "and event", "סיום אירוע", "סיים אירוע" };

	public static final List<ITaskTypeKey> TASK_TYPE_KEYS_DEFAULT_VALUE = new ArrayList<ITaskTypeKey>(
			Arrays.asList(new TaskTypeKey(TaskType.Call, "call"),
					new TaskTypeKey(TaskType.Call, "התקשר"),
					new TaskTypeKey(TaskType.Call, "להתקשר"),
					new TaskTypeKey(TaskType.Meeting, "פגישה"),
					new TaskTypeKey(TaskType.GeneralRemainder, "תזכורת"),
					new TaskTypeKey(TaskType.GeneralRemainder, "להזכיר"),
					new TaskTypeKey(TaskType.Note, "פתק"),
					new TaskTypeKey(TaskType.Note, "פתקה"),
					new TaskTypeKey(TaskType.Meeting, "meeting"),
					new TaskTypeKey(TaskType.Note, "note"), new TaskTypeKey(
							TaskType.Note, "not")));

	public static final List<String> CALL_TASK_TO_KEYS = new ArrayList<String>(
			Arrays.asList("to", "ל"));
	public static final List<String> CALL_TASK_PHONE_KEYS = new ArrayList<String>(
			Arrays.asList("phone number", "phone", "מספר טלפון", "טלפון"));
	public static final List<String> CALL_TASK_DUEDATE_KEYS = new ArrayList<String>(
			Arrays.asList("at"));
	public static final List<StringValue> CALL_TASK_DUEDATE_MONTH_KEYS = new ArrayList<StringValue>(
			Arrays.asList(new StringValue("january",1),
					new StringValue("february",2),
					new StringValue("march",3),
					new StringValue("april",4),
					new StringValue("may",5),
					new StringValue("june",6),
					new StringValue("july",7),
					new StringValue("august",8),
					new StringValue("september",9),
					new StringValue("october",10),
					new StringValue("november",11),
					new StringValue("december",12)));
	
	public static final List<StringValue> CALL_TASK_DUEDATE_DAY_OF_WEEK_KEYS = new ArrayList<StringValue>(
			Arrays.asList(new StringValue("sunday",1),
						new StringValue("monday",2),
						new StringValue("tuesday",3),
						new StringValue("wednesday",4),
						new StringValue("thursday",5),
						new StringValue("friday",6),
						new StringValue("saturday",7)));
		
	public static final List<String> CALL_TASK_SUBJECT_KEYS = new ArrayList<String>(
			Arrays.asList("subject", "regarding", "נושא", "בנוגע"));

	public static final long NO_SPEECH_RESTART_TIME_MS = 5000;
	public static final String NEW_SPEECH_TO_TEXT_RESULT = "com.chenapps.TaskIt.services.newtask.newSpeechToTextResults";
	public static final String SPEECH_RECOGNISER_RESULT = "SPEECH_RECOGNISER_RESULT";
	
	public static String PHONE_NUMBER_REGEXP_STRING = "^\\d{7,11}$";

	public static final int SPEECH_RECOGNAIZER_MAX_RESULTS = 3;

	private List<String> insertNewTaskActivationCommands;
	private List<String> insertNewTaskDeactivationKeys;

	private List<ITaskTypeKey> taskTypesKeysList;

	@Override
	public void setInsertNewTaskActivationKeys(
			List<String> insertNewTaskActivationKeys) {
		this.insertNewTaskActivationCommands = insertNewTaskActivationKeys;
	}

	@Override
	public List<String> getInsertNewTaskActivationKeys() {
		return insertNewTaskActivationCommands;
	}

	@Override
	public void setInsertNewTaskDeactivationKeys(
			List<String> insertNewTaskDeactivationKeys) {
		this.insertNewTaskDeactivationKeys = insertNewTaskDeactivationKeys;
	}

	@Override
	public List<String> getInsertNewTaskDeactivationKeys() {
		return insertNewTaskDeactivationKeys;
	}

	@Override
	public void setTaskTypeKeysList(List<ITaskTypeKey> taskTypesKeysList) {
		this.taskTypesKeysList = taskTypesKeysList;
	}

	@Override
	public List<ITaskTypeKey> getTaskTypeKeys() {
		return taskTypesKeysList;
	}

	@Override
	public List<String> getCallToActionKeys() {
		return CALL_TASK_TO_KEYS;
	}

	@Override
	public List<String> getCallPhoneActionKeys() {
		return CALL_TASK_PHONE_KEYS;
	}

	@Override
	public List<String> getCallDueDateActionKeys() {
		return CALL_TASK_DUEDATE_KEYS;
	}

	@Override
	public List<String> getCallSubjectActionKeys() {
		return CALL_TASK_SUBJECT_KEYS;
	}

	@Override
	public String getPhoneNumberRegexpString() {
		return PHONE_NUMBER_REGEXP_STRING;
	}
}

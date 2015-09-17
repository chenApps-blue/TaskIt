package com.chenapps.TaskIt.receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chenapps.TaskIt.core.ApplicationManager;
import com.chenapps.TaskIt.core.INewTaskRawTextListener;
import com.chenapps.TaskIt.core.ITaskTypeKey;
import com.chenapps.TaskIt.core.enums.TaskDetectorState;
import com.chenapps.TaskIt.core.enums.TaskType;
import com.chenapps.TaskIt.utils.ITaskItAudioManager;
import com.chenapps.TaskIt.utils.TaskItAudioManager;

public class TaskDetectorReceiver extends BroadcastReceiver {

	private TaskDetectorState taskDetectorState;
	private INewTaskRawTextListener newTaskRawTextListener;	
	private List<String> resultRecording;
	private ITaskItAudioManager taskItAudioManager;
	private TaskType taskType;

	public TaskDetectorReceiver(Context context,INewTaskRawTextListener newTaskRawTextListener) {
		this.newTaskRawTextListener = newTaskRawTextListener;
		taskItAudioManager = new TaskItAudioManager(context);
		taskDetectorState = TaskDetectorState.Idle;
		resultRecording = new ArrayList<String>();
		setupResultRecording();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String[] speachToTextResultList = intent
				.getStringArrayExtra(ApplicationManager.SPEECH_RECOGNISER_RESULT);
		if (speachToTextResultList == null)
			return;
		List<String> results = new ArrayList<String>(
				Arrays.asList(speachToTextResultList));
		//List<String> concatinatedReslut = ConcatinateResultsWithPreviousResults(results);
		recordResults(results);
		
		switch (taskDetectorState) {
		case Idle:
			taskType = TaskType.NotSet;
			if (!detectStartTaskSignal(results)){
				clearResultRecording();				
				break;	 
			}
			taskDetectorState = TaskDetectorState.detectTaskType;
			taskItAudioManager.playSoundStartTaskRecordingNotification();
			
		case detectTaskType:
			taskType = detectTask(results);
			if ( taskType != TaskType.NotSet) {
				taskItAudioManager.playSoundTaskTypeDetectedNotification();
				taskDetectorState = TaskDetectorState.RecordTask;				
			}
		case RecordTask:
			if (!detectEndTaskSignal(results))
				break;
			taskItAudioManager.playSoundEndTaskRecordingNotification();
			fireNewTaskRecorded();
			taskDetectorState = TaskDetectorState.Idle;
			clearResultRecording();
			break;
		default:
			break;
		}
	}

	private void fireNewTaskRecorded() {
		if(newTaskRawTextListener != null);
			newTaskRawTextListener.onRawTextReady(taskType, resultRecording);			
	}

	private void recordResults(List<String> results) {
		for (int i = 0; i < results.size()
				&& i < ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS; i++) {
			resultRecording.set(i, resultRecording.get(i)+' '+results.get(i));
		}
	}

	private void setupResultRecording() {
		resultRecording = new ArrayList<String>(ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS);
		clearResultRecording();
	}

	private void clearResultRecording() {
		resultRecording.clear();
		for(int i=0;i<ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS;i++)
			resultRecording.add("");
	}

	private TaskType detectTask(List<String> textStrings) {
		for (String text : textStrings) {
			for (ITaskTypeKey taskTypeKey : ApplicationManager.getInstance()
					.getTaskTypeKeys()) {
				if (text.contains(taskTypeKey.getTaskTypeString()))
					return taskTypeKey.getTaskType();
			}			
		}
		return TaskType.NotSet;
	}

	private boolean detectStartTaskSignal(List<String> textStrings) {

		for (String text : textStrings) {
			for (String commandString : ApplicationManager.getInstance()
					.getInsertNewTaskActivationKeys()) {
				if (text.contains(commandString)) {
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean detectEndTaskSignal(List<String> textStrings) {
		for (String text : textStrings) {
			for (String commandString : ApplicationManager.getInstance()
					.getInsertNewTaskDeactivationKeys()) {
				if (text.contains(commandString)) {
					return true;
				}
			}
		}

		return false;
	}
}

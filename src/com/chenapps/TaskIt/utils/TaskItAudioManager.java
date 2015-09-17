package com.chenapps.TaskIt.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;

public class TaskItAudioManager implements ITaskItAudioManager{

	private AudioManager mAudioManager;
	private final ToneGenerator toneGenerator = new ToneGenerator(
			AudioManager.STREAM_NOTIFICATION, 100);
	private int mStreamVolume;
	
	public TaskItAudioManager(Context context) {
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mStreamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
	}
	
	@Override
	public void activateStreemSound() {
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mStreamVolume,
				0);
	}

	@Override
	public void muteStreemSound() {
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0); 
	}

	@Override
	public void playSoundStartTaskRecordingNotification() {
		try {
			activateStreemSound();
			toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2);
			muteStreemSound();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void playSoundTaskTypeDetectedNotification() {
		try {
			activateStreemSound();
			toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
			muteStreemSound();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void playSoundEndTaskRecordingNotification() {
		try {
			activateStreemSound();
			toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
			muteStreemSound();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
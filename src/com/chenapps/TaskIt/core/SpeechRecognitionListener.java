package com.chenapps.TaskIt.core;

import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class SpeechRecognitionListener implements RecognitionListener
{
	
	private static SpeechRecognitionListener instance = null;
	
	public static SpeechRecognitionListener getInstance() {
		if (instance == null)
			instance = new SpeechRecognitionListener();

		return instance;
	 }
	
	private SpeechRecognitionListener() { }

	private IVoiceRecognitionControl voiceRecognitioncontrol;

	public void setVoiceRecognitionControl(IVoiceRecognitionControl voiceRecognitionControl) {
		this.voiceRecognitioncontrol = voiceRecognitionControl;
	}
	
	public void processVoiceCommands(String... voiceCommands) {
		voiceRecognitioncontrol.processVoiceCommands(voiceCommands);
    }
	
    @Override
    public void onBeginningOfSpeech()
    {    	
    	voiceRecognitioncontrol.activateStreemSound();
        if (voiceRecognitioncontrol.isNoSpeachCountDownTimerRunning())
            voiceRecognitioncontrol.stopNoSpeechCountDownTimer();
    }
    
    @Override
    public void onReadyForSpeech(Bundle params)
    {    	
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        	voiceRecognitioncontrol.startNoSpeechCountDownTimer();
    }

    @Override
    public void onError(int error)
    {
    	if (voiceRecognitioncontrol.isNoSpeachCountDownTimerRunning())
            voiceRecognitioncontrol.stopNoSpeechCountDownTimer();
    	
    	voiceRecognitioncontrol.stopListening();
    	voiceRecognitioncontrol.startListening();
    }

    @Override
    public void onPartialResults(Bundle partialResults)
    {
    	List<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
   	  	String[] commands = new String[matches.size()];
   	  	commands = matches.toArray(commands);
   	  	processVoiceCommands(commands);
    }

    @Override
    public void onResults(Bundle results)
    {    
    	List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
   	  	String[] commands = new String[matches.size()];
   	  	commands = matches.toArray(commands);
   	  	processVoiceCommands(commands);      	  	
    }

    @Override
    public void onRmsChanged(float rmsdB)
    {
    }
    
    
    @Override
    public void onBufferReceived(byte[] buffer)
    {
    	Log.i("tag",String.valueOf(buffer.length));
    }
    
    @Override
    public void onEndOfSpeech()
    {
    	voiceRecognitioncontrol.muteStreemSound();
    }
    
    @Override
    public void onEvent(int eventType, Bundle params)
    {

    }

}
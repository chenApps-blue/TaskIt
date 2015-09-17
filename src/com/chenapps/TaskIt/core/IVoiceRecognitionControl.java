package com.chenapps.TaskIt.core;


 public interface IVoiceRecognitionControl {

	 abstract void startNoSpeechCountDownTimer();

	 abstract void stopNoSpeechCountDownTimer();

	 abstract boolean isNoSpeachCountDownTimerRunning();

	 abstract boolean isListening();

	 abstract void stopListening();

	 abstract void startListening();

	 abstract void activateStreemSound();

	 abstract void muteStreemSound();
	
	 abstract void processVoiceCommands(String... voiceCommands);
	
}

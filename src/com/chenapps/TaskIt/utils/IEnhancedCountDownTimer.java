package com.chenapps.TaskIt.utils;

public interface IEnhancedCountDownTimer {
	void startCountDown();
	void stopCountDown();
	boolean isRunning(); 
	void setonCountDownFinishedListener (IEnhancedCountDownEventLisener listener);
}

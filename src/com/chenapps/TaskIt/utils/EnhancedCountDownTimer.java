package com.chenapps.TaskIt.utils;

import java.util.ArrayList;
import java.util.List;

import android.os.CountDownTimer;

public class EnhancedCountDownTimer extends CountDownTimer implements IEnhancedCountDownTimer{

	private boolean isRunning;
	private List<IEnhancedCountDownEventLisener> listeners = new ArrayList<IEnhancedCountDownEventLisener> ();

	public EnhancedCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		isRunning = false;
	}

	@Override
	public void onTick(long millisUntilFinished) {		
	}

	@Override
	public void onFinish() {
		
		isRunning = false;
	}

	@Override
	public void startCountDown() {
		isRunning = true;
		start();
	}

	@Override
	public void stopCountDown() {
		isRunning = false;
		cancel();
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void setonCountDownFinishedListener(
			IEnhancedCountDownEventLisener listener) {
		this.listeners.add(listener);		
	}
	
}
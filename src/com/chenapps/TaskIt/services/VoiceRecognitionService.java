package com.chenapps.TaskIt.services;

import java.util.Arrays;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.chenapps.TaskIt.core.ApplicationManager;
import com.chenapps.TaskIt.core.IVoiceRecognitionControl;
import com.chenapps.TaskIt.core.IncomingHandler;
import com.chenapps.TaskIt.core.SpeechRecognitionListener;
import com.chenapps.TaskIt.utils.EnhancedCountDownTimer;
import com.chenapps.TaskIt.utils.IEnhancedCountDownEventLisener;
import com.chenapps.TaskIt.utils.IEnhancedCountDownTimer;
import com.chenapps.TaskIt.utils.ITaskItAudioManager;
import com.chenapps.TaskIt.utils.TaskItAudioManager;

public class VoiceRecognitionService extends Service implements
		IVoiceRecognitionControl, IEnhancedCountDownEventLisener {
	public static final int MSG_RECOGNIZER_START_LISTENING = 1;
	public static final int MSG_RECOGNIZER_CANCEL = 2;
	private SpeechRecognizer mSpeechRecognizer;
	private Intent mSpeechRecognizerIntent;
	private ITaskItAudioManager taskItAudioManager;
	private boolean isListening;
	private IEnhancedCountDownTimer noSpeechCountDownTimer;

	private final Messenger mServerMessenger = new Messenger(
			new IncomingHandler(this));

	@Override
	public void onCreate() {
		super.onCreate();
		taskItAudioManager = new TaskItAudioManager(this.getApplicationContext());
		setupSpeechRecognition();
		setupNospeechCountdownTimer();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mServerMessenger.getBinder();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		activateStreemSound();
		stopListening();
		if (mSpeechRecognizer != null)
			mSpeechRecognizer.destroy();
	}

	@Override
	public void onCountDownFinished() {
		stopListening();
		startListening();
	}

	@Override
	public void muteStreemSound() {
		taskItAudioManager.muteStreemSound();
	}

	@Override
	public void activateStreemSound() {
		taskItAudioManager.activateStreemSound();
	}

	@Override
	public void startListening() {
		noSpeechCountDownTimer.startCountDown();
		mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
		isListening = true;
		muteStreemSound();
	}

	@Override
	public void stopListening() {
		stopNoSpeechCountDownTimer();
		mSpeechRecognizer.cancel();
		isListening = false;
		activateStreemSound();
	}

	public boolean sendMessage(Message message) {
		try {
			mServerMessenger.send(message);
			return true;
		} catch (RemoteException e) {
			return false;
		}
	}

	@Override
	public boolean isListening() {
		return this.isListening;
	}

	@Override
	public boolean isNoSpeachCountDownTimerRunning() {
		return noSpeechCountDownTimer.isRunning();
	}

	@Override
	public void stopNoSpeechCountDownTimer() {
		noSpeechCountDownTimer.stopCountDown();
	}

	@Override
	public void startNoSpeechCountDownTimer() {
		noSpeechCountDownTimer.startCountDown();
	}

	private void setupSpeechRecognition() {
		mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		SpeechRecognitionListener speechRecognitionListener = SpeechRecognitionListener
				.getInstance();
		speechRecognitionListener.setVoiceRecognitionControl(this);
		mSpeechRecognizer.setRecognitionListener(speechRecognitionListener);
		mSpeechRecognizerIntent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		// mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
		// Locale.getDefault());
		// mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
		// "en-US");
		// mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
		// "en-US");
		// mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,
		// true);
		mSpeechRecognizerIntent.putExtra(
				RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
		mSpeechRecognizerIntent.putExtra(
				RecognizerIntent.EXTRA_MAX_RESULTS, ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS);
	}

	private void setupNospeechCountdownTimer() {
		noSpeechCountDownTimer = new EnhancedCountDownTimer(
				ApplicationManager.NO_SPEECH_RESTART_TIME_MS,
				ApplicationManager.NO_SPEECH_RESTART_TIME_MS);
	}

	@Override
	public void processVoiceCommands(String... processResults) {
		broadcastResults(processResults);
		stopListening();
		startListening();
	}

	private void broadcastResults(String[] processResults) {
		try {
			Intent intent = new Intent();
			intent.setAction(ApplicationManager.NEW_SPEECH_TO_TEXT_RESULT);
			
			intent.putExtra(ApplicationManager.SPEECH_RECOGNISER_RESULT, sliceArray(processResults));
			sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] sliceArray(String[] processResults) {
		if (processResults.length <= ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS)
			return processResults;
		String[] rv = new String[ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS];
		Arrays.asList(processResults).subList(0, ApplicationManager.SPEECH_RECOGNAIZER_MAX_RESULTS).toArray(rv);
		
		return (rv) ;
	}
}

package com.chenapps.TaskIt.activity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chenapps.TaskIt.R;
import com.chenapps.TaskIt.core.ApplicationManager;
import com.chenapps.TaskIt.core.INewTaskRawTextListener;
import com.chenapps.TaskIt.core.NewTaskRawTextListener;
import com.chenapps.TaskIt.receiver.TaskDetectorReceiver;
import com.chenapps.TaskIt.services.VoiceRecognitionService;

public class TaskItMainActivity extends ActionBarActivity {

	private String outputFile = null;

	private int mBindFlag;
	private Messenger mServiceMessenger;
	private TextView textView;
	private INewTaskRawTextListener newTaskRawTextListener;
	private TaskDetectorReceiver taskDetectorReceiver;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ti_activity_layout__main);

		textView = (TextView) findViewById(R.id.textView);
		Button butonStart = (Button) findViewById(R.id.button__start);
		butonStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				start();
			}
		});
		Button butonStop = (Button) findViewById(R.id.button__stop);
		butonStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stop();
			}
		});

		setupVoiceRecognitionCommands();
		startService();

		outputFile = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/myrecording.3gp";
		new MediaRecorder();
	}

	private void setupVoiceRecognitionCommands() {
		ApplicationManager
				.getInstance()
				.setInsertNewTaskActivationKeys(
						Arrays.asList(ApplicationManager.INSERT_TASK_ACTIVATION_KEY_DEFAULT_VALUE));
		ApplicationManager
				.getInstance()
				.setInsertNewTaskDeactivationKeys(
						Arrays.asList(ApplicationManager.INSERT_TASK_DEACTIVATION_KEY_DEFAULT_VALUE));
		ApplicationManager.getInstance().setTaskTypeKeysList(
				ApplicationManager.TASK_TYPE_KEYS_DEFAULT_VALUE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		newTaskRawTextListener = new NewTaskRawTextListener(this);
		taskDetectorReceiver = new TaskDetectorReceiver(this, newTaskRawTextListener);
		bindService(new Intent(this, VoiceRecognitionService.class),
				mServiceConnection, mBindFlag);
		registerReceiver(uiBroadcastReceiver, new IntentFilter(
				ApplicationManager.NEW_SPEECH_TO_TEXT_RESULT));
		registerReceiver(taskDetectorReceiver, new IntentFilter(
				ApplicationManager.NEW_SPEECH_TO_TEXT_RESULT));
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(uiBroadcastReceiver);
		if (mServiceMessenger != null) {
			unbindService(mServiceConnection);
			mServiceMessenger = null;
		}
	}

	private void startService() {
		Intent service = new Intent(this, VoiceRecognitionService.class);
		this.startService(service);
		mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0
				: this.BIND_ABOVE_CLIENT;
	}

	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mServiceMessenger = new Messenger(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mServiceMessenger = null;
		}

	};
		
	private BroadcastReceiver uiBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String[] sepeachToTextResult = intent.getStringArrayExtra(ApplicationManager.SPEECH_RECOGNISER_RESULT);
			updateUI(sepeachToTextResult);
		}
	};
		
	private void updateUI(final String[] textList) {
		runOnUiThread(new Runnable() {

            @Override
            public void run() {
            	String[] result = textList;
        		textView.setText(result[0]);
            }
        });
		
	}

	protected void stop() {
		Message msg = new Message();
		msg.what = VoiceRecognitionService.MSG_RECOGNIZER_CANCEL;

		try {
			mServiceMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	private void start() {
		Message msg = new Message();
		msg.what = VoiceRecognitionService.MSG_RECOGNIZER_START_LISTENING;

		try {
			mServiceMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_it_main, menu);
		return true;
	}

	public void play(View view) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {

		MediaPlayer m = new MediaPlayer();
		m.setDataSource(outputFile);
		m.prepare();
		m.start();
		Toast.makeText(getApplicationContext(), "Playing audio",
				Toast.LENGTH_LONG).show();

	}
}

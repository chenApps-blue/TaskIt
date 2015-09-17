package com.chenapps.TaskIt.receiver;

import java.util.Date;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.chenapps.TaskIt.services.VoiceRecognitionService;

public class CallReceiver extends AbstractPhonecallReceiver{

    private Messenger mServiceMessenger;
	
	Runnable runOnServiceConnected;

	@Override
	protected void onIncomingCall(final Context ctx, String number, Date start) {
		runOnServiceConnected = new Runnable() {
		    public void run() {
		    	startSpeachrecognition(ctx);
		    	unBindService(ctx);
		    }
		};
		bindService(ctx);
	}
	
	@Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {  	
    }

	@Override	
    protected void onOutgoingCallStarted(final Context ctx, String number, Date start) {
		runOnServiceConnected = new Runnable() {
		    public void run() {
		    	startSpeachrecognition(ctx);
		    	unBindService(ctx);
		    }
		};
		bindService(ctx);
    }
    
	@Override
    protected void onIncomingCallEnded(final Context ctx, String number, Date start, Date end) {
		runOnServiceConnected = new Runnable() {
		    public void run() {
		    	endSpeechRecognition(ctx);
		    	unBindService(ctx);
		    }
		};
		bindService(ctx);
    }

	@Override
    protected void onOutgoingCallEnded(final Context ctx, String number, Date start, Date end) {
		runOnServiceConnected = new Runnable() {
		    public void run() {
		    	endSpeechRecognition(ctx);
		    	unBindService(ctx);
		    }
		};
		bindService(ctx);		
    }
		
    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
    }        
    
    private void startSpeachrecognition(Context ctx) {
    	sendmessage(VoiceRecognitionService.MSG_RECOGNIZER_START_LISTENING);                
	}
    
	private void endSpeechRecognition(Context ctx) {
    	sendmessage(VoiceRecognitionService.MSG_RECOGNIZER_CANCEL);
	}
	
	private void sendmessage(int message) {
    	Message msg = new Message();
        msg.what = message; 

        try
        {
        	mServiceMessenger.send(msg);
        } 
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
	}


    
    private final ServiceConnection mServiceConnection = new ServiceConnection()
	{ 
	    @Override
	    public void onServiceConnected(ComponentName name, IBinder service)
	    {
	        mServiceMessenger = new Messenger(service);
	        if(runOnServiceConnected != null)
	        	runOnServiceConnected.run();
	    }

	    @Override
	    public void onServiceDisconnected(ComponentName name)
	    {	        
	        mServiceMessenger = null;
	    }

	};

	private void bindService(Context ctx) {
		final Intent serviceIntent = new Intent(ctx,
    			VoiceRecognitionService.class);
    	ctx.getApplicationContext().bindService(serviceIntent,
    			mServiceConnection, Context.BIND_AUTO_CREATE);
	}

	private void unBindService(Context ctx){
		if (mServiceMessenger != null)
	    {
	        ctx.getApplicationContext().unbindService(mServiceConnection);
	        mServiceMessenger = null;
	    }
	}

    
}
package com.chenapps.TaskIt.core;

import java.lang.ref.WeakReference;

import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.chenapps.TaskIt.services.VoiceRecognitionService;

public class IncomingHandler extends Handler
{
    private WeakReference<VoiceRecognitionService> mtarget;

    public IncomingHandler(VoiceRecognitionService target)
    {
        mtarget = new WeakReference<VoiceRecognitionService>(target);
    }


    @Override
    public void handleMessage(Message msg)
    {
        final IVoiceRecognitionControl target = mtarget.get();

        switch (msg.what)
        {
            case VoiceRecognitionService.MSG_RECOGNIZER_START_LISTENING:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                	target.muteStreemSound();

	             if (!target.isListening())
	                 target.startListening();
	             break;

             case VoiceRecognitionService.MSG_RECOGNIZER_CANCEL:
                  target.stopListening();
                  break;
         }
   } 
}
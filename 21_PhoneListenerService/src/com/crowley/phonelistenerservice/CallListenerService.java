package com.crowley.phonelistenerservice;

import java.io.File;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallListenerService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(new CallListener(), PhoneStateListener.LISTEN_CALL_STATE);
		Log.i("Crowley", "Listener created....");
	}
	
	private final class CallListener extends PhoneStateListener {
		String incomingNumber = null;
		MediaRecorder recorder = null;
		File file = null;
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_RINGING://来电响铃中...
					this.incomingNumber = incomingNumber;
					Log.i("Crowley", "phone ringing....");
					break;
					
				case TelephonyManager.CALL_STATE_OFFHOOK://接通电话...
					file = new File(Environment.getExternalStorageDirectory(), this.incomingNumber + "-" + System.currentTimeMillis() + ".3gp");
					recorder = new MediaRecorder();
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					recorder.setOutputFile(file.getAbsolutePath());
					recorder.setAudioSamplingRate(96000);
					recorder.prepare();
					recorder.start();
					Log.i("Crowley", "start recording....");
					break;
					
				case TelephonyManager.CALL_STATE_IDLE://挂断电话...
					recorder.stop();
					recorder.release();
					Log.i("Crowley", "stop recording....");
					recorder = null;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	}

}

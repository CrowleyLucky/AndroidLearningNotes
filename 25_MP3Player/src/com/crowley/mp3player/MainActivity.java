package com.crowley.mp3player;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crowley.mp3player.MP3Service.ServiceBinder;

public class MainActivity extends Activity {
	private MP3Service service = null;
	private EditText etFileName = null;
	private Button btnPlay = null;
	private String fileName;
	private TextView tvTotalTime;
	private TextView tvTimeRemain;
	private TextView tvIsLoop;
	private boolean pause = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etFileName = (EditText) findViewById(R.id.file_name);
		tvTotalTime = (TextView) findViewById(R.id.total_time);
		tvTimeRemain = (TextView) findViewById(R.id.time_remain);
		tvIsLoop = (TextView) findViewById(R.id.is_loop);
		btnPlay = (Button) findViewById(R.id.play);
		fileName = etFileName.getText().toString();
		
		Intent intent = new Intent(this, MP3Service.class);
		ServiceConnection conn = new MP3ServiceConnection();
		bindService(intent, conn, BIND_AUTO_CREATE);
		
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
		
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						TimeUnit.SECONDS.sleep(1);
						if(null != service && false == pause) {
							tvTimeRemain.setText("TimeRemain:" + service.getTimeRemain());							
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();*/
	}
	
	class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				service.pauseMP3();
				pause = true;
				btnPlay.setText(R.string.btn_play);
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if(null != service && true == pause) {
					service.playMP3();
					pause = false;
					btnPlay.setText(R.string.btn_pause);
				}
				break;
			}
		}
		
	}
	
	public void playMP3(View v) {
		switch (v.getId()) {
		case R.id.play:
			if(!pause) {
				service.pauseMP3();
				pause = true;
				btnPlay.setText(R.string.btn_play);
			} else {
				service.setFileName(fileName);
				tvTotalTime.setText("time:" + service.getTotalTime());
				tvIsLoop.setText("isLoop:" + service.isLoop());
				service.playMP3();
				pause = false;
				btnPlay.setText(R.string.btn_pause);
			}
			break;
		case R.id.replay:
			service.replayMP3();
			pause = false;
			btnPlay.setText(R.string.btn_pause);
			break;
		case R.id.stop:
			service.stopMP3();
			pause = true;
			btnPlay.setText(R.string.btn_play);
			break;
		}
	}
	
	private final class MP3ServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MainActivity.this.service = ((ServiceBinder)service).getMP3Service();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
	}
	
}




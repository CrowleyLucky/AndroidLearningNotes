package com.crowley.asynctaskandhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ProgressBar asyncTaskPB;
	private ProgressBar handlerPB;
	private TextView asyncText;
	private TextView handlerText;
	private Handler handler;
	public static final int MSG_PRO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		asyncTaskPB = (ProgressBar) findViewById(R.id.asyncTask);
		handlerPB = (ProgressBar) findViewById(R.id.handler);
		asyncText = (TextView) findViewById(R.id.progressAsync);
		handlerText = (TextView) findViewById(R.id.progressHandler);
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_PRO:
					handlerPB.setProgress((Integer)msg.obj);
					handlerText.setText("HandlerProgress:" + msg.obj);
					break;
				default:
					break;
				}
			}
		};
	}
	
	
	public void startAsyncTask(View view) {
		ProgressAsyncTask task = new ProgressAsyncTask(asyncTaskPB, asyncText);
		task.execute(60);
	}
	
	public void startHandler(View view) {
		new Thread() {

			@Override
			public void run() {
				handlerPB.setMax(100);
				handlerPB.setProgress(0);
				for(int i = 0; i < 100; i++ ) {
					Message msg = new Message();
					msg.what = MSG_PRO;
					msg.obj = i;
					handler.sendMessage(msg);
					NetOperation.operate();
				}
			}
			
		}.start();
		
	}
}

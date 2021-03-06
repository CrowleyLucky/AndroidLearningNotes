package com.lmh.android.multithreaddownloader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button start;
	private Button pause;
	private ProgressBar pb;
	private Handler handler;
	private boolean isPause;
	public static final int MAX_PROGRESS = 300;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		handler = new UIHandler();
		start = (Button) findViewById(R.id.button_start);
		pause = (Button) findViewById(R.id.button_pause);
		pb = (ProgressBar) findViewById(R.id.progress_bar);
		start.setOnClickListener(new ChangeStateListener());
		pause.setOnClickListener(new ChangeStateListener());
		pb.setMax(MAX_PROGRESS);//Set the range of the progress bar to 0...max.
		pb.setProgress(0);//Set the current progress to the specified value. Does not do anything if the progress bar is in indeterminate mode.
		
	}
	
	private class ChangeStateListener implements View.OnClickListener {
		private Thread thread = null;
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.button_start:
				start.setEnabled(false);
				pause.setEnabled(true);
				if(thread == null) {
					thread = new Thread(new ProgressHandler());
					thread.start();
				}
				isPause = false;
				break;
			case R.id.button_pause:
				start.setEnabled(true);
				pause.setEnabled(false);
				isPause = true;
				break;
			}
		}
	}
	
	private class ProgressHandler implements Runnable {

		@Override
		public void run() {
			int pro = pb.getProgress();
			
			while(pro < MAX_PROGRESS) {
				try {
					pro = pb.getProgress();
					Message msg = Message.obtain();
					msg.getData().putInt("progress", pro);
					handler.sendMessage(msg);
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//不能在子线程里面刷新UI线程
			/*Looper.prepare();
			Toast.makeText(MainActivity.this.getApplicationContext(), "文件下载完成！", Toast.LENGTH_SHORT).show();
			Looper.loop();*/
		}
		
	}
	
	private class UIHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			int pro = msg.getData().getInt("progress");
			if(!isPause) pb.setProgress(pro >= MAX_PROGRESS ? pro : (pro + 1));
			if(pro == MAX_PROGRESS) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "文件下载完成！", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	

}

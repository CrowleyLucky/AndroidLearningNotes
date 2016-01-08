package com.crowley.servicelifecycle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	private ServiceConnection conn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void startService(View v) {
		Intent intent = new Intent(this, LaunchByStartServiceMethod.class);
		this.startService(intent);
	}
	
	public void bindService(View v) {
		Intent intent = new Intent(this, LaunchByBindServiceMethod.class);
		this.startService(intent);//如果先调用此方法，并将onUnbind()方法重写返回值为true，在bindService()，
									//当unbindService()之后【不会调用onDestroy(),若未先调用startService(),则unbindService()之后会调用onDestroy()】，
									//重新bindService(),则会调用onRebind()方法。
		conn = new BaseServiceConnection();
		this.bindService(intent, conn, BIND_AUTO_CREATE);
	}
	
	public void unBindService(View v) {
		if(null == conn) {
			System.out.println("already unBinded...");
			return;
		}
		System.out.println("before:" + conn);
		this.unbindService(conn);
		System.out.println("after:" + conn);
		conn = null;
	}
	
	class BaseServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("Crowley", "onServiceConnected() from bindService()...");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("Crowley", "onServiceDisconnected() from bindService()...");
		}
		
	}
}

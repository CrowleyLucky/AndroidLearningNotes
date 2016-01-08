package com.crowley.servicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LaunchByBindServiceMethod extends Service {
	private Binder binder = new ServiceBinder();

	private class ServiceBinder extends Binder {
		public LaunchByBindServiceMethod getService() {
			return LaunchByBindServiceMethod.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("Crowley", "onBind() from bindService()...");
		return binder;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Crowley", "onCreate() from bindService()...");
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d("Crowley", "onStart() from bindService()...");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Crowley", "onStartCommand() from bindService()...");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("Crowley", "onDestroy() from bindService()...");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("Crowley", "onUnbind() from bindService()...");
		//return super.onUnbind(intent);
		return true;
	}

	@Override
	public void onRebind(Intent intent) {
		Log.d("Crowley", "onRebind() from bindService()...");
		super.onRebind(intent);
	}

}

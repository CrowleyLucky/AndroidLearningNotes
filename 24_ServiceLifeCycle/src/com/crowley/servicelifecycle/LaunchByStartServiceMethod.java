package com.crowley.servicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LaunchByStartServiceMethod extends Service {
	private Binder binder = new ServiceBinder();

	private class ServiceBinder extends Binder {
		public LaunchByStartServiceMethod getService() {
			return LaunchByStartServiceMethod.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("Crowley", "onCreate() from startService()...");
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Crowley", "onCreate() from startService()...");
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d("Crowley", "onStart() from startService()...");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Crowley", "onStartCommand() from startService()...");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("Crowley", "onDestroy() from startService()...");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("Crowley", "onUnbind() from startService()...");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		Log.d("Crowley", "onRebind() from startService()...");
		super.onRebind(intent);
	}

}

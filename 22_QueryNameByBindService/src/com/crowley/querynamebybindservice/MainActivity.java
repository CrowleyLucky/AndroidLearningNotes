package com.crowley.querynamebybindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	NameQueryService nameQueryService = null;
	Button button = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.query);
		
		Intent intent = new Intent(this, NameQueryService.class);
		NameServiceConnection conn = new NameServiceConnection();
		this.bindService(intent, conn, BIND_AUTO_CREATE);
		
		
	}

	public void startQuery(View  view) {
		for(int i = 0; i <= 5; i++) {
			Log.d("Crowley", "name:" + (i + 1) + nameQueryService.getName(i));
		}
	}
	
	private class NameServiceConnection implements ServiceConnection {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("Crowley", "onServiceConnected()..." + "ComponentName:" + name);
			nameQueryService =  ((NameQueryService.NameBinder)service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("Crowley", "onServiceDisconnected()..." + "ComponentName:" + name);
		}
		
	}
}

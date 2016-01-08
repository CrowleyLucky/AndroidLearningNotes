package com.crowley.remoteserviceclientbyaidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.crowley.aidl.NameQuery;

public class MainActivity extends Activity {
	private NameQuery nameQuery = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = new Intent("com.crowley.aidl");
		NameServiceConnection conn = new NameServiceConnection();
		Log.d("Crowley", "ready to bind service...");
		this.bindService(intent, conn, BIND_AUTO_CREATE);
	}
	
	class NameServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			try {
				Log.d("Crowley", "onServiceConnected()..." + "ComponentName:" + name);
				nameQuery =  NameQuery.Stub.asInterface(service);//TODO Service里 onBind()返回的是NameBinder, 在这里为啥变成NameQuery了？
				for(int i = 0; i < 5; i++) {
					Log.d("Crowley", "name:" + nameQuery.getName(i));
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("Crowley", "onServiceDisconnected()..." + "ComponentName:" + name);
		}
		
	}

}

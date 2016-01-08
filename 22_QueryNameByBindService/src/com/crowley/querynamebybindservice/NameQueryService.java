package com.crowley.querynamebybindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class NameQueryService extends Service {
	private IBinder binder = new NameBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class NameBinder extends Binder {
		public NameQueryService getService() {
			return NameQueryService.this;
		}
	}
	
	private String[] names = new String[]{"Crowley", "LMH", "SMC", "LMG"};
	
	public String getName(int index) {
		if(index >= 0 && index < names.length) {
			return names[index];
		}
		if(5 == index) {
			Log.d("Croley", "calling stopSelf() ..");
			this.stopSelf();
		}
		return "unKnown";
	}
	
}

package com.crowley.remoteserviceserverbyaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.crowley.aidl.NameQuery;

public class NameQueryService extends Service {
	NameBinder binder = new NameBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	private final class NameBinder extends NameQuery.Stub {
		

		@Override
		public String getName(int index) throws RemoteException {
			return NameQueryService.this.getName(index);
		}
	}
	
	
	private String[] names = new String[]{"Crowley--from-AIDL", "LMH--from-AIDL", "SMC--from-AIDL", "LMG--from-AIDL"};
	
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

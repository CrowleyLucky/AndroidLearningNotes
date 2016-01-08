package com.crowley.phonelistenerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("Crowley", "phone startup...complete....");
		Intent intents = new Intent();
		intents.setClass(context, CallListenerService.class);
		context.startService(intents);
		Log.i("Crowley", "starting service...");
	}

}

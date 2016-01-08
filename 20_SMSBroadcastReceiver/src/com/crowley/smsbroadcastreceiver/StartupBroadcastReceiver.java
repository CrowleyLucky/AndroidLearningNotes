package com.crowley.smsbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("Crowley", "phone startup...complete....");
		Intent intents = new Intent();
		intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intents.setClassName("com.crowley.smsbroadcastreceiver", "com.crowley.smsbroadcastreceiver.MainActivity");
		context.startActivity(intents);
	}

}

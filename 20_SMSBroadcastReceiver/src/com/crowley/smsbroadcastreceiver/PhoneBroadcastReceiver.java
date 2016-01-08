package com.crowley.smsbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String tel = getResultData();
		Log.i("Crowley", "tel:" + tel);
		
		String newTel = "12358" + tel;
		
		if(tel.equals("5556")) {
			//abortBroadcast();//这里无法阻止广播的向下传递
			setResultData(null);
		} else {
			setResultData(newTel);
		}
	}

}

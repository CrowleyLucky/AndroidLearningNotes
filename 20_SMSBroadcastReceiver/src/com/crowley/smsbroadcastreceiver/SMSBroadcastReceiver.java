package com.crowley.smsbroadcastreceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for(Object o : pdus) {
			byte[] pdu = (byte[]) o;
			SmsMessage sms = SmsMessage.createFromPdu(pdu);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String receiveTime = format.format(new Date(sms.getTimestampMillis()));
			Log.d("Crowley", "tel:" + sms.getOriginatingAddress());
			Log.d("Crowley", "receive time:" + receiveTime);
			Log.d("Crowley", "content:" + sms.getMessageBody());
			System.out.println("hehe ~");
			if(sms.getMessageBody().equals("123")) {
				abortBroadcast();
			}
			
		}
	}

}

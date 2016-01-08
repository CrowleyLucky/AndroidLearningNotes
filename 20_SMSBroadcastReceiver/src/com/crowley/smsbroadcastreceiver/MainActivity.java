package com.crowley.smsbroadcastreceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("Crowley", "main activity");
		
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(800);
		registerReceiver(new SMSBroadcastReceiver(), filter);//注册了三次，三次都会成功，当收到广播消息之后，会依次实例化三次同一个广播接收者
		registerReceiver(new SMSBroadcastReceiver(), filter);
		registerReceiver(new SMSBroadcastReceiver(), filter);
		
		/*IntentFilter filter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
		filter.setPriority(800);
		registerReceiver(new StartupBroadcastReceiver(), filter);*/
		/*
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(1000);
		registerReceiver(new MySMSBroadcastReceiver(), filter);*/
	}
	
	class MySMSBroadcastReceiver extends BroadcastReceiver {
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
				abortBroadcast();
			}
			
		}
	}
}

package com.example.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText ticker = null;
	private EditText title = null;
	private EditText content= null;
	private EditText delay = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ticker = (EditText) this.findViewById(R.id.ticker);
		title = (EditText) this.findViewById(R.id.title);
		content = (EditText) this.findViewById(R.id.content);
		delay = (EditText) this.findViewById(R.id.delay);
		ticker.setText("ticker-text");
		title.setText("title-text");
		content.setText("content-text");
		delay.setText("0");
	}
	
	public void send(View view) {
		Toast.makeText(this, "sending now ...", Toast.LENGTH_SHORT).show();
		String tickerStr = ticker.getText().toString();
		String titleStr = title.getText().toString();
		String contentStr = content.getText().toString();
		int delayInt = Integer.parseInt(delay.getText().toString());
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10010"));
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent, 0);
		
		Notification notification = new Notification.Builder(this)
		.setTicker(tickerStr)
		.setSmallIcon(android.R.drawable.stat_notify_chat)
		.setContentTitle(titleStr)
		.setContentIntent(pendingIntent)
		.setContentText(contentStr)
		.setWhen(System.currentTimeMillis() + delayInt * 1000)
		.build();
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(0, notification);
		
		
	}
	
}

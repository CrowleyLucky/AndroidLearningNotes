package com.crowley.sendmessage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText telView = null;
	private EditText msgView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		telView = (EditText) this.findViewById(R.id.tel_num);
		msgView = (EditText) this.findViewById(R.id.msg_body);
	}

	public void sendMsg(View view) {
		String tel = telView.getText().toString();
		String msgBody = msgView.getText().toString();
		
		if(tel.equals("") || msgBody.equals("")) {
			Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		SmsManager manager = SmsManager.getDefault();
		int i = 0;
		for(String s : manager.divideMessage(msgBody)) {
			manager.sendTextMessage(tel, null, s, null, null);
			Toast.makeText(getApplicationContext(), "发送成功" + ++i, Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

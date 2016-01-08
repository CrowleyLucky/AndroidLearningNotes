package com.cowley.dialer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	EditText telNumText = null;
	Button dialButton = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		telNumText = (EditText) this.findViewById(R.id.tel_num);
		dialButton = (Button) this.findViewById(R.id.button);
	}
	
	
	public void dialNumber(View view) throws InterruptedException {
		String telNum = "";
		Toast.makeText(getBaseContext(), "Dialing", Toast.LENGTH_SHORT).show();
		
		telNum = telNumText.getText().toString();
		if(null == telNum || telNum.equals("")) {
			Toast.makeText(getBaseContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telNum));
		/*Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + telNum));*/
		
		
		startActivity(intent);
		
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

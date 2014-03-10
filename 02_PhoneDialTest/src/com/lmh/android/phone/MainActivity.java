package com.lmh.android.phone;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button normalButton = null;
	EditText telNumber = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        normalButton = (Button) this.findViewById(R.id.normal_call_button);
        telNumber = (EditText) this.findViewById(R.id.tel_number);
        
        normalButton.setOnClickListener(new NormalButtonListener());
        normalButton.setOnLongClickListener(new EmergencyButtonListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private final class NormalButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			String telNum = telNumber.getText().toString();
			if(telNum == null || telNum.equals("")) {
				Toast.makeText(MainActivity.this.getApplicationContext(), R.string.tel_empty_hint, Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.setAction("android.intent.action.CALL");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("tel:" + telNum));
			startActivity(intent);
		}
    }
    private final class EmergencyButtonListener implements View.OnLongClickListener {

		@Override
		public boolean onLongClick(View v) {
			String telNum = telNumber.getText().toString();
			if(telNum == null || telNum.equals("")) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "emergency call~", Toast.LENGTH_SHORT).show();
				return false;
			}
			Intent intent = new Intent();
			intent.setAction("android.intent.action.CALL");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("tel:" + telNum));
			startActivity(intent);
			return true;
		}
    	
    }
}

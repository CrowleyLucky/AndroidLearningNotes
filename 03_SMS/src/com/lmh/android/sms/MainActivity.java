package com.lmh.android.sms;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText telNumber = null;
	private EditText smsContent = null;
	private Button send = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        telNumber = (EditText) this.findViewById(R.id.tel_number);
        smsContent = (EditText) this.findViewById(R.id.sms_content);
        send = (Button) this.findViewById(R.id.send);
        send.setOnClickListener(new SendButtonListener());
    }


    private final class SendButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			String telNum = telNumber.getText().toString();
			String content = smsContent.getText().toString();
			System.out.println("telNum:" + telNum);
			if(telNum == null || telNum.equals("") || content == null || content.equals("")) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "请完成空白处的填写！", Toast.LENGTH_SHORT).show();
				return;
			}
			SmsManager manager = SmsManager.getDefault();
			List<String> contents = manager.divideMessage(content);
			for(String text : contents) {
				manager.sendTextMessage(telNum, null, text, null, null);
			}
			Toast.makeText(MainActivity.this.getApplicationContext(), "短信发送成功！一共发送了" + contents.size() + "条", Toast.LENGTH_LONG).show();

		}
    	
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

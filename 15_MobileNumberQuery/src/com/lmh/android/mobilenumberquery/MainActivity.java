package com.lmh.android.mobilenumberquery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button query ;
	private EditText telText;
	private TextView belongs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		query = (Button) findViewById(R.id.queryButton);
		telText = (EditText) findViewById(R.id.tel);
		belongs = (TextView) findViewById(R.id.belonging);
		query.setOnClickListener(new QueryListener());
	}
	
	private class QueryListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			belongs.setText("");
			String tel = telText.getText().toString();
			if(tel == null || tel.equals("")) {
				Toast.makeText(MainActivity.this, R.string.empty_tel, Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				String[] strs = MobileService.getMobileBelongingTo(tel).split("[�� ]");
				String content = "";
				for(String s : strs) {
					content += s + "\n";
				}
				belongs.setText(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

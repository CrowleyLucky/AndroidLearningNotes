package com.lmh.android.connectmethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText username;
	private EditText password;
	private Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new LoginListener());
	}

	private class LoginListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String usernameStr = username.getText().toString();
			String passwordStr = password.getText().toString();
			boolean isLogin;
			try {
				isLogin = UserService.login(usernameStr, passwordStr);
				if(isLogin) {
					Toast.makeText(MainActivity.this.getApplicationContext(), "µÇÂ¼³É¹¦£¡", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this.getApplicationContext(), "µÇÂ¼Ê§°Ü£¡", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}

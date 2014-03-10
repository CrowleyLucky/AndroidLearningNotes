package com.lmh.android.htmlviewer;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText url;
	private Button submit;
	private TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		url = (EditText) this.findViewById(R.id.url);
		submit = (Button) this.findViewById(R.id.button);
		content = (TextView) this.findViewById(R.id.content);
		submit.setOnClickListener(new SubmitListener());
	}
	
	private class SubmitListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String urlPath = url.getText().toString();
			try {
				String contents = new String(NetworkService.readFromPath(urlPath), "UTF-8");
				content.setText(contents);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
	}

}

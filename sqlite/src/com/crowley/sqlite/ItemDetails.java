package com.crowley.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ItemDetails extends Activity {
	private TextView tvDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_details);
		tvDetails = (TextView)findViewById(R.id.details);
		
		Intent intent = getIntent();
		
		String details = intent.getStringExtra("details");
		tvDetails.setText(details);
	}
	
	
	public void close(View view) {
		Intent intent = new Intent();
		intent.putExtra("data", "revoke successful");
		setResult(200, intent);
		this.finish();
	}

}

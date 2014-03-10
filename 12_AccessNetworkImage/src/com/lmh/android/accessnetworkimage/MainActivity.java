package com.lmh.android.accessnetworkimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText url;
	private ImageView image;
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		url = (EditText) findViewById(R.id.second);
		image = (ImageView) findViewById(R.id.fourth);
		submit = (Button) findViewById(R.id.third);
		submit.setOnClickListener(new SubmitListener());
		
	}

	private class SubmitListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String urlPath = url.getText().toString();
			byte[] byteImage;
			try {
				byteImage = ImageService.readFromPath(urlPath);
				Bitmap bitImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
				image.setImageBitmap(bitImage);
			} catch (Exception e) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "image loading failure!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
		}
		
	}

}

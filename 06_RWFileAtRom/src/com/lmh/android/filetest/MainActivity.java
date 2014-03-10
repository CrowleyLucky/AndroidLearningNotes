package com.lmh.android.filetest;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText fileName;
	private EditText fileContent;
	private Button saveToRom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fileName = (EditText) this.findViewById(R.id.file_name);
		fileContent = (EditText) this.findViewById(R.id.file_content);
		saveToRom = (Button) this.findViewById(R.id.save);
		saveToRom.setOnClickListener(new SaveButtonListener());
	}

	private final class SaveButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String fileNameStr = fileName.getText().toString();
			String fileConStringStr = fileContent.getText().toString();
			if(fileNameStr.equals("") || fileConStringStr.equals("")) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "请填写空白！", Toast.LENGTH_SHORT).show();
				return;
			}
			FileService service = new FileService(MainActivity.this.getApplicationContext());
			try {
				//service.saveToROM(fileNameStr, fileConStringStr);//保存到ROM中
				if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					service.saveToSDCard(fileNameStr, fileConStringStr);//保存到SDCard中
					Toast.makeText(MainActivity.this.getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this.getApplicationContext(), "保存失败！！！", Toast.LENGTH_SHORT).show();
				}
				
				
				
			} catch (IOException e) {
				Toast.makeText(MainActivity.this.getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
			
		}
		
	}

}

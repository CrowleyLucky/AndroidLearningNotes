package com.lmh.android.sharedpreferences;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lmh.android.sharedpreferences.service.PreferenceService;

public class MainActivity extends Activity {
	private EditText nameText;
	private EditText ageText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nameText = (EditText) this.findViewById(R.id.username);
		ageText = (EditText) this.findViewById(R.id.age);
	}

	//������������<Button>��onClick��ֵ��ͬ��������View.onClickListener�ӿ��е�onClick������ͬ��
	public void save(View v) {
		PreferenceService service = new PreferenceService(this);
		String username = nameText.getText().toString();
		String age = ageText.getText().toString();
		service.save(username, Integer.valueOf(age));
		Toast.makeText(this, "����ɹ�", Toast.LENGTH_SHORT).show();
	}
	
	//������������<Button>��onClick��ֵ��ͬ��������View.onClickListener�ӿ��е�onClick������ͬ��
	public void load(View v) {
		PreferenceService service = new PreferenceService(this);
		Map<String, String> map = service.load();
		nameText.setText(map.get("username"));
		ageText.setText(map.get("age"));
		Toast.makeText(this, "���سɹ�", Toast.LENGTH_SHORT).show();
	}
	
}

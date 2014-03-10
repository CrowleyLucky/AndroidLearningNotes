package com.lmh.android.sharedpreferences.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceService {
	private SharedPreferences preference;
	
	public PreferenceService(Context context) {
		preference = context.getSharedPreferences("user-preferences", Context.MODE_PRIVATE);
	}
	
	public void save(String username, int age) {
		Editor editor = preference.edit();
		editor.putString("username", username);
		editor.putInt("age", Integer.valueOf(age));
		editor.commit();
	}
	
	public Map<String, String> load() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", preference.getString("username", "default"));
		map.put("age", "" + preference.getInt("age", 8));
		return map;
	}
	
}

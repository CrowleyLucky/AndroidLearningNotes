package com.lmh.android.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.lmh.android.sqlite.domain.User;
import com.lmh.android.sqlite.service.UserService;

public class MainActivity extends Activity {
	private ListView listView = null;
	private UserService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) this.findViewById(R.id.list_view);
		service = new UserService(this.getApplicationContext());
		listView.setOnItemClickListener(new ItemClickListener());
		
		//showListViewWithSimpleAdapter();
		//showListViewWithSimpleCursorAdapter();
		showListViewWithUserDefinedAdapter();
	}

	private void showListViewWithUserDefinedAdapter() {
		List<User> users = service.getScrollData(0, 100);
		UserAdapter adapter = new UserAdapter(this, R.layout.user_info, users);
		listView.setAdapter(adapter);
	}

	private void showListViewWithSimpleCursorAdapter() {
		Cursor cursor = service.getCursorScrollData(0, 25);
		SimpleCursorAdapter simpleAdapter = new SimpleCursorAdapter(this.getApplicationContext(),  R.layout.user_info, cursor,
				new String[]{"_id", "username", "password", "phone"}, new int[]{R.id.user_id, R.id.username, R.id.password, R.id.phone});
		listView.setAdapter(simpleAdapter);
	}

	private void showListViewWithSimpleAdapter() {
		List<User> users = service.getScrollData(0, 32);
		List<Map<String, Object>> parsed = this.listConvert(users);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.getApplicationContext(), parsed, R.layout.user_info,
				new String[]{"id", "username", "password", "phone"}, new int[]{R.id.user_id, R.id.username, R.id.password, R.id.phone});
		listView.setAdapter(simpleAdapter);
	}

	private List<Map<String, Object>> listConvert(List<User> from) {
		List<Map<String, Object>> to = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for(User u: from) {
			map = new HashMap<String, Object>();
			map.put("id", u.getId());
			map.put("username", u.getUsername());
			map.put("password", u.getPassword());
			map.put("phone", u.getPhone());
			to.add(map);
		}
		return to;
	}
	
	private final class ItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ListView items = (ListView) parent;
			//◊‘∂®“Â  ≈‰∆˜:UserAdapter
			
			User u = (User) items.getItemAtPosition(position);
			Toast.makeText(MainActivity.this, "id:" + u.getId() + ",position:" + position, Toast.LENGTH_SHORT).show();
			
			//SimpleAdapter
			/*Map<String, Object> u = (Map<String, Object>) items.getItemAtPosition(position);
			Toast.makeText(MainActivity.this, "id:" + u.get("id") + ",position:" + position + ",name:" + u.get("username"), Toast.LENGTH_SHORT).show();*/
			
			//SimpleCursorAdapter
			/*Cursor cursor = (Cursor) items.getItemAtPosition(position);
			Toast.makeText(MainActivity.this,
					"id:" + cursor.getInt(cursor.getColumnIndex("_id")) + 
					",position:" + position + 
					",name:£©" + cursor.getString(cursor.getColumnIndex("username")), 
					Toast.LENGTH_SHORT).show();*/

		}
		
	}
}






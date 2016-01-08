package com.crowley.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listView = null;
	public static final String TAG = "Crowley";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) this.findViewById(R.id.list_view);
		showViewOption3();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView lv = (ListView) parent;
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>)lv.getItemAtPosition(position);
				//Toast.makeText(MainActivity.this, "name:" + map.get("name") + "\nsalary:" + map.get("salary") + "\nsequence:" + position, Toast.LENGTH_SHORT).show();
				String details = "name:" + map.get("name") + "\nsalary:" + map.get("salary") + "\nsequence:" + position;
				
				
				Intent intent = new Intent();
				//intent.setClass(MainActivity.this, ItemDetails.class);//method 1
				//intent.setClassName(MainActivity.this, "com.crowley.sqlite.ItemDetails");//method 2
				//intent.setClassName("com.crowley.sqlite", "com.crowley.sqlite.ItemDetails");//method 3 可用来调用其他应用activity
				//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));//method 4
				
				intent.setAction("com.crowley.test");
				intent.addCategory("com.lmh.test");
				intent.putExtra("details", details);
				//startActivity(intent);
				startActivityForResult(intent, 100);
				
			}
		});
		
		Uri uri = Uri.parse("content://com.crowley.personprovider/person");
		this.getBaseContext().getContentResolver().registerContentObserver(uri, true, new PersonContentObeserver(new Handler()));
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(this, "requestCode:" + requestCode + ", resultCode:" + resultCode + ", data:" + data.getExtras().getString("data"), Toast.LENGTH_SHORT).show();
	}
	
	private class PersonContentObeserver extends ContentObserver {

		public PersonContentObeserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Log.d(MainActivity.TAG, "I have been notified!!!!");
		}
		
	}
	
	
	private void showViewOption3() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < 80; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", "name" + i);
			map.put("number", "" + (1528845345 + new Random().nextInt(100000)));
			map.put("salary", "" + new Random(1528845345).nextInt(100000));
			list.add(map);
		}
		
		PersonAdapter adapter = new PersonAdapter(this.getBaseContext(), R.layout.item, list);
		listView.setAdapter(adapter);
		
	}

	private void showViewOption2() {
		SQLiteHelper helper = new SQLiteHelper(this, "person-db.db", null, 1);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select a.id as _id, '15523311061' number, a.* from persons as a  ", null);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, c, new String[]{"name", "number", "age"}, new int[]{R.id.name, R.id.number, R.id.salary}, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
		listView.setAdapter(adapter);
	}
	
	private void showViewOption1() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < 80; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", "name" + i);
			map.put("number", "" + (1528845345 + new Random().nextInt(100000)));
			map.put("salary", "" + new Random(1528845345).nextInt(100000));
			list.add(map);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(), list, R.layout.item, new String[]{"name", "number", "salary"}, new int[]{R.id.name, R.id.number, R.id.salary});
		listView.setAdapter(adapter);
	}
}

class PersonAdapter extends BaseAdapter {
	private Context context;
	private int resourceId;
	private List<Map<String, String>> list;
	private LayoutInflater inflater;
	
	public PersonAdapter(Context context, int resourceId, List<Map<String, String>> list) {
		this.context = context;
		this.resourceId = resourceId;
		this.list = list;
		inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	private List<Integer> ints = new ArrayList<Integer>();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name = null;
		TextView number = null;
		TextView salary = null;
		
		if(null == convertView) {
			convertView = inflater.inflate(resourceId, null);
			name = (TextView) convertView.findViewById(R.id.name);
			number = (TextView) convertView.findViewById(R.id.number);
			salary = (TextView) convertView.findViewById(R.id.salary);
			
			ViewCache cache = new ViewCache();
			cache.name = name;
			cache.number = number;
			cache.salary = salary;
			convertView.setTag(cache);
			//Log.d(MainActivity.TAG, "create view...");
		} else {
			ViewCache cache = (ViewCache) convertView.getTag();
			name = cache.name;
			number = cache.number;
			salary = cache.salary;
		}
		
		
		Map<String, String> map = list.get(position);
		name.setText(map.get("name"));
		number.setText(map.get("number"));
		salary.setText(map.get("salary"));
		Log.d(MainActivity.TAG, "create view..." + convertView.hashCode());
		
		
		if(!ints.contains(convertView.hashCode())) {
			ints.add(convertView.hashCode());
			Log.d(MainActivity.TAG, "uncached Object..." + ints.size());
		} else {
			Log.d(MainActivity.TAG, "cached Object..." + ints.size());
		}
		
		
		return convertView;
	}
	private final class ViewCache {
		public TextView name = null;
		public TextView number = null;
		public TextView salary = null;
	}
	
}





package com.lmh.android.sqlite;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lmh.android.sqlite.domain.User;

public class UserAdapter extends BaseAdapter {
	private List<User> users;
	private int resource;//绑定的条目界面ID
	private LayoutInflater inflater;
	
	public UserAdapter(Context context, int resource, List<User> users) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.users = users;
	}
	
	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	@Override//将对控件的ID进行缓存
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView idView = null;
		TextView usernameView = null;
		TextView passwordView = null;
		TextView phoneView = null;
		
		if(convertView == null) {
			convertView = inflater.inflate(resource, null);//生成条目对象
			//TODO 进入该语句块的次数为什么没有达到加载数据的条数这么多次
			//System.out.println("convertView == null:" + convertView);
			idView = (TextView) convertView.findViewById(R.id.user_id);
			usernameView = (TextView) convertView.findViewById(R.id.username);
			passwordView = (TextView) convertView.findViewById(R.id.password);
			phoneView = (TextView) convertView.findViewById(R.id.phone);
			
			ViewHolder cache = new ViewHolder();
			cache.idView = idView;
			cache.usernameView = usernameView;
			cache.passwordView = passwordView;
			cache.phoneView = phoneView;
			convertView.setTag(cache);
		} else {
			ViewHolder cache = (ViewHolder) convertView.getTag();
			
		//	System.out.println("cached ID:" + cache.idView.getText());
			
			idView = cache.idView;
			usernameView = cache.usernameView;
			passwordView = cache.passwordView;
			phoneView = cache.phoneView;
		}
		
		User user = users.get(position);
		idView.setText(user.getId().toString());
		usernameView.setText(user.getUsername());
		passwordView.setText(user.getPassword());
		phoneView.setText(user.getPhone());
		//System.out.println("new ID:" + user.getId());
		return convertView;
	}
	private static class  ViewHolder {
		TextView idView = null;
		TextView usernameView = null;
		TextView passwordView = null;
		TextView phoneView = null;
	}
	
	/*@Override  //传统做法：不对控件ID进行缓存，性能较低
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView idView = null;
		TextView usernameView = null;
		TextView passwordView = null;
		TextView phoneView = null;
		
		if(convertView == null) {
			convertView = inflater.inflate(resource, null);//生成条目对象
		}
		idView = (TextView) convertView.findViewById(R.id.user_id);
		usernameView = (TextView) convertView.findViewById(R.id.username);
		passwordView = (TextView) convertView.findViewById(R.id.password);
		phoneView = (TextView) convertView.findViewById(R.id.phone);
		
		
		System.out.println("convertView:" + convertView);
		
		User user = users.get(position);
		idView.setText(user.getId().toString());
		usernameView.setText(user.getUsername());
		passwordView.setText(user.getPassword());
		phoneView.setText(user.getPhone());
		
		return convertView;
	}*/

}




















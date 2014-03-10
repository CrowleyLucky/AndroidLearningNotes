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
	private int resource;//�󶨵���Ŀ����ID
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

	
	@Override//���Կؼ���ID���л���
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView idView = null;
		TextView usernameView = null;
		TextView passwordView = null;
		TextView phoneView = null;
		
		if(convertView == null) {
			convertView = inflater.inflate(resource, null);//������Ŀ����
			//TODO ���������Ĵ���Ϊʲôû�дﵽ�������ݵ�������ô���
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
	
	/*@Override  //��ͳ���������Կؼ�ID���л��棬���ܽϵ�
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView idView = null;
		TextView usernameView = null;
		TextView passwordView = null;
		TextView phoneView = null;
		
		if(convertView == null) {
			convertView = inflater.inflate(resource, null);//������Ŀ����
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




















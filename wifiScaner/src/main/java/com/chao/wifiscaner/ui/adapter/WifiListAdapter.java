package com.chao.wifiscaner.ui.adapter;

import java.util.ArrayList;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.utils.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WifiListAdapter extends BaseAdapter{
	private ArrayList<WifiItem> wifiItems;
	private Context mContext;
	
	public WifiListAdapter(Context context,ArrayList<WifiItem> wifiItems){
		this.mContext=context;
		this.wifiItems=wifiItems;
	}

	@Override
	public int getCount() {
		return wifiItems.size();
	}

	@Override
	public WifiItem getItem(int position) {
		return wifiItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_main_wifi, null);
		}
		TextView ssidText=ViewHolder.get(convertView, R.id.ssidText);
		TextView passwordText=ViewHolder.get(convertView, R.id.passwordText);
		ssidText.setText(wifiItems.get(position).getName());
		passwordText.setText("密码："+wifiItems.get(position).getPassword());
		return convertView;
	}

}

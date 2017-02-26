package com.chao.wifiscaner;

import android.app.Application;

import com.chao.wifiscaner.model.WifiItem;

import java.util.ArrayList;

//import com.umeng.fb.push.FeedbackPush;

public class App extends Application {
	private ArrayList<WifiItem> wifiItems;
	
	@Override
	public void onCreate() {
		super.onCreate();
		wifiItems=new ArrayList<WifiItem>();
//		initUmeng();
	}

	public ArrayList<WifiItem> getWifiItems() {
		return wifiItems;
	}

	public void setWifiItems(ArrayList<WifiItem> wifiItems) {
		this.wifiItems = wifiItems;
	}
	/*
	private void initUmeng(){
		FeedbackPush.getInstance(this).init(false);
	}
	*/
}

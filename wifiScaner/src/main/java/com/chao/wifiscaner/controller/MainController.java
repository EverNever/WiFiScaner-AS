package com.chao.wifiscaner.controller;

import com.chao.wifiscaner.action.ActionCode;
import com.chao.wifiscaner.action.CheckRootAction;
import com.chao.wifiscaner.action.GetWifiAction;
import com.chao.wifiscaner.action.ShowDialogAction;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;

public class MainController extends Controller{
	private Activity activity;

	public View loadingView;
	public View failedView;
	public View norootView;
	public View contentView;
	public View noContentView;
	public BaseAdapter adapter;
	
	public MainController(Activity activity,BaseAdapter adapter,View...viewGroup){
		this.activity=activity;
		this.adapter=adapter;
		this.loadingView=viewGroup[0];
		this.failedView=viewGroup[1];
		this.norootView=viewGroup[2];
		this.noContentView=viewGroup[3];
		this.contentView=viewGroup[4];
		
		addAction(ActionCode.CHECK_ROOT, new CheckRootAction(this));
		addAction(ActionCode.GET_WIFI, new GetWifiAction(this));
		addAction(ActionCode.SHOW_DIALOG, new ShowDialogAction(this));
	}
	
	public Activity getActivity(){
		return activity;
	}
}

package com.chao.wifiscaner.controller;

import com.chao.wifiscaner.action.ActionCode;
import com.chao.wifiscaner.action.GetPutAction;
import com.chao.wifiscaner.action.InputAction;
import com.chao.wifiscaner.action.OutputAction;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;

public class PutController extends Controller {
	protected Activity activity;
	public View loadingView;
	public View contentView;
	public View noputView;
	public View nosdcardView;
	public BaseAdapter adapter;
	
	public PutController(Activity activity,BaseAdapter adapter,View...viewGroup){
		this.activity=activity;
		this.adapter=adapter;
		this.loadingView=viewGroup[0];
		this.noputView=viewGroup[1];
		this.nosdcardView=viewGroup[2];
		this.contentView=viewGroup[3];
		
		addAction(ActionCode.GET_PUT, new GetPutAction(this));
		addAction(ActionCode.INPUT_WIFI, new InputAction(this));
		addAction(ActionCode.OUTPUT_WIFI, new OutputAction(this));
	}

	@Override
	public Activity getActivity() {
		return activity;
	}
}

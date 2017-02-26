package com.chao.wifiscaner.action;

import java.util.ArrayList;

import android.app.Activity;

import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.controller.PutController;
import com.chao.wifiscaner.model.PutItem;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.ui.PutActivity;
import com.chao.wifiscaner.view.LoadingDialog;

public abstract class PutAction extends Action {
	protected Activity activity;
	protected PutController controller;
	protected ArrayList<WifiItem> wifiItems;
	protected ArrayList<PutItem> putItems;
	protected final LoadingDialog dialog;

	public PutAction(Controller controller) {
		super(controller);
		this.controller=(PutController)controller;
		this.activity=this.controller.getActivity();
		wifiItems=((PutActivity)activity).wifiItems;
		putItems=((PutActivity)activity).putItems;
		dialog=new LoadingDialog(activity);
	}
}

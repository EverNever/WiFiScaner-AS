package com.chao.wifiscaner.action;

import android.app.Activity;

import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.controller.MainController;

public abstract class MainAction extends Action{
	protected Activity activity;
	protected MainController controller;

	public MainAction(Controller controller) {
		super(controller);
		this.controller=(MainController)controller;
		this.activity=this.controller.getActivity();
	}
}

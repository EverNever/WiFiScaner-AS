package com.chao.wifiscaner.action;

import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.utils.RootUtil;

public class CheckRootAction extends MainAction {
	
	public CheckRootAction(Controller controller) {
		super(controller);
	}
	@Override
	public void run(Object... params) {
		if(RootUtil.checkIsRoot()){
			if(RootUtil.getRootPermission()){
				controller.runAction(ActionCode.GET_WIFI);
			}else{
				activity.setContentView(controller.failedView);
			}
		}else{
			activity.setContentView(controller.norootView);
		}
	}


}

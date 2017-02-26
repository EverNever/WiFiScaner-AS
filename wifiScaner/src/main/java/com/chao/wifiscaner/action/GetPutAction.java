package com.chao.wifiscaner.action;

import java.io.File;
import java.util.ArrayList;

import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.model.PutItem;
import com.chao.wifiscaner.ui.PutActivity;
import com.chao.wifiscaner.utils.FileUtil;

public class GetPutAction extends PutAction {

	public GetPutAction(Controller controller) {
		super(controller);
	}

	@Override
	public void run(Object... params) {
		if(!FileUtil.checkSDcardExist()){
			activity.setContentView(controller.nosdcardView);
		}else if(getPutItems().size()==0){
			activity.setContentView(controller.noputView);
		}else{
			putItems.clear();
			putItems.addAll(getPutItems());
			controller.adapter.notifyDataSetChanged();
			activity.setContentView(controller.contentView);
		}
	}

	private ArrayList<PutItem> getPutItems(){
		File[] files=new File(FileUtil.getBackupPath()).listFiles();
		ArrayList<PutItem> trueFiles=new ArrayList<PutItem>();
		for(File file:files){
			if(file.getName().endsWith(".html")
					&&file.getName().startsWith("wsbak")){
						trueFiles.add(new PutItem(file));
					}
		}
		return trueFiles;
	}
}

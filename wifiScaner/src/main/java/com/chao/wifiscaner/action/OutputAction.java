package com.chao.wifiscaner.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.ui.PutActivity;
import com.chao.wifiscaner.ui.WebActivity;
import com.chao.wifiscaner.utils.FileUtil;
import com.chao.wifiscaner.utils.ToolUtil;
import com.chao.wifiscaner.view.LoadingDialog;

public class OutputAction extends PutAction {
	private String outPath;
	private MaterialDialog mDialog;
	private String timeStamp="";
	private String filename="";

	public OutputAction(Controller controller) {
		super(controller);
		dialog.setLoadingText("导出数据……");
	}

	@Override
	public void run(Object... params) {
		outPath=FileUtil.getBackupPath()+"/";
		timeStamp=ToolUtil.getCurrentTime(ToolUtil.TIME_STAMP);
		filename=getFileName();
		mDialog=new MaterialDialog(activity).setTitle("导出成功")
				.setMessage("WiFi密码："+wifiItems.size()+"\n你可以把导出的文件（"+FileUtil.getBackupPath()+"）复制到新的手机上导入")
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mDialog.dismiss();
					}
				})
				.setNegativeButton("查看", new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mDialog.dismiss();
						String url="file://"+outPath+filename+".html";
						String title=ToolUtil.getNormalTimeByStamp(timeStamp);
						Intent intent=WebActivity.creatURLIntent(activity, url, title);
						ToolUtil.startActivityWithAnim(activity, intent);
					}
				});
		new OutputTask().execute(filename);
	}
	
	class OutputTask extends AsyncTask<String, Integer, Void>{
		@Override
		protected void onPreExecute() {
			dialog.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			output(params[0], getContent());
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			//刷新一下导出列表
			controller.runAction(ActionCode.GET_PUT);
			dialog.dismiss();
			mDialog.show();
		}
	}
	
	private String getFileName(){
		//wsbak_1426852920337_5
		return "wsbak_"+timeStamp+"_"+wifiItems.size();
	}

	private String getContent(){
		StringBuilder content = new StringBuilder();
		String part1="<!DOCTYPE html><html lang='cn'><head><meta charset='UTF-8'><title>WiFi密码查看器导出文件</title></head><body><h1>WiFi密码查看器导出文件</h1><h2>";
		String part2=ToolUtil.getNormalTimeByStamp(timeStamp);
		String part3="</h2><table border='1'><tr><th>WiFi热点</th><th>密码</th></tr>";
		//part4--mainContent
		StringBuilder part4 = new StringBuilder();
		String part5="</table><h3>WiFi密码查看器，无线你的生活<br><a href='http://wifiscaner.bmob.cn/'>点此下载</a></h3></body></html>";
		
		for(WifiItem item:wifiItems){
			part4.append("<tr><td>"+item.getName()+"</td><td >"+item.getPassword()+"</td></tr>");
		}
		return content.append(part1).append(part2).append(part3).append(part4).append(part5).toString();
	}
	
	public void output(String filename,String content) {
		try {
			File f = new File(outPath+filename+".html");
			if (f.exists()) {
				System.out.print("文件存在");
			} else {
				System.out.print("文件不存在");
				f.createNewFile();
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(content);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

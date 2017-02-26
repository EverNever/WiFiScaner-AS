package com.chao.wifiscaner.action;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.utils.FileUtil;
import com.chao.wifiscaner.utils.RootUtil;

public class InputAction extends PutAction {
	private String filepath="";
	private ArrayList<WifiItem> inputWifiItems;
	private ArrayList<WifiItem> resultItems;
	private boolean isSuccess=true;

	public InputAction(Controller controller) {
		super(controller);
		dialog.setLoadingText("导入数据……");
	}

	@Override
	/**
	 * 传入文件路径
	 */
	public void run(Object... params) {
		filepath=(String) params[0];	
		new InputTask().execute(filepath);
	}
	
	class InputTask extends AsyncTask<String, Integer, Integer>{
		@Override
		protected void onPreExecute() {
			dialog.show();
		}
		@Override
		protected Integer doInBackground(String... params) {
			StringBuilder content=new StringBuilder();
			inputWifiItems=getWifiItem();
			resultItems=new ArrayList<WifiItem>();
			boolean isExist=false;
			for(WifiItem inputItem:inputWifiItems){
				isExist=false;
				for(WifiItem item:wifiItems){
					if(inputItem.getName().equals(item.getName())){
						isExist=true;
						break;
					}
				}
				if(!isExist){
					resultItems.add(inputItem);
				}
			}
			if(resultItems==null||resultItems.size()==0)return 0;
			for(WifiItem item:resultItems){
				content.append("\nnetwork={\n\tssid=\""+item.getName()+"\"\n\tpsk=\""+item.getPassword()+"\"\n\tkey_mgmt=WPA-PSK\n}\n");
			}
			isSuccess=FileUtil.appendContent(RootUtil.path, content.toString());
			return resultItems.size();
		}
		@Override
		protected void onPostExecute(Integer result) {
			dialog.dismiss();
			if(!isSuccess){
				Toast.makeText(activity, "导入失败，请检查root权限", Toast.LENGTH_SHORT).show();
			}else if(result!=0){
				Toast.makeText(activity, "导入成功，新增"+result+"个密码", Toast.LENGTH_SHORT).show();
				sendRefreshAction();
			}else{
				Toast.makeText(activity, "没有新内容", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private ArrayList<WifiItem> getWifiItem(){
		ArrayList<WifiItem> wifiItems=new ArrayList<WifiItem>();
		
		String content = new FileUtil().ReadFile(filepath);

        Pattern namePat = Pattern.compile("<td>[\\s\\S]*?</td>");
        Matcher nameMat = namePat.matcher(content);
        while(nameMat.find()){
        	String allSS=nameMat.group(0);
        	String ssid=allSS.substring(allSS.indexOf(">")+1, allSS.lastIndexOf("<"));
            wifiItems.add(new WifiItem(ssid, null));
        }
        
        Pattern passPat = Pattern.compile("<td[^>]>[\\s\\S]*?</td>");
        Matcher passMat = passPat.matcher(content);
    	int index=0;
        while(passMat.find()){
        	String allPS=passMat.group(0);
        	String password=allPS.substring(allPS.indexOf(">")+1, allPS.lastIndexOf("<"));
            wifiItems.get(index).setPassword(password);
            index++;
        }
		return wifiItems;
	}
	
	private void sendRefreshAction(){
        Intent intent = new Intent();  
        intent.setAction(ActionCode.GET_WIFI);  
        activity.sendBroadcast(intent);
	}
}

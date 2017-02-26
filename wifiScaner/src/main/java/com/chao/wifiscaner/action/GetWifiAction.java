package com.chao.wifiscaner.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.chao.wifiscaner.App;
import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.controller.MainController;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.utils.FileUtil;
import com.chao.wifiscaner.utils.RootUtil;

public class GetWifiAction extends MainAction {
	private boolean isFailed=false;

	public GetWifiAction(Controller controller) {
		super(controller);
	}

	@Override
	public void run(Object... params) {
		new GetWifiAsyncTask().execute();
	}

	class GetWifiAsyncTask extends
			AsyncTask<String, Integer, ArrayList<WifiItem>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			activity.setContentView(controller.loadingView);
		}

		@Override
		protected ArrayList<WifiItem> doInBackground(String... params) {
			//为了让重试看到点效果，这里强行休眠1秒
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getWifiItems(RootUtil.path);
		}

		@Override
		protected void onPostExecute(ArrayList<WifiItem> result) {
			super.onPostExecute(result);
			if(isFailed){
				//捕获异常，获取root失败
				activity.setContentView(controller.failedView);
			}else if (result.size() == 0) {
				// 没有内容
				activity.setContentView(controller.noContentView);
			} else {
				((App)activity.getApplication()).getWifiItems().clear();
				((App)activity.getApplication()).getWifiItems().addAll(result);
				controller.adapter.notifyDataSetChanged();
				activity.setContentView(controller.contentView);
			}
		}
	}

	private ArrayList<WifiItem> getWifiItems(String path) {
		ArrayList<WifiItem> wifiItems = new ArrayList<WifiItem>();
		BufferedReader reader = null;
		String line = null;
		WifiItem item=null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			while ((line = reader.readLine()) != null) {
				if (line.trim().equals("network={")) {
					item=new WifiItem();
					continue;
				}
				if (line.trim().startsWith("ssid=\"")) {
					item.setName(line.substring(line.indexOf("\"")+1, line.lastIndexOf("\"")));
					continue;
				}
				if (line.trim().startsWith("psk=\"")) {
					item.setPassword(line.substring(line.indexOf("\"")+1, line.lastIndexOf("\"")));
					continue;
				}
				if (line.trim().equals("}")) {
					if(item.getPassword()==null||item.getPassword().equals("")){
						item.setPassword("无");
					}
					wifiItems.add(item);
					item=null;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			isFailed=true;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return wifiItems;
	}

}

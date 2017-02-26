package com.chao.wifiscaner.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.chao.wifiscaner.App;
import com.chao.wifiscaner.R;
import com.chao.wifiscaner.action.ActionCode;
import com.chao.wifiscaner.controller.PutController;
import com.chao.wifiscaner.model.PutItem;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.ui.adapter.PutListAdapter;
import com.chao.wifiscaner.utils.ToolUtil;
import com.chao.wifiscaner.utils.ViewHolder;

import java.util.ArrayList;

public class PutActivity extends BaseActivity {
	private View loadingView;
	private View contentView;
	private View noputView;
	private View nosdcardView;

	private Button outputButton_noput;
	private Button outputButton_content;
	private ListView putList;

	private PutController controller;
	private PutListAdapter adapter;
	
	public ArrayList<PutItem> putItems;
	public ArrayList<WifiItem> wifiItems;

	@Override
	protected int getLayoutResource() {
		return R.layout.layout_put_loading;
	}

	@Override
	protected void initLayout() {
        actionbar.setDisplayHomeAsUpEnabled(true);
        
		LayoutInflater inflater = getLayoutInflater();
		loadingView = inflater.inflate(R.layout.layout_put_loading, null);
		noputView = inflater.inflate(R.layout.layout_put_noput, null);
		nosdcardView=inflater.inflate(R.layout.layout_put_nosdcard, null);
		contentView = inflater.inflate(R.layout.layout_put_content, null);

		outputButton_noput = ViewHolder.get(noputView, R.id.outputButton);
		outputButton_content = ViewHolder.get(contentView, R.id.outputButton);
		putList = ViewHolder.get(contentView, R.id.putList);

		OnClickListener outputListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				controller.runAction(ActionCode.OUTPUT_WIFI);
			}
		};
		outputButton_content.setOnClickListener(outputListener);
		outputButton_noput.setOnClickListener(outputListener);
		putList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url="file://"+putItems.get(position).getFile().getPath();
				String title=putItems.get(position).getTime();
				Intent intent=WebActivity.creatURLIntent(PutActivity.this, url, title);
				ToolUtil.startActivityWithAnim(PutActivity.this, intent);
			}
		});

	}

	@Override
	protected void initValue() {
		wifiItems=((App)getApplication()).getWifiItems();
		putItems=new ArrayList<PutItem>();
		adapter = new PutListAdapter(this, putItems,controller);
		controller = new PutController(this, adapter, loadingView, noputView,nosdcardView,
				contentView);
		//我的失误，adpater必须传入一个controller进去，但是上面那个会报空指针，只好强行set
		adapter.setController(controller);
		
		putList.setAdapter(adapter);
		controller.runAction(ActionCode.GET_PUT);
	}
	/*
	//友盟统计
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	*/
}

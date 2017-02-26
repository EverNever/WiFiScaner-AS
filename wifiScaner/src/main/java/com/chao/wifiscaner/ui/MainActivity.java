package com.chao.wifiscaner.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.chao.wifiscaner.App;
import com.chao.wifiscaner.R;
import com.chao.wifiscaner.action.ActionCode;
import com.chao.wifiscaner.controller.MainController;
import com.chao.wifiscaner.ui.adapter.WifiListAdapter;
import com.chao.wifiscaner.utils.Key;
import com.chao.wifiscaner.utils.ToolUtil;
import com.chao.wifiscaner.utils.ViewHolder;
import com.chao.yuedu.ADMediaManager;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity {
	private View loadingView;
	private View failedView;
	private View norootView;
	private View contentView;
	private View noContentView;

	private Button retryButton;
	private Button helpButton;
	private Button failedFbButton;
	private Button norootFbButton;
	private ListView wifiList;

	private MainController controller;
	private WifiListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 注册刷新广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(ActionCode.GET_WIFI);
		registerReceiver(refreshReceiver, filter);

//		initUmeng();
//		initAD();
		showLove();
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.layout_main_loading;
	}

	@Override
	protected void initLayout() {
		LayoutInflater inflater = getLayoutInflater();
		loadingView = inflater.inflate(R.layout.layout_main_loading, null);
		failedView = inflater.inflate(R.layout.layout_main_failed, null);
		norootView = inflater.inflate(R.layout.layout_main_noroot, null);
		noContentView = inflater.inflate(R.layout.layout_main_nocontent, null);
		contentView = inflater.inflate(R.layout.layout_main_content, null);

		retryButton = ViewHolder.get(failedView, R.id.retryButton);
		failedFbButton = ViewHolder.get(failedView, R.id.fbButton);
		helpButton = ViewHolder.get(norootView, R.id.helpButton);
		norootFbButton = ViewHolder.get(norootView, R.id.fbButton);
		wifiList = ViewHolder.get(contentView, R.id.wifiList);

		OnClickListener fbListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				FeedbackAgent agent = new FeedbackAgent(MainActivity.this);
//				agent.startFeedbackActivity();
				Toast.makeText(MainActivity.this, "no comment!", Toast.LENGTH_SHORT).show();
			}
		};
		failedFbButton.setOnClickListener(fbListener);
		norootFbButton.setOnClickListener(fbListener);
		retryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				controller.runAction(ActionCode.CHECK_ROOT);
			}
		});
		helpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = WebActivity.creatURLIntent(MainActivity.this,
						Key.URL_HELP, "帮助");
				ToolUtil.startActivityWithAnim(MainActivity.this, intent);
			}
		});
		wifiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				controller.runAction(ActionCode.SHOW_DIALOG,
						((App) getApplication()).getWifiItems().get(position));
			}
		});
	}

	@Override
	protected void initValue() {
		adapter = new WifiListAdapter(this,
				((App) getApplication()).getWifiItems());
		controller = new MainController(this, adapter, loadingView, failedView,
				norootView, noContentView, contentView);
		wifiList.setAdapter(adapter);
		controller.runAction(ActionCode.CHECK_ROOT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_gifts:
			ADMediaManager.show(this);
			item.setIcon(R.drawable.ic_main_menu_gifts_normal);
			break;
		case R.id.menu_settings:
			ToolUtil.startActivityWithAnim(this, SettingActivity.class);
			break;
		case R.id.menu_help:
			Intent intent = WebActivity.creatURLIntent(MainActivity.this,
					Key.URL_HELP, "帮助");
			ToolUtil.startActivityWithAnim(MainActivity.this, intent);
			break;
		case R.id.menu_fb:
//			FeedbackAgent agent = new FeedbackAgent(this);
//			agent.startFeedbackActivity();
			Toast.makeText(MainActivity.this, "no comment!", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ADMediaManager.showExitInDialog(MainActivity.this, Key.KUGUO_ID, Key.KUGUO_CHANNEL);
		}
		return false;
	}

	private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ActionCode.GET_WIFI)) {
				controller.runAction(ActionCode.GET_WIFI);
			}
		}

	};
	
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(refreshReceiver);
	}

/*
	// 友盟统计
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

	private void initUmeng() {
		//反馈同步
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
		agent.openFeedbackPush();
		boolean autoUpdate = ToolUtil.getIsAutoUpdate(this);
		if (autoUpdate) {
			UmengUpdateAgent.silentUpdate(this);
		} else {
			UmengUpdateAgent.update(this);
		}
	}

	private void initAD() {
		ADMediaManager.setListner(new ADMDListner() {
			
			@Override
			public void onMDShow() {
			}
			
			@Override
			public void onMDLoadSuccess() {
			}
			
			@Override
			public void onMDExitOutFinish() {
			}
			
			@Override
			public void onMDExitInFinish() {
				MobclickAgent.onKillProcess(MainActivity.this);
				System.exit(0);
			}
			
			@Override
			public void onMDClose() {
			}
			
			@Override
			public void onInstanll(int arg0) {
			}
		});
		ADMediaManager.load(this,Key.KUGUO_ID, Key.KUGUO_CHANNEL);
		ChaceBManager.showTopBanner(this, ChaceBManager.LEFT_BOTTOM,
				ChaceBManager.MODE_APPIN, Key.KUGUO_ID, Key.KUGUO_CHANNEL);
	}
*/

	private void showLove(){
		if(ToolUtil.getIsShowLove(this)){
			ToolUtil.setHasShowLove(this, true);
			final MaterialDialog loveDialog=new MaterialDialog(this);
			loveDialog
			.setTitle("感谢使用")
			.setMessage("亲，喜欢我们的产品吗？给个好评吧！")
			.setNegativeButton("不喜欢", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					FeedbackAgent agent = new FeedbackAgent(MainActivity.this);
//					agent.startFeedbackActivity();
					Toast.makeText(MainActivity.this, "no comment!", Toast.LENGTH_SHORT).show();
					loveDialog.dismiss();
				}
			})
			.setPositiveButton("喜欢", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					loveDialog.dismiss();
					ToolUtil.markMarketScore(MainActivity.this);
				}
			})
			.show();
		}
	}
}

package com.chao.wifiscaner.ui;

import android.os.Handler;
import android.text.format.Time;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.utils.ToolUtil;

public class WelcomeActivity extends BaseActivity {
	private boolean isFirstRun=false;

	@Override
	protected int getLayoutResource() {
		return R.layout.layout_welcome;
	}

	@Override
	protected void initLayout() {
		isFirstRun=ToolUtil.getIsFirstRun(this);
//		initUmeng();
		if(isFirstRun){
			//设置第一次运行时间
			Time t = new Time();
			t.setToNow();
			int date = t.monthDay;
			ToolUtil.setFirstDate(this,date+"");
			
			ToolUtil.startActivityWithAnim(this, IntroActivity.class);
			finish();
		}else{
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run(){
					ToolUtil.startActivityWithAnim(WelcomeActivity.this, MainActivity.class);
					WelcomeActivity.this.finish();
				}
			}, 2000);
		}
	}

	@Override
	protected void initValue() {
	}
	//友盟统计
	/*
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
	
	private void initUmeng(){
		//TODO debug模式，最后去掉
//		MobclickAgent.setDebugMode(true );
		//数据加密
		AnalyticsConfig.enableEncrypt(true);
	}
	*/
}

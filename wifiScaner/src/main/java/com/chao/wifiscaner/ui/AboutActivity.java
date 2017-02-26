package com.chao.wifiscaner.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.utils.Key;
import com.chao.wifiscaner.utils.ToolUtil;
import com.chao.wifiscaner.view.LoadingDialog;

public class AboutActivity extends BaseActivity {
	private Toolbar toolbar;
	private View markSection;
	private View updateSection;
	private View introSection;
	private View moreSection;
	private TextView meText;

	private TextView markText;
	private TextView updateText;
	private TextView introText;
	private TextView moreText;
	private ImageView moreImage;
	
	private LoadingDialog dialog;
	
	private boolean hasClickMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void initLayout() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);

		markSection = findViewById(R.id.markSection);
		updateSection = findViewById(R.id.updateSection);
		introSection = findViewById(R.id.introSection);
		moreSection = findViewById(R.id.moreSection);
		meText = (TextView) findViewById(R.id.meText);

		markText = (TextView) markSection.findViewById(R.id.title);
		updateText = (TextView) updateSection.findViewById(R.id.title);
		introText = (TextView) introSection.findViewById(R.id.title);
		moreText = (TextView) moreSection.findViewById(R.id.title);
		//小红点
		moreImage=(ImageView) moreSection.findViewById(R.id.newImage);
	}

	protected void initValue() {
		toolbar.setTitle(R.string.about_name);
		toolbar.setBackgroundColor(Color.TRANSPARENT);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		markText.setText("去评分");
		updateText.setText("检查新版本");
		introText.setText("功能介绍");
		moreText.setText("了解更多");

		meText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = WebActivity.creatURLIntent(AboutActivity.this,
						Key.URL_ME, "关于我");
				ToolUtil.startActivityWithAnim(AboutActivity.this, intent);
			}
		});
		markSection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToolUtil.markMarketScore(AboutActivity.this);
			}
		});
		updateSection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.show();
//				UmengUpdateAgent.forceUpdate(AboutActivity.this);
			}
		});
		introSection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToolUtil.startActivityWithAnim(AboutActivity.this, IntroActivity.class);
			}
		});
		moreSection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!hasClickMore) {
					moreImage.setVisibility(View.GONE);
					ToolUtil.setHasClickMore(AboutActivity.this, true);
				}
				ToolUtil.joinQQGroup(AboutActivity.this, Key.QQ_KEY);
			}
		});
		//对话框
		dialog = new LoadingDialog(this);
		dialog.setLoadingText("正在检查新版本……");
        //设置了解更多的小红点
        hasClickMore=ToolUtil.getHasClickMore(this);
        if(!hasClickMore){
            moreImage.setVisibility(View.VISIBLE);
        }
		//设置检查更新回调
		/*
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
				dialog.dismiss();
				switch (updateStatus) {
		        case UpdateStatus.Yes:
		            UmengUpdateAgent.showUpdateDialog(AboutActivity.this, updateInfo);
		            break;
		        case UpdateStatus.No:
		            Toast.makeText(AboutActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
		            break;
		        case UpdateStatus.NoneWifi:
		            Toast.makeText(AboutActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
		            break;
		        case UpdateStatus.Timeout:
		            Toast.makeText(AboutActivity.this, "连接超时，请检查网络", Toast.LENGTH_SHORT).show();
		            break;
		        }
			}
		});
		*/
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.layout_about;
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

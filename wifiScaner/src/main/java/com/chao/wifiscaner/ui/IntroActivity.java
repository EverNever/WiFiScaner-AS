package com.chao.wifiscaner.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.ui.adapter.IntroPagerAdapter;
import com.chao.wifiscaner.utils.ToolUtil;

import java.util.ArrayList;

public class IntroActivity extends BaseActivity {
	private IntroPagerAdapter adapter;
	private ViewPager introPager;
	private View page_last;
	private Button beginButton;
	
	private ArrayList<View> views;
	private ImageView[] dots;

	private int currentIndex=0;
	private boolean isFirstRun;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDots();
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.layout_intro;
	}

	@Override
	protected void initLayout() {
		LayoutInflater inflater = LayoutInflater.from(this);
		page_last=inflater.inflate(R.layout.page_intro_last, null);
		beginButton=(Button) page_last.findViewById(R.id.beginButton);
		
		introPager=(ViewPager) findViewById(R.id.introPager);
	}

	@Override
	protected void initValue() {
		isFirstRun=ToolUtil.getIsFirstRun(IntroActivity.this);
		
		views=new ArrayList<View>();
		View page_1=new View(this);
		View page_2=new View(this);
		View page_3=new View(this);
		page_1.setBackgroundResource(R.drawable.img_intro_1);
		page_2.setBackgroundResource(R.drawable.img_intro_2);
		page_3.setBackgroundResource(R.drawable.img_intro_3);
		views.add(page_1);
		views.add(page_2);
		views.add(page_3);
		views.add(page_last);
		
		adapter=new IntroPagerAdapter(this,views);
		introPager.setAdapter(adapter);
		introPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		beginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isFirstRun){
					ToolUtil.setIsFirstRun(IntroActivity.this, false);
					ToolUtil.startActivityWithAnim(IntroActivity.this, MainActivity.class);
				}
				IntroActivity.this.finish();
			}
		});
	}
	
	private void initDots() {
		LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		dots = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) dotLayout.getChildAt(i);
			dots[i].setEnabled(false);
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(true);
	}
	
	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);

		currentIndex = position;
	}
	
	@Override
	//拦截掉按键事件，防止返回到欢迎页
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
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

package com.chao.wifiscaner.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.utils.Key;
import com.chao.wifiscaner.utils.ToolUtil;

public class SettingActivity extends PreferenceActivity {
	private CheckBoxPreference checkBox;
	private PreferenceScreen put;
	private PreferenceScreen about;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		initLayout();
		initValue();
	}

	private void initLayout() {
		checkBox = (CheckBoxPreference) findPreference(Key.PF_AUTO_UPDATE);
		put = (PreferenceScreen) findPreference(Key.PF_PUT);
		about = (PreferenceScreen) findPreference(Key.PF_ABOUT);

		LinearLayout root = (LinearLayout) findViewById(android.R.id.list)
				.getParent().getParent().getParent();
		Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(
				R.layout.toolbar, root, false);
		if(root!=null){
			root.addView(toolbar, 0);
		}else{
			// TODO 2.3会报错，暂时未找到解决方案，只能不显示toolbar
		}
		toolbar.setTitle(R.string.setting_name);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		put.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				ToolUtil.startActivityWithAnim(SettingActivity.this,
						PutActivity.class);
				return true;
			}
		});
		about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				ToolUtil.startActivityWithAnim(SettingActivity.this,
						AboutActivity.class);
				return true;
			}
		});
	}

	private void initValue() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isChecked = prefs.getBoolean(Key.PF_AUTO_UPDATE, true);
		checkBox.setChecked(isChecked);
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
	*/
}

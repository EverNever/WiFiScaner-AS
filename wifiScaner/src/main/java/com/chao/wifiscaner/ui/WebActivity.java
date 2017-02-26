package com.chao.wifiscaner.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.utils.ViewHolder;

import java.util.List;

public class WebActivity extends BaseActivity {
	private View failedView;
	private View normalView;

	private WebView webView;
	private ProgressBar webProgress;
	private TextView errorText;

	private String title;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(normalView);
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.layout_web_normal;
	}

	@Override
	protected void initLayout() {
		actionbar.setDisplayHomeAsUpEnabled(true);
		LayoutInflater inflater = LayoutInflater.from(this);
		normalView = inflater.inflate(R.layout.layout_web_normal, null);
		failedView = inflater.inflate(R.layout.layout_web_failed, null);

		webView = ViewHolder.get(normalView, R.id.webView);
		webProgress = ViewHolder.get(normalView, R.id.webProgress);
		errorText = ViewHolder.get(failedView, R.id.errorText);
	}

	@Override
	protected void initValue() {
		title = getTitleIntentExtra();
		url = getURLIntentExtra();
		actionbar.setTitle(title);
		initWebView();
		webView.loadUrl(url);
	}

	@SuppressLint("NewApi")
	private void initWebView() {
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);
		WebChromeClient mChromeClient = new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				webProgress.setProgress(newProgress);
			}
		};
		webView.setWebChromeClient(mChromeClient);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				//可以下载APK
				if (url.endsWith(".apk")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					List<ResolveInfo> activitys = WebActivity.this.getPackageManager()
							.queryIntentActivities(intent,
									PackageManager.MATCH_DEFAULT_ONLY);
					if (activitys.size() > 0) {
						ActivityInfo ai = activitys.get(0).activityInfo;
						intent.setClassName(ai.packageName, ai.name);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						WebActivity.this.startActivity(intent);
					} else {
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						WebActivity.this.startActivity(intent);
					}
					return false;
				} else {
					
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				webProgress.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				webProgress.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				errorText.setText("世界上最遥远的距离就是没网，请检查你的网络连接。\n\n错误原因："
						+ description);
				WebActivity.this.setContentView(failedView);
			}
		});
		// 此方法调用防止webView 白屏
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	private String getURLIntentExtra() {
		Intent intent = getIntent();
		return intent.getStringExtra("url");
	}

	private String getTitleIntentExtra() {
		Intent intent = getIntent();
		return intent.getStringExtra("title");
	}

	public static Intent creatURLIntent(Context context, String url,
			String title) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.setClass(context, WebActivity.class);
		return intent;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// 友盟统计
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

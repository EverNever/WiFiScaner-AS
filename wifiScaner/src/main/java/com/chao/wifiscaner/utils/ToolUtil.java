package com.chao.wifiscaner.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chao.wifiscaner.R;

import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.ClipboardManager;
import android.text.format.Time;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class ToolUtil {
	public static final int TIME_STAMP = 2325;
	public static final int TIME_NORMAL = 1242;

	/**
	 * 获取当前时间，普通或者时间戳
	 * 
	 * @param mode
	 * @return time
	 */
	public static String getCurrentTime(int mode) {
		long time = System.currentTimeMillis();
		if (mode == TIME_STAMP)
			return time + "";
		return getNormalTimeByStamp(time + "");
	}

	/**
	 * 通过时间戳获取正常时间
	 * 
	 * @param stamp
	 * @return time
	 */
	public static String getNormalTimeByStamp(String stamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long stampL = Long.parseLong(stamp);
		String d = format.format(new Date(stampL));
		return d;
	}

	/**
	 * 启动Activity带动画
	 * 
	 * @param context
	 * @param cls
	 */
	public static void startActivityWithAnim(Context context, Class<?> cls) {
		Intent i = new Intent(context, cls);
		context.startActivity(i);
		((Activity) context).overridePendingTransition(R.anim.pop_in,
				R.anim.pop_out);
	}

	/**
	 * 启动activity
	 * 
	 * @param context
	 * @param intent
	 */
	public static void startActivityWithAnim(Context context, Intent intent) {
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.pop_in,
				R.anim.pop_out);
	}

	/**
	 * 复制内容到剪贴板
	 * 
	 * @param context
	 * @param content
	 * @param alertmsg
	 */
	public static void copyToClipboard(Context context, String content,
			String alertmsg) {
		ClipboardManager clip = (ClipboardManager) context
				.getSystemService(context.CLIPBOARD_SERVICE);
		clip.setText(content);
		Toast.makeText(context, alertmsg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 分享内容给好友
	 * 
	 * @param context
	 * @param title
	 * @param content
	 */
	public static void shareWithOther(Context context, String title,
			String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		context.startActivity(Intent.createChooser(intent, title));
	}

	/**
	 * 去评分
	 * 
	 * @param context
	 */
	public static void markMarketScore(Context context) {
		String mAddress = "market://details?id=" + context.getPackageName();
		Intent marketIntent = new Intent("android.intent.action.VIEW");
		marketIntent.setData(Uri.parse(mAddress));
		try {
			context.startActivity(marketIntent);
		} catch (Exception e) {
			Toast.makeText(context, "未找到应用市场", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * QQ群
	 * 
	 * @param context
	 * @param key
	 * @return success
	 */
	public static boolean joinQQGroup(Context context, String key) {
		Intent intent = new Intent();
		intent.setData(Uri
				.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D"
						+ key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
		// //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try {
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			// 未安装手Q或安装的版本不支持
			Toast.makeText(context, "未安装手机QQ或安装的版本不支持", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
	}

	// preferences的方法
	public static boolean getIsAutoUpdate(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean isAuto = prefs.getBoolean(Key.PF_AUTO_UPDATE, true);
		return isAuto;
	}

	public static boolean getIsFirstRun(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean isFirstRun = prefs.getBoolean(Key.PF_FIRST_RUN, true);
		return isFirstRun;
	}

	public static void setIsFirstRun(Context context, boolean isFirst) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(Key.PF_FIRST_RUN, isFirst);
		editor.commit();
	}

	public static String getFirstDate(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getString(Key.PF_FIRST_DATE, "");
	}

	public static void setFirstDate(Context context, String date) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(Key.PF_FIRST_DATE, date);
		editor.commit();
	}

	public static boolean getIsShowLove(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean hasShow = prefs.getBoolean(Key.PF_HAS_SHOW, false);
		if(hasShow){
			return false;
		}
		Time t = new Time();
		t.setToNow();
		int date = t.monthDay;
		
		String firstDate = prefs.getString(Key.PF_FIRST_DATE, "");
		int intFirstDate = Integer.parseInt(firstDate);
		// bug不管了
		if (date - (intFirstDate % 30) >= 2) {
			return true;
		}
		return false;
	}

	public static void setHasShowLove(Context context, boolean hasShow) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(Key.PF_HAS_SHOW, hasShow);
		editor.commit();
	}
	
	public static boolean getHasClickMore(Context context){
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getBoolean(Key.PF_SEE_MORE, false);		
	}
	public static void setHasClickMore(Context context, boolean has) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(Key.PF_SEE_MORE, has);
		editor.commit();
	}
}

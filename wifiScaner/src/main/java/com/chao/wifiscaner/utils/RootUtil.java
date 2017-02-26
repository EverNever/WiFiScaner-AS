package com.chao.wifiscaner.utils;

import java.io.DataOutputStream;
import java.io.File;

public class RootUtil {
	public final static String path = "/data/misc/wifi/wpa_supplicant.conf";

	/**
	 * 判断是否已经Root
	 * 
	 * @return isRoot
	 */
	public static boolean checkIsRoot() {
		boolean root = false;
		try {
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) {
				root = false;
			} else {
				root = true;
			}

		} catch (Exception e) {

		}
		return root;
	}

	/**
	 * 获取Root权限
	 * 
	 * @return isSuccess
	 */
	public static boolean getRootPermission() {
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + "/data/misc/wifi " + path;
			process = Runtime.getRuntime().exec("su"); // 切换到root
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}

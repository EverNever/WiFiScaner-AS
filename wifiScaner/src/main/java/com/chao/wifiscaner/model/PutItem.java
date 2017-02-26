package com.chao.wifiscaner.model;

import java.io.File;
import com.chao.wifiscaner.utils.ToolUtil;

public class PutItem {
	private File file;
	private String time;
	private int size;
		
	public PutItem(File file){
		this.file=file;
		this.time=getTime(file);
		this.size=getSize(file);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSize() {
		return size;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	private String getTime(File file){
		String name=file.getName();
		String timeStamp=name.substring(name.indexOf("_")+1,name.lastIndexOf("_"));		
		return ToolUtil.getNormalTimeByStamp(timeStamp);
	}
	
	private int getSize(File file){
		String name=file.getName();
		String size=name.substring(name.lastIndexOf("_")+1, name.lastIndexOf("."));
		return Integer.parseInt(size);
	}
}

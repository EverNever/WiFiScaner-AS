package com.chao.wifiscaner.model;

public class WifiItem {
	private String name;
	private String password;
	
	public WifiItem(String name,String password){
		this.name=name;
		this.password=password;
	}

	public WifiItem() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}

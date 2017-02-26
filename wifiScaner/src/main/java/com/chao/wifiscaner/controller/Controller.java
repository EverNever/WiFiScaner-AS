package com.chao.wifiscaner.controller;

import java.util.HashMap;

import android.app.Activity;

import com.chao.wifiscaner.action.Action;

public abstract class Controller {
	
	private final HashMap<String,Action> myIdToActionMap = new HashMap<String,Action>();

	public final void addAction(String actionId, Action action) {
		myIdToActionMap.put(actionId, action);
	}
	
	public final void runAction(String actionId, Object ... params) {
		final Action action = myIdToActionMap.get(actionId);
		if (action != null) {
			action.run(params);
		}
	}
	public abstract Activity getActivity();
}

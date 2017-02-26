package com.chao.wifiscaner.action;

import com.chao.wifiscaner.controller.Controller;

public abstract class Action {
	protected Controller controller;
	
	public Action(Controller controller){
		this.controller=controller;
	}
	
	abstract public void run(Object ... params);
}

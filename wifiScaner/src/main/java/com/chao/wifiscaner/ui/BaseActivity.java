/**
 * 来自
 * https://github.com/antoniolg/MaterialEverywhere
 */
package com.chao.wifiscaner.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public abstract class BaseActivity extends ActionBarActivity {
	protected ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        actionbar=getSupportActionBar();
        initLayout();
        initValue();
    }
    /**
     * 默认layout
     * @return resource
     */
    protected abstract int getLayoutResource();
    /**
     * 初始化layout,listener
     */
    protected abstract void initLayout();
    /**
     * 初始化view的值
     */
    protected abstract void initValue();
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId()==android.R.id.home)this.finish();
    	return super.onOptionsItemSelected(item);
    }
}

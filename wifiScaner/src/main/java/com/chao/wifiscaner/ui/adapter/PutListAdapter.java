package com.chao.wifiscaner.ui.adapter;

import java.util.ArrayList;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.action.ActionCode;
import com.chao.wifiscaner.controller.PutController;
import com.chao.wifiscaner.model.PutItem;
import com.chao.wifiscaner.utils.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PutListAdapter extends BaseAdapter {
	private ArrayList<PutItem> putItems;
	private Context context;
	private PutController controller;

	public PutListAdapter(Context context, ArrayList<PutItem> putItems,
			PutController controller) {
		this.context = context;
		this.putItems = putItems;
		this.controller = controller;
	}
	
	public void setController(PutController controller){
		this.controller=controller;
	}
	
	@Override
	public int getCount() {
		return putItems.size();
	}

	@Override
	public PutItem getItem(int position) {
		return putItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_put_output, null);
		}
		TextView timeText = ViewHolder.get(convertView, R.id.timeText);
		TextView sizeText = ViewHolder.get(convertView, R.id.sizeText);
		Button inputButton = ViewHolder.get(convertView, R.id.inputButton);
		timeText.setText(putItems.get(position).getTime());
		sizeText.setText(putItems.get(position).getSize() + "个WiFi密码");
		inputButton.setOnClickListener(new InputListener(position));
		return convertView;
	}

	class InputListener implements View.OnClickListener {
		private int position;

		public InputListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			controller.runAction(ActionCode.INPUT_WIFI, putItems.get(position)
					.getFile().getPath());
		}
	}

}

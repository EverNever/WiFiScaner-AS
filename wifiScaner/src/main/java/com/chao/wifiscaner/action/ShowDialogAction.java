package com.chao.wifiscaner.action;

import me.drakeet.materialdialog.MaterialDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chao.wifiscaner.R;
import com.chao.wifiscaner.controller.Controller;
import com.chao.wifiscaner.model.WifiItem;
import com.chao.wifiscaner.utils.ToolUtil;

public class ShowDialogAction extends MainAction {
	private WifiItem item;
	private final MaterialDialog dialog;

	public ShowDialogAction(Controller controller) {
		super(controller);
		dialog = new MaterialDialog(activity);
		initView();
	}

	@Override
	public void run(Object... params) {
		item = (WifiItem) params[0];
		dialog.setTitle(item.getName());
		dialog.show();
	}

	private void initView() {
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				R.layout.item_main_dialog);
		adapter.add("仅复制密码");
		adapter.add("复制热点和密码");
		adapter.add("分享热点和密码给好友");
		
		ListView listView = new ListView(activity);
		listView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					ToolUtil.copyToClipboard(activity, item.getPassword(),
							"密码已复制到剪贴板");
					dialog.dismiss();
					break;
				case 1:
					ToolUtil.copyToClipboard(activity, "热点：" + item.getName()
							+ "\n" + "密码：" + item.getPassword(), "热点和密码已复制到剪贴板");
					dialog.dismiss();
					break;
				case 2:
					ToolUtil.shareWithOther(activity, "分享"+item.getName()+"给好友", "热点：" + item.getName()
							+ "\n" + "密码：" + item.getPassword());
					dialog.dismiss();
					break;
				default:
					break;
				}
			}
		});
		
		dialog.setCanceledOnTouchOutside(true).setContentView(listView);
	}

}

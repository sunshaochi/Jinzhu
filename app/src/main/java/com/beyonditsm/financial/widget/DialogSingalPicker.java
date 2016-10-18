package com.beyonditsm.financial.widget;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyonditsm.financial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义选择月份
 * 
 * @author yang
 * 
 */
public class DialogSingalPicker {
	private Context context;
	private Dialog dialog;
	private Display display;
	private SexClickListener listener;
	private ArrayList<String> list = new ArrayList<>();
	public DialogSingalPicker(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	public DialogSingalPicker builder(int position) {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.single_select, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 获取自定义Dialog布局中的控件
		TextView save=(TextView) view.findViewById(R.id.adress_Save);
		final SingalPicker citypicker=(SingalPicker) view.findViewById(R.id.citypicker);
		citypicker.setDefault(position,list);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.getAdress(citypicker.getCity_string() , citypicker.getPosition());
				dialog.dismiss();
			}
		});
		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		initdata();
		return this;
	}


	private void initdata() {
		// 添加change事件
  
	}

	public DialogSingalPicker setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public DialogSingalPicker setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public void show() {
		dialog.show();
	}

	public interface SexClickListener {
		void getAdress(String adress ,int position);
	}

	public void setOnSheetItemClickListener(SexClickListener listener){
		this.listener=listener;
	}

}

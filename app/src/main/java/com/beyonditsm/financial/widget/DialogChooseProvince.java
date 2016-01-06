package com.beyonditsm.financial.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.widget.city.ProvincePicker;

/**
 * 自定义选择省
 * 
 * @author yang
 * 
 */
public class DialogChooseProvince {
	private Context context;
	private Dialog dialog;
	private Display display;
	private SexClickListener listener;

	public DialogChooseProvince(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	@SuppressWarnings("deprecation")
	public DialogChooseProvince builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.province_select, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 获取自定义Dialog布局中的控件
		final ProvincePicker citypicker= (ProvincePicker) view.findViewById(R.id.provincepicker);
		TextView save=(TextView) view.findViewById(R.id.province_Save);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.getAdress(citypicker.getCity_string());
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

	public DialogChooseProvince setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public DialogChooseProvince setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public void show() {
		dialog.show();
	}

	public interface SexClickListener {
		void getAdress(String adress);
	}

	public void setOnSheetItemClickListener(SexClickListener listener){
		this.listener=listener;
	}

}

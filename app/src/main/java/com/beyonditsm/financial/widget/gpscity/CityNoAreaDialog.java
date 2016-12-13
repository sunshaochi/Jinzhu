package com.beyonditsm.financial.widget.gpscity;

import android.annotation.SuppressLint;
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
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.beyonditsm.financial.widget.jijietong.JJTPicker;

import java.util.List;

/**（没用到）
 * Created by bitch-1 on 2016/12/13.
 */

public class CityNoAreaDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private DialogJJTAddress.SexClickListener listener;
    private List<JJTProvinceEntity> provinceEntityList;
    private JJTPicker jjtPicker;

    public CityNoAreaDialog(Context context, List<JJTProvinceEntity> provinceEntityList) {
        this.context = context;
        this.provinceEntityList = provinceEntityList;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    @SuppressLint({"InflateParams", "RtlHardcoded"})
    @SuppressWarnings("deprecation")
    public CityNoAreaDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.gps_city_select, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        TextView save=(TextView) view.findViewById(R.id.adress_Save);
        jjtPicker=(JJTPicker) view.findViewById(R.id.citypicker);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.getAdress(jjtPicker.getListAddress(),jjtPicker.getListAddressId());
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

    public JJTPicker getJJTPicker(){
        return this.jjtPicker;
    }

    private void initdata() {
        // 添加change事件

    }

    public CityNoAreaDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CityNoAreaDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public interface SexClickListener {
        void getAdress(List<String> adress,List<Integer> id);
    }

    public void setOnSheetItemClickListener(DialogJJTAddress.SexClickListener listener){
        this.listener=listener;
    }

}

package com.beyonditsm.financial.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;

import java.util.List;

/**
 * 自定义dialog，基本信息不符合的dialog
 * Created by Administrator on 2016/10/20 0020.
 */

public class CoustomDialog {

    private Context context;
    private Display display;
    private Dialog dialog;
    private ListView lvUnnatural;
    private List<String> list;

    public CoustomDialog(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public CoustomDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.coustom_layout, null);


        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
        TextView tvLook = (TextView) view.findViewById(R.id.tv_look);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        lvUnnatural = (ListView) view.findViewById(R.id.lv_unnatural);


        dialog = new Dialog(context, R.style.serviceNoteOperationDialog);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        params.width = d.getWidth() - 50;
        dialogWindow.setAttributes(params);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.x = 0;
//        lp.y = 0;
//        dialogWindow.setAttributes(lp);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("position",String.valueOf(1));
                context.startActivity(intent);
            }
        });
        lvUnnatural.setAdapter(new MyAdapter());
        return this;
    }

    private class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView==null) {
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(R.layout.lv_coustomdialog_item, null);
                holder.tvItem = (TextView) convertView.findViewById(R.id.tv_coustomItem);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            if (!TextUtils.isEmpty(list.get(position))) {
                holder.tvItem.setText(list.get(position));
            }
            return convertView;
        }

        class Holder{
            TextView tvItem;
        }
    }

    public CoustomDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CoustomDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }
    public void show(){
        dialog.show();
    }

}

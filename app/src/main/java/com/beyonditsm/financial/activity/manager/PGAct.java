package com.beyonditsm.financial.activity.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 任务评估界面
 * Created by Yang on 2015/11/24 0024.
 */
public class PGAct extends BaseActivity{
    private static final int RESULT = 0;
    @ViewInject(R.id.pg_lv)
    private ListView pg_lv;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_pg);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        MyAdapter adapter = new MyAdapter();
        pg_lv.setAdapter(adapter);
        pg_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                setResult(RESULT,intent);
                finish();
            }
        });
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(PGAct.this,R.layout.pg_item,null);
            return view;
        }
    }
}

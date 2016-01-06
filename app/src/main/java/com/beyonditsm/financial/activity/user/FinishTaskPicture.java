package com.beyonditsm.financial.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.photo.PicSelectActivity;
import com.beyonditsm.financial.adapter.GvPhotoAdapter;
import com.beyonditsm.financial.entity.ImageBean;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/11/18.
 */


public class FinishTaskPicture extends BaseActivity {


    @ViewInject(R.id.iv_pic)
    private ImageView ivPhoto;
    @ViewInject(R.id.tv_place)
    private TextView tv_place;
    @ViewInject(R.id.gv_pic)
    private GridView gv_pic;

    private MyAdapter adapter;

    private List<TaskEntity> list;
    private List<String> urlList;
    private int position;//位置

//    private BitmapUtils bitmapUtils;
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    @Override
    public void setLayout() {
        setContentView(R.layout.finish_taskpic_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setTopTitle("任务详情");
        setLeftTv("返回");
        list=new ArrayList<TaskEntity>();
        urlList=new ArrayList<String>();
        list=getIntent().getParcelableArrayListExtra("list");
        position=getIntent().getIntExtra("position", -1);
        for (int i=0;i<list.size();i++){
            urlList.add(IFinancialUrl.BASE_IMAGE_URL+list.get(i).getFieldValue());
        }
        String url=IFinancialUrl.BASE_IMAGE_URL+list.get(0).getFieldValue();
        tv_place.setText(list.get(0).getFieldName());
    //    adapter=new MyAdapter(urlList,getApplicationContext());
        ImageLoader.getInstance().displayImage(url,ivPhoto,options);
//        bitmapUtils=new BitmapUtils(getApplicationContext());
//        bitmapUtils.display(ivPhoto,url);
    }

    private class MyAdapter extends BaseAdapter{
        List<String> list;
        Context context;
        private MyAdapter(List<String> list,Context context){
            this.list=list;
            this.context=context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(context,R.layout.photo_gv_item,parent);
            ImageView imageView= (ImageView) convertView.findViewById(R.id.ivPhoto);
            ImageLoader.getInstance().displayImage(list.get(position),imageView,options);
            return convertView;
        }
    }



}

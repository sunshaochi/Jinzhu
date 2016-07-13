package com.beyonditsm.financial.activity.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/11/18
 */
public class FinishTaskPicture extends BaseActivity {


    @ViewInject(R.id.iv_pic)
    private ImageView ivPhoto;
    @ViewInject(R.id.tv_place)
    private TextView tv_place;

    //    private BitmapUtils bitmapUtils;
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ava_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ava_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ava_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    @Override
    public void setLayout() {
        setContentView(R.layout.finish_taskpic_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setTopTitle("任务详情");
        setLeftTv("返回");
        List<String> urlList = new ArrayList<>();
        List<TaskEntity> list =getIntent().getParcelableArrayListExtra("list");
        for (int i = 0; i< list.size(); i++){
            urlList.add(IFinancialUrl.BASE_IMAGE_URL+ list.get(i).getFieldValue());
        }
        String url=IFinancialUrl.BASE_IMAGE_URL+ list.get(0).getFieldValue();
        tv_place.setText(list.get(0).getFieldName());
    //    adapter=new MyAdapter(urlList,getApplicationContext());
        ImageLoader.getInstance().displayImage(url,ivPhoto,options);
//        bitmapUtils=new BitmapUtils(getApplicationContext());
//        bitmapUtils.display(ivPhoto,url);
    }

}

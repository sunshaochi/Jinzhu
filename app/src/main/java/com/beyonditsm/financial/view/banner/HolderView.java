package com.beyonditsm.financial.view.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

/**
 * Created by Administrator on 2016/9/9.
 */
public class HolderView implements Holder<String> {

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

//        imageView.setAdjustViewBounds(true);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
//        imageView.setImageResource(R.mipmap.ic_default_adimage);
        if (data.startsWith("http://")){
            ImageLoader.getInstance().displayImage(data,imageView,options);
        }else{
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL+data,imageView,options);
        }

    }
}

package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.PictureInfo;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;

/**
 * Created by wangbin on 15/12/17
 */
public class PhotoAdapter extends BaseAdapter{
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private Context context;
    private List<PictureInfo.DataEntity.RowsEntity
            > list;
    public PhotoAdapter(Context context,List<PictureInfo.DataEntity.RowsEntity> list){
        this.context=context;
        this.list=list;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.photo_gv_item, null);
            viewHolder.ivPhoto = (ImageView) convertView
                    .findViewById(R.id.ivPhoto);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL+list.get(position).getAccessoryPath(),viewHolder.ivPhoto,options);
        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
    }
}

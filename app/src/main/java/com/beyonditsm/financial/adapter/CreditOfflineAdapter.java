package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.CreditOfflineDetil;
import com.beyonditsm.financial.fragment.listener.CreditOfflineReloadListener;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.util.Uitls;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;

/**
 * Created by xuleyuan on 2016/8/16.
 */
public class CreditOfflineAdapter extends BaseAdapter {
    private Context context;
    private List<CreditOfflineDetil.ImagesBean> list;
    private final String CHANGEABLE = "3";
    private final String UNCHANGEABLE = "2";
    CreditOfflineReloadListener creditListener;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    public CreditOfflineAdapter(Context context, List<CreditOfflineDetil.ImagesBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyDataChange(List<CreditOfflineDetil.ImagesBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.gv_credit_offline_item, null);
            holder.tvTag = (TextView) convertView.findViewById(R.id.tv_tag);
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_uploaded);
            holder.btnReload = (Button) convertView.findViewById(R.id.btn_reUpload);
            holder.llBg = (LinearLayout) convertView.findViewById(R.id.ll_credit_offline_bg);
            holder.llDownBg = (LinearLayout) convertView.findViewById(R.id.ll_down_bg);
            holder.divider = convertView.findViewById(R.id.v_divider);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (list.get(position).getSts()) {
            case CHANGEABLE:
                changeableGetView(position, holder);
                break;
            case UNCHANGEABLE:
                unChangeableGetView(position, holder);
                break;
            default:
                unChangeableGetView(position, holder);
                break;
        }

        return convertView;
    }

    public void setCreditListener(CreditOfflineReloadListener creditOfflineReloadListener) {
        this.creditListener = creditOfflineReloadListener;
    }

    private void unChangeableGetView(final int position, ViewHolder holder) {

        holder.llBg.setBackgroundResource(R.drawable.bg_black_stroke);
        holder.tvTag.setBackgroundResource(R.color.address_color);
        holder.divider.setBackgroundResource(R.color.tv_third_color);
        holder.llDownBg.setBackgroundResource(R.color.gray_rom);

        holder.tvTag.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + list.get(position).getImgUrl(), holder.ivPic, options);
        holder.btnReload.setVisibility(View.GONE);

    }

    private void changeableGetView(final int position, ViewHolder holder) {
        holder.tvTag.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + list.get(position).getImgUrl(), holder.ivPic, options);
        holder.btnReload.setVisibility(View.VISIBLE);
        holder.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditListener.onReload(list.get(position).getId(), list.get(position).getName(), list.get(position).getImgUrl());
            }
        });
    }


    class ViewHolder {
        TextView tvTag;
        ImageView ivPic;
        Button btnReload;
        LinearLayout llBg;
        LinearLayout llDownBg;
        View divider;
    }
}


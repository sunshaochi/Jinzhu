package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.PhoneInfo;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by gxy on 2015/11/24
 */
public class AddressBookAdapter extends BaseAdapter implements SectionIndexer{
    private LayoutInflater inflater=null;
    private List<PhoneInfo> list=null;
    BitmapUtils bitmapUtils=null;


    public AddressBookAdapter(List<PhoneInfo> list,Context context){
        this.list=list;
        inflater=LayoutInflater.from(context);
        bitmapUtils=new BitmapUtils(context);
    }

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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final PhoneInfo mContent = list.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.addressbook_item,null);
            holder=new ViewHolder();
            holder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phonenumber= (TextView) convertView.findViewById(R.id.tv_phonenumber);
            holder.btn_invate= (Button) convertView.findViewById(R.id.btn_invate);
            holder.tv_aregister= (TextView) convertView.findViewById(R.id.tv_aregister);
            holder.tv_zm= (TextView) convertView.findViewById(R.id.tv_zm);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        if(list.size()!=0){
            //根据position获取分类的首字母的char ascii值
            int section = getSectionForPosition(position);
            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if(position == getPositionForSection(section)){
                holder.tv_zm.setVisibility(View.VISIBLE);
                holder.tv_zm.setText(mContent.getSortLetters());
            }else{
                holder.tv_zm.setVisibility(View.GONE);
            }
            holder.tv_name.setText(list.get(position).getName());
            holder.tv_phonenumber.setText(list.get(position).getPhoneNumber());
         /*   if(list.get(position).getImage()==null){
                holder.tv_name.setText(list.get(position).getName().toString());
                holder.tv_phonenumber.setText(list.get(position).getPhoneNumber().toString());
            }else {
             //   holder.iv_head.setImageBitmap(list.get(position).getImage());
                holder.tv_name.setText(list.get(position).getName().toString());
                holder.tv_phonenumber.setText(list.get(position).getPhoneNumber().toString());
            }*/

        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    static class ViewHolder{
        public ImageView iv_head;
        public TextView tv_name;
        public TextView tv_phonenumber;
        public Button btn_invate;
        public TextView tv_aregister;
        public TextView tv_zm;
    }
}

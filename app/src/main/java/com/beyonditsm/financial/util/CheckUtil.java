package com.beyonditsm.financial.util;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by xuleyuan on 2016/10/21.
 */

public class CheckUtil {
    /**
     * 遍历Layout查看是否内部元件有未填写的控件，若有，则
     * 返回其TAG
     */
    public static String CheckOutNull(ViewGroup viewGroup){

       for (int i =0;i<viewGroup.getChildCount();i++){
           if (viewGroup.getChildAt(i)!=null && viewGroup.getChildAt(i) instanceof ViewGroup){
               if (CheckOutNull((ViewGroup) viewGroup.getChildAt(i)) !=null){
                   return CheckOutNull((ViewGroup) viewGroup.getChildAt(i));
               }
           }else {
               View view = viewGroup.getChildAt(i);
               if (view instanceof TextView){
                   if (view instanceof EditText){
                       EditText editText = (EditText)view;
                       if (TextUtils.isEmpty(editText.getText()) && editText.getTag() != null && !(editText.getTag()+"").equals("")){
                           MyLogUtils.info(editText.getTag()+"");
                           return editText.getTag()+"";
                       }
                   }else {
                       TextView textView = (TextView) view;
                       if ((textView.getHint()+"").equals("请选择") && TextUtils.isEmpty(textView.getText()+"") && textView.getTag() != null && !(textView.getTag()+"").equals("")){
                           return textView.getTag()+"";
                       }
                   }

               }

           }
       }
        return null;
    }

}

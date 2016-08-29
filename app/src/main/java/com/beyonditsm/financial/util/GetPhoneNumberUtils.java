package com.beyonditsm.financial.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.beyonditsm.financial.entity.PhoneInfo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/11/25.
 */
public class GetPhoneNumberUtils {
    public static List<PhoneInfo> lists;


    public static List<PhoneInfo> getNumber(Context context){
        @SuppressLint("Recycle")
        Cursor cursor=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String phoneNumber;
        String phoneName;
        Bitmap image;
        lists= new ArrayList<>();
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //    image=getHighPhoto(cursor.getPosition() + "", context.getContentResolver());
                //    PhoneInfo phoneInfo=new PhoneInfo(phoneName, phoneNumber,image);
                PhoneInfo phoneInfo = new PhoneInfo(phoneName, phoneNumber);
                lists.add(phoneInfo);
            }
        }
        return lists;
    }

    /**
     * 获取联系人高清头像
     * @param people_id 联系人ID
     * @param cr 调用容器
     * @return 联系人的高清头像
     */
    public static Bitmap getHighPhoto(String people_id, ContentResolver cr) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(people_id));
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri, true);
        if (input == null) {
            return null;
        }
        return BitmapFactory.decodeStream(input);
    }

}

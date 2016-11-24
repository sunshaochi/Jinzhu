package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.AddressBookAdapter;
import com.beyonditsm.financial.entity.PhoneInfo;
import com.beyonditsm.financial.util.CharacterParserUtils;
import com.beyonditsm.financial.util.GetPhoneNumberUtils;
import com.beyonditsm.financial.util.PinyinComparator;
import com.beyonditsm.financial.util.SideBarUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gxy on 2015/11/24
 */

public class AddressBookAct extends BaseActivity {

    @ViewInject(R.id.lv_addressbook)
    private ListView lv_addressbook;
    @ViewInject(R.id.sidrbar)
    private SideBarUtils sideBar;
    @ViewInject(R.id.dialog)
    private TextView dialog;
    private CharacterParserUtils characterParser;
    private AddressBookAdapter adapter;
    private List<PhoneInfo> list;

    @Override
    public void setLayout() {
        setContentView(R.layout.addressbook_friends);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("通讯录朋友");
        setLeftTv("返回");
        characterParser = CharacterParserUtils.getInstance();
        PinyinComparator pinyinComparator = new PinyinComparator();

//        GetPhoneNumberUtils.getNumber(getApplicationContext());
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBarUtils.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_addressbook.setSelection(position);
                }

            }
        });
        list = filledData(GetPhoneNumberUtils.getNumber(getApplicationContext()));
        Collections.sort(list, pinyinComparator);
        adapter = new AddressBookAdapter(list, getApplicationContext());
        lv_addressbook.setAdapter(adapter);
        lv_addressbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone_num = list.get(position).getPhoneNumber();
            }
        });
    }

    private List<PhoneInfo> filledData(List<PhoneInfo> infoList) {
        List<PhoneInfo> mSortList = new ArrayList<>();

        for (int i = 0; i < infoList.size(); i++) {
            PhoneInfo sortModel = new PhoneInfo();
            sortModel.setName(infoList.get(i).getName());
            sortModel.setPhoneNumber(infoList.get(i).getPhoneNumber());
            //    sortModel.setImage(infoList.get(i).getImage());


            String pinyin = characterParser.getSelling(infoList.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }



    private void sendMess(String message, String number) {
        Uri smsToUri = Uri.parse("smsto:" + number);
        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", message);
        startActivity(mIntent);
    }

}

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
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.manager.ManagerMainAct;
import com.beyonditsm.financial.adapter.AddressBookAdapter;
import com.beyonditsm.financial.db.FriendDao;
import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.PhoneInfo;
import com.beyonditsm.financial.fragment.FriendFrg;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.CharacterParserUtils;
import com.beyonditsm.financial.util.GetPhoneNumberUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.PinyinComparator;
import com.beyonditsm.financial.util.SideBarUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.rong.imkit.RongIM;

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
                addFriend(phone_num.replace("-", "").replace(" ", ""));
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


    private void addFriend(final String phone) {
        RequestManager.getCommManager().addFriend(phone, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonData = jsonObject.optJSONObject("data");
                String status = jsonData.optString("status");
                if ("noRegister".equals(status)) {

                    sendMess("邀请注册", phone);
                } else if ("noFirend".equals(status)) {
                    sendBroadcast(new Intent(FriendFrg.UPDATA));
                    String roleName = SpUtils.getRoleName(getApplicationContext());
                    switch (roleName) {
                        case "ROLE_CREDIT_MANAGER":
                            sendBroadcast(new Intent(ManagerMainAct.UPDATATAB));
                            break;
                        case "ROLE_COMMON_CLIENT":
                            sendBroadcast(new Intent(MainActivity.UPDATATAB));
                            break;
                        default:
                            sendBroadcast(new Intent(MainActivity.UPDATATAB));
//                        sendBroadcast(new Intent(ServiceMainAct.UPDATATAB));
                            break;
                    }
                    MyToastUtils.showShortToast(getApplicationContext(), jsonData.optString("message"));
                } else  {
                    JSONObject data = jsonData.getJSONObject("data");
                    String rcNickname = data.optString("rcNickname");
                    String rcHeadPic = data.optString("rcHeadPic");
                    String id = data.optString("id");
                    LogUtils.i(id+rcHeadPic+rcNickname);
                    if (RongIM.getInstance() != null) {
                        RongIM.getInstance().startPrivateChat(AddressBookAct.this, id, rcNickname);
                    }
                    FriendBean friendBean = new FriendBean();
                    friendBean.setUserId(id);
                    friendBean.setUserName(rcNickname);
                    friendBean.setUserHead(rcHeadPic);
                    FriendDao.saveMes(friendBean);
                }

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void sendMess(String message, String number) {
        Uri smsToUri = Uri.parse("smsto:" + number);
        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", message);
        startActivity(mIntent);
    }

}

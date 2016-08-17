package com.beyonditsm.financial.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.AppManager;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditStepAct;
import com.beyonditsm.financial.activity.credit.CreditUploadAct;
import com.beyonditsm.financial.activity.credit.SubFlowAct;
import com.beyonditsm.financial.activity.user.HomeCreditDetailAct;
import com.beyonditsm.financial.activity.user.MyCreditAct;
import com.beyonditsm.financial.adapter.CreditOfflineAdapter;
import com.beyonditsm.financial.entity.CreditEvent;
import com.beyonditsm.financial.entity.CreditOfflineDetil;
import com.beyonditsm.financial.entity.ProductSortEntity;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.fragment.listener.CreditOfflineDialogListener;
import com.beyonditsm.financial.fragment.listener.CreditOfflineReloadListener;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.AutoDismissDialog;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.FinalLoadDialog;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuleyuan on 2016/8/1.
 */
public class CreditOfflineFrag extends BaseFragment implements CreditOfflineReloadListener,CreditOfflineDialogListener {
    @ViewInject(R.id.gv_upload)
    private GridView gvUpload;
    @ViewInject(R.id.tvCredit)
    private TextView tvCredit;
    @ViewInject(R.id.iv_progress)
    private ImageView ivProgress;
    @ViewInject(R.id.ll_upload)
    private LinearLayout llUpload;
    @ViewInject(R.id.ll_uploadSuccess)
    private LinearLayout llUploadSuccess;
    @ViewInject(R.id.crethr_btn1)
    private Button crethrBtn1;
    @ViewInject(R.id.crethr_btn2)
    private Button crethrBtn2;
    @ViewInject(R.id.tvNo)
    private TextView tvNo;//不需要增信材料
    @ViewInject(R.id.llHas)
    private LinearLayout llHas;//需要增信材料
    @ViewInject(R.id.lv_credit_third)
    private LoadingView lvCreditThird;
    @ViewInject(R.id.tv_description)
    private TextView tvDescription;
    @ViewInject(R.id.tv_upload)
    private TextView tvUpload;
    private Gson gson = new Gson();
    private CreditOfflineAdapter adapter;
    private String orderId;

    private int act_type;
    private String orderStatus;
    private String orderSts;
    private String photoSaveName = "";
    private String path = "";
    private String photoSavePath;
    public static final int ZOOM = 0;
    public static final int TAKE = 1;
    private FinalLoadDialog dialog;

    @SuppressLint("InflateParams")
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.credit_offline_frg, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        act_type = getArguments().getInt("act_type", 0);
        orderStatus = getArguments().getString("orderStatus");
        orderId = CreditStepAct.orderId;
//        if(act_type==1){
//            tvCredit.setVisibility(View.GONE);
//        }
        lvCreditThird.setNoContentTxt("暂无上传资料项");
        if ("PASS".equals(orderStatus)) {
            ivProgress.setBackgroundResource(R.mipmap.progress_04);
            tvCredit.setVisibility(View.GONE);
        }
        dialog = new FinalLoadDialog(context);
        dialog.setTitle("努力上传中");
        dialog.setCancelable(false);
        getUploadList(orderId);
//        lvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), CreditUploadAct.class);
//                intent.putExtra("orderId", orderId);
//                intent.putExtra("flowId", datas.get(position).getFlowId());
//                intent.putExtra("status", datas.get(position).getStatus());
//                getActivity().startActivity(intent);
//            }
//        });
    }


    /**
     * 提交成功，刷新
     */
    public void onEvent(CreditEvent event) {
        getUploadList(orderId);
        applayStatus(orderId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void setListener() {

    }

    /**
     * 获取上传资料列表
     *
     * @param orderId 订单id
     */
    private void getUploadList(final String orderId) {

        RequestManager.getCommManager().getCreditOfflineDetil(orderId, new RequestManager.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSucess(String result) throws JSONException {

                lvCreditThird.loadComplete();
                ResultData<CreditOfflineDetil> rd = (ResultData<CreditOfflineDetil>) GsonUtils.json(result, CreditOfflineDetil.class);
                CreditOfflineDetil creditOfflineDetil = rd.getData();
                tvDescription.setText(creditOfflineDetil.getOrderRemark() + "");
                if ("REJECT".equals(creditOfflineDetil.getOrderSts())) {
                    tvUpload.setVisibility(View.VISIBLE);
                } else {
                    tvUpload.setVisibility(View.GONE);
                }

                List<CreditOfflineDetil.ImagesBean> list = creditOfflineDetil.getImages();
                assert list != null;
                if (list.size() > 0) {
                    if (adapter == null) {
                        adapter = new CreditOfflineAdapter(context, list);
                    } else {
                        adapter.notifyDataChange(list);
                    }
                    adapter.setCreditListener(CreditOfflineFrag.this);
                    gvUpload.setAdapter(adapter);
                } else {
                    lvCreditThird.noContent();
                }

            }

            @Override
            public void onError(int status, String msg) {
                lvCreditThird.loadError();
                lvCreditThird.setOnRetryListener(new LoadingView.OnRetryListener() {
                    @Override
                    public void OnRetry() {
                        getUploadList(orderId);
                    }
                });

            }
        });
    }

    /**
     * 提交审核
     *
     * @param orderId 订单id
     */
    private void applayCredit(final String orderId) {
        RequestManager.getCommManager().applyCredit(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                findOrderFlow(orderId);
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getContext(), msg);
            }
        });
    }


    /**
     * 是否需要增信资料
     *
     * @param orderId 订单id
     */
    private void findOrderFlow(final String orderId) {
        RequestManager.getCommManager().findOrderFlow(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");
                if (!"没有可用的增信流程".equals(message)) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    CreditStepAct.upList = gson.fromJson(array.toString(), new TypeToken<List<UpLoadEntity>>() {
                    }.getType());

                } else {
                    CreditStepAct.upList = null;
                }
                if (act_type == 0) {
//                    EventBus.getDefault().post(new CreditStepAct.FirstEvent(3, orderId));
                    llUploadSuccess.setVisibility(View.VISIBLE);
                    llUpload.setVisibility(View.GONE);
                    findUploadSuccess();
                } else {
                    MyToastUtils.showShortToast(getContext(), "订单已提交，请耐心等待审批");
                    getActivity().sendBroadcast(new Intent(MyCreditAct.CREDIT_RECEIVER));
                    getActivity().sendBroadcast(new Intent(MyCreditDetailFragment.UPDATE_ORDER));
                    getActivity().sendBroadcast(new Intent(MyCreditStatusFragment.UPDATE_DEAL));
                    getActivity().finish();
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void findUploadSuccess() {
        if (CreditStepAct.upList != null && CreditStepAct.upList.size() > 0) {
            crethrBtn1.setText("提交增信材料");
            llHas.setVisibility(View.VISIBLE);
            tvNo.setVisibility(View.GONE);
        } else {
            crethrBtn1.setText("继续申请");
            llHas.setVisibility(View.GONE);
            tvNo.setVisibility(View.VISIBLE);
        }
        crethrBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CreditStepAct.upList != null && CreditStepAct.upList.size() > 0) {
                    Intent intent = new Intent(getActivity(), SubFlowAct.class);
                    intent.putParcelableArrayListExtra("sub_list", (ArrayList<? extends Parcelable>) CreditStepAct.upList);
                    intent.putExtra("order_id", orderId);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    getActivity().finish();
                    AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
                }
            }
        });
        crethrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.sendBroadcast(new Intent(MainActivity.UPDATATAB));
                getActivity().finish();
                AppManager.getAppManager().finishActivity(HomeCreditDetailAct.class);
            }
        });
    }

    @OnClick({R.id.tv_upload})
    public void todo(View v) {
        switch (v.getId()){
            case R.id.tv_upload:

                break;
        }
    }


    private void applayStatus(final String orderId) {
        RequestManager.getCommManager().applayStatus(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                int r = data.getInt("result");
                orderSts = data.getString("orderSts");
                MyLogUtils.info("返回的结果：" + r + ",orderSts:" + orderSts);
                if (r == 1) {
                    tvCredit.setBackgroundResource(R.drawable.button_gen);
                    tvCredit.setEnabled(true);
                    tvCredit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            applayCredit(orderId);
                        }
                    });
                } else if (r == 0) {
                    tvCredit.setBackgroundResource(R.drawable.button_grey);
                    tvCredit.setEnabled(false);
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    @Override
    public void onReload(String id, String name, String imageUrl) {
        RequestManager.getCommManager().saveOrUpdateOrderImage(orderId, id, name, imageUrl, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    @Override
    public void onPickImage(String imageName, final String uItemId) {

            MySelfSheetDialog dialog = new MySelfSheetDialog(context);
            dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    //执行拍照前，应该先判断SD卡是否存在
                    String SDState = Environment.getExternalStorageState();
                    if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                        photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                        path = photoSavePath + photoSaveName;
                        Uri imageUri;
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
                        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, TAKE);
                    } else {
                        MyToastUtils.showShortToast(context, "SD卡不存在");
                    }

                }
            }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(openAlbumIntent, ZOOM);
                }
            }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode != getActivity().RESULT_OK) {
                return;
            }
            Uri uri;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String time = df.format(new Date());
            switch (requestCode) {
                case ZOOM:// 相册
                    if (data == null || "".equals(data.toString())) {
                        return;
                    }
                    uri = data.getData();
                    if (null != uri && !"".equals(uri.toString())) {
                        Bitmap compressB = MyBitmapUtils.zoomImgKeepWH(MyBitmapUtils.decodeUriAsBitmap(context, uri), 300, 300, true);
                        MyBitmapUtils.saveBitmap(compressB, "upload/cache/credit_upload" + time + ".png");
                    }

                    break;
                case TAKE:// 拍照
//                    path = photoSavePath + photoSaveName;
                    MyBitmapUtils.saveBitmap(MyBitmapUtils.LoadBigImg(path, 300, 300), "upload/cache/credit_upload" + time + ".png");
                    break;
            }
            path = Environment.getExternalStorageDirectory() + "/upload/cache/credit_upload" + time + ".png";
            uploadFile(path);
            super.onActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传图片
     *
     * @param file 图片地址
     */
    private void uploadFile(final String file) {
        dialog.show();
        Map<String, FileBody> fileMaps = new HashMap<>();
        FileBody fb = new FileBody(new File(file));
        fileMaps.put("file", fb);

        RequestManager.getCommManager().toUpLoadFile(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                dialog.cancel();

                MyLogUtils.degug(result);


            }

            @Override
            public void onError(int status, String msg) {
                dialog.cancel();
                MyLogUtils.info(msg);
            }
        });
    }


}

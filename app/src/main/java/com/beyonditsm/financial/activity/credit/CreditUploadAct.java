package com.beyonditsm.financial.activity.credit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.CreditEvent;
import com.beyonditsm.financial.entity.CreditImageBean;
import com.beyonditsm.financial.entity.CreditUplEntity;
import com.beyonditsm.financial.entity.SubUplEntity;
import com.beyonditsm.financial.entity.SumLoadEntity;
import com.beyonditsm.financial.entity.UpLoadEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.leaf.library.widget.MyListView;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款上传资料
 * Created by wangbin on 16/3/22.
 */
public class CreditUploadAct extends BaseActivity {
    @ViewInject(R.id.tvDes)
    private TextView tvDes;
    @ViewInject(R.id.lvUpLoad)
    private ListView lvUpLoad;
    private String orderId;
    private String flowId;

    private UpLoadEntity upLoadData;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2; // 结果

    private String photoSavePath;
    private String photoSaveName;
    private String path;// 图片全路径
    String appHome = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jinzhu_tx";


    private String uploadItemId;//记录itemId
    private String imageId = "";//记录图片id
    private String imageIsPass;
    private int imagePosi;//记录位置
    //    private int imageLimit;//记录每次的限制
//    private String creUpName;
    private LinkedHashMap<String, List<CreditImageBean>> imageMap = new LinkedHashMap<>();//存放

    private List<CreditUplEntity> creDatas;//请求结果data；
    private List<CreditUplEntity> resultData;//最终生成的data
    private MyAdapter myAdapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_upload);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("上传资料图片");
        orderId = getIntent().getStringExtra("orderId");
        flowId = getIntent().getStringExtra("flowId");

        File file = new File(Environment.getExternalStorageDirectory(), "upload/cache");

        if (!file.exists())
            file.mkdirs();

        photoSavePath = Environment.getExternalStorageDirectory() + "/upload/cache/";

        findFlowDetail(orderId, flowId);
    }

    @OnClick({R.id.tvBack,R.id.tvSave})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.tvSave:
                if (resultData == null || resultData.size() == 0) {
                    MyToastUtils.showShortToast(getApplicationContext(), "您没有做任何操作");
                    return;
                }
                MyLogUtils.info("提交信息：" + GsonUtils.bean2Json(resultData));
                toSubmit(getSubEntity(resultData));
                break;
        }
    }

    /**
     * 获取提交信息
     */
    private SumLoadEntity getSubEntity(List<CreditUplEntity> getList) {
        SumLoadEntity sue = new SumLoadEntity();
        sue.setOrderId(orderId);
        sue.setFlowId(flowId);
        List<SubUplEntity> list = new ArrayList<>();
        for (int i = 0; i < getList.size(); i++) {
            SubUplEntity se = new SubUplEntity();
            se.setUploadItemId(getList.get(i).getUploadItemId());
            se.setImageUrl(getList.get(i).getImage());
            list.add(se);
        }
        sue.setUploadItem(list);
        return sue;
    }

    /**
     * 获取上传列表
     *
     * @param orderId
     * @param flowId
     */
    private void findFlowDetail(String orderId, String flowId) {
        RequestManager.getCommManager().findFlowDetail(orderId, flowId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                upLoadData = GsonUtils.json2Bean(data.toString(), UpLoadEntity.class);
                if (!TextUtils.isEmpty(upLoadData.getDisplayName()))
                    tvDes.setText(upLoadData.getDisplayName());
                creDatas = upLoadData.getItems();

                imageMap = getImageMap(creDatas);
                myAdapter = new MyAdapter(creDatas);
                lvUpLoad.setAdapter(myAdapter);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    /**
     * 提交资料
     *
     * @param upl
     */
    private void toSubmit(SumLoadEntity upl) {
        RequestManager.getCommManager().submitOrderFlow(upl, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(), "提交成功");
                EventBus.getDefault().post(new CreditEvent());
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(), msg);
            }
        });
    }

    /**
     * 获取初始化
     *
     * @param ceList
     * @return
     */
    private LinkedHashMap<String, List<CreditImageBean>> getImageMap(List<CreditUplEntity> ceList) {
        LinkedHashMap<String, List<CreditImageBean>> map = new LinkedHashMap<>();
        if (ceList != null && ceList.size() > 0) {
            for (int i = 0; i < ceList.size(); i++) {
                map.put(ceList.get(i).getUploadItemId(), ceList.get(i).getImage());
            }
        }
        return map;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = null;
        switch (requestCode) {
            case PHOTOZOOM:// 相册
                if (data == null) {
                    return;
                }
                uri = data.getData();
                Bitmap userbitmap = MyBitmapUtils.decodeUriAsBitmap(CreditUploadAct.this, uri);
                Bitmap compressB = MyBitmapUtils.zoomImgKeepWH(userbitmap, 100, 100, true);
                MyBitmapUtils.saveBitmap(compressB, "upload/cache/credit_upload.png");
                break;
            case PHOTOTAKE:// 拍照
                path = photoSavePath + photoSaveName;
                MyBitmapUtils.saveBitmap(MyBitmapUtils.LoadBigImg(path, 100, 100), "upload/cache/credit_upload.png");
                break;
        }
        path = Environment.getExternalStorageDirectory() + "/upload/cache/credit_upload.png";
        uploadFile(path);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadFile(final String file) {
        Map<String, FileBody> fileMaps = new HashMap<String, FileBody>();
        FileBody fb = new FileBody(new File(file));
        fileMaps.put("file", fb);

        RequestManager.getCommManager().toUpLoadFile(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {

                CreditImageBean cib = new CreditImageBean();
                if (imageMap.get(uploadItemId).size() == 0) {
                    cib.setImageUrl(result);
                    imageMap.get(uploadItemId).add(cib);
                } else if (imageMap.get(uploadItemId).size() <= imagePosi) {
                    cib.setImageUrl(result);
                    if (imageIsPass != null)
                        cib.setIsPass(imageIsPass);
                    cib.setId(imageId);
                    imageMap.get(uploadItemId).add(cib);
                } else {
                    cib.setImageUrl(result);
                    if (imageIsPass != null)
                        cib.setIsPass(imageIsPass);
                    cib.setId(imageId);
                    if (imageMap.get(uploadItemId).size() - 1 == imagePosi) {
                        imageMap.get(uploadItemId).remove(imagePosi);
                        imageMap.get(uploadItemId).add(cib);
                    } else {
                        imageMap.get(uploadItemId).remove(imagePosi);
                        imageMap.get(uploadItemId).add(imagePosi, cib);
                    }
                }
                resultData = new ArrayList<CreditUplEntity>();
                int i = 0;
                for (LinkedHashMap.Entry<String, List<CreditImageBean>> entry : imageMap.entrySet()) {
                    CreditUplEntity ce = new CreditUplEntity();
                    ce.setLimit(creDatas.get(i).getLimit());
                    ce.setUploadItemId(entry.getKey());
                    ce.setImage(entry.getValue());
                    ce.setUploadDisplayName(creDatas.get(i).getUploadDisplayName());
                    resultData.add(ce);
                    i++;
                }
                myAdapter.notifyDataChange(resultData);


            }

            @Override
            public void onError(int status, String msg) {
                MyLogUtils.info(msg);
            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        private List<CreditUplEntity> list;

        private MyAdapter(List<CreditUplEntity> list) {
            this.list = list;
        }

        public void notifyDataChange(List<CreditUplEntity> list) {
            this.list = list;
            notifyDataSetChanged();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(CreditUploadAct.this, R.layout.lv_credit_upload_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!TextUtils.isEmpty(list.get(position).getUploadDisplayName()))
                holder.tvImgDes.setText(list.get(position).getUploadDisplayName());
            holder.lvUpLoad.setAdapter(new ImageAdapter(list.get(position).getImage(), list.get(position).getUploadItemId(), list.get(position).getLimit()));
            return convertView;
        }

        public class ViewHolder {
            public final MyListView lvUpLoad;
            public final TextView tvImgDes;
            public final View root;

            public ViewHolder(View root) {
                lvUpLoad = (MyListView) root.findViewById(R.id.lvUpLoad);
                tvImgDes = (TextView) root.findViewById(R.id.tvImgDes);
                this.root = root;
            }
        }
    }

    /**
     * 图片显示适配器
     */
    private class ImageAdapter extends BaseAdapter {
        private List<CreditImageBean> list;
        private int limit;
        private String uItemId;

        public ImageAdapter(List<CreditImageBean> list, String uItemId, int limit) {
            this.list = list;
            this.uItemId = uItemId;
            this.limit = limit;
        }


        @Override
        public int getCount() {
            return limit;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(CreditUploadAct.this, R.layout.lv_iamge_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list != null && list.size() > 0) {
                if (position < list.size() && list.get(position) != null) {
                    ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + list.get(position).getImageUrl(), holder.ivPhoto, options);
                    if ("0".equals(list.get(position).getIsPass())) {
                        holder.tvStatus.setText("已驳回");
                    } else if ("1".equals(list.get(position).getIsPass())) {
                        holder.tvStatus.setText("通过");
                    } else if ("2".equals(list.get(position).getIsPass())) {
                        holder.tvStatus.setText("待审核");
                    }
                }
            }

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageId = "";
                    imageIsPass = null;
                    if ((position < list.size() && !"1".equals(list.get(position).getIsPass())) || position >= list.size()) {
                        MySelfSheetDialog dialog = new MySelfSheetDialog(CreditUploadAct.this);
                        dialog.builder().addSheetItem("拍照", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                uploadItemId = uItemId;
                                if (list.size() > position) {
                                    if (!TextUtils.isEmpty(list.get(position).getId())) {
                                        imageId = list.get(position).getId();
                                        imageIsPass = list.get(position).getIsPass();
                                    }
                                }
                                imagePosi = position;

                                photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                                Uri imageUri = null;
                                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
                                openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(openCameraIntent, PHOTOTAKE);
                            }
                        }).addSheetItem("从相册选取", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                uploadItemId = uItemId;
                                if (list.size() > position) {
                                    if (!TextUtils.isEmpty(list.get(position).getId())) {
                                        imageId = list.get(position).getId();
                                        imageIsPass = list.get(position).getIsPass();
                                    }
                                }
                                imagePosi = position;

                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(openAlbumIntent, PHOTOZOOM);
                            }
                        }).show();

                    }
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public final ImageView ivPhoto;
            public final TextView tvStatus;
            public final View root;

            public ViewHolder(View root) {
                ivPhoto = (ImageView) root.findViewById(R.id.ivPhoto);
                tvStatus = (TextView) root.findViewById(R.id.tvStatus);
                this.root = root;
            }
        }
    }
}

package com.beyonditsm.financial.view;

/**
 * Created by wangbin on 15/12/20.
 */
public class ChangeAddressDialog  {
//    private Context context;
//    private Dialog dialog;
//    private Display display;
//    private WheelView wvProvince;
//    private WheelView wvCity;
//    private WheelView wvArea;
//
//    private ArrayList<ProvinceInfo> arrProvinces = new ArrayList<ProvinceInfo>();
//    private ArrayList<CityInfo> arrCitys = new ArrayList<CityInfo>();
//    private ArrayList<AreaInfo> areas = new ArrayList<AreaInfo>();
//
//    private Map<String,List<CityInfo>> mCitisDatasMap = new HashMap<String, List<CityInfo>>();
//
//    private ProvinceAdapter pAdapter;
//    private CityAdapter cityAdapter;
//    private AreaAdapter areaAdapter;
//
//    private String strProvince = "北京市";
//    private String strCity = "北京市";
//
//    private int maxsize = 100;
//    private int minsize = 1;
//
//    public ChangeAddressDialog(Context context) {
//        this.context = context;
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    @SuppressLint("InflateParams")
//    @SuppressWarnings("deprecation")
//    public ChangeAddressDialog builder() {
//        // 获取Dialog布局
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.city_select, null);
//
//        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
//
//        // 获取自定义Dialog布局中的控件
//        TextView save=(TextView) view.findViewById(R.id.adress_Save);
//        wvProvince= (WheelView) view.findViewById(R.id.wvProvince);
//        wvCity= (WheelView) view.findViewById(R.id.wvCity);
//        wvArea= (WheelView) view.findViewById(R.id.wvArea);
//        arrProvinces= ProvinceDao.getProvince();
//        pAdapter=new ProvinceAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
//        wvProvince.setVisibleItems(5);
//        wvProvince.setViewAdapter(pAdapter);
//        wvProvince.setCurrentItem(getProvinceItem(strProvince));
//
//        initCitys(mCitisDatasMap.get(strProvince));
//        cityAdapter = new CityAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
//        wvCity.setVisibleItems(5);
//        wvCity.setViewAdapter(cityAdapter);
//        wvCity.setCurrentItem(getCityItem(strCity));
//
//        // 定义Dialog布局和参数
//        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
//        dialog.setContentView(view);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.x = 0;
//        lp.y = 0;
//        dialogWindow.setAttributes(lp);
////        initdata();
//        return this;
//    }
//
//
//    private class ProvinceAdapter extends AbstractWheelTextAdapter {
//        ArrayList<ProvinceInfo> list;
//
//        protected ProvinceAdapter(Context context, ArrayList<ProvinceInfo> list, int currentItem, int maxsize, int minsize) {
//            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
//            this.list = list;
//            setItemTextResource(R.id.tempValue);
//        }
//
//        @Override
//        public View getItem(int index, View cachedView, ViewGroup parent) {
//            View view = super.getItem(index, cachedView, parent);
//            return view;
//        }
//
//        @Override
//        public int getItemsCount() {
//            return list.size();
//        }
//
//        @Override
//        protected CharSequence getItemText(int index) {
//            return list.get(index).getName() + "";
//        }
//    }
//
//    private class CityAdapter extends AbstractWheelTextAdapter {
//        ArrayList<CityInfo> list;
//
//        protected CityAdapter(Context context, ArrayList<CityInfo> list, int currentItem, int maxsize, int minsize) {
//            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
//            this.list = list;
//            setItemTextResource(R.id.tempValue);
//        }
//
//        @Override
//        public View getItem(int index, View cachedView, ViewGroup parent) {
//            View view = super.getItem(index, cachedView, parent);
//            return view;
//        }
//
//        @Override
//        public int getItemsCount() {
//            return list.size();
//        }
//
//        @Override
//        protected CharSequence getItemText(int index) {
//            return list.get(index).getName() + "";
//        }
//    }
//
//
//    private class AreaAdapter extends AbstractWheelTextAdapter {
//        ArrayList<AreaInfo> list;
//
//        protected AreaAdapter(Context context, ArrayList<AreaInfo> list, int currentItem, int maxsize, int minsize) {
//            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
//            this.list = list;
//            setItemTextResource(R.id.tempValue);
//        }
//
//        @Override
//        public View getItem(int index, View cachedView, ViewGroup parent) {
//            View view = super.getItem(index, cachedView, parent);
//            return view;
//        }
//
//        @Override
//        public int getItemsCount() {
//            return list.size();
//        }
//
//        @Override
//        protected CharSequence getItemText(int index) {
//            return list.get(index).getName() + "";
//        }
//    }
//
//    /**
//     * 返回省会索引，没有就返回默认“四川”
//     *
//     * @param province
//     * @return
//     */
//    public int getProvinceItem(String province) {
//        int size = arrProvinces.size();
//        int provinceIndex = 0;
//        boolean noprovince = true;
//        for (int i = 0; i < size; i++) {
//            if (province.equals(arrProvinces.get(i))) {
//                noprovince = false;
//                return provinceIndex;
//            } else {
//                provinceIndex++;
//            }
//        }
//        if (noprovince) {
//            strProvince = "北京市";
//            return 0;
//        }
//        return provinceIndex;
//    }
//
//    /**
//     * 得到城市索引，没有返回默认“成都”
//     *
//     * @param city
//     * @return
//     */
//    public int getCityItem(String city) {
//        int size = arrCitys.size();
//        int cityIndex = 0;
//        boolean nocity = true;
//        for (int i = 0; i < size; i++) {
//            System.out.println(arrCitys.get(i));
//            if (city.equals(arrCitys.get(i))) {
//                nocity = false;
//                return cityIndex;
//            } else {
//                cityIndex++;
//            }
//        }
//        if (nocity) {
//            strCity = "北京市";
//            return 0;
//        }
//        return cityIndex;
//    }
//
//    /**
//     * 根据省会，生成该省会的所有城市
//     *
//     * @param citys
//     */
//    public void initCitys(List<CityInfo> citys) {
//        if (citys != null) {
//            arrCitys.clear();
//            int length = citys.size();
//            for (int i = 0; i < length; i++) {
//                arrCitys.add(citys.get(i));
//            }
//        } else {
//            List<CityInfo> city = mCitisDatasMap.get("110000");
//            arrCitys.clear();
//            int length = city.size();
//            for (int i = 0; i < length; i++) {
//                arrCitys.add(city.get(i));
//            }
//        }
//        if (arrCitys != null && arrCitys.size() > 0
//                && !arrCitys.contains(strCity)) {
////            strCity = arrCitys.get(0);
//        }
//    }
}

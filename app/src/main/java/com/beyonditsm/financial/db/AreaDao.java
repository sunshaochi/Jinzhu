package com.beyonditsm.financial.db;

import com.beyonditsm.financial.entity.ClickBean;
import com.beyonditsm.financial.util.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * Created by wangbin on 15/12/20.
 */
public class AreaDao extends TemplateDAO<ClickBean, String> {
    public AreaDao() {
        super(ShUtils.getDbhelper());
    }

    private static AreaDao dao;

    private static AreaDao getDao() {
        if (dao == null) {
            dao = new AreaDao();
        }
        return dao;
    }

    public static void saveISClick(ClickBean clickBean) {
        getDao().insert(clickBean);
    }

    public static List<ClickBean> findIsClick(String orderid) {
        List<ClickBean> datas = getDao().find(null, ClickBean.ORDERID+"=?", new String[]{orderid}, null, null, null, null);
        return datas;
    }
}
//        AreaDao extends TemplateDAO<AreaInfo, String> {
//
//    public AreaDao() {
//        super(ShUtils.getDbhelper());
//    }
//
//    private static AreaDao dao;
//
//    private static AreaDao getDao() {
//        if (dao == null) {
//            dao = new AreaDao();
//        }
//        return dao;
//    }
//    public static void addArea(List<AreaInfo> list){
//        if(list!=null&&list.size()>0){
//            for(int i=0;i<list.size();i++){
//                getDao().insert(list.get(0));
//            }
//        }
//    }
//
//    public static ArrayList<AreaInfo> getArea(String cityCode){
//        String selection = "cityCode=?";
//        String[] selectionArgs = { cityCode };
//        return (ArrayList<AreaInfo>) getDao().find(null, selection, selectionArgs, null, null, null, null);
//    }
//}

package com.beyonditsm.financial.db;

import com.beyonditsm.financial.entity.ProvinceInfo;
import com.beyonditsm.financial.util.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbin on 15/12/20.
 */
public class ProvinceDao extends TemplateDAO<ProvinceInfo, String> {
    public ProvinceDao() {
        super(ShUtils.getDbhelper());
    }

    private static ProvinceDao dao;

    private static ProvinceDao getDao() {
        if (dao == null) {
            dao = new ProvinceDao();
        }
        return dao;
    }
    public static void addProvince(List<ProvinceInfo> list){
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                getDao().insert(list.get(0));
            }
        }
    }

    public static ArrayList<ProvinceInfo> getProvince(){
      return (ArrayList<ProvinceInfo>) getDao().find();
    }
}

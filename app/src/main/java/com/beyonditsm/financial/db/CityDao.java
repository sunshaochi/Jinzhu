package com.beyonditsm.financial.db;

import com.beyonditsm.financial.entity.CityInfo;
import com.beyonditsm.financial.util.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbin on 15/12/20.
 */
public class CityDao extends TemplateDAO<CityInfo, String> {

    public CityDao() {
        super(ShUtils.getDbhelper());
    }

    private static CityDao dao;

    private static CityDao getDao() {
        if (dao == null) {
            dao = new CityDao();
        }
        return dao;
    }
    public static void addCity(List<CityInfo> list){
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                getDao().insert(list.get(0));
            }
        }
    }

    public static ArrayList<CityInfo> getCity(String provinceCode){
        String selection = "provinceCode=?";
        String[] selectionArgs = { provinceCode };
        return (ArrayList<CityInfo>) getDao().find(null, selection, selectionArgs, null, null, null, null);
    }
}

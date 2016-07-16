package com.beyonditsm.financial.db;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.util.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * 好友数据库
 *
 * @author yang
 */
public class FriendDao extends TemplateDAO<FriendBean, String> {

    public FriendDao() {
        super(ShUtils.getDbhelper());
    }

    private static FriendDao dao;

    private static FriendDao getDao() {
        if (dao == null) {
            dao = new FriendDao();
        }
        return dao;
    }

    /**
     * 插入消息
     *
     * @param friend 好友实体类
     */
    public static void saveMes(FriendBean friend) {
        getDao().insert(friend);
    }

    /**
     * 查找所有的
     *
     */
    public static List<FriendBean> findfriend() {
        return getDao().find(null, "user_id", null, null, null, null, null);
    }

    /**
     * 删除其中一条消息
     */
    public static void deleteMes(String userId) {
        SQLiteDatabase db = getDao().getWritableDatabase();
        db.delete(getDao().getTableName(), "user_id=?", new String[]{userId});
    }

    /**
     * 删除所有的消息
     */
    public static void deleteAllMes() {
        getDao().deleteAll();
    }

    /**
     * 更新用户信息
     */
    public static void updateUser(FriendBean friendBean) {
        FriendDao dao = getDao();
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", friendBean.getUserId());
        values.put("user_head", friendBean.getUserHead());
        values.put("user_name", friendBean.getUserName());
        db.update(dao.getTableName(), values, "user_id=?",
                new String[]{friendBean.getUserId()});
    }

}

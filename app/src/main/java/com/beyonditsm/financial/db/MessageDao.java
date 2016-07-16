package com.beyonditsm.financial.db;


import android.database.sqlite.SQLiteDatabase;

import com.beyonditsm.financial.entity.MessageBean;
import com.beyonditsm.financial.util.ShUtils;
import com.leaf.library.db.TemplateDAO;

import java.util.List;

/**
 * 消息数据库
 * @author wangbin
 *
 */
public class MessageDao extends TemplateDAO<MessageBean, String>{

	public MessageDao() {
		super(ShUtils.getDbhelper());
	}
	
	private static MessageDao dao;
	
	private static MessageDao getDao(){
		if(dao==null){
			dao=new MessageDao();
		}
		return dao;
	}
	
	/**
	 * 插入消息
	 * @param mess 消息实体类
	 */
	public static void saveMes(MessageBean mess){
		getDao().insert(mess);
	}
	
	/**
	 * 查找所有的
	 */
	public static List<MessageBean> findMess(){
		return getDao().find(null, null, null, null, null, "time desc", null);
	}
	
	/**
	 * 删除其中一条消息
	 */
	public static void deleteMes(String msg_id){
		SQLiteDatabase db=getDao().getWritableDatabase();
		db.delete(getDao().getTableName(),"msg_id=?",new String[]{msg_id});
	}
	
	/**
	 * 删除所有的消息
	 */
	public static void deleteAllMes(){
		getDao().deleteAll();
	}

}

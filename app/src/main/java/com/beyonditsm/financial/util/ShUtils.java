package com.beyonditsm.financial.util;

import android.content.Context;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.leaf.library.db.SmartDBHelper;
/**
 * 数据库操作辅助类
 * @author wangbin
 *
 */
public class ShUtils {
   
	private static SmartDBHelper dbHelper;
	public static Context getApplicationContext(){
		return getApplication().getApplicationContext();
	}
	
	public static SmartDBHelper getDbhelper(){
		if(dbHelper==null){
			dbHelper=new SmartDBHelper(getApplicationContext(), 
					ConstantValue.DB_NAME,null,ConstantValue.VERSION,ConstantValue.MODELS);
		}
		return dbHelper;
	}
	
	public static MyApplication getApplication(){
		return MyApplication.getInstance();
	}
}

package com.beyonditsm.financial.util;

import com.beyonditsm.financial.entity.ResultData;
import com.tandong.sa.json.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * json工具类
 * 
 * 
 */
public class GsonUtils {

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}

	/**
	 * json字符串转成Java--Bean
	 * 
	 * @param result
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Bean(String result, Class<T> clazz) {
		Gson gson = new Gson();
		T t = gson.fromJson(result, clazz);
		return t;
	}

	/**
	 * Java--Bean转成json字符串
	 * 
	 * @param obj
	 *            Java--Bean
	 * @return
	 */
	public static String bean2Json(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}


	
	public static ResultData<?> json(String result,Class<?> clazz){
		Gson gson = new Gson();
		Type objectType = type(ResultData.class, clazz);
        return gson.fromJson(result, objectType);
	}
	

	static ParameterizedType type(final Class<?> raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}

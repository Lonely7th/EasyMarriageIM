package com.em.im.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.em.im.MyApplication;
import com.em.im.base.ContentValue;
import com.em.im.bean.AMapBean;
import com.em.im.bean.UserBean;
import com.google.gson.Gson;

/**
 * Time ： 2019/3/21 0021 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class AMapUtil {
    private static Gson gson = new Gson();

    public static void changeMapInfo(String str){
        SharedPreferences.Editor editor = MyApplication.sf.edit();
        editor.putString(ContentValue.AMAP_JSONSTR, str);
        editor.apply();
    }

    public static AMapBean getMapInfo(){
        String str = MyApplication.sf.getString(ContentValue.AMAP_JSONSTR,"");
        if(TextUtils.isEmpty(str)){
            return null;
        }else{
            return gson.fromJson(str, AMapBean.class);
        }
    }

}

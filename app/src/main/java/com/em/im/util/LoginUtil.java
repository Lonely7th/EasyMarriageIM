package com.em.im.util;

import android.content.SharedPreferences;

import com.em.im.MyApplication;
import com.em.im.base.ContentValue;
import com.em.im.bean.UserBean;
import com.google.gson.Gson;

/**
 * Time ： 2018/12/20 0020 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class LoginUtil {
    private static Gson gson = new Gson();
    /**
     * 登录成功
     */
    public static void loginSuccess(String userInfo){
        //保存用户信息
        SharedPreferences.Editor editor = MyApplication.sf.edit();
        //添加登录状态
        editor.putBoolean(ContentValue.LOGIN_STATUS, true);
        editor.putString(ContentValue.LOGIN_JSONSTR, userInfo);
        editor.apply();
    }

    /**
     * 注销登录
     */
    public static void exitLogin(){
        //清空登录状态
        SharedPreferences.Editor editor = MyApplication.sf.edit();
        editor.putBoolean(ContentValue.LOGIN_STATUS, false);
        editor.putString(ContentValue.LOGIN_JSONSTR,"");
        editor.apply();
    }

    /**
     * 判断登录状态
     */
    public static boolean isLogin() {
        return MyApplication.sf.getBoolean(ContentValue.LOGIN_STATUS, false);
    }

    /**
     * 获取用户信息
     */
    public static UserBean getUserInfo(){
        if(isLogin()){
            return gson.fromJson(MyApplication.sf.getString(ContentValue.LOGIN_JSONSTR,""),UserBean.class);
        }else{
            return new UserBean();
        }
    }

    public static void changeUserInfo(UserBean userBean){
        SharedPreferences.Editor editor = MyApplication.sf.edit();
        editor.putString(ContentValue.LOGIN_JSONSTR, gson.toJson(userBean));
        editor.apply();
    }
}

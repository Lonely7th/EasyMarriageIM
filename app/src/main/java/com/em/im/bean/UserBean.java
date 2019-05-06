package com.em.im.bean;

/**
 * Time ： 2018/12/26 0026 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class UserBean {
    private String UserNo;
    private String UserName;
    private String UserHead = "";
    private String UserPhone;
    private String UserCity;

    private String Token = "";
    private boolean isFollow;

    private String WxUserId;

    private String CreatTime;

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) {
        UserNo = userNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserHead() {
        return UserHead;
    }

    public void setUserHead(String userHead) {
        UserHead = userHead;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserCity() {
        return UserCity;
    }

    public void setUserCity(String userCity) {
        UserCity = userCity;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public String getWxUserId() {
        return WxUserId;
    }

    public void setWxUserId(String wxUserId) {
        WxUserId = wxUserId;
    }

    public String getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(String creatTime) {
        CreatTime = creatTime;
    }
}

package com.em.im.bean;

/**
 * Time ： 2018/12/26 0026 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class UserBean {
    private String UserNo;
    private String UserName;
    private String Token = "";
    private String PhoneNumber;
    private String UserHead = "";
    private String City;
    private String CreatTime;
    private boolean isFollow;

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

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserHead() {
        return UserHead;
    }

    public void setUserHead(String userHead) {
        UserHead = userHead;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(String creatTime) {
        CreatTime = creatTime;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}

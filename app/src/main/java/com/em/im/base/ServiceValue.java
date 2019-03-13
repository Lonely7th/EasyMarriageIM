package com.em.im.base;

/**
 * Time ： 2019/3/13 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class ServiceValue {
    /**
     * 服务端IP
     */
    public final static String BASE_URL = "http://172.31.71.35:8080/";
    public final static String IMAGE_URL = "http://172.31.71.35:8089/";
    /**
     * 状态码
     */
    public static final int HttpSuccess = 200;
    public static final int HttpError = 500;
    /**
     * 用户状态相关
     */
    public static final String Login = BASE_URL + "api/v1/login";
}

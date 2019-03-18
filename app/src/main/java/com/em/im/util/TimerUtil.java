package com.em.im.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time ： 2018/12/28 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class TimerUtil {

    public static String timeStamp2Date(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }
}

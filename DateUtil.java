package org.yzl.house.utils;

import java.sql.Date;

/**
 * Created by ltaoj on 2017/10/16.
 */
public class DateUtil {

    /**
     * 将2017.10.09的时间转换为2017-10-09
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        String[] hms = strDate.split(".");
        if (hms.length == 3) {
            String result = String.join("-", hms);
            return Date.valueOf(result);
        }
        return null;
    }

    /**
     * 返回当前年份
     * @return
     */
    public static String getYear() {
        return formatNumber((new Date(System.currentTimeMillis()).getYear() + 1990));
    }

    public static String getMonth() {
        return formatNumber(new Date(System.currentTimeMillis()).getMonth() + 1);
    }

    public static String getDate() {
        return formatNumber(new Date(System.currentTimeMillis()).getDate());
    }

    private static String formatNumber(int number) {
        if (String.valueOf(number).length() == 1) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }
}

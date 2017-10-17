package org.yzl.house.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yzl.house.entity.Room;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by ltaoj on 2017/10/8.
 */
public class ExcelUtil {

    public static Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());

    static {
        map.put("配套", 1);
        map.put("非配套", 0);
        map.put("已售住房", 0);
        map.put("周转住房", 1);
        map.put("在住", 0);
        map.put("房源", 1);
        map.put("占房", 2);
        map.put("强占", 3);
        map.put("扣", 0);
        map.put("交", 1);
        map.put("转账", 2);
        map.put("是", 1);
        map.put("否", 0);
    }

    public static Object checkType(String value, Field  field) {
        if (field.getType().toString().equals("int")) {
           if (value.matches("^[0-9]*[1-9][0-9]*$")) {
               return Integer.parseInt(value);
           } else if (map.containsKey(value)){
               return map.get(value);
           }
        } else if (field.getType().toString().equals("double")) {
            return Double.parseDouble(value);
        } else if (field.getType().toString().equals("class java.lang.Integer"))  {
            return Integer.parseInt(value);
        } else if (field.getType().toString().equals("class java.sql.Date")) {
            if (null == value || value.trim().equals(""))
                return null;
            else {
                return DateUtil.parseDate(value);
            }
        }
        return value;
    }
}

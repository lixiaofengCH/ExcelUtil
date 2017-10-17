package org.yzl.house.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ltaoj on 2017/10/15.
 */
public class ObjectReflect {

    /**
     * 将多个对象的值映射到一个实体
     * @param clz 目标实体
     * @param objects 其他对象
     * @param <T> 返回类型
     * @return
     */
    public static <T extends Object> T getBigObject(Class<T> clz, Object... objects){
        try {
            T object = (T) Class.forName(clz.getName()).newInstance();

            for (int i = 0; i < objects.length; i++) {
                Field[] fields = objects[i].getClass().getDeclaredFields();
                for (Field field : fields) {
                    String getMethodName = getMethodName(field.getName(), field.getType().toString().equals("boolean"));
                    String setMethodName = setMethodName(field.getName());
                    try {
                        Method getMethod = objects[i].getClass().getMethod(getMethodName, new Class[]{});
                        Method setMethod = clz.getMethod(setMethodName, new Class[]{field.getType()});

                        Object value = getMethod.invoke(objects[i], new Object[]{});

                        setMethod.invoke(object, new Object[]{value});
                    } catch (NoSuchMethodException e) {
                        continue;
                    }
                }
            }

            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回字段name的标准get方法名
     * @param name 字段名
     * @param isBoolean 字段是否为布尔类型
     * @return 标准get方法名
     */
    public static String getMethodName(String name, boolean isBoolean) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String getMethodName;
        if (isBoolean) {
            getMethodName = "is" + firstLetter  + name.substring(1);
        } else {
            getMethodName = "get" + firstLetter + name.substring(1);
        }
        return getMethodName;
    }

    /**
     * 返回字段name的标准set方法名
     * @param name 字段名
     * @return 标准set方法名
     */
    public static String setMethodName(String name) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        return "set" + firstLetter + name.substring(1);
    }
}

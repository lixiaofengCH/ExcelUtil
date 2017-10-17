package org.yzl.house.utils.excel;

/**
 * Created by ltaoj on 2017/10/7.
 */
public class ExcelFactory {
    public static <T extends ResolveExcel> T createResolveExcel(Class<T> clz) {
        ResolveExcel resolveExcel = null;
        try {
            resolveExcel = (ResolveExcel) Class.forName(clz.getName()).newInstance();
            return (T) resolveExcel;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends FormatExcel> T createFormatExcel(Class<T> clz) {
        FormatExcel formatExcel = null;
        try {
            formatExcel = (FormatExcel) Class.forName(clz.getName()).newInstance();
            return (T) formatExcel;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

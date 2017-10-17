package org.yzl.house.utils.excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.yzl.house.utils.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ltaoj on 2017/10/7.
 */
public abstract class ResolveExcel {

    /**
     *
     * @param title key为excel表头内容，value为对应的对象字段名
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T extends Object> List<T> readExcel(MultipartFile multipartFile, Map<String, String> title, Class<T> clz) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            List<T> result = new ArrayList<T>();
            // 只处理第一页
            if (xssfWorkbook.getNumberOfSheets() > 0) {
                // 获取第一个sheet
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                if (xssfSheet == null)
                    return null;
                // 获取表头
                XSSFRow sheetHdRow = xssfSheet.getRow(0);
                // 跳过第一行表头
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    int minColIdx = xssfRow.getFirstCellNum();
                    int maxColIdx = xssfRow.getLastCellNum();
                    // 创建对象
                    T rowObject = (T) Class.forName(clz.getName()).newInstance();
                    // 遍历该行
                    for (int colIdx = minColIdx; colIdx < maxColIdx; colIdx++) {
                        // 判断是否需要该列信息
                        if (title.keySet().contains(sheetHdRow.getCell(colIdx).toString())) {
                            Field field = Class.forName(clz.getName()).getDeclaredField(title.get(sheetHdRow.getCell(colIdx).toString()));
                            field.setAccessible(true);
                            // 需修改:
                            // 1.需要将某些字段的值转换为int表示
                            // 2.有些字符串数字要转换成其他基本类型
                            field.set(rowObject, ExcelUtil.checkType(xssfRow.getCell(colIdx).toString(), field));
                        }
                    }
                    result.add(rowObject);
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel返回对象数组
     * @return
     */
    public abstract <T extends Object> List<T> resolve(MultipartFile excel);
}

package org.yzl.house.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.yzl.house.domain.CustomExcelTable;
import org.yzl.house.exception.FormatExcelException;
import org.yzl.house.utils.IOUtil;
import org.yzl.house.utils.ObjectReflect;
import org.yzl.house.utils.PathConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ltaoj on 2017/10/16.
 */
public abstract class FormatExcel {

    //默认日期格式
    public static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";
    // 默认文件名
    public static String DEFAULT_FILE_NAME = "未命名.xlsx";

    protected String title;
    protected String datePattern;
    protected String fileName;

    /**
     * 生成Excel，返回File对象
     * @param customExcelTable excel表每行显示数据、excel表头
     * @param data excel其他显示制表时间、作者、标题等的数据
     * @return 返回excel文件对象
     */
    protected File formatA(CustomExcelTable customExcelTable, Map<String, Object> data) throws FormatExcelException {
        try {
            title = data.containsKey("title") ? (String) data.get("title") : null;
            datePattern = data.containsKey("datePattern") ? (String) data.get("datePattern") : DEFAULT_DATE_PATTERN;
            fileName = data.containsKey("fileName") ? (String) data.get("fileName") : DEFAULT_FILE_NAME;

            // 声明一个工作薄
            SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
            workbook.setCompressTempFiles(true);
            // 创建Sheet
            Sheet sheet = workbook.createSheet();
//            if (excelItems == null || excelItems.size() <= 0) {
//                throw new FormatExcelException("数据不能为空或者小于等于0");
//            }
            // 创建表格内容
            createCustomTable(0, workbook, sheet, customExcelTable);

            // 将工作簿写到临时文件对象
            File file = IOUtil.getFile(PathConfig.getPath(),fileName);
            OutputStream out = IOUtil.getFileStream(file);
            workbook.write(out);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成C表
     * @param data 标题、制表单位、制表人、统计日期、副标题等内容
     * @param tables
     * @return
     */
    protected File formatC(Map<String, Object> data, CustomExcelTable... tables) {
        try {
            title = data.containsKey("title") ? (String) data.get("title") : null;
            datePattern = data.containsKey("datePattern") ? (String) data.get("datePattern") : DEFAULT_DATE_PATTERN;
            fileName = data.containsKey("fileName") ? (String) data.get("fileName") : DEFAULT_FILE_NAME;

            // 声明一个工作薄
            SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
            workbook.setCompressTempFiles(true);
            // 创建Sheet
            Sheet sheet = workbook.createSheet();

            int rowIndex = 0;

            // 创建C表表头

            // 创建多个表格
            for (int i = 0;i < tables.length;i++) {
                rowIndex = createCustomTable(rowIndex, workbook, sheet, tables[i]);
            }

            // 将工作簿写到临时文件对象
            File file = IOUtil.getFile(PathConfig.getPath(),fileName);
            OutputStream out = IOUtil.getFileStream(file);
            workbook.write(out);
            return file;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 在一个sheet中创建表头+内容并返回行下标，用于一张表中创建多个带表头的表格
     * @param beginRow 开始创建的行
     * @param workbook workbook对象
     * @param sheet sheet对象
     * @param customExcelTable customExcelTable excel表每行显示数据、excel表头
     * @return
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private int createCustomTable(int beginRow, Workbook workbook, Sheet sheet, CustomExcelTable customExcelTable) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Row row = null;
        Cell cell = null;
        Map<String, String> headMap = customExcelTable.getHeadMap();
        List<? extends Object> excelItems = customExcelTable.getExcelItems();

        int rowIndex = beginRow;
        // 标题
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFont(titleFont);
        int cellSize = headMap.size();
        // 创建生成标题的行
        if (title != null) {
            row = sheet.createRow(rowIndex++);
            // 创建单元格
            for (int i = 0;i < cellSize;i++) {
                cell = row.createCell(i);
                cell.setCellStyle(titleCellStyle);
            }
            // 合并单元格
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, cellSize - 1));
            // 设置标题
            row.getCell(0).setCellValue(title);
        }
        row = sheet.createRow(rowIndex++);
        // 保存字段顺序
        String[] fieldNeeded = new String[cellSize];
        int i = 0;
        // 生成表头
        for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext(); ) {
            String fieldName = iter.next();
            String headerName = headMap.get(fieldName);
            fieldNeeded[i] = fieldName;
            cell = row.createCell(i++);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(headerName);
            if (headerName != null) {
                sheet.setColumnWidth(i - 1, headerName.getBytes().length * 2 * 172);
            }
        }

        // 生成数据
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        int rowSize = excelItems == null ? 0 : excelItems.size();
        for (int j = rowIndex; j < rowSize + rowIndex; j++) {
            Object obj = excelItems.get(j - rowIndex);
            row = sheet.createRow(j);
            for (int k = 0; k < cellSize; k++) {
                cell = row.createCell(k);
                cell.setCellStyle(dataCellStyle);
                if (fieldNeeded[k].equals("rowNumber")) {
                    cell.setCellValue(k);
                } else {
                    Field field = obj.getClass().getDeclaredField(fieldNeeded[k]);
                    String getMethodName = ObjectReflect.getMethodName(field.getName(), field.getType().toString().equals("boolean"));
                    Method getMethod = obj.getClass().getMethod(getMethodName);
                    Object value = getMethod.invoke(obj, new Object[]{});
                    cell.setCellValue(value.toString());
                    if (value != null) { // 调整列宽
                        int columnWidth = sheet.getColumnWidth(k);
                        int valueLength = value.toString().getBytes().length * 2 * 172;
                        if (columnWidth < valueLength) {
                            sheet.setColumnWidth(k, valueLength);
                        }
                    }
                }
            }
        }
        return rowIndex + 1;
    }
    /**
     * 生成Excel，返回File对象
     * @param excelItems 表格行数据
     * @return excel文件对象
     */
    public abstract File format(List<? extends Object> excelItems) throws FormatExcelException;
}

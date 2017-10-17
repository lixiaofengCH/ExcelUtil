package org.yzl.house.utils.excel;

import org.yzl.house.domain.CustomExcelTable;
import org.yzl.house.exception.FormatExcelException;
import org.yzl.house.utils.DateUtil;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ltaoj on 2017/10/17.
 */
public class FormatA3 extends FormatExcel {
    public File format(List<?> excelItems) throws FormatExcelException {
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("roomId", "房屋编号");
        headMap.put("campus", "校区");
        headMap.put("buildingName", "楼栋名称");
        headMap.put("roomNumber", "房间号");
        headMap.put("area", "面积（㎡）");
        headMap.put("isMaching", "房型");
        headMap.put("houseType", "户型");
        headMap.put("roomType", "房间类型");
        headMap.put("useStatus", "使用情况");
        headMap.put("name", "住户姓名");
        headMap.put("coDate", "退房时间");
        headMap.put("identity", "身份");
        headMap.put("unit", "单位");
        headMap.put("", "系统");
        headMap.put("", "公开情况");
        headMap.put("keyStatus", "钥匙");
        headMap.put("", "备注");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("fileName", "A3(周转住房房源" + DateUtil.getYear() + DateUtil.getMonth() + ").xlsx");
        return formatA(new CustomExcelTable(excelItems, headMap), data);
    }
}

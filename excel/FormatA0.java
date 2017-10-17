package org.yzl.house.utils.excel;

import org.yzl.house.domain.CustomExcelTable;
import org.yzl.house.exception.FormatExcelException;
import org.yzl.house.utils.PathConfig;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ltaoj on 2017/10/16.
 */
public class FormatA0 extends FormatExcel {

    @Override
    public File format(List<?> excelItems) throws FormatExcelException{
        Map<String, String> headMap = new LinkedHashMap<String, String>();// 保存键顺序与放入一致
        headMap.put("roomId", "序号");
        headMap.put("campus", "校区");
        headMap.put("buildingName", "楼栋名称");
        headMap.put("roomNumber", "房间号");
        headMap.put("area", "面积（㎡）");
        headMap.put("isMaching", "房型");
        headMap.put("houseType", "户型");
        headMap.put("roomType", "房间类型");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("fileName", "A0(住房基础数据表).xlsx");
        return formatA(new CustomExcelTable(excelItems, headMap), data);
    }
}

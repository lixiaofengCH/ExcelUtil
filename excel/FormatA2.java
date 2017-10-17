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
public class FormatA2 extends FormatExcel {
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
        headMap.put("ciDate", "入住时间");
        headMap.put("identity", "身份");
        headMap.put("unit", "单位");
        headMap.put("otherIdentity", "其余身份");
        headMap.put("jobNumber", "工号");
        headMap.put("workyear", "工作年月");
        headMap.put("retireyear", "退休年月");
        headMap.put("type", "房租类型");
        headMap.put("standard", "房租标准");
        headMap.put("shouldRentpay", "应缴房租");
        headMap.put("houseGuardpay", "房卫费");
        headMap.put("payType", "缴租形式");
        headMap.put("realPay", "实缴");
        headMap.put("depositId", "发票号");
        headMap.put("payDate", "交至时间");
        headMap.put("isWelfare", "福利房");
        headMap.put("isCommodity", "商品房");
        headMap.put("mateName", "配偶姓名");
        headMap.put("mateUnit", "配偶单位");
        headMap.put("phone", "联系方式");
        headMap.put("otherRemark", "其余情况说明");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("fileName", "A2(周转住房非扣费部分明细表" + DateUtil.getYear() + DateUtil.getMonth() + ").xlsx");
        return formatA(new CustomExcelTable(excelItems, headMap), data);
    }
}

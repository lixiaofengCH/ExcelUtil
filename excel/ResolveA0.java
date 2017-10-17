package org.yzl.house.utils.excel;

import org.springframework.web.multipart.MultipartFile;
import org.yzl.house.entity.Room;
import org.yzl.house.utils.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ltaoj on 2017/10/7.
 */
public class ResolveA0 extends ResolveExcel {

    public List<Room> resolve(MultipartFile multipartFile) {
        Map<String, String> title = new HashMap<String, String>();
        title.put("房屋编号", "roomId");
        title.put("校区", "campus");
        title.put("楼栋名称", "buildingName");
        title.put("房间号", "roomNumber");
        title.put("面积（㎡）", "area");
        title.put("房型", "isMaching");
        title.put("户型", "houseType");
        title.put("房间类型", "roomType");
        title.put("使用情况", "useStatus");
        return readExcel(multipartFile, title, Room.class);
    }
}

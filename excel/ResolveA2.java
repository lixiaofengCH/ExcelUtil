package org.yzl.house.utils.excel;

import org.springframework.web.multipart.MultipartFile;
import org.yzl.house.entity.Resident;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ltaoj on 2017/10/7.
 */
public class ResolveA2 extends ResolveExcel {
    public List<Resident> resolve(MultipartFile excel) {
        Map<String, String> title = Collections.synchronizedMap(new HashMap<String, String>());
        title.put("住户姓名", "name");
        title.put("联系方式", "phone");
        title.put("身份", "identity");
        title.put("单位", "unit");
        title.put("其余身份", "otherIdentity");
        title.put("工号", "jobNumber");
        title.put("工作年月", "workyear");
        title.put("退休年月", "retireyear");
        title.put("配偶姓名", "mateName");
        title.put("配偶单位", "mateUnit");
        title.put("担保人姓名", "sponsorName");
        title.put("担保人单位", "sponsorUnit");
        title.put("担保人工号", "sponsorJnumber");
        title.put("担保人联系方式", "sponsorPhone");
        return readExcel(excel, title, Resident.class);
    }

}

package org.yzl.house.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by ltaoj on 2017/10/16.
 */
public class NetIOUtil {
    /**
     * 下载文件
     * @param file 要下载的文件
     * @param response
     */
    public static void downloadFile(File file, HttpServletResponse response, boolean isDelete) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[bis.available()];
            bis.read(buffer);
            bis.close();
            if (isDelete) { // 下载后删除临时文件
                file.delete();
            }
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

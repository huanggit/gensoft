package com.gensoft.saasapi.util;

import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by alan on 16-7-13.
 */
@Service
public class FileUtil {

    @Value("${fileupload.path}")
    private String logoImgPath;

    public String saveBytes(byte[] bytes) throws BusinessException {
        File file = createNewFile("test");
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            return file.getPath();
        } catch (IOException e) {
            //// TODO: 16-7-14 log
            throw new BusinessException(ApiResult.CODE_FILE_SAVE_ERROR);
        }finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public File createNewFile(String name){
        /** 根据真实路径创建目录 **/
        File dir = new File(logoImgPath);
        if (!dir.exists())
            dir.mkdirs();
        long epoch = System.currentTimeMillis()/1000;
        File newfile = new File(dir.getPath()+epoch+name);
        return newfile;
    }
}

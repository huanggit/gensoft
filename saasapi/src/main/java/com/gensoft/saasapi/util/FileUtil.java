package com.gensoft.saasapi.util;

import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by alan on 16-7-13.
 */
@Service
public class FileUtil {

    @Value("${fileupload.path}")
    private String logoImgPath;


    public byte[] getBytes(String filePath) throws IOException {
        byte[] buffer = null;
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        return buffer;
    }

    public String saveBytes(byte[] bytes) throws BusinessException {
        File dir = new File(logoImgPath);
        long epoch = System.currentTimeMillis() / 1000;
        String path = dir.getPath() + epoch + ".bin";
        File file = createNewFile(path);
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
        } finally {
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

    public File createNewFile(String name) {
        /** 根据真实路径创建目录 **/
        File dir = new File(logoImgPath);
        if (!dir.exists())
            dir.mkdirs();
        long epoch = System.currentTimeMillis() / 1000;
        File newFile = new File(dir.getPath() + epoch + name);
        return newFile;
    }

    public String saveFileToPath(MultipartFile multipartFile) throws BusinessException {
        try {
            byte[] bytes = multipartFile.getBytes();
            long size = bytes.length;
            if (size >= 1048576) throw new BusinessException(ApiResult.CODE_FILE_SIZE_EXCEEDS_1_M);
            String fileName = multipartFile.getName();
            int index = fileName.lastIndexOf(".");
            String prefix = fileName.substring(index);
            if (!prefix.endsWith("jpg"))
                throw new BusinessException(ApiResult.CODE_FILE_INVALID_FORMAT);
            File newFile = createNewFile(prefix);
            FileCopyUtils.copy(bytes, newFile);
            return newFile.getPath();
        } catch (IOException e) {
            throw new BusinessException(ApiResult.CODE_FILE_SAVE_ERROR);
        }
    }
}

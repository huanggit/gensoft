package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.WebSocketController;
import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import com.gensoft.saasapi.util.FileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by alan on 16-7-15.
 */
@Component
@WebSocketController
public class FileController {

    @Autowired
    FileUtil fileUtil;

    public ByteBuf downloadFile(@RequestParam("filePath") String filePath) throws BusinessException {
        try {
            byte[] bytes = fileUtil.getBytes(filePath);
            ByteBuf buf = Unpooled.buffer().writeBytes(bytes);
            return buf;
        }catch (IOException e){
            throw new BusinessException(ApiResult.CODE_FILE_DONOT_EXISTS);
        }
    }

}

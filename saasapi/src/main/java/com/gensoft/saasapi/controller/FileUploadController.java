package com.gensoft.saasapi.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gensoft.saasapi.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gensoft.core.web.ApiResult;

@RestController
public class FileUploadController {  
	String uploadDir = "userLogo";

	@Autowired
	FileUtil fileUtil;
   
     @RequestMapping(value="/uploadlogo")
	 public ApiResult uploadlogo(HttpServletRequest req, HttpServletResponse x){
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
    	MultipartFile multipartFile = multipartRequest.getFile("file");
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = multipartFile.getOriginalFilename();
		long size =  multipartFile.getSize();
		if(size>=1048576 ){
			return ApiResult.failedInstance("上传文件请保持在1M以下！");
		}
    	int lidex = fileName.lastIndexOf(".");
    	String prefix = fileName.substring(lidex);
    	if(!prefix.endsWith("jpg")){
    		return ApiResult.failedInstance("上传文件类型错误！");
    	}
		File newfile = fileUtil.createNewFile(prefix);
		try {
			multipartFile.transferTo(newfile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ApiResult.successInstance(newfile.getPath()); 
    }
      
   
}  
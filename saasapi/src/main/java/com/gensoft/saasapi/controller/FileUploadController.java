package com.gensoft.saasapi.controller;

import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;  
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gensoft.core.web.ApiResult;
import com.gensoft.saasapi.config.BaseProperties;  
   
@Controller
@RestController
public class FileUploadController {  
	String uploadDir = "userLogo";
	
	@Autowired
	private BaseProperties baseProperties;
	
    
   
     @RequestMapping(value="/uploadlogo")  
     ApiResult uploadlogo(HttpServletRequest req, HttpServletResponse resp){  
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
    	System.out.println(baseProperties.getLogoimgpath());
    	if(!prefix.endsWith("jpg")){
    		return ApiResult.failedInstance("上传文件类型错误！");
    	}
    	/** 根据真实路径创建目录 **/
		File dir = new File(baseProperties.getLogoimgpath());
		if (!dir.exists())
			dir.mkdirs();
        long epoch = System.currentTimeMillis()/1000;
		File newfile = new File(dir.getPath()+epoch+prefix);
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
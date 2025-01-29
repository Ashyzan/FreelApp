package com.freelapp.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	public String saveLogoImage(MultipartFile file) throws IllegalStateException, IOException {
		
		
		
		String finalFileName = LocalDateTime.now() + file.getOriginalFilename();
		
		file.transferTo(new File("/FreelApp/src/main/resources/static/logoImage/" + finalFileName));
		
		String imgPath = "/logoImage/" + finalFileName;
		
		return imgPath;
	}
	
	public Boolean anyErrorFormatImage(MultipartFile file) {
		
		boolean result = false;
		
		String contentType = file.getContentType();
		
		if (!contentType.equals("image/jpeg") && !contentType.equals("image/jpg")) {
		    
			result = true;
			
		}
		
		return result;
	}
	
public Boolean anySizeImgError(MultipartFile file) {
	
		Long maxSize = 500000l;
		
		boolean result = false;
		
		Long contentDimension = file.getSize();
		
		if (Long.compare(contentDimension, maxSize) < 0 ) {
		    
			result = true;
			
		}
		
		return result;
	}
	

}

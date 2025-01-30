package com.freelapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.freelapp.model.User;

@Service
public class UploadFileService {
	
	
	public String saveLogoImage(MultipartFile file, User utente) throws IllegalStateException, IOException {
		
		
//	creazione file name - quando sarà introdotto userLogged aggiungere id utente al nome per univocità
		String finalFileName = LocalDateTime.now() + file.getOriginalFilename(); 
		
//		creazione/verifica esistenza della cartella Upload e sottocartelle
//		utente.getId() sarà poi sostituito con userLogged
		Path uploadPath = Paths.get("upload/images/logo/utentiId/" + utente.getId() );
		
//		verifica esistenza ed eventuale creazione della cartella Upload 
//		e sottocartelle
		if(!Files.exists(uploadPath)){
			Files.createDirectories(uploadPath);
		}
		
//	trasferimento alla directory del file 
		InputStream inputStream = file.getInputStream();
		Path filePath = uploadPath.resolve(finalFileName);
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

//	creazione dell'url che viene salvato a db nella colonna urlLogo
		String imgPath = uploadPath + "/" + finalFileName;
		
		return imgPath;
	}
	
//	metodo che verifica se il formato del file rispecchia i requisiti (JPEG/JPG) e restituisce
//	un boolean per la successiva generazione dell'errore custom relativo al formato
	public Boolean anyErrorFormatImage(MultipartFile file) {
		
		boolean result = false;
		
		String contentType = file.getContentType();
		
		if (!file.isEmpty() && !contentType.equals("image/jpeg") && !contentType.equals("image/jpg")) {
		    
			result = true;
			
		}
		
		return result;
	
	}
	
	


}

package com.tutorial.oauth2.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceController {
	
	
	@GetMapping("/info")
	public void getInfo(HttpServletResponse response,@RequestParam Map<String,String> params) throws IOException{
		return new ResponseEntity<>("Welcome, everybody!"+currentUserName, 
			          HttpStatus.OK); 
	}
	
	
	@GetMapping("/resource")
	public ResponseEntity<?> getResource(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		
		    return new ResponseEntity<>("Welcome "+currentUserName, 
			          HttpStatus.OK); 
		}else {
			return new ResponseEntity<>("Welcome whoever!", 
			          HttpStatus.OK); 
		}
		 
	}
}

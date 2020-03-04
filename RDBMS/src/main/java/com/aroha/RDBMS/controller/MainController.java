package com.aroha.RDBMS.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aroha.RDBMS.model.Test;
import com.aroha.RDBMS.service.UserService;

@RestController
public class MainController {

	@Value("${spring.datasource.url}")
	public String dbUrl;
	
    @Autowired
    UserService userService;
    
//    @PostMapping("/getTable")
//	public ResponseEntity<?> getTableData(@RequestBody Test test) throws Exception{
//		return ResponseEntity.ok(userService.getTableData(test));		
//	}
    
    @PostMapping("/allData")
    public ResponseEntity<?> allData(@RequestBody Test test) throws Exception{
    	return ResponseEntity.ok(userService.allData(test, dbUrl));
    }

}

package com.aroha.RDBMS.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.RDBMS.model.Test;
import com.aroha.RDBMS.payload.DataResponse;
import com.aroha.RDBMS.repository.TestRepository;
import com.google.gson.Gson;

@Service
public class UserService {

	@Autowired
	TestRepository testRepo;


	Statement stmt=null;
	ResultSet rs=null;
	Connection con = null;

	public DataResponse allData(Test test, String dbUrl){
		int id=dbUrl.lastIndexOf('/');
		String str=dbUrl.substring(0, id);
		String mainUrl=str+"/"+test.getDbName();
		JSONObject jsonObject = new JSONObject();
		DataResponse dataResponse = new DataResponse();
		boolean status=true;
		JSONArray jsona = null;
		try {
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
            //=================================================================//
			con = getConnection(mainUrl, test.getUsername(), test.getPassword());
			stmt = con.createStatement();
			String res="select * from"+" "+test.getTableName();
			rs = stmt.executeQuery(res);
			jsona = getResult(rs);
			//=================================================================//
			if(jsona.isNull(1)) {
				status=false;
				dataResponse.setStatus(status);
				dataResponse.setMessage("No Records Found in the Table");
				return dataResponse;
			}
			//=================================================================//
			List output=getJsonArrayAsList(jsona);
			status=true;
			dataResponse.setStatus(status);
			dataResponse.setMessage("Data Fetched Successfully");
			dataResponse.setResult(getJsonArrayAsList(jsona));
            //=================================================================//
			JSONObject output1 = new JSONObject(output);
			String path="D:/JSONDATA/";
			String name="DB"+"-"+test.getDbName()+"_"+"TABLE"+"-"+test.getTableName();
			File file=new File(path+name);
			String csv = CDL.toString(jsona);
			FileUtils.writeStringToFile(file, csv);
			return dataResponse;
		}
		catch(Exception e) {
			status=false;
			dataResponse.setStatus(status);
			System.out.println(e.getMessage());
			dataResponse.setMessage(e.getMessage());
			return dataResponse;
		}
	}

	private Connection getConnection(final String jdbcUrl, final String userName, final String password)
			throws Exception {
		Connection conn = DriverManager.getConnection(jdbcUrl, userName, password);
		return conn;
	}

	private JSONArray getResult(ResultSet rs) throws Exception {
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				obj.put(column_name, rs.getObject(column_name));
			}
			json.put(obj);
		}
		return json;
	}


	private List getJsonArrayAsList(JSONArray jsona) {
		return jsona.toList();
	}

}

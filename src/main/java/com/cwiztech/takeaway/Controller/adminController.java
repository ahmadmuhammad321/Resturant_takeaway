package com.cwiztech.takeaway.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.cwiztech.takeaway.Model.Admin;
import com.cwiztech.takeaway.Repository.adminRepository;
import com.cwiztech.takeaway.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class adminController {
	
	@Autowired
	private adminRepository adminrepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException{
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		List<Admin> admin = adminrepository.findAll();
		rtn = mapper.writeValueAsString(admin);
		return rtn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		List<Admin> admin = adminrepository.findActive();
		
		rtn = mapper.writeValueAsString(admin);
		return rtn;
	}
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String getbyID(@PathVariable long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		Admin admin = new Admin();
		admin = adminrepository.findOne(id);
		
		rtn = mapper.writeValueAsString(admin);
		return rtn;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		JSONArray objArray = new JSONArray(); 
		objArray.put(new JSONObject(data));
		objArray = new JSONArray(InsertUpdateAll(objArray.toString()));
		
		return objArray.getJSONObject(0).toString();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable long id, @RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		JSONArray objArray = new JSONArray(); 
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("admin_ID", id);
		
		objArray.put(jsonObj);
		objArray = new JSONArray(InsertUpdateAll(objArray.toString()));
		
		return objArray.getJSONObject(0).toString();
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public String insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		return InsertUpdateAll(data);
	}
	
	public String InsertUpdateAll(String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long id = 0;
		
		JSONArray jsonadmins = new JSONArray(data);
		List<Admin> admins = new ArrayList<Admin>();
		
		for (int i=0; i<jsonadmins.length(); i++) {
			JSONObject jsonObj = jsonadmins.getJSONObject(i);
			Admin admin = new Admin();
	
			if (jsonObj.has("admin_ID") && !jsonObj.isNull("admin_ID")) {
				id = jsonObj.getLong("admin_ID");
				
				if (id>0) {
					admin = adminrepository.findOne(id);
					if(admin == null)
						continue;
				}
			}
			
			if (id == 0) {
	
				if (!jsonObj.has("admin_NAME") || jsonObj.isNull("admin_NAME"))
					return "admin_NAME is missing";
				
				if (!jsonObj.has("admin_EMAIL") || jsonObj.isNull("admin_EMAIL"))
					return "admin_EMAIL is missing";
				if (!jsonObj.has("admin_PASSWORD") || jsonObj.isNull("admin_PASSWORD"))
					return "admin_PASSWORD is missing";
				
			}
			
			if (jsonObj.has("admin_NAME") && !jsonObj.isNull("admin_NAME"))
				admin.setADMIN_NAME(jsonObj.getString("admin_NAME"));
			if (jsonObj.has("admin_EMAIL") && !jsonObj.isNull("admin_EMAIL"))
				admin.setADMIN_EMAIL(jsonObj.getString("admin_EMAIL"));
			if (jsonObj.has("admin_PASSWORD") && !jsonObj.isNull("admin_PASSWORD"))
				admin.setADMIN_PASSWORD(jsonObj.getString("admin_PASSWORD"));
			if (jsonObj.has("admin_STATUS") && !jsonObj.isNull("admin_STATUS"))
				admin.setADMIN_STATUS(jsonObj.getString("admin_STATUS"));
			
			
	
			if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				admin.setISACTIVE(jsonObj.getString("isactive"));
			else if (id == 0)
				admin.setISACTIVE("Y");
			
			admin.setMODIFIED_BY((long) 0);
			admin.setMODIFIED_WHEN(dateFormat1.format(date));
			admin = adminrepository.saveAndFlush(admin);
			
			admins.add(admin);
		}
		
		rtn = mapper.writeValueAsString(admins);
		return rtn;	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record deleted!";
		adminrepository.delete(id);
		return rtn;	
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record removed!";
		Admin admin = adminrepository.findOne(id);
		admin.setISACTIVE("N");
		adminrepository.saveAndFlush(admin);
		return rtn;	
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		JSONObject jsonObj = new JSONObject(data);
		if (!jsonObj.has("search") || jsonObj.isNull("search"))
			return "search is missing";
		
		List<Admin> admin = adminrepository.findBySearch("%" + jsonObj.getString("search") + "%");
		
		
		rtn = mapper.writeValueAsString(admin);
		return rtn;
	}

	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public String getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		JSONObject jsonObj = new JSONObject(data);
		if (!jsonObj.has("search") || jsonObj.isNull("search"))
			return "search is missing";
		
		List<Admin> admin = adminrepository.findAllBySearch("%" + jsonObj.getString("search") + "%");
		
		rtn = mapper.writeValueAsString(admin);
		return rtn;
	}
	

}

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


import com.cwiztech.takeaway.Model.Chef;
import com.cwiztech.takeaway.Model.Order;
import com.cwiztech.takeaway.Repository.chefRepository;
import com.cwiztech.takeaway.Repository.orderRepository;
import com.cwiztech.takeaway.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/chef")
public class chefController {
	
	@Autowired
	private chefRepository chefrepository;
	@Autowired
	private orderRepository orderrepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException{
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		List<Chef> chef = chefrepository.findAll();
		rtn = mapper.writeValueAsString(chef);
		return rtn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		List<Chef> chef = chefrepository.findActive();
		
		rtn = mapper.writeValueAsString(chef);
		return rtn;
	}
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String getbyID(@PathVariable long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		Chef chef = new Chef();
		chef = chefrepository.findOne(id);
		
		rtn = mapper.writeValueAsString(chef);
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
		jsonObj.put("chef_ID", id);
		
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
		
		JSONArray jsonchefs = new JSONArray(data);
		List<Chef> chefs = new ArrayList<Chef>();
		
		for (int i=0; i<jsonchefs.length(); i++) {
			JSONObject jsonObj = jsonchefs.getJSONObject(i);
			Chef chef = new Chef();
	
			if (jsonObj.has("chef_ID") && !jsonObj.isNull("chef_ID")) {
				id = jsonObj.getLong("chef_ID");
				
				if (id>0) {
					chef = chefrepository.findOne(id);
					if(chef == null)
						continue;
				}
			}
			
			if (id == 0) {
	
				if (!jsonObj.has("chef_NAME") || jsonObj.isNull("chef_NAME"))
					return "chef_NAME is missing";
				if (!jsonObj.has("chef_EMAIL") || jsonObj.isNull("chef_EMAIL"))
					return "chef_EMAIL is missing";
				if (!jsonObj.has("chef_PASSWORD") || jsonObj.isNull("chef_PASSWORD"))
					return "chef_PASSWORD is missing";
				if (!jsonObj.has("order_ID") || jsonObj.isNull("order_ID"))
					return "order_ID is missing";
				
				
			}
			
			if (jsonObj.has("chef_NAME") && !jsonObj.isNull("chef_NAME"))
				chef.setCHEF_NAME(jsonObj.getString("chef_NAME"));
			if (jsonObj.has("chef_EMAIL") && !jsonObj.isNull("chef_EMAIL"))
				chef.setCHEF_EMAIL(jsonObj.getString("chef_EMAIL"));
			if (jsonObj.has("chef_PASSWORD") && !jsonObj.isNull("chef_PASSWORD"))
				chef.setCHEF_PASSWORD(jsonObj.getString("chef_PASSWORD"));
			if (jsonObj.has("order_ID") && !jsonObj.isNull("order_ID")) {
				Order order = new Order();
				if (id>0) {
					 order = orderrepository.findOne(jsonObj.getLong("order_ID"));
					if(order == null)
						return "order_ID is invalid";
				}
				chef.setORDER_ID(order);
			}
			
			
	
			if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				chef.setISACTIVE(jsonObj.getString("isactive"));
			else if (id == 0)
				chef.setISACTIVE("Y");
			
			chef.setMODIFIED_BY((long) 0);
			chef.setMODIFIED_WHEN(dateFormat1.format(date));
			chef = chefrepository.saveAndFlush(chef);
			
			chefs.add(chef);
		}
		
		rtn = mapper.writeValueAsString(chefs);
		return rtn;	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record deleted!";
		chefrepository.delete(id);
		return rtn;	
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record removed!";
		Chef chef = chefrepository.findOne(id);
		chef.setISACTIVE("N");
		chefrepository.saveAndFlush(chef);
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
		
		List<Chef> chef = chefrepository.findBySearch("%" + jsonObj.getString("search") + "%");
		
		
		rtn = mapper.writeValueAsString(chef);
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
		
		List<Chef> chef = chefrepository.findAllBySearch("%" + jsonObj.getString("search") + "%");
		
		rtn = mapper.writeValueAsString(chef);
		return rtn;
	}

}

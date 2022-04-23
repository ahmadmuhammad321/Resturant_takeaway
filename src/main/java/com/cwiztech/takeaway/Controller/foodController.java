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

import com.cwiztech.takeaway.Model.Food;
import com.cwiztech.takeaway.Repository.foodRepository;
import com.cwiztech.takeaway.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/food")
public class foodController {
	
	@Autowired
	private foodRepository foodrepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException{
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		List<Food> food = foodrepository.findAll();
		rtn = mapper.writeValueAsString(food);
		return rtn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		List<Food> food = foodrepository.findActive();
		
		rtn = mapper.writeValueAsString(food);
		return rtn;
	}
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String getbyID(@PathVariable long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		Food food = new Food();
		food = foodrepository.findOne(id);
		
		rtn = mapper.writeValueAsString(food);
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
		jsonObj.put("food_ID", id);
		
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
		
		JSONArray jsonfoods = new JSONArray(data);
		List<Food> foods = new ArrayList<Food>();
		
		for (int i=0; i<jsonfoods.length(); i++) {
			JSONObject jsonObj = jsonfoods.getJSONObject(i);
			Food food = new Food();
	
			if (jsonObj.has("food_ID") && !jsonObj.isNull("food_ID")) {
				id = jsonObj.getLong("food_ID");
				
				if (id>0) {
					food = foodrepository.findOne(id);
					if(food == null)
						continue;
				}
			}
			
			if (id == 0) {
	
				if (!jsonObj.has("food_NAME") || jsonObj.isNull("food_NAME"))
					return "food_NAME is missing";
				if (!jsonObj.has("food_QUANTITY") || jsonObj.isNull("food_QUANTITY"))
					return "food_QUANTITY is missing";
				if (!jsonObj.has("unit_PRICE") || jsonObj.isNull("unit_PRICE"))
					return "unit_PRICE is missing";
				
				
			}
			
			if (jsonObj.has("food_NAME") && !jsonObj.isNull("food_NAME"))
				food.setFOOD_NAME(jsonObj.getString("food_NAME"));
			if (jsonObj.has("food_QUANTITY") && !jsonObj.isNull("food_QUANTITY"))
				food.setFOOD_QUANTITY(jsonObj.getString("food_QUANTITY"));
			if (jsonObj.has("unit_PRICE") && !jsonObj.isNull("unit_PRICE"))
				food.setUNIT_PRICE(jsonObj.getLong("unit_PRICE"));
			if (jsonObj.has("food_CATEGORY") && !jsonObj.isNull("food_CATEGORY"))
				food.setFOOD_CATEGORY(jsonObj.getString("food_CATEGORY"));
			
			
			
	
			if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				food.setISACTIVE(jsonObj.getString("isactive"));
			else if (id == 0)
				food.setISACTIVE("Y");
			
			food.setMODIFIED_BY((long) 0);
			food.setMODIFIED_WHEN(dateFormat1.format(date));
			food = foodrepository.saveAndFlush(food);
			
			foods.add(food);
		}
		
		rtn = mapper.writeValueAsString(foods);
		return rtn;	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record deleted!";
		foodrepository.delete(id);
		return rtn;	
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record removed!";
		Food food = foodrepository.findOne(id);
		food.setISACTIVE("N");
		foodrepository.saveAndFlush(food);
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
		
		List<Food> food = foodrepository.findBySearch("%" + jsonObj.getString("search") + "%");
		
		
		rtn = mapper.writeValueAsString(food);
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
		
		List<Food> food = foodrepository.findAllBySearch("%" + jsonObj.getString("search") + "%");
		
		rtn = mapper.writeValueAsString(food);
		return rtn;
	}

}

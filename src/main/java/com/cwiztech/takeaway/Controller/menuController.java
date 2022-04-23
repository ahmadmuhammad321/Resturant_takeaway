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
import com.cwiztech.takeaway.Model.Menu;
import com.cwiztech.takeaway.Repository.foodRepository;
import com.cwiztech.takeaway.Repository.menuRepository;
import com.cwiztech.takeaway.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/menu")
public class menuController {
	
	@Autowired
	private menuRepository menurepository;
	@Autowired
	private foodRepository foodrepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException{
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		List<Menu> menu = menurepository.findAll();
		rtn = mapper.writeValueAsString(menu);
		return rtn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		List<Menu> menu = menurepository.findActive();
		
		rtn = mapper.writeValueAsString(menu);
		return rtn;
	}
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String getbyID(@PathVariable long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		Menu menu = new Menu();
		menu = menurepository.findOne(id);
		
		rtn = mapper.writeValueAsString(menu);
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
		jsonObj.put("menu_ID", id);
		
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
		
		JSONArray jsonmenus = new JSONArray(data);
		List<Menu> menus = new ArrayList<Menu>();
		
		for (int i=0; i<jsonmenus.length(); i++) {
			JSONObject jsonObj = jsonmenus.getJSONObject(i);
			Menu menu = new Menu();
	
			if (jsonObj.has("menu_ID") && !jsonObj.isNull("menu_ID")) {
				id = jsonObj.getLong("menu_ID");
				
				if (id>0) {
					menu = menurepository.findOne(id);
					if(menu == null)
						continue;
				}
			}
			
			if (id == 0) {
	
				if (!jsonObj.has("price") || jsonObj.isNull("price"))
					return "price is missing";
				if (!jsonObj.has("food_ID") || jsonObj.isNull("food_ID"))
					return "food_ID is missing";
				
				
			}
			
			if (jsonObj.has("price") && !jsonObj.isNull("price"))
				menu.setPRICE(jsonObj.getLong("price"));
			if (jsonObj.has("food_ID") && !jsonObj.isNull("food_ID")) {
				Food food = new Food();
				if (id>0) {
					 food = foodrepository.findOne(jsonObj.getLong("food_ID"));
					if(food == null)
						return "food_ID is invalid";
				}
				menu.setFOOD_ID(food);
			}
			
			
			
	
			if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				menu.setISACTIVE(jsonObj.getString("isactive"));
			else if (id == 0)
				menu.setISACTIVE("Y");
			
			menu.setMODIFIED_BY((long) 0);
			menu.setMODIFIED_WHEN(dateFormat1.format(date));
			menu = menurepository.saveAndFlush(menu);
			
			menus.add(menu);
		}
		
		rtn = mapper.writeValueAsString(menus);
		return rtn;	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record deleted!";
		menurepository.delete(id);
		return rtn;	
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record removed!";
		Menu menu = menurepository.findOne(id);
		menu.setISACTIVE("N");
		menurepository.saveAndFlush(menu);
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
		
		List<Menu> menu = menurepository.findBySearch("%" + jsonObj.getString("search") + "%");
		
		
		rtn = mapper.writeValueAsString(menu);
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
		
		List<Menu> menu = menurepository.findAllBySearch("%" + jsonObj.getString("search") + "%");
		
		rtn = mapper.writeValueAsString(menu);
		return rtn;
	}

}

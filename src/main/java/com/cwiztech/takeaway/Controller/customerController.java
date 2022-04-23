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

import com.cwiztech.takeaway.Model.Customer;
import com.cwiztech.takeaway.Model.Food;
import com.cwiztech.takeaway.Model.Payment;
import com.cwiztech.takeaway.Repository.customerRepository;
import com.cwiztech.takeaway.Repository.foodRepository;
import com.cwiztech.takeaway.Repository.paymentRepository;
import com.cwiztech.takeaway.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class customerController {
	
	@Autowired
	private customerRepository customerrepository;
	@Autowired
	private foodRepository foodrepository;
	@Autowired
	private paymentRepository paymentrepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException{
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		List<Customer> customer = customerrepository.findAll();
		rtn = mapper.writeValueAsString(customer);
		return rtn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		List<Customer> customer = customerrepository.findActive();
		
		rtn = mapper.writeValueAsString(customer);
		return rtn;
	}
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String getbyID(@PathVariable long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		Customer customer = new Customer();
		customer = customerrepository.findOne(id);
		
		rtn = mapper.writeValueAsString(customer);
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
		jsonObj.put("customer_ID", id);
		
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
		
		JSONArray jsoncustomers = new JSONArray(data);
		List<Customer> customers = new ArrayList<Customer>();
		
		for (int i=0; i<jsoncustomers.length(); i++) {
			JSONObject jsonObj = jsoncustomers.getJSONObject(i);
			Customer customer = new Customer();
	
			if (jsonObj.has("customer_ID") && !jsonObj.isNull("customer_ID")) {
				id = jsonObj.getLong("customer_ID");
				
				if (id>0) {
					customer = customerrepository.findOne(id);
					if(customer == null)
						continue;
				}
			}
			
			if (id == 0) {
	
				if (!jsonObj.has("customer_NAME") || jsonObj.isNull("customer_NAME"))
					return "customer_NAME is missing";
				if (!jsonObj.has("customer_EMAIL") || jsonObj.isNull("customer_EMAIL"))
					return "customer_EMAIL is missing";
				if (!jsonObj.has("food_ID") || jsonObj.isNull("food_ID"))
					return "food_ID is missing";
				if (!jsonObj.has("payment_ID") || jsonObj.isNull("payment_ID"))
					return "payment_ID is missing";
				
				
			}
			
			if (jsonObj.has("customer_NAME") && !jsonObj.isNull("customer_NAME"))
				customer.setCUSTOMER_NAME(jsonObj.getString("customer_NAME"));
			if (jsonObj.has("customer_EMAIL") && !jsonObj.isNull("customer_EMAIL"))
				customer.setCUSTOMER_EMAIL(jsonObj.getString("customer_EMAIL"));
			if (jsonObj.has("customer_PHONE") && !jsonObj.isNull("customer_PHONE"))
				customer.setCUSTOMER_PHONE(jsonObj.getLong("customer_PHONE"));
			if (jsonObj.has("food_ID") && !jsonObj.isNull("food_ID")) {
				Food food = new Food();
				if (id>0) {
					 food = foodrepository.findOne(jsonObj.getLong("food_ID"));
					if(food == null)
						return "food_ID is invalid";
				}
				customer.setFOOD_ID(food);
			}
			if (jsonObj.has("payment_ID") && !jsonObj.isNull("payment_ID")) {
				Payment payment = new Payment();
				if (id>0) {
					 payment = paymentrepository.findOne(jsonObj.getLong("payment_ID"));
					if(payment == null)
						return "payment_ID is invalid";
				}
				customer.setPAYMENT_ID(payment);
			}
			
			
	
			if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				customer.setISACTIVE(jsonObj.getString("isactive"));
			else if (id == 0)
				customer.setISACTIVE("Y");
			
			customer.setMODIFIED_BY((long) 0);
			customer.setMODIFIED_WHEN(dateFormat1.format(date));
			customer = customerrepository.saveAndFlush(customer);
			
			customers.add(customer);
		}
		
		rtn = mapper.writeValueAsString(customers);
		return rtn;	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record deleted!";
		customerrepository.delete(id);
		return rtn;	
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record removed!";
		Customer customer = customerrepository.findOne(id);
		customer.setISACTIVE("N");
		customerrepository.saveAndFlush(customer);
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
		
		List<Customer> customer = customerrepository.findBySearch("%" + jsonObj.getString("search") + "%");
		
		
		rtn = mapper.writeValueAsString(customer);
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
		
		List<Customer> customer = customerrepository.findAllBySearch("%" + jsonObj.getString("search") + "%");
		
		rtn = mapper.writeValueAsString(customer);
		return rtn;
	}

}

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
import com.cwiztech.takeaway.Model.Order;
import com.cwiztech.takeaway.Model.Payment;
import com.cwiztech.takeaway.Repository.customerRepository;
import com.cwiztech.takeaway.Repository.orderRepository;
import com.cwiztech.takeaway.Repository.paymentRepository;
import com.cwiztech.takeaway.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/payment")
public class paymentController {
	
	@Autowired
	private paymentRepository paymentrepository;
	@Autowired
	private orderRepository orderrepository;
	@Autowired
	private customerRepository customerrepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException{
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		List<Payment> payment = paymentrepository.findAll();
		rtn = mapper.writeValueAsString(payment);
		return rtn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		List<Payment> payment = paymentrepository.findActive();
		
		rtn = mapper.writeValueAsString(payment);
		return rtn;
	}
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String getbyID(@PathVariable long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String rtn = "";
		
		Payment payment = new Payment();
		payment = paymentrepository.findOne(id);
		
		rtn = mapper.writeValueAsString(payment);
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
		jsonObj.put("payment_ID", id);
		
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
		
		JSONArray jsonpayments = new JSONArray(data);
		List<Payment> payments = new ArrayList<Payment>();
		
		for (int i=0; i<jsonpayments.length(); i++) {
			JSONObject jsonObj = jsonpayments.getJSONObject(i);
			Payment payment = new Payment();
	
			if (jsonObj.has("payment_ID") && !jsonObj.isNull("payment_ID")) {
				id = jsonObj.getLong("payment_ID");
				
				if (id>0) {
					payment = paymentrepository.findOne(id);
					if(payment == null)
						continue;
				}
			}
			
			if (id == 0) {
	
				if (!jsonObj.has("customer_ID") || jsonObj.isNull("customer_ID"))
					return "customer_ID is missing";
				if (!jsonObj.has("order_ID") || jsonObj.isNull("order_ID"))
					return "order_ID is missing";
				if (!jsonObj.has("payment_AMOUNT") || jsonObj.isNull("payment_AMOUNT"))
					return "payment_AMOUNT is missing";
				
				
				
			}
			
			
			if (jsonObj.has("customer_ID") && !jsonObj.isNull("customer_ID")) {
				Customer customer = new Customer();
				if (id>0) {
					 customer = customerrepository.findOne(jsonObj.getLong("customer_ID"));
					if(customer == null)
						return "customer_ID is invalid";
				}
				payment.setCUSTOMER_ID(customer);
			}
			if (jsonObj.has("order_ID") && !jsonObj.isNull("order_ID")) {
				Order order = new Order();
				if (id>0) {
					 order = orderrepository.findOne(jsonObj.getLong("customer_ID"));
					if(order == null)
						return "customer_ID is invalid";
				}
				payment.setORDER_ID(order);
			}
			if (jsonObj.has("payment_AMOUNT") && !jsonObj.isNull("payment_AMOUNT"))
				payment.setPAYMENT_AMOUNT(jsonObj.getLong("payment_AMOUNT"));
			if (jsonObj.has("payment_DATE") && !jsonObj.isNull("payment_DATE"))
				payment.setPAYMENT_DATE(jsonObj.getString("payment_DATE"));
			if (jsonObj.has("payment_TYPE") && !jsonObj.isNull("payment_TYPE"))
				payment.setPAYMENT_TYPE(jsonObj.getString("payment_TYPE"));
			
			
			
	
			if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				payment.setISACTIVE(jsonObj.getString("isactive"));
			else if (id == 0)
				payment.setISACTIVE("Y");
			
			payment.setMODIFIED_BY((long) 0);
			payment.setMODIFIED_WHEN(dateFormat1.format(date));
			payment = paymentrepository.saveAndFlush(payment);
			
			payments.add(payment);
		}
		
		rtn = mapper.writeValueAsString(payments);
		return rtn;	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record deleted!";
		paymentrepository.delete(id);
		return rtn;	
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		if (checkTokenResponse.has("error"))
			return checkTokenResponse.toString();
		
		String rtn = "Record removed!";
		Payment payment = paymentrepository.findOne(id);
		payment.setISACTIVE("N");
		paymentrepository.saveAndFlush(payment);
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
		
		List<Payment> payment = paymentrepository.findBySearch("%" + jsonObj.getString("search") + "%");
		
		
		rtn = mapper.writeValueAsString(payment);
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
		
		List<Payment> payment = paymentrepository.findAllBySearch("%" + jsonObj.getString("search") + "%");
		
		rtn = mapper.writeValueAsString(payment);
		return rtn;
	}

}

package com.cwiztech.takeaway.token;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AccessToken {
	
	public static JSONObject checkToken(String accessToken) {
		JSONObject rtn = new JSONObject();
		RestTemplate restTemplate = new RestTemplate();
		
		String token = accessToken;
		String[] parts = token.split(" ");
		String OauthToken = parts[1];
		
		try {
			ResponseEntity<String> getToken = restTemplate.exchange("http://api.cwiztech.com:8080/v1/oauth/check_token?token=" + OauthToken, HttpMethod.GET, null, String.class);
			rtn = new JSONObject(getToken.getBody().toString());
		} catch (Exception e) {
			rtn.put("error", "invalid_token");
			rtn.put("error_description", "Token was not recognised");
		}

		return rtn;
	}

}

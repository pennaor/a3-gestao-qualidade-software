package com.lucas.picpay.service;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthTransacaoService
{
	public Boolean validacaoTransicao() {
        try
        {
        	ResponseEntity<Map> response = new RestTemplate().getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        	
        	if (response.getStatusCode() != HttpStatus.OK) 
        	
        		return false;

            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return (Boolean) data.get("authorization");

        } 
        
        catch (Exception e) 
        {
            return false;
        }
    }
}

package com.javasampleapproach.resttemplatesecurity.customize;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.javasampleapproach.resttemplatesecurity.model.Customer;



public class RestfulClient {
	
	RestTemplate restTemplate;
	
	public RestfulClient(String username, String password){
		restTemplate = RestTemplateBuilder.securityRestTemplateBuilder(username, password);
	}
	

	/**
	 * POST ENTITYs
	 */
	public void postEntity(){
	    System.out.println("Begin /POST request!");
	        // replace http://localhost:8080 by your restful services
	    String postUrl = "http://localhost:8080/post";
	    Customer customer = new Customer(123, "Jack", 23);
	    ResponseEntity<String> postResponse = restTemplate.postForEntity(postUrl, customer, String.class);
	    System.out.println("======================================Response for Post Request: " + postResponse.getBody());
	}
	
	/**
	 * GET ENTITY
	 */
	public void getEntity(){
	    System.out.println("Begin /GET request!");
	    String getUrl = "http://localhost:8080/get?id=1&name='Mary'&age=20";
	    ResponseEntity<Customer> getResponse = restTemplate.getForEntity(getUrl, Customer.class);
	    System.out.println("======================================Response for Get Request: " + getResponse.getBody().toString());
	}
}

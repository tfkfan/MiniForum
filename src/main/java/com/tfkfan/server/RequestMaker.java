package com.tfkfan.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class RequestMaker<T> {
	private final static Logger log = Logger.getLogger(RequestMaker.class.getName());

	private Class<T> clazz;

	private RestTemplate temp;

	public RequestMaker(Class<T> clazz) {
		this.clazz = clazz;
		temp = new RestTemplate();

	}

	public List<T> get(String url,ParameterizedTypeReference<List<T>> type) {
		ResponseEntity<List<T>> responseEntity = temp.exchange(ServerUtils.getAbsoluteRoot() + url, HttpMethod.GET,
				null, type);
		List<T> objects = responseEntity.getBody();
		return objects;
	}

	public T put(HttpEntity<T> entity, String url, ParameterizedTypeReference<T> type) { 
		ResponseEntity<T> responseEntity = temp.exchange(ServerUtils.getAbsoluteRoot() + url, HttpMethod.PUT,
				entity, clazz);
		T object = responseEntity.getBody();
		log.info(object.toString());
		return object;
	}

}

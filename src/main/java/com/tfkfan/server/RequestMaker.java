package com.tfkfan.server;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RequestMaker<T> {
	private final static Logger log = Logger.getLogger(RequestMaker.class.getName());

	private Class<T> clazz;

	private RestTemplate temp;

	public RequestMaker(Class<T> clazz) {
		this.clazz = clazz;
		temp = new RestTemplate();
	}

	public List<T> get(String url,ParameterizedTypeReference<List<T>> type) {
		return temp.exchange(ServerUtils.getAbsoluteRoot() + url, HttpMethod.GET,
				null, type).getBody();
	}
	
	public T get(String url) {
		return temp.exchange(ServerUtils.getAbsoluteRoot() + url, HttpMethod.GET,
				null, clazz).getBody();
	}

	public void put(T entity, String url) {
		temp.exchange(ServerUtils.getAbsoluteRoot() + url, HttpMethod.PUT,
				new HttpEntity<T>(entity), clazz);
	}

	public void post(T entity, String url) {
		temp.exchange(ServerUtils.getAbsoluteRoot() + url, HttpMethod.POST,
				new HttpEntity<T>(entity), clazz);
	}
}

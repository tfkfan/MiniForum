package com.tfkfan.server;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class RequestMaker<T> {
	private Client client;
	private WebTarget target;
	private Class<T> clazz;

	public RequestMaker() {
		init();
	}

	public void init() {
		client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
	}

	public WebTarget getTarget() {
		return target;
	}

	public void setTarget(String page) {
		target = client.target(ServerUtils.getAbsoluteRoot() + page);
	}

	public List<T> getList(GenericType<List<T>> type) {
		Response resp = target.request(MediaType.APPLICATION_JSON).get(Response.class);
		return resp.readEntity(type);
	}

	public T addEntity(T entity, GenericType<T> type) {
		Response resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(Entity.json(entity), Response.class);
		return null;
	}

}

package com.maiwodi.networkanalyzer.app.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.json.JSONObject;

public class JerseyClient {

	private static final Logger LOGGER = LogManager.getLogger(JerseyClient.class.getName());

	public static String sendGetResponse(String base, String path) {
		LOGGER.debug("The URL of GET is: {}", base + path);

		Client client = ClientBuilder.newClient();
		// base example: http://localhost:9998
		// path example: resource
		WebTarget webTarget = client.target(base).path(path);

		Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = builder.get();

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.readEntity(String.class);
		return output;
	}

	public static Response sendPostResponse(String base, String path, JSONObject json) {

		String jsonStr = json.toString();

		return sendPostResponse(base, path, jsonStr);
	}

	public static Response sendPostResponse(String base, String path, String json) {
		LOGGER.debug("The URL of POST is: {}, and the JSON to POST is {}", base + path, json);

		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(base).path(path);

		Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = builder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		LOGGER.debug("Post response status: {}", response.getStatus());

		return response;
	}

	public static Response sendDeleteResponse(String base, String path) {
		LOGGER.debug("The URL of DELETE is: {}", base + path);

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(base).path(path);

		Invocation.Builder builder = webTarget.request();
		Response response = builder.delete();

		LOGGER.debug("Delete response status: {}", response.getStatus());

		return response;
	}
}

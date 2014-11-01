package com.fmt.rest.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fmt.rest.service.MongoResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/*
function setUserObject(accountName, obj) {
	$.ajax( { url: "https://api.mongolab.com/api/1/databases/dailybalance/collections/dailybalances?apiKey=4faf2a26e4b020a46b49e5ed",
          data: JSON.stringify( obj ),
          type: "POST",
          contentType: "application/json" } );
}
 */
public class MongoClientPost {

	public static void main(String[] args) {
	}

	public static String MongoPost(String user, String pass, String site, String json) {
		String GETResponse= "no response";
		System.out.println("MongoPost URL: "+ MongoClientGet.MONGO_URL);

		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(MongoClientGet.MONGO_URL)
					.queryParam("apiKey", MongoResource.APIKEY);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (!(response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.CREATED.getStatusCode())) {
				throw new RuntimeException("Failed(MongoPost): HTTP error code : "+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server(MongoPost):\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}

//-------FROM Password project---------	
	public static String PasswordPostForm(String username, String password, String site, String role) {
		final String url= MongoClientGet.PASS_REST_URL+ "/password";
		System.out.println("URL(PasswordPostForm): "+ url);
		System.out.printf("linux command: curl -v -X POST -d username=%s -d password=%s -d site=%s -d role=%s http://localhost:8080/password/rest/password", username, password, site, role);

		String output= "{status:failed}";
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(MongoClientGet.PASS_REST_URL+ "/password")
					.queryParam("username", username)
					.queryParam("password", password)
					.queryParam("site", site)
					.queryParam("role", role);

			ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
					.post(ClientResponse.class);

			if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
				throw new RuntimeException("Failed(PasswordPostForm): HTTP error code : "
						+ response.getStatus());
			}

			System.out.println("Output from Server(PasswordPostForm).... \n");
			output = response.getEntity(String.class);
			System.out.println(output);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}
}

package com.fmt.rest.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fmt.rest.service.MongoResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/*
	uuu= 'https://api.mongolab.com/api/1/databases/dailybalance/collections/dailybalances/'+document.userAccounts._id.$oid+'?apiKey=4faf2a26e4b020a46b49e5ed';
	
	//alert(uuu);
$.ajax( { url: uuu,
          data: JSON.stringify( { "$set" : { "paycheck" : document.userAccounts.paycheck } } ),
          type: "PUT",
          contentType: "application/json",
		  success: function(data, txtstatus, xbr){
			//alert("success:"+ document.userAccounts.account.balance);
			//alert("success:"+ document.userAccounts.userName);
			//generateSummary(document.userAccounts.userName);
			generateTable(document.userAccounts.userName);
		  },
	});
 */
public class MongoClientPut {
 
	public static void main(String[] args) {
	}

	public static String MongoPut(String user, String pass, String site, String userAccountId, String json) {
		String GETResponse= "no response";
		System.out.println("URL: "+ MongoClientGet.MONGO_URL+ "/"+ userAccountId);

		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(MongoClientGet.MONGO_URL+ "/"+ userAccountId)
					.queryParam("apiKey", MongoResource.APIKEY);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, json);

			if (!(response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.ACCEPTED.getStatusCode())) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server:\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}
	
	public static String MongoPutCounter(String user, String pass, String site, String userAccountId, String json) {
		String GETResponse= "no response";
		System.out.println("URL: "+ MongoClientGet.COUNTER_URL+ "/"+ userAccountId);

		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(MongoClientGet.COUNTER_URL+ "/"+ userAccountId)
					.queryParam("apiKey", MongoResource.APIKEY);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, json);

			if (!(response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.ACCEPTED.getStatusCode())) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server:\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}
}
package com.fmt.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.fmt.rest.client.MongoClientGet;
import com.fmt.rest.client.MongoClientPost;
import com.fmt.rest.client.MongoClientPut;
import com.fmt.rest.intl.Strings;

@Path("/mongolab")
public class MongoResource {
	public static final String APIKEY= "4faf2a26e4b020a46b49e5ed";
	public static final String APIREPLACE= "FMTAPIKEY";

	@GET
	@Produces({ MediaType.APPLICATION_JSON })	//MediaType.APPLICATION_JSON
	public Response getMongoJson(@QueryParam("action") String action,
			@QueryParam("username") String user,
			@QueryParam("password") String pass,
			@QueryParam("jsonData") String json,
			@QueryParam("accountId") String accountId) {
		final String site= "dailybalance-js"; //"com.fmt.dailybalance";
		System.out.printf("GET(getMongoJson): params %s %s %s %s %s\n", user, pass, action, accountId, json);
		if(null != json)	json= json.replaceAll(APIREPLACE, APIKEY);
		Status stat= Response.Status.OK;
		
		CacheControl cc = new CacheControl();
		cc.setMaxAge(60);
		cc.setNoCache(true);
		String database= "{\"status\":\"not authorized user\"}";
		
			if(null == action || action.isEmpty()) {
				database= "{\"status\":\""+Strings.GETneed+"\"}";
			} else {
				if(action.equalsIgnoreCase("GET")) {
					if(MongoClientGet.doesUserExist(user, pass, site)) {
						database= MongoClientGet.GETMongo();
						stat= Response.Status.OK;
					} else {
						stat= Response.Status.FORBIDDEN;
					}
				} else if(action.equalsIgnoreCase("POST")) {
					if(null == json || json.isEmpty()) {
						database= "{\"status\":\""+Strings.POSTneed+"\"}";
					} else {
						final String addResponse= MongoClientPost.PasswordPostForm(user, pass, site, "user");
						System.out.println("addResponse: "+ addResponse);
						if(addResponse.contains("\"status\":\"success\"")) {
							database= MongoClientPost.MongoPost(user, pass, site, json);
							stat= Response.Status.CREATED;
						} else {
							database= addResponse;
							stat= Response.Status.CONFLICT;
						}
					}
				} else if(action.equalsIgnoreCase("PUT")) {
					if(null == json || json.isEmpty() || null == accountId || accountId.isEmpty()) {
						database= "{\"status\":\""+Strings.PUTneed+"\"}";
					} else {
						database= MongoClientPut.MongoPut(user, pass, site, accountId, json);
						stat= Response.Status.ACCEPTED;
					}
				} /*else if(action.equalsIgnoreCase("COUNT")) {
					if(null == accountId || accountId.isEmpty()) {
						database= "{\"status\":\"COUNT need accountId parameter populated\"}";
						stat= Response.Status.BAD_REQUEST;
					} else {
						database= MongoClientGet.GETCounter(accountId);
						stat= Response.Status.OK;
					}
				}*/
			}
			
		//}
		
		database= database.replaceAll(APIKEY, APIREPLACE);
		System.out.println("\ngetMongoJson Response: "+ database);
		ResponseBuilder rb = Response.status(stat).entity(database);
		return rb.cacheControl(cc).header("Access-Control-Allow-Origin", "*").build();
	}
}   

package com.fmt.rest.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fmt.rest.service.MongoResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MongoClientGet {
	//final public static String BASE_URL= "http://localhost:8080/mongo-wrapper";
	final public static String BASE_URL= "http://mongo-wrapper.fmtmac.cloudbees.net";
	final public static String REST_URL= BASE_URL+"/rest";

	final public static String PASS_BASE_URL= "http://password.fmtmac.cloudbees.net";
	final public static String PASS_REST_URL= PASS_BASE_URL+"/rest";
	final public static String MONGO_URL= "https://api.mongolab.com/api/1/databases/dailybalance/collections/dailybalances";
	final public static String COUNTER_URL= "https://api.mongolab.com/api/1/databases/dailybalance/collections/counter";

	public static void main(String[] args) {
		//GETMongo();
		GETMongoDBTest("fXXX2", "fXXX2", "dailybalance-js", "GET", "");
		GETMongoDBTest("fXXX2", "fXXX2", "dailybalance-js", "PUT", oldDBData);
	}
	public static String GETMongo() {
		String GETResponse= "no response";
		System.out.println("URL: "+ MONGO_URL);

		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(MONGO_URL)
					.queryParam("apiKey", MongoResource.APIKEY);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).
					get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server:\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}
	
	public static String GETCounter(String siteName) {
		String GETResponse= "no response";
		System.out.println("URL: "+ COUNTER_URL);

		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(COUNTER_URL)
					.queryParam("apiKey", MongoResource.APIKEY);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).
					get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server:\n%s\n", GETResponse);

			/*Increment Counter
			//[ { "_id" : { "$oid" : "517e847ce4b09ee355bee8b2"} , "site" : { "count" : 2 , "name" : "test"}} ]
			//Matcher matcher= new Matcher("");
			MongoURI mongoURI = new MongoURI(COUNTER_URL);
			DB connectedDB = mongoURI.connectDB();
			if (mongoURI.getUsername() != null) {
				connectedDB.authenticate(mongoURI.getUsername(), mongoURI.getPassword());
			}*/
			


		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}

	public static String GETMongoDBTest(String username, String password, String site, String action, String json) {
		String GETResponse= "no response";
		final String url= REST_URL+ "/mongolab";
		System.out.println("URL(GETMongoDBTest): "+ url);
		System.out.println("linux command: curl "+url+"?username="+username+"&password="+password+"&site="+site+"&action="+ action+"&jsonData="+ json);
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(url)
					.queryParam("username", username)
					.queryParam("action", action)
					.queryParam("password", password)
					.queryParam("site", site)
					.queryParam("jsonData", json);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).
					get(ClientResponse.class);

			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new RuntimeException("Failed(GETMongoDBTest): HTTP error code : "
						+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server(GETMongoDBTest):\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}

//-------FROM Password project---------	
	public static boolean doesUserExist(String username, String password, String site) {
		String getRole= GETRole(username, password, site, "GET");
		return getRole.contains("\"status\":\"success\"");
	}

	public static String GETRole(String username, String password, String site, String action) {
		String GETResponse= "no response";
		final String url= PASS_REST_URL+ "/password";
		System.out.println("URL(GETRole): "+ url);
		System.out.println("linux command: curl "+url+"?username="+username+"&password="+password+"&site="+site+"&action="+ action);
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(url)
					.queryParam("username", username)
					.queryParam("action", action)
					.queryParam("password", password)
					.queryParam("site", site);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).
					get(ClientResponse.class);

			if (!(response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.ACCEPTED.getStatusCode())) {
				throw new RuntimeException("Failed(GETRole): HTTP error code : "
						+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server(GETRole):\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}

		return GETResponse;
	}

	public final static String oldDBData= "[ { \"_id\" : { \"$oid\" : \"4fb72b33e4b0d10d5eee2038\"} , \"hitCount\" : 490} , { \"_id\" : { \"$oid\" : \"4fd15037e4b06cfe854612eb\"} , \"budget\" : { \"bills\" : [ { \"date\" : \"2012-02-11T14:22:53.753Z\" , \"description\" : \"IRA\" , \"amount\" : 105.76 , \"evtType\" : \"bill\" , \"recurType\" : \"weekly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-04-07T13:22:53.753Z\" , \"description\" : \"CellPhone\" , \"amount\" : 73.45 , \"evtType\" : \"bill\" , \"recurType\" : \"monthly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-04-07T13:22:53.753Z\" , \"description\" : \"nstarElectric\" , \"amount\" : 30 , \"evtType\" : \"bill\" , \"recurType\" : \"monthly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-12-27T14:22:53.753Z\" , \"description\" : \"XMas\" , \"amount\" : 200 , \"evtType\" : \"bill\" , \"recurType\" : \"yearly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-04-07T13:22:53.753Z\" , \"description\" : \"cellPhoneGoal\" , \"amount\" : 73.45 , \"evtType\" : \"goal\" , \"recurType\" : \"monthly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-02-05T14:22:53.753Z\" , \"description\" : \"Haircut\" , \"amount\" : 20 , \"evtType\" : \"bill\" , \"recurType\" : \"monthly\" , \"recurPeriodicity\" : 3} , { \"date\" : \"2012-01-02T14:22:53.753Z\" , \"description\" : \"AAAGoal\" , \"amount\" : 53 , \"evtType\" : \"goal\" , \"recurType\" : \"yearly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-04-02T13:22:53.753Z\" , \"description\" : \"Rent\" , \"amount\" : 1650 , \"evtType\" : \"bill\" , \"recurType\" : \"monthly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2013-01-02T14:22:53.753Z\" , \"description\" : \"newCar\" , \"amount\" : 2000 , \"evtType\" : \"accumulator\" , \"recurType\" : \"yearly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2011-12-26T14:22:53.753Z\" , \"description\" : \"XMasGoal\" , \"amount\" : 200 , \"evtType\" : \"goal\" , \"recurType\" : \"yearly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2012-01-02T14:22:53.753Z\" , \"description\" : \"AAA\" , \"amount\" : 56 , \"evtType\" : \"bill\" , \"recurType\" : \"yearly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2012-12-31T14:22:53.753Z\" , \"description\" : \"invest-vanguard\" , \"amount\" : 400 , \"evtType\" : \"bill\" , \"recurType\" : \"monthly\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2013-09-30T13:22:53.753Z\" , \"description\" : \"LA-vacation\" , \"amount\" : 2600 , \"evtType\" : \"bill\" , \"recurType\" : \"once\" , \"recurPeriodicity\" : 1} , { \"date\" : \"2013-12-30T14:22:53.753Z\" , \"description\" : \"LA-Vaction-goal\" , \"amount\" : 2600 , \"evtType\" : \"goal\" , \"recurType\" : \"once\" , \"recurPeriodicity\" : 1}] , \"events\" : [ ]} , \"current\" : { \"accounts\" : [ { \"bankName\" : \"Fidelity\" , \"balance\" : 13357}] , \"creditCards\" : [ { \"due\" : \"22\" , \"cardName\" : \"AmEx\" , \"lastUpdate\" : \"2013-03-28T11:08:42.667Z\" , \"balance\" : 2327 , \"bill\" : 859.3} , { \"due\" : \"9\" , \"cardName\" : \"Chase\" , \"lastUpdate\" : \"2012-06-25T11:08:17.693Z\" , \"balance\" : 0 , \"bill\" : 0}]} , \"misc\" : { \"futureDays\" : 92 , \"skipAhead\" : 0 , \"outstanding\" : 0 , \"presavings\" : 0 , \"monthlyBudget\" : 757 , \"weeklyExpenses\" : 455.73 , \"weeklyExpensesGas\" : 25 , \"bankInterest\" : 0.16 , \"creditCardInterest\" : 1} , \"password\" : \"undef\" , \"paycheck\" : { \"end401k\" : \"2013-11-01T16:21:52.000Z\" , \"endSsn\" : \"2013-12-25T17:21:52.000Z\" , \"skipPaycheck\" : false , \"nextPaycheck\" : 0 , \"contribution401k\" : 0 , \"ssnTaxes\" : 0 , \"date\" : \"2013-03-22T16:21:52.000Z\" , \"amount\" : 2139.41 , \"recurType\" : \"biweekly\" , \"description\" : \"Paycheck\" , \"evtType\" : \"income\" , \"recurPeriodicity\" : 1} , \"username\" : \"ftaylor92\"} ]";
}

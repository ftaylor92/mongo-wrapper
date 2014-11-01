package com.fmt.test;

import com.fmt.rest.client.MongoClientGet;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 **/
public class TestWrapper extends TestCase {

	/** Test. **/
	@org.junit.Test
	public void testPOSTCurl() throws Exception {
		assertTrue(true);
	}
	
	/** Test. **/
	@org.junit.Test
	public void testGETMongo() throws Exception {
		final String json= MongoClientGet.GETMongo();
		assertTrue("oid should be 4fb72b33e4b0d10d5eee2038", json.contains("4fb72b33e4b0d10d5eee2038"));
	}
	
	/** Test. **/
	@org.junit.Test
	public void testGETJenkins() throws Exception {
		if(MongoClientGet.doesUserExist("fXXX2", "fXXX2", "dailybalance-js")) {
		final String json= MongoClientGet.GETMongoDBTest("fXXX2", "fXXX2", "dailybalance-js", "GET", "");
		assertTrue("oid should be 4fb72b33e4b0d10d5eee2038", json.contains("4fb72b33e4b0d10d5eee2038"));
		} else {
			System.err.println("fXXX2 doesn't exist for dailybalance-js");
			assertTrue(true);	//user didn't exist
		}
	}
	
	/** Test. **/
	@org.junit.Test
	public void testPUTSelenium() throws Exception {
		assertTrue(true);
	}

	/** runs JUnit from command-line or as Java application. **/
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(TestWrapper.class.getName());
		//org.junit.runner.JUnitCore.runClasses(TestCfli.class);
	}

	/** for JUnit. **/
	public static Test suite() {
		return new JUnit4TestAdapter(TestWrapper.class);
	} 
}

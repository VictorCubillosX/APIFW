package Methods;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import excelManager.GetTCData;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testCase.StepAPI;

import java.util.ArrayList;
import java.util.List;



/*
* This is a class that contains the methods of the request types used to run 
* the API tests of the Runner.java class
* 
* Input: NA
* Output:NA 
* 
* By: Team SDET Batch 1 
*/

public class APIMethods {

	String body = "";

	public void action(StepAPI step) throws Exception {
		int code = 0;
		String jsR = "";
		
		switch (step.getKeyword()) {
		case "POST": {
			
			Response resp = (Response) RestAssured.given().body(step.getValueAPI().toString())
					.header("content-type", "application/json").post(step.getParameters());
			jsR = resp.getBody().jsonPath().get("message").toString();
			code = resp.getStatusCode();
			body = resp.body().prettyPrint();
			assertEquals(code, (int) (Double.parseDouble(step.getStatusCode())));
			
			System.out.println("Status Code : " + code);
			System.out.println("Message : " + jsR);

			
			break;
		}

		case "GET": {
			
			Response resp = RestAssured.given().header("content-type", "application/json").when()
					.get(step.getParameters());
			jsR = resp.getBody().jsonPath().get("message").toString();
			code = resp.getStatusCode();
			body = resp.body().prettyPrint();
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			
			System.out.println("Status Code : " + code);
			
			break;
		}
		case "PUT": {
			
			Response resp = (Response) RestAssured.given().body(step.getValueAPI().toString())
					.header("content-type", "application/json").put(step.getParameters());

			jsR = resp.getBody().jsonPath().get("message").toString();
			code = resp.getStatusCode();
			body = resp.body().prettyPrint();
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			resp.body().prettyPrint();
			System.out.println("Status Code : " + code);
			
			break;
		}

		}

	}

	public String getBody() {
		return body;
	}

}

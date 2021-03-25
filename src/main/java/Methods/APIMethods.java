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

public class APIMethods {

	public String action(StepAPI step) throws ParseException {
		int code = 0;
		String jsR = "";
		String body ="";
		switch (step.getKeyword()) {
		case "POST": {
			try {
				Response resp = (Response) RestAssured.given().body(step.getValueAPI().toString())
						.header("content-type", "application/json").post(step.getParameters());
				jsR = resp.getBody().jsonPath().get("message").toString();
				code = resp.getStatusCode();
				body = resp.body().prettyPrint();
				assertEquals(code, (int) (Double.parseDouble(step.getStatusCode())));
				resp.body().prettyPrint();	
				System.out.println("Status Code : " + code);
				System.out.println("Message : " + jsR);
					
			} catch (AssertionError e) {
				System.out.println("Status Code : " + code);
				System.out.println("Message : " + jsR);

			}
			return body;
		}

		case "GET": {
			try {
				Response resp = RestAssured.given().header("content-type", "application/json").when()
						.get(step.getParameters());
				jsR = resp.getBody().jsonPath().get("message").toString();
				code = resp.getStatusCode();
				body = resp.body().prettyPrint();
				assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
				//resp.body().prettyPrint();
				System.out.println("Status Code : " + code);
			} catch (AssertionError e) {
				System.out.println("Status Code : " + code);
				System.out.println("Message : " + jsR);

			}
			return body;
		}
		case "PUT": {
			try {
				Response resp = (Response) RestAssured.given().body(step.getValueAPI().toString())
						.header("content-type", "application/json").put(step.getParameters());

				jsR = resp.getBody().jsonPath().get("message").toString();
				code = resp.getStatusCode();
				body = resp.body().prettyPrint();
				assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
				resp.body().prettyPrint();
				System.out.println("Status Code : " + code);
			} catch (AssertionError e) {
				System.out.println("Status Code : " + code);
				System.out.println("Message : " + jsR);

			}
			return body;
		}
		case "DELETE": {
			Response resp = (Response) RestAssured.given().delete(step.getParameters());
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			System.out.println("Usuario Eliminado");
			return body;

		}

		}
		return body;

	}

}

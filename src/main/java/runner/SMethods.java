package runner;

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

import excelManager.GetTCData;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testCase.Step;
import testCase.StepAPI;

import java.util.ArrayList;
import java.util.List;

public class SMethods {

	public void action(StepAPI step) {
		switch (step.getKeyword()) {
		case "POST": {
			Response resp = (Response) RestAssured.given().body(step.getValueAPI().toString())
					.header("content-type", "application/json").post(step.getParameters());
			resp.body().prettyPrint();
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			break;
		}
			
		case "GET": {
			String string = step.getValidationValue();
			String[] parts = string.split(":");
			String part2 = parts[1]; // 2
			
			int id =  Integer.parseInt(part2);
			
			System.out.println("Id a buscar: "+ id);
			
			Response resp = RestAssured.given()
					.header("content-type", "application/json")
					.when()
					.get(step.getParameters());
			resp.body().prettyPrint();	
			
			JsonPath json = resp.body().jsonPath();
		
			String r = json.getString("$.data.avatar");
						
			System.out.println(r);
			System.out.println("1");
			//assertEquals(resp.getBody().jsonPath().get("id").toString(), part2);
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			break;
		}

		}

	}

}

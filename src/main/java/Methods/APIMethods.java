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

	public void action(StepAPI step) throws ParseException {
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

			int id = Integer.parseInt(part2);

			System.out.println("Id a buscar: " + id);

			Response resp = RestAssured.given()
					.header("content-type", "application/json")
					.when()
					.get(step.getParameters());
			resp.body().prettyPrint();

			JsonPath json = resp.body().jsonPath();

			String response = resp.body().toString();

			JSONParser parser = new JSONParser();

			// JSONObject j = new JSONObject(resp.body().toString());

			// j.get("id").toString();

			// String r = json.getString(".data.id");

			// System.out.println(r);
			// System.out.println("1");
			// assertEquals(resp.getBody().jsonPath().get("id").toString(), part2);
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			break;
		}
		case "PUT": {
			Response resp = (Response) RestAssured.given().body(step.getValueAPI().toString())
					.header("content-type", "application/json").put(step.getParameters());
			resp.body().prettyPrint();
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			break;
		}
		case "DELETE":{
			Response resp = (Response) RestAssured.given()
					.delete(step.getParameters());
			assertEquals(resp.getStatusCode(), (int) (Double.parseDouble(step.getStatusCode())));
			System.out.println("Usuario Eliminado");
		
		}

		}

	}

}

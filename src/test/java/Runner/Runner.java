package Runner;


import org.testng.annotations.Test;

import Methods.APIMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;

import excelManager.GetTCData;
import io.restassured.RestAssured;
import readObject.ReadObject;
import testCase.StepAPI;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	List<StepAPI> steps;
	APIMethods fw = new APIMethods();
	GetTCData tcSteps = new GetTCData();

	@BeforeClass
	public void testSetup() {

		try {
			steps = tcSteps.getStepAPI();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	@Test
	public void test() throws ParseException {
		APIMethods fw = new APIMethods();

		for (int i = 0; i < steps.size(); i++) {
			steps.get(i);
			RestAssured.baseURI = steps.get(i).getUrl();
			RestAssured.basePath = steps.get(i).getUri();
			fw.action(steps.get(i));
		}

	}
}

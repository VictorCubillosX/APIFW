package runner;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterClass;

import excelManager.GetTCData;
import io.restassured.RestAssured;
import readObject.ReadObject;
import testCase.Step;
import testCase.StepAPI;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	List<StepAPI> steps;
	SMethods fw = new SMethods();
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
	public void test() {
		SMethods fw = new SMethods();

		for (int i = 0; i < steps.size(); i++) {
			steps.get(i);
			RestAssured.baseURI = steps.get(i).getUrl();
			RestAssured.basePath = steps.get(i).getUri();
			fw.action(steps.get(i));
		}

	}
}

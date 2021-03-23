package Runner;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Methods.APIMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
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
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;

	@BeforeClass
	public void testSetup() {
		htmlReporter = new ExtentHtmlReporter("extent.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		try {
			steps = tcSteps.getStepAPI();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		// you can customize the report name if omitted default will be used
		test = extent.createTest("MyFirstTest", "Sample description");

	}

	@Test
	public void test() throws ParseException {
		APIMethods fw = new APIMethods();

		for (int i = 0; i < steps.size(); i++) {
			try {
				steps.get(i);
				RestAssured.baseURI = steps.get(i).getUrl();
				RestAssured.basePath = steps.get(i).getUri();
				fw.action(steps.get(i));
				test.pass(steps.get(i).getValueAPI().toString());
			} catch (Exception e) {
				test.fail(steps.get(i).getValueAPI().toString());
				test.error(e);
			}
		}

	}
	
    @AfterSuite
    public void tearDown() {
        extent.flush();
    }
}

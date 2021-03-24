package Runner;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import excelManager.ReadExcelFile;
import Runner.GUI;
import Methods.APIMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

	APIMethods fw = new APIMethods();
	GetTCData tcSteps = new GetTCData();
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	String path;
	String fileName;
	String tcSelected;

	@BeforeClass
	public void testSetup() throws IOException {
		htmlReporter = new ExtentHtmlReporter("extent.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);


	}

	@DataProvider(name = "pasos")
	Object[][] getData() throws IOException {
		GUI gui = new GUI();
		List<List<StepAPI>> testCases = new ArrayList<>();

		String[] info = gui.showGui();
		path = info[0];
		fileName = info[1];
		tcSelected = info[2];
		String[] cases = tcSelected.split("-");
		for (int i = 0; i < cases.length; i++) {
			List<StepAPI> steps = GetTCData.getStepAPI(path, fileName, cases[i]);
			testCases.add(steps);
		}

		Object[][] datos = new Object[testCases.size()][1];
		for (int row = 0; row < testCases.size(); row++) {
			datos[row][0] = testCases.get(row);
		}
		return datos;
	}

	@Test(dataProvider = "pasos")
	public void test(List<StepAPI> pasos) throws IOException {
		APIMethods fw = new APIMethods();
		StepAPI paso1 = pasos.get(0);
		test = extent.createTest(paso1.getDescription());
		for (StepAPI step : pasos) {
			try {
				RestAssured.baseURI = step.getUrl();
				RestAssured.basePath = step.getUri();
				fw.action(step);
				test.pass(step.getValueAPI().toString());
			} catch (Exception e) {
				test.fail(step.getValueAPI().toString());
				test.error(e);
			}
		}

	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
}

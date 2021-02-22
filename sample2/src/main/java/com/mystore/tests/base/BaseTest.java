package com.mystore.tests.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.mystore.driver.WebDriverFactory;
import com.mystore.util.ExtentManager;
import com.mystore.util.GMailUtil;

public class BaseTest {

	public Properties prop;

	public BaseTest() {
		prop = WebDriverFactory.getInstance().prop;
	}

	// loadConfig method is to load the configuration
	@BeforeSuite(alwaysRun = true)
	public void init() {
		ExtentManager.setExtent();
		DOMConfigurator.configure("log4j.xml");
	}

	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public void setup(@Optional("Chrome") String browser) throws Exception {
		System.setProperty("browser", browser);
		// Launching the URL
		getDriver().get(prop.getProperty("url"));
	}

	public WebDriver getDriver() {
		return WebDriverFactory.getInstance().getDriver();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		getDriver().quit();
	}

	@Parameters("sendEmail")
	@AfterSuite(alwaysRun = true)
	public void afterSuite(Boolean sendEmail, ITestResult result) {
		ExtentManager.endReport();

		if (sendEmail) {
			GMailUtil.sendEmail(prop.getProperty("email.report.from"), prop.getProperty("email.report.to"),
					prop.getProperty("email.report.password"), "CAT Automation Report", constructEmailBody(result),
					"test-output/emailable-report.html");
		}
	}

	private String constructEmailBody(ITestResult result) {
		StringBuilder sb = new StringBuilder();
		sb.append("Hi, Team");
		sb.append("\n");
		sb.append("Automation scripts has been run on ...");

		try {
			sb.append(FileUtils.readFileToString(new File("test-output/emailable-report.html"),
					StandardCharsets.UTF_8.name()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sb.append("Application URL: " + prop.getProperty("url"));

		return sb.toString();
	}
}

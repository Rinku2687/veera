package com.mystore.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	public Properties prop;

	public WebDriverFactory() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Configuration\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Declare ThreadLocal Driver
	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private String browserName;

	private static WebDriverFactory instance;

	public static WebDriverFactory getInstance() {
		if (instance == null) {
			instance = new WebDriverFactory();
		}
		return instance;
	}

	public WebDriver getDriver() {

		browserName = System.getProperty("browser");
		if (driver.get() == null) {
			this.createDriver();
		}
		return driver.get();
	}

	private void createDriver() {
		WebDriver d = null;
		try {

			Boolean isTestsRunInGrid = Boolean.parseBoolean(System.getProperty("isTestsRunInGrid", "false"));
			if (isTestsRunInGrid) {

				String gridUrl = prop.getProperty("grid.url");

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				d = new RemoteWebDriver(new URL(gridUrl), capabilities);

			} else {

				if (browserName.equalsIgnoreCase("Chrome")) {
					WebDriverManager.chromedriver().setup();
					// Set Browser to ThreadLocalMap
					d = new ChromeDriver();
				} else if (browserName.equalsIgnoreCase("FireFox")) {
					WebDriverManager.firefoxdriver().setup();
					d = new FirefoxDriver();
				} else if (browserName.equalsIgnoreCase("IE")) {
					WebDriverManager.iedriver().setup();
					d = new InternetExplorerDriver();
				} else {
					throw new Exception("Please provide valid browser option");
				}
			}

			d.manage().window().maximize();
			// Delete all the cookies
			d.manage().deleteAllCookies();
			// Implicit TimeOuts
			d.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicitWait")), TimeUnit.SECONDS);
			// PageLoad TimeOuts
			d.manage().timeouts().pageLoadTimeout(Integer.parseInt(prop.getProperty("pageLoadTimeOut")),
					TimeUnit.SECONDS);
			driver.set(d);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * 
 */
package com.mystore.tests.sample;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mystore.dataprovider.DataProviders;
import com.mystore.pageobjects.HomePage;
import com.mystore.pageobjects.LoginPage;
import com.mystore.tests.base.BaseTest;
import com.mystore.util.Log;

public class LoginTest extends BaseTest {

	private LoginPage loginPage;
	private HomePage homePage;

	@Test(groups = { "Smoke", "Sanity" }, dataProvider = "credentials", dataProviderClass = DataProviders.class)
	public void verifyLogin(String uname, String pswd) throws Throwable {
		loginPage = new LoginPage();

		Log.startTestCase("loginTest");

		Log.info("Enter Username and Password & then user is going to click on SignIn");

		homePage = loginPage.login(uname, pswd);

		Log.info("Verifying if user is able to login");

		Log.info("getting Page Title");

		String actTitle = homePage.getHomePagetitle();

		String ExpTitle = "Official Mopar Site | Owner Vehicle Dashboard";
		Assert.assertEquals(actTitle, ExpTitle);

		Log.info("Verified pageTitle");

		boolean Validatehello=( homePage).validateHello();
		
		Assert.assertTrue(Validatehello, "Hello");
		Log.info("Verified hello on homePage");
		
		Log.info("Login is Sucess");
		Log.endTestCase("loginTest");
	}

}

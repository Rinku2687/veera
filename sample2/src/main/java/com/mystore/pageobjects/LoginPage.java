/**
 * 
 */
package com.mystore.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mystore.action.Action;

public class LoginPage extends Action {

	@FindBy(xpath = "//li[@class='navbar-signin']//span[contains(text(),'Sign In / Register')]")
	WebElement signin;

	@FindBy(xpath = "//input[@id='emailAddress']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='userPassword']")
	WebElement password;

	@FindBy(xpath = "//button[normalize-space()='Sign In']")
	WebElement submitBtn;

	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	public HomePage login(String uname, String pswd) throws Throwable {
		click(signin);
		implicitWait(5);
		type(userName, uname);
		type(password, pswd);
		JSClick(submitBtn);
		
		Thread.sleep(10000);
		
		return new HomePage();
	}

}

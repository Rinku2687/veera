
package com.mystore.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mystore.action.Action;

public class HomePage extends Action {

	@FindBy(xpath = "//li[@class='navbar-signin']//span[contains(text(),'Hello')]")
	private WebElement Hello;

	@FindBy(xpath = "")
	private WebElement b;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	
    public String getHomePagetitle() {
	String HPT=getTitle();
	return HPT;
}
    public boolean validateHello() {
    	switchToNewWindow();
    	return isDisplayed(Hello);
    	
    }


	
	}


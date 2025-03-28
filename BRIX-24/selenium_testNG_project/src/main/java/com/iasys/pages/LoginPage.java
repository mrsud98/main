package com.iasys.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iasys.base.TestBase;

public class LoginPage extends TestBase{

	public LoginPage() {
		PageFactory.initElements(driver, this);
}

	@FindBy(xpath = "//input[@placeholder='Username']")
	WebElement userName;
	
	@FindBy(xpath="//input[@placeholder='Password']")
	WebElement password;
	
	@FindBy(xpath="//button[@class='btn btn-warning w-100']")
	WebElement LoginButton;
	
	@FindBy(xpath="//img[@alt='Brix']")
	WebElement BrixLogo;
	
	@FindBy(xpath="//button[@class='secondary-button small-link']")
	WebElement proceed;
	
	@FindBy(xpath="//*[@id=\"proceed-link\"]")
	WebElement link;
	
	@FindBy(xpath="//button[normalize-space()='Yes']")
	WebElement Yes_button;
	
	@FindBy(xpath="//div[contains(text(),'User not found in the system. Please verify the pr')]")
	WebElement WarningPopupforInvalidUsername;
	
	
	@FindBy(xpath="//img[@alt='bell']")
    WebElement notification1;
	
   @FindBy(xpath="//div[@id='user_notification_win']//div[2]//div[2]//div[1]//a[1]")
	WebElement click_on_notification;
	
	public void verify_login_with_valid_credentrials()
	{
		userName.sendKeys("DG");
		password.sendKeys("123");
	    LoginButton.click();
	    if(Yes_button.isDisplayed()) {
			Yes_button.click();
       }else
       {
    	   System.out.println("User is not login in any other session");
    	   Yes_button.click();
       }
	    
	}
	
	public void verify_login_with_Invalid_credentrials()
	{
		userName.sendKeys("Inavalid");
		password.sendKeys("123");
		LoginButton.click();
		
	}
	
		public boolean isLogoDisplay() {
		return BrixLogo.isDisplayed();
	}
	
	public boolean ispopupDisplay() {
		return WarningPopupforInvalidUsername.isDisplayed();
	}
	
    public void verify_multiple_page_cases()
    
    {
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='e-spinner-pane e-spin-show']//div[@class='e-spinner-inner']//*[name()='svg']")));
    	    	
    	notification1.click();
    	click_on_notification.click();
    	
    	
}   }

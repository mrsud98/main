package com.iasys.testCases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.iasys.base.TestBase;
//import com.iasys.pages.ConcurrentLoginPopUp;
import com.iasys.pages.LoginPage;
import com.iasys.pages.MultipleWindowHandle;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTestCases extends TestBase {

	LoginPage loginpage;
	MultipleWindowHandle MultipleWindowHandleObj;
	//ConcurrentLoginPopUp concurrentLoginPopUp;
	
	public LoginTestCases() {
	 super();
}
	
	//@BeforeSuite(alwaysRun = true)
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		initialization();
		loginpage = new LoginPage();
		MultipleWindowHandleObj= new MultipleWindowHandle();
	
	}  

	@Test
	public void TC_01_loginWithValidcredentials() throws InterruptedException
	{
		loginpage.verify_login_with_valid_credentrials();
	
		
        // Step 4: Assert successful login or perform further actions
		boolean flag = loginpage.isLogoDisplay();
		Assert.assertTrue(flag,"Logo is not display");
		extentTest.log(LogStatus.INFO, "Logo is displayed correctly.");
		
						}
	
	@Test
	public void TC_02_loginWithInvalidcredentials() {
		
		loginpage.verify_login_with_Invalid_credentrials();
		boolean flag1 = loginpage.ispopupDisplay();
		Assert.assertTrue(flag1,"Login not possible due to invalid cedentails");
		
			}
	
	
	@Test
	public void TC_03_MultipleWindowHandleTest(){
		
		loginpage.verify_login_with_valid_credentrials();
		MultipleWindowHandleObj.verify_multiple_page_cases();
		
      	}
	
	@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Ensures browser is closed after each test
            System.out.println("WebDriver closed.");
        }
    }
	
		   }


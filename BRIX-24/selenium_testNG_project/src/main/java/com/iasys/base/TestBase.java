package com.iasys.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.iasys.utilities.SeleniumActions;
import com.iasys.utilities.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	protected static WebDriver driver;
	protected static Properties properties;
	protected static SeleniumActions sele_Actions;
	protected static ChromeOptions chromeOptions;
	protected static ExtentReports extent;
	protected static ExtentTest extentTest;
	protected static WebDriverWait wait;
	//public WebElement Advance =driver.findElement(By.xpath("//*[@id=\"details-button\"]"));
	
//	WebElement SafeLink = driver.findElement(By.xpath("//*[@id=\"proceed-link\"]"));
	
	//call the constructor
	public TestBase()
	{
		try {
			
			properties= new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\iasys\\config\\config.properties");
			properties.load(ip);
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found");
			
		}catch(IOException e)
		{
			System.out.println("IO Exception");
		
		}
	}
	
	// Setup Extent Report - This gets called once at the start
		@BeforeSuite(alwaysRun = true)
		public void setExtentReport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
		extent.addSystemInfo("Host Name", "Sudarshan windows")
			  .addSystemInfo("User name","Sudarshan")
			  .addSystemInfo("Environment","QA");
	
	}
			
    protected static void initialization() {
		
		String browserName = properties.getProperty("browser");
		driver = getDriver(browserName);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		//driver.manage().timeouts().pageLoadTimeout(5, null);
		driver.get(properties.getProperty("url"));
		
		 if(driver.findElement(By.xpath("//*[@id=\"details-button\"]")).isDisplayed()){
		    	driver.findElement(By.xpath("//*[@id=\"details-button\"]")).click();
		 }else {
			 System.out.println("Code is running");
			 		 }
			 			 			 
		 WebDriverWait MyWait = new WebDriverWait(driver,10);
		 try {
		 WebElement link =MyWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"proceed-link\"]")));
		 link.click();
		 }catch(Exception e)
		 {
			
				
				   e.printStackTrace();
				  driver.findElement(By.xpath("//*[@id=\"proceed-link\"]")).click();
				 		 }
		
			// driver.findElement(By.xpath("//*[@id=\"proceed-link\"]")).click();
											              
		sele_Actions = new SeleniumActions();
		}
    
		private static WebDriver getDriver(String browserName) {
		
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			return new ChromeDriver(chromeOptions);
		} else if (browserName.equalsIgnoreCase("FF")) {
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup();
			return new InternetExplorerDriver();
		}
		return null;
		
	}
	@BeforeMethod
	public void startTest(ITestResult result) {
		extentTest = extent.startTest(result.getMethod().getMethodName());
	}
	
	// Log test result after each test
		@AfterMethod
		public void captureResult(ITestResult result) {
			if (result.getStatus() == ITestResult.FAILURE) {
				extentTest.log(LogStatus.FAIL, "Test Case Failed: " + result.getName());
				extentTest.log(LogStatus.FAIL, "Cause: " + result.getThrowable());
			} else if (result.getStatus() == ITestResult.SKIP) {
				extentTest.log(LogStatus.SKIP, "Test Case Skipped: " + result.getName());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				extentTest.log(LogStatus.PASS, "Test Case Passed: " + result.getName());
			}
			extent.endTest(extentTest); // End the test
		}

		// Flush the report after all tests complete
		@AfterSuite(alwaysRun = true)
		public void endReport() {
			extent.flush();
			//driver.quit();
	
		}
	}


	
	
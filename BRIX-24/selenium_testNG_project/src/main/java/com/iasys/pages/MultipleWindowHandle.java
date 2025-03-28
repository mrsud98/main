package com.iasys.pages;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iasys.base.TestBase;



public class MultipleWindowHandle extends TestBase {

	public MultipleWindowHandle() {
		PageFactory.initElements(driver, this);
	
	}
	
	@FindBy(xpath="//img[@alt='bell']")
    WebElement notification1;
	
   @FindBy(xpath="//div[@id='user_notification_win']//div[2]//div[2]//div[1]//a[1]")
	WebElement click_on_notification;
	
   
   public void verify_multiple_page_cases()
   
   {
   	WebDriverWait wait = new WebDriverWait(driver, 10);
   	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='e-spinner-pane e-spin-show']//div[@class='e-spinner-inner']//*[name()='svg']")));
   	    	
   	notification1.click();
   	click_on_notification.click();
   	Set<String> WindowIds = driver.getWindowHandles();
   
		
		List<String> windowList= new ArrayList(WindowIds); 
		String parentID=windowList.get(0); 
		System.out.println(parentID);
		
		String childID=windowList.get(1); 
		System.out.println(childID);
		//switch to child window 
		
	
		driver.switchTo().window(childID); 
	
		System.out.println(driver.getTitle()); 
		 //switch to parent window 
	
		
		driver.switchTo().window(parentID); 
		System.out.println(driver.getTitle()); 

	}
   	
}   
   
   
   


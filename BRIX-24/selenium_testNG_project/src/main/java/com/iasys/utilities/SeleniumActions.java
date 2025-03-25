package com.iasys.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.iasys.base.TestBase;

public class SeleniumActions extends TestBase {

	public void selectbyText(WebElement element,String text)
	{
		Select select = new Select(element);
		select.selectByVisibleText(text);
		
		
	}
	
	
	public void selectbyValue(WebElement element,String text) {
		Select select = new Select(element);
		select.selectByValue(text);
	}
		
		
		
	}
	
	
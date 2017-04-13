package util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;

public class CommonMethods extends BaseTest 
{
	
	public static void waitForElement(String locator) 
	{
	    WebDriverWait wait = new WebDriverWait(driver,120);
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(locator))));	    
	}
	public static void waitForElementInvisible(String locator) 
	{
	    WebDriverWait wait = new WebDriverWait(driver,120);
	    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(locator))));	    
	}
	public static void waitForElements(List<WebElement> elementLocators, int timeInSec) 
	{
	    WebDriverWait wait = new WebDriverWait(driver, timeInSec);
	    wait.until(ExpectedConditions.visibilityOfAllElements(elementLocators));	    
	}
	public static void hover(WebElement menu)
	{
		Actions act=new Actions(driver);
		act.moveToElement(menu).build().perform();
		//return menu;
	}
	public static void click(String locator)
	{
		driver.findElement(By.cssSelector(locator)).click();
	}
	
	public static void clear(WebElement clearTextBox)
	{
		clearTextBox.clear();
	}
	public static void enter(String locator, String value)
	{
		driver.findElement(By.cssSelector(locator)).sendKeys(value);		
	}
	
	public static void select(WebElement selectElement, int index)
	{
		Select dropdown = new Select(selectElement);
		dropdown.selectByIndex(index);
		//return selectElement;
	}
	public static void enterStartDate(WebElement element, String date)
    {
		element.sendKeys(date);
    }
	
	public static void enterEndtDate(WebElement element, String date)
    {
		element.sendKeys(date);
    }
	
	public  static void switchToWindow()
	{		
		Set<String> handle = driver.getWindowHandles();
		Iterator<String> iter = handle.iterator();
		String mainWindow=iter.next();
		String childWindow=iter.next();
		driver.switchTo().window(childWindow);
	}
	
	public static String getText(String locator)
	{
		return driver.findElement(By.cssSelector(locator)).getText();
	}
}

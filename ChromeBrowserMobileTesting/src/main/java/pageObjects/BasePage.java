package pageObjects;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.james.api.basetest.BaseTest;

public class BasePage {

	protected WebDriver driver;
	private final Logger logger;
	BaseTest baseTest;
	

	public BasePage(WebDriver driver) {
		this.driver = driver;
		logger = Logger.getLogger(BasePage.class.getName());
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public void click(WebElement element) {
		
		if (element.isDisplayed()) {
			element.click();
		}
	}
	
	public void click(By by) {
		WebElement element = waitUntilPresenceOfElementLocated(by,30);
		if (element.isDisplayed()) {
			element.click();
		}
	}
	
	public void enter(By by, String text) {
		WebElement element = waitUntilPresenceOfElementLocated(by,30);
		if (element.isDisplayed()) {
			element.sendKeys(text);;
		}
	}
	
	private WebElement waitUntilPresenceOfElementLocated(By by, long timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return element;
		
	}
	
	public void fluentWaitForElement(WebDriver driver, By by, int timeOutInSeconds, int pollingSec) {
		WebElement element = driver.findElement(by);
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(pollingSec, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, TimeoutException.class)
				.ignoring(StaleElementReferenceException.class);
		
		for (int i = 0; i < 2; i++) {
			try {
				fwait.until(ExpectedConditions.presenceOfElementLocated(by));
				fwait.until(ExpectedConditions.visibilityOf(element));
				fwait.until(ExpectedConditions.elementToBeClickable(element));
			} catch (Exception e) {
				System.out.println("Element not found, trying again - " + element.toString().substring(70));
			}
		}
		
	}
	
}

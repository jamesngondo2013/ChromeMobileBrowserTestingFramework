package com.james.api.basetest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.james.api.context.TestObject;
import com.james.api.util.database.mapper.Customer;

import managers.BrowserFactory;

public class BaseTest {

	private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
	private WebDriver driver;
	public WebDriverWait wait;
	protected Logger logger;
	protected FirefoxProfile profile;

	protected String testSuiteName;
	protected String testName;
	protected String testMethodName;

	BrowserFactory browserFactory;
	protected TestObject testObject;
	protected Customer customer;

	private static final String SQL_BEFORE = "@sqlbefore";
	private static final String SQL_AFTER = "@sqlafter";
	private static final String REPLACE_TAG = "@replace";

	public BaseTest() {
		try {
			testObject = new TestObject();
			browserFactory = new BrowserFactory(testObject);
			customer = new Customer();
			logger = Logger.getLogger(BaseTest.class.getName());
		} catch (Exception e) {

		}
	}

	public WebDriver getDriver() {
		return drivers.get();
	}

	@Parameters({ "deviceName" })
	@BeforeMethod()
	public void setUp(Method method, @Optional String deviceName, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName();
		logger.info("Test Name: " + testName);

		String browser = System.getenv("BROWSER");
		String grid;
		boolean useGrid;

		if (browser == null) {
			browser = testObject.getServiceApi().getProp().getProperty("BROWSER").trim();
			grid = testObject.getServiceApi().getProp().getProperty("GRID").trim();
			useGrid = grid.equalsIgnoreCase("True");

			if (deviceName != null) {
				driver = browserFactory.createChromeWithMobileEmulation(deviceName, browser, useGrid);
				drivers.set(driver);
				driver.get(testObject.getServiceApi().getProp().getProperty("ENDPOINT").trim());
			}
		} else {
			browserFactory.createChromeWithMobileEmulation(deviceName, browser, true);
			drivers.set(driver);
			driver.get(testObject.getServiceApi().getProp().getProperty("ENDPOINT").trim());
		}

		profile = new FirefoxProfile();
		// Set preferences for file type
		profile.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream");

		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		this.testSuiteName = ctx.getSuite().getName();
		this.testName = testName;
		this.testMethodName = method.getName();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		// getDriver().quit();
		// drivers.remove();
	}

	/** Take screenshot */
	protected void takeScreenshot(String fileName) {
		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots"
				+ File.separator + getTodaysDate() + File.separator + testSuiteName + File.separator + testName
				+ File.separator + testMethodName + File.separator + getSystemTime() + " " + fileName + ".png";
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Todays date in yyyyMMdd format */
	private static String getTodaysDate() {
		return (new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}

	/** Current time in HHmmssSSS */
	private String getSystemTime() {
		return (new SimpleDateFormat("HHmmssSSS").format(new Date()));
	}

}
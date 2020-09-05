package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.james.api.context.TestObject;

public class LoginPage extends BasePage{
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
	}
	
	private By usernameTxt = By.id("username");
	private By passwordTxt = By.id("password");
	private By loginBtn = By.xpath("//*[@class='fa fa-2x fa-sign-in']");
	
	public void clickLoginButton() {
		fluentWaitForElement(driver, loginBtn, 30, 2);
		click(loginBtn);
	}
	
	public void enterUsername(String text) {
		fluentWaitForElement(driver, usernameTxt, 30, 2);
		enter(usernameTxt, text);
	}
	
	public void enterPassword(String text) {
		fluentWaitForElement(driver, usernameTxt, 30, 2);
		enter(passwordTxt, text);
	}

}

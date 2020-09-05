package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.james.api.context.TestObject;

public class DashboardPage extends BasePage{

	
	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
}

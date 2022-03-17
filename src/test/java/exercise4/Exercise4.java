package exercise4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Exercise4 {
	WebDriver driver;
	
	@BeforeMethod
	public void startUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	
	
	@AfterMethod
	public void tearDown() {
		System.out.println("EXIT");
		driver.quit();
	}
}

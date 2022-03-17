package exercise4;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;

public class Exercise4 {
	WebDriver driver;
	String user = "joseira";
	String password = "Test_654123";

	@BeforeMethod
	public void startUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void tc_REQ005_001() throws InterruptedException {
		driver.get("https://practice.automationbro.com/my-account/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Actions action = new Actions(driver);
		// login
		WebElement userInput = driver.findElement(By.xpath("//*[@id='username']"));
		WebElement passwordInput = driver.findElement(By.xpath("//*[@id='password']"));
		WebElement loginBtn = driver.findElement(By.xpath("//*[@id='customer_login']/div[1]/form/p[3]/button"));
		userInput.sendKeys(user);
		passwordInput.sendKeys(password);
		loginBtn.click();
		// go to shop
		Thread.sleep(5000);
		WebElement shopMenu = driver.findElement(By.xpath("//*[@id='menu-item-567']/a"));
		shopMenu.click();
		// open a product
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		int getProduct = (int) (Math.random() * products + 1);
		System.out.println(getProduct);
		WebElement product = driver.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]/a/h2"));
		String productSelected = product.getText();
		product.click();
		// return to shop
		Thread.sleep(5000);
		shopMenu = driver.findElement(By.xpath("//*[@id='menu-item-567']/a"));
		shopMenu.click();
		// check if the item is displayed in recent files
		WebElement recentProduct = driver
				.findElement(By.xpath("//*[@id='woocommerce_recently_viewed_products-1']/ul/li[1]/a/span"));
		System.out.println("selected " + productSelected);
		System.out.println("recent " + recentProduct.getText());
		wait.until(ExpectedConditions.visibilityOf(recentProduct));
		Assert.assertEquals(productSelected, recentProduct.getText());
	}

	@Test
	public void tc_REQ007_001() {
		driver.get("https://practice.automationbro.com/contact/");
		//Enter data
		WebElement nameField = driver.findElement(By.xpath("//*[@id='evf-277-field_ys0GeZISRs-1']"));
		WebElement emailField = driver.findElement(By.xpath("//*[@id='evf-277-field_LbH5NxasXM-2']"));
		WebElement phoneField = driver.findElement(By.xpath("//*[@id='evf-277-field_66FR384cge-3']"));
		WebElement messageField = driver.findElement(By.xpath("//*[@id='evf-277-field_yhGx3FOwr2-4']"));
		WebElement submitBtn = driver.findElement(By.xpath("//*[@id='evf-submit-277']"));
		nameField.sendKeys("Jose");
		emailField.sendKeys("jose@gmail.com");
		phoneField.sendKeys("1234567891");
		messageField.sendKeys("Hola mundo");
		//click on submit
		submitBtn.click();
		//verify message
		WebElement submitMessage=driver.findElement(By.xpath("//*[@id='primary']/div/div/div/section[3]/div/div/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div/div/div"));
		System.out.println(submitMessage.getText());
		Assert.assertEquals(submitMessage.getText(), "Thanks for contacting us! We will be in touch with you shortly");
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		System.out.println("EXIT");
		Thread.sleep(5000);
		driver.quit();
	}
	// WebElement
	// addBtn=driver.findElement(By.xpath("//*[@id='primary']/ul//li["+getProduct+"]//a[text()='Add
	// to cart']"));
}

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
		Actions action=new Actions(driver);
		WebElement shopMenu = driver.findElement(By.xpath("//*[@id='menu-item-567']/a"));
		WebElement userInput = driver.findElement(By.xpath("//*[@id='username']"));
		WebElement passwordInput = driver.findElement(By.xpath("//*[@id='password']"));
		WebElement loginBtn = driver.findElement(By.xpath("//*[@id='customer_login']/div[1]/form/p[3]/button"));
		userInput.sendKeys(user);
		passwordInput.sendKeys(password);
		loginBtn.click();
		
		Thread.sleep(5000);
		shopMenu = driver.findElement(By.xpath("//*[@id='menu-item-567']/a"));
		shopMenu.click();
		
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		int getProduct = (int) (Math.random() * products + 1);
		System.out.println(getProduct);
		// WebElement
		// addBtn=driver.findElement(By.xpath("//*[@id='primary']/ul//li["+getProduct+"]//a[text()='Add
		// to cart']"));
		WebElement product = driver.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]/a/h2"));
		String productSelected = product.getText();
		product.click();
		
		Thread.sleep(5000);
		shopMenu = driver.findElement(By.xpath("//*[@id='menu-item-567']/a"));
		shopMenu.click();
		
		WebElement recentProduct = driver
				.findElement(By.xpath("//*[@id='woocommerce_recently_viewed_products-1']/ul/li[1]/a/span"));
		System.out.println("selected " + productSelected);
		System.out.println("recent " + recentProduct.getText());
		wait.until(ExpectedConditions.visibilityOf(recentProduct));
		Assert.assertEquals(productSelected, recentProduct.getText());
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		System.out.println("EXIT");
		Thread.sleep(5000);
		driver.quit();
	}
}

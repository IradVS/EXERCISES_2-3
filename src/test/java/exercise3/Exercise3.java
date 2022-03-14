package exercise3;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.netty.handler.codec.DateFormatter;

public class Exercise3 {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://www.phptravels.net/flights");
		driver.manage().window().maximize();
	}

	@Test
	public void flightTest() {

		// Validate page loads correctly
		// Click on “Flights” icon
		// Select “Round trip” radio button
		WebElement radioBtn = driver.findElement(By.xpath("//div/input[@id='round-trip']"));
		radioBtn.click();
		// Enter “Monterrey” on “Departure city”
		WebElement departureCity = driver.findElement(By.xpath("//div/input[@id='autocomplete']"));
		departureCity.sendKeys("Monterrey");
		departureCity.sendKeys(Keys.ARROW_DOWN);
		departureCity.sendKeys(Keys.ENTER);
		// Enter “Cancun” on “Arrival city”
		WebElement arrivalCity = driver.findElement(By.xpath("//div/input[@id='autocomplete2']"));
		arrivalCity.sendKeys("Cancun");
		arrivalCity.sendKeys(Keys.ARROW_DOWN);
		arrivalCity.sendKeys(Keys.ENTER);
		// Enter tomorrow date on “depart date” field
		WebElement departDate=driver.findElement(By.xpath("//div/input[@id='departure']"));
		departDate.clear();
		departDate.sendKeys(getDate());
		// Enter 10 days after today date on “Return date”
		WebElement returnDate=driver.findElement(By.xpath("//div/input[@id='return']"));
		returnDate.clear();
		returnDate.sendKeys(getDate(10));
		// Select 3 adults and 1 child on “Passengers” field
		WebElement passagersDropdown=driver.findElement(By.xpath("//*[@id='onereturn']/div[3]/div/div/div/a"));
		passagersDropdown.click();
		WebElement adultPlusBtn=driver.findElement(By.xpath("//*[@id='onereturn']/div[3]/div/div/div/div/div[1]/div/div/div[2]/i"));
		adultPlusBtn.click();
		adultPlusBtn.click();
		WebElement childPlusBtn=driver.findElement(By.xpath("//*[@id='onereturn']/div[3]/div/div/div/div/div[2]/div/div/div[2]/i"));
		childPlusBtn.click();
		// . Select “First” on dropdown
		WebElement dropDown=driver.findElement(By.xpath("//*[@id='flight_type']"));
		Select dropdownMulti = new Select(dropDown);
		dropdownMulti.selectByValue("first");
		// Click “Search” button
		WebElement searchBtn=driver.findElement(By.xpath("//*[@id='flights-search']"));
		searchBtn.click();
		// Add an explicit wait to validate information is loaded correctly and add checkpoint needed (i.e. it can be “Modify Search” header)
		WebElement flightHeader=driver.findElement(By.xpath("//*[@id='fadein']/section[1]"));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(flightHeader));
		System.out.println("wait passed");
		WebElement modifyHeader=driver.findElement(By.xpath("//*[@id='fadein']/div[1]"));
		Assert.assertTrue(modifyHeader.isDisplayed());
	}

	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		ZoneId zonedId = ZoneId.of("America/Montreal");
		LocalDate today = LocalDate.now(zonedId);
		return dtf.format(today);
	}

	public String getDate(int days) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		ZoneId zonedId = ZoneId.of("America/Montreal");
		LocalDate today = LocalDate.now(zonedId);
		return dtf.format(today.plusDays(days));
	}

	@AfterMethod
	public void turnOff() throws InterruptedException {
		System.out.println("EXIT");
		Thread.sleep(5000);
		driver.quit();
	}
}

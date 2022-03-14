package exercise2;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Exercise2 {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.manage().window().maximize();
	}

	@Test
	public void radioButtonTest() {
		WebElement radioButton = driver.findElement(By.xpath("//input[@value='radio1']"));
		radioButton.click();
		Assert.assertEquals(radioButton.isSelected(), true);

	}

	@Test
	public void suggestionClassTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		WebElement suggestionInput = driver.findElement(By.xpath("//input[@id='autocomplete']"));
		suggestionInput.sendKeys("Mexi");
		Thread.sleep(1000);
		suggestionInput.sendKeys(Keys.ARROW_DOWN);
		suggestionInput.sendKeys(Keys.ENTER);
	}

	@Test
	public void dropdownTest() {
		WebElement dropdown = driver.findElement(By.xpath("//select[@id='dropdown-class-example']"));
		// Verify Options
		Select dropdownMulti = new Select(dropdown);
		List<WebElement> allElementsMulti = dropdownMulti.getOptions();
		String[] expectedElements = { "Select", "Option1", "Option2", "Option3" };
		System.out.println("Values present in Multi value Dropdown");
		int i = 0;
		for (WebElement elementMulti : allElementsMulti) {
			// Verify the content of the drop down
			Assert.assertEquals(elementMulti.getText(), expectedElements[i]);
			i++;
		}
		// select option1
		dropdownMulti.selectByIndex(1);
		// Assert if option is correctly displayed
		Assert.assertEquals(dropdownMulti.getFirstSelectedOption().getText(), "Option1");
	}

	@Test
	public void checkboxTest() {
		WebElement checkbox1 = driver.findElement(By.xpath("//*[@id='checkBoxOption1']"));
		checkbox1.click();
		Assert.assertTrue(checkbox1.isEnabled());

		WebElement checkbox2 = driver.findElement(By.xpath("//*[@id='checkBoxOption2']"));
		checkbox2.click();
		Assert.assertTrue(checkbox2.isEnabled());

	}

	@Test
	public void changeWindow() throws InterruptedException {
		WebElement buttonWindow = driver.findElement(By.xpath("//*[@id='openwindow']"));
		buttonWindow.click();
		String winHandleBefore = driver.getWindowHandle();
		// Perform the click operation that opens new window
		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		// Perform the actions on new window
		Thread.sleep(3000);
		Assert.assertEquals(driver.getTitle(),
				"QA Click Academy | Selenium,Jmeter,SoapUI,Appium,Database testing,QA Training Academy");
		// Close the new window, if that window no more required
		driver.close();
		// Switch back to original browser (first window)
		driver.switchTo().window(winHandleBefore);
		Assert.assertEquals(driver.getTitle(), "Practice Page");
	}

	@Test
	public void changeTab() throws InterruptedException {
		WebElement buttonTab = driver.findElement(By.xpath("//*[@id='opentab']"));
		buttonTab.click();
		String winHandleBefore = driver.getWindowHandle();
		// Perform the click operation that opens new window
		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		// Perform the actions on new window
		Thread.sleep(3000);
		Assert.assertEquals(driver.getTitle(), "Rahul Shetty Academy");
		// Close the new window, if that window no more required
		driver.close();
		// Switch back to original browser (first window)
		driver.switchTo().window(winHandleBefore);
		Assert.assertEquals(driver.getTitle(), "Practice Page");
	}

	@Test
	public void alertTest() throws InterruptedException {
		String name = "Jose";
		WebElement txtFieldAlert = driver.findElement(By.xpath("//*[@id='name']"));
		WebElement btnAlert = driver.findElement(By.xpath("//*[@id='alertbtn']"));
		WebElement btnConfirm = driver.findElement(By.xpath("//*[@id='confirmbtn']"));
		// On “Switch to Alert Example” enter your name then click on “Alert” button
		txtFieldAlert.sendKeys(name);
		btnAlert.click();
		// Validate pop up message is “Hello , share this practice page and share your
		// knowledge”
		Assert.assertEquals(driver.switchTo().alert().getText(),
				"Hello " + name + ", share this practice page and share your knowledge");
		// Accept Alert and then enter your name again on input field
		driver.switchTo().alert().accept();
		txtFieldAlert.clear();
		txtFieldAlert.sendKeys(name);
		// Click “Confirm” button and validate message displayed
		btnConfirm.click();
		Assert.assertEquals(driver.switchTo().alert().getText(),
				"Hello " + name + ", Are you sure you want to confirm?");
		// Cancel alert
		driver.switchTo().alert().dismiss();
	}

	@Test
	public void tabletest() {
		// On Web Table Example, iterate thru all the table and validate instructor is
		// “Rahul Shetty” in all rows.
		int rows = driver.findElements(By.xpath("//table[@name='courses']/tbody//tr")).size();
		int cols = driver.findElements(By.xpath("//table[@name='courses']/tbody//tr[1]/th")).size();
		for (int i = 1; i < cols + 1; i++) {
			if (driver.findElement(By.xpath("//table[@name='courses']/tbody//tr[1]/th[" + i + "]")).getText()
					.equals("Instructor")) {
				for (int j = 2; j < rows + 1; j++) {
					Assert.assertEquals(
							driver.findElement(By.xpath("//table[@name='courses']/tbody//tr[" + j + "]/td[" + i + "]"))
									.getText(),
							"Rahul Shetty");
				}
			}
		}
	}

	@Test
	public void tableFixed() {
		int rows = driver.findElements(By.xpath("//div[@class='tableFixHead']//table[@id='product']/thead/tr/th"))
				.size();
		int cols = driver.findElements(By.xpath("//div[@class='tableFixHead']//table[@id='product']/tbody/tr")).size();
		WebElement totalAmountMessage = driver.findElement(By.xpath("//div[@class='totalAmount']"));
		int amount = 0;
		int suma = 0;
		for (int i = 1; i < rows + 1; i++) {
			if (driver
					.findElement(By.xpath("//div[@class='tableFixHead']//table[@id='product']/thead/tr/th[" + i + "]"))
					.getText().equals("Amount")) {
				for (int j = 1; j < cols + 1; j++) {
					suma = Integer.parseInt(driver.findElement(By.xpath(
							"//div[@class='tableFixHead']//table[@id='product']/tbody/tr[" + j + "]/td[" + i + "]"))
							.getText());
					amount += suma;
				}
			}
		}
		String message = totalAmountMessage.getText();
		message = message.replaceAll("[^-?0-9]+", "");
		Assert.assertTrue(message.equals("" + amount));
	}

	@Test
	public void displayTest() {
		WebElement input = driver.findElement(By.xpath("//*[@id='displayed-text']"));
		WebElement btnEnable = driver.findElement(By.id("show-textbox"));
		WebElement btnDisable = driver.findElement(By.id("hide-textbox"));
		btnDisable.click();
		Assert.assertFalse(input.isDisplayed());
		btnEnable.click();
		Assert.assertTrue(input.isDisplayed());
	}

	@Test
	public void mouseHoverTest() throws InterruptedException {
		WebElement btnMouse=driver.findElement(By.id("mousehover"));
		Actions actionProvider = new Actions(driver);
	    actionProvider.moveToElement(btnMouse).build().perform();
		WebElement top=driver.findElement(By.xpath("/html/body/div[4]/div/fieldset/div/div/a[1]"));
		WebElement reload=driver.findElement(By.xpath("/html/body/div[4]/div/fieldset/div/div/a[2]"));
		Thread.sleep(3000);
	    Assert.assertTrue(top.isDisplayed());
	    Assert.assertTrue(reload.isDisplayed());
	}
	@Test
	public void iframeTest() {
		WebElement frame=driver.findElement(By.xpath("//iframe[@id='courses-iframe']"));
		driver.switchTo().frame(frame);
		WebElement btnJoin=driver.findElement(By.cssSelector("#carousel-example-generic > div > div > div > div > div > a"));
		Assert.assertTrue(btnJoin.isDisplayed());
	}
	
	@AfterMethod
	public void turnOff() throws InterruptedException {
		System.out.println("EXIT");
		Thread.sleep(5000);
		driver.quit();
	}

}

package exercise4;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	public void tc_REQ001_001() {
		driver.get("https://practice.automationbro.com/shop/");
		String searchTerm="converse";
		WebElement searchInput = driver.findElement(By.xpath("//*[@id='woocommerce-product-search-field-0']"));
		searchInput.sendKeys(searchTerm);
		WebElement searchButton = driver.findElement(By.xpath("//*[@id='woocommerce_product_search-1']/form/button"));
		searchButton.click();
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		String searchResult;
		for (int i = 1; i < products; i++) {
			searchResult = driver
					.findElement(By.xpath("//*[@id='primary']/ul//li[" + i + "]/a/h2")).getText();
			System.out.println("Result "+searchResult);
			Assert.assertTrue(searchResult.toLowerCase().contains(searchTerm.toLowerCase()));
			//Thread.sleep(2000);
		}

	}

	/*
	 * the user navigates in the shop pages looking the products 9 by 9
	 */
	@Test
	public void tc_REQ003_001() {
		// enter to: https://practice.automationbro.com/shop/
		driver.get("https://practice.automationbro.com/shop/");
		// check how many products are displayed
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		Assert.assertTrue(products <= 9);
	}

	/*
	 * check behavior when a registered user views a product and after looks for
	 * recently viewed products
	 */
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

	/* check behavior when all fields are correctly entered */
	@Test
	public void tc_REQ007_001() {
		driver.get("https://practice.automationbro.com/contact/");
		// Enter data
		WebElement nameField = driver.findElement(By.xpath("//*[@id='evf-277-field_ys0GeZISRs-1']"));
		WebElement emailField = driver.findElement(By.xpath("//*[@id='evf-277-field_LbH5NxasXM-2']"));
		WebElement phoneField = driver.findElement(By.xpath("//*[@id='evf-277-field_66FR384cge-3']"));
		WebElement messageField = driver.findElement(By.xpath("//*[@id='evf-277-field_yhGx3FOwr2-4']"));
		WebElement submitBtn = driver.findElement(By.xpath("//*[@id='evf-submit-277']"));
		nameField.sendKeys("Jose");
		emailField.sendKeys("jose@gmail.com");
		phoneField.sendKeys("1234567891");
		messageField.sendKeys("Hola mundo");
		// click on submit
		submitBtn.click();
		// verify message
		WebElement submitMessage = driver.findElement(By.xpath(
				"//*[@id='primary']/div/div/div/section[3]/div/div/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div/div/div"));
		System.out.println(submitMessage.getText());
		Assert.assertEquals(submitMessage.getText(), "Thanks for contacting us! We will be in touch with you shortly");
	}

	/* check behavior when name is empty */
	@Test
	public void tc_REQ007_002() {
		driver.get("https://practice.automationbro.com/contact/");
		// Enter data
		WebElement emailField = driver.findElement(By.xpath("//*[@id='evf-277-field_LbH5NxasXM-2']"));
		WebElement phoneField = driver.findElement(By.xpath("//*[@id='evf-277-field_66FR384cge-3']"));
		WebElement messageField = driver.findElement(By.xpath("//*[@id='evf-277-field_yhGx3FOwr2-4']"));
		WebElement submitBtn = driver.findElement(By.xpath("//*[@id='evf-submit-277']"));

		emailField.sendKeys("jose@gmail.com");
		phoneField.sendKeys("1234567891");
		messageField.sendKeys("Hola mundo");
		submitBtn.click();

		WebElement fieldRequired = driver.findElement(By.xpath(
				"//*[@id='primary']/div/div/div/section[3]/div/div/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div/div/div"));
		Assert.assertTrue(fieldRequired.isDisplayed());
	}

	/* validate when user removes a product from the cart */
	@Test
	public void tc_REQ010_001() throws InterruptedException {
		driver.get("https://practice.automationbro.com/shop/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		// int numberOfProducts = (int) (Math.random() * 5 + 1);
		int getProduct = 1;
		WebElement addBtn;
		// for (int i = 0; i < numberOfProducts; i++) {
		getProduct = (int) (Math.random() * products + 1);
		addBtn = driver.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]//a[text()='Add to cart']"));
		addBtn.click();
		// wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='primary']/ul//li["+getProduct+"]//a[text()='View
		// cart']"))));
		Thread.sleep(2000);
		// }
		WebElement cartBtn = driver.findElement(By.xpath("//*[@id='primary-menu']/li[9]/a/i"));
		wait.until(ExpectedConditions.visibilityOf(cartBtn));
		cartBtn.click();
		int columns = driver.findElements(By.xpath("//*[@id='post-7']/div/div[2]/form/table/tbody//tr")).size();
		System.out.println(columns);
		String nameOfProduct;
		WebElement deletedProduct;
		for (int i = columns - 1; i > 0; i--) {
			nameOfProduct = driver
					.findElement(By.xpath("//*[@id='post-7']/div/div[2]/form/table/tbody/tr[" + i + "]/td[3]/a"))
					.getText();
			driver.findElement(By.xpath("//*[@id='post-7']/div/div[2]/form/table/tbody/tr[" + i + "]/td/a")).click();
			Thread.sleep(4000);
			System.out.println(driver.findElement(By.xpath("//*[@id='post-7']/div/div[2]/div[1]/div")).getText());
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='post-7']/div/div[2]/div[1]/div")).getText()
					.contains(nameOfProduct));
			// Thread.sleep(3000);
		}
	}

	/* check behavior when email is in wrong format */
	@Test
	public void tc_REQ007_003() throws InterruptedException {
		driver.get("https://practice.automationbro.com/contact/");
		Actions action = new Actions(driver);
		// Enter data
		WebElement nameField = driver.findElement(By.xpath("//*[@id='evf-277-field_ys0GeZISRs-1']"));
		WebElement emailField = driver.findElement(By.xpath("//*[@id='evf-277-field_LbH5NxasXM-2']"));
		WebElement phoneField = driver.findElement(By.xpath("//*[@id='evf-277-field_66FR384cge-3']"));
		WebElement messageField = driver.findElement(By.xpath("//*[@id='evf-277-field_yhGx3FOwr2-4']"));
		WebElement submitBtn = driver.findElement(By.xpath("//*[@id='evf-submit-277']"));

		action.moveToElement(nameField).build().perform();
		nameField.sendKeys("Jose");
		emailField.sendKeys("jose@");
		phoneField.sendKeys("1234567891");
		Thread.sleep(5000);
		WebElement errorMessage = driver.findElement(By.xpath("//*[@id='evf-277-field_LbH5NxasXM-2-error']"));
		messageField.sendKeys("Hola mundo");
		Assert.assertEquals(errorMessage.getText(), "Please enter a valid email address.");
		// click on submit
		submitBtn.click();
	}

	/*
	 * Validate a registered user is able to checkout by filling the fields with the
	 * proper values
	 */
	@Test
	public void tc_REQ011_001() throws InterruptedException {
		// enter to: https://practice.automationbro.com/my-account/
		driver.get("https://practice.automationbro.com/my-account/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// login
		WebElement userInput = driver.findElement(By.xpath("//*[@id='username']"));
		WebElement passwordInput = driver.findElement(By.xpath("//*[@id='password']"));
		WebElement loginBtn = driver.findElement(By.xpath("//*[@id='customer_login']/div[1]/form/p[3]/button"));
		userInput.sendKeys(user);
		passwordInput.sendKeys(password);
		loginBtn.click();
		// click on shop menu
		WebElement shopMenu = driver.findElement(By.xpath("//*[@id='menu-item-567']/a"));
		shopMenu.click();
		// choose a random number of products to click on add to cart button
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		int numberOfProducts = (int) (Math.random() * 5 + 1);
		int getProduct;
		WebElement addBtn;
		for (int i = 0; i < numberOfProducts; i++) {
			getProduct = (int) (Math.random() * products + 1);
			addBtn = driver
					.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]//a[text()='Add to cart']"));
			addBtn.click();
			// wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='primary']/ul//li["+getProduct+"]//a[text()='View
			// cart']"))));
			Thread.sleep(2000);
		}
		// click on any "view cart" link
		WebElement cartBtn = driver.findElement(By.xpath("//*[@id='primary-menu']/li[9]/a/i"));
		wait.until(ExpectedConditions.visibilityOf(cartBtn));
		cartBtn.click();
		// click on "proceed to checkout"
		driver.findElement(By.xpath("//*[@id='post-7']/div/div[2]/div[2]/div/div/a")).click();
		// fill first name field
		driver.findElement(By.xpath("//*[@id='billing_first_name']")).sendKeys("Phillip");
		// fill last name field
		driver.findElement(By.xpath("//*[@id='billing_last_name']")).sendKeys("Sherman");
		// fill country/region field
		driver.findElement(By.xpath("//*[@id='select2-billing_country-container']")).click();
		WebElement dropdownInput = driver.findElement(By.xpath("/html/body/span[2]/span/span[1]/input"));
		dropdownInput.sendKeys("aus");
		dropdownInput.sendKeys(Keys.ENTER);

		// fill street address field
		driver.findElement(By.xpath("//*[@id='billing_address_1']")).sendKeys("Calle Wallaby");

		// fill suburb field
		driver.findElement(By.xpath("//*[@id='billing_city']")).sendKeys("Hornsby Heights NSW");

		// fill state field
		driver.findElement(By.xpath("//*[@id='select2-billing_state-container']")).click();
		WebElement stateDropInput = driver.findElement(By.xpath("/html/body/span[2]/span/span[1]/input"));
		stateDropInput.sendKeys("new");
		stateDropInput.sendKeys(Keys.ENTER);

		// fill postal code field
		driver.findElement(By.xpath("//*[@id='billing_postcode']")).sendKeys("2077");

		// fill phone field
		driver.findElement(By.xpath("//*[@id='billing_phone']")).sendKeys("1234567891");

		// fill email address field
		// driver.findElement(By.xpath(""));

		// click on place order
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='place_order']")).click();

		// assert order message
		Thread.sleep(5000);
		WebElement messageElement = driver.findElement(
				By.xpath("//*[@id='post-8']/div/div/div/div/section/div/div/div/div/div/div/div/div/div[2]/div/p"));
		// wait.until(ExpectedConditions.visibilityOf(messageElement));
		String messageString = messageElement.getText();
		Assert.assertEquals(messageString, "Thank you. Your order has been received.");
	}

	/*
	 * validate that the cart icon reflects when user adds a product to the cart
	 */
	@Test
	public void tc_REQ009_001() throws InterruptedException {
		// enter to: https://practice.automationbro.com/shop/
		driver.get("https://practice.automationbro.com/shop/");
		// choose a random number of products to click on add to cart button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		int numberOfProducts = (int) (Math.random() * 5 + 1);
		int getProduct = 1;
		WebElement addBtn;
		for (int i = 0; i < numberOfProducts; i++) {
			getProduct = (int) (Math.random() * products + 1);
			addBtn = driver
					.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]//a[text()='Add to cart']"));
			addBtn.click();
			Thread.sleep(2000);
		}
		// validate if the cart icon is displayed in the top of the page
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='primary-menu']/li[9]/a/i")).isDisplayed());
		// validate the number of products in the cart matches with the icon number
		Assert.assertTrue(Integer.parseInt(
				driver.findElement(By.xpath("//*[@id='primary-menu']/li[9]/a/span")).getText()) == numberOfProducts);
	}

	/*
	 * Validate that products are added to the cart after click on add to cart
	 * button
	 */
	@Test
	public void tc_REQ008_001() throws InterruptedException {
		// enter to: https://practice.automationbro.com/shop/
		driver.get("https://practice.automationbro.com/shop/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// choose a random product to click on add to cart button
		int products = driver.findElements(By.xpath("//*[@id='primary']/ul//li")).size();
		int getProduct = (int) (Math.random() * products + 1);
		WebElement addBtn = driver
				.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]//a[text()='Add to cart']"));
		String selectedProduct = driver.findElement(By.xpath("//*[@id='primary']/ul//li[" + getProduct + "]/a/h2"))
				.getText();
		addBtn.click();
		Thread.sleep(2000);
		// click on any "view cart" link
		WebElement cartBtn = driver.findElement(By.xpath("//*[@id='primary-menu']/li[9]/a/i"));
		wait.until(ExpectedConditions.visibilityOf(cartBtn));
		cartBtn.click();
		// verify if the item in the cart is the same previosly added
		String nameOfProduct = driver
				.findElement(By.xpath("//*[@id='post-7']/div/div[2]/form/table/tbody/tr[1]/td[3]/a")).getText();
		Assert.assertTrue(selectedProduct.equals(nameOfProduct));
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		System.out.println("EXIT");
		Thread.sleep(5000);
		driver.quit();
	}
	//
}

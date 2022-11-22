package tests;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class B_GlobalVariables {
	WebDriver driver;
	WebDriverWait wait;
	String baseURL = "http://adactinhotelapp.com/",
        userName = "ADTraining2211",
        password = "Password101",
        location = "Sydney",
        hotel = "Hotel Creek",
        roomType = "Standard",
        roomNo = "2 - Two",
        adultRoom = "2 - Two",
        childRoom = "1 - One",
        firstName = "TESTFN",
        lastName = "TESTLN",
        billingAddress = "BILLING ADDRESS",
        ccnumber = "1234567812345678",
        cctype = "American Express",
        expiryMonth = "December",
        expiryYear = "2022",
        cccvv = "111",
        expectedPageTitle = "Adactin.com - Hotel Reservation System";
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		//Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@Test
	public void testhappypath() {
		
		//Login Page
	    driver.get(baseURL);
	    
	    //Method 1 - Verification point
	  	Assert.assertEquals(driver.getTitle(), expectedPageTitle);
	  	
	    driver.findElement(By.id("username")).sendKeys(userName);
	    driver.findElement(By.id("password")).sendKeys(password);
	    driver.findElement(By.id("login")).click();	    
	    
	    //Search Hotel Page	    
	    //Explicit wait
  		wait=new WebDriverWait(driver, Duration.ofSeconds(60));
  		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=\"Logout\"]")));
	  		
	  	//Method 2 - Verification point
	  	if(driver.findElement(By.xpath("//a[text()=\"Logout\"]")).isDisplayed()) {
			System.out.println("User logged in successfully");
			Assert.assertEquals(true,  true);
		}
		else {
			System.out.println("User log in failed");
			//Assert.fail("Dashboard not loaded");
		}
	  		
	    new Select(driver.findElement(By.id("location"))).selectByVisibleText(location);
	    new Select(driver.findElement(By.id("hotels"))).selectByVisibleText(hotel);
	    new Select(driver.findElement(By.id("room_type"))).selectByVisibleText(roomType);
	    new Select(driver.findElement(By.id("room_nos"))).selectByVisibleText(roomNo);
	    new Select(driver.findElement(By.id("adult_room"))).selectByVisibleText(adultRoom);
	    new Select(driver.findElement(By.id("child_room"))).selectByVisibleText(childRoom);	    
	    driver.findElement(By.id("Submit")).click();
	    
	    //Select Hotel Page
	    driver.findElement(By.id("radiobutton_0")).click();
	    driver.findElement(By.id("continue")).click();
	    
	    //Book A Hotel Page
	    driver.findElement(By.id("first_name")).sendKeys(firstName);
	    driver.findElement(By.id("last_name")).sendKeys(lastName);
	    driver.findElement(By.id("address")).sendKeys(billingAddress);
	    driver.findElement(By.id("cc_num")).sendKeys(ccnumber);	    
	    new Select(driver.findElement(By.id("cc_type"))).selectByVisibleText(cctype);
	    new Select(driver.findElement(By.id("cc_exp_month"))).selectByVisibleText(expiryMonth);
	    new Select(driver.findElement(By.id("cc_exp_year"))).selectByVisibleText(expiryYear);	    
	    driver.findElement(By.id("cc_cvv")).sendKeys(cccvv);
	    driver.findElement(By.id("book_now")).click();

		// Explicit wait until booking is completed
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = driver.findElement(By.id("process_span"));
		wait.until(ExpectedConditions.stalenessOf(element));

	    driver.findElement(By.linkText("Logout")).click();
	}
	
	@AfterMethod
	public void tearDown() {
	  driver.quit();
	}
}

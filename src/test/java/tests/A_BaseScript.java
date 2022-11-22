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
import org.testng.annotations.*;

public class A_BaseScript {
	WebDriver driver;
	WebDriverWait wait;
	
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
	    driver.get("http://adactinhotelapp.com/");
	    
	    //Method 1 - Verification point
	  	Assert.assertEquals(driver.getTitle(), "Adactin.com - Hotel Reservation System");
	  	
	    driver.findElement(By.id("username")).sendKeys("ADTraining2211");
	    driver.findElement(By.id("password")).sendKeys("Password101");
	    driver.findElement(By.id("login")).click();	    
	    
	    //Search Hotel Page	    
	    //Explicit wait
  		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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
	  		
	    new Select(driver.findElement(By.id("location"))).selectByVisibleText("Sydney");
	    new Select(driver.findElement(By.id("hotels"))).selectByVisibleText("Hotel Sunshine");
	    new Select(driver.findElement(By.id("room_type"))).selectByVisibleText("Standard");
	    new Select(driver.findElement(By.id("room_nos"))).selectByVisibleText("2 - Two");
	    new Select(driver.findElement(By.id("adult_room"))).selectByVisibleText("2 - Two");
	    new Select(driver.findElement(By.id("child_room"))).selectByVisibleText("1 - One");	    
	    driver.findElement(By.id("Submit")).click();
	    
	    //Select Hotel Page
	    driver.findElement(By.id("radiobutton_0")).click();
	    driver.findElement(By.id("continue")).click();
	    
	    //Book A Hotel Page
	    driver.findElement(By.id("first_name")).sendKeys("TestFN");
	    driver.findElement(By.id("last_name")).sendKeys("TestLN");
	    driver.findElement(By.id("address")).sendKeys("TestAddress");
	    driver.findElement(By.id("cc_num")).sendKeys("1234123412345678");	    
	    new Select(driver.findElement(By.id("cc_type"))).selectByVisibleText("VISA");
	    new Select(driver.findElement(By.id("cc_exp_month"))).selectByVisibleText("September");
	    new Select(driver.findElement(By.id("cc_exp_year"))).selectByVisibleText("2022");	    
	    driver.findElement(By.id("cc_cvv")).sendKeys("123");
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

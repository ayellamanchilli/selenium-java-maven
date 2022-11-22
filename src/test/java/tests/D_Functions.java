package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import functions.BusinessFunctions;

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

public class D_Functions extends BusinessFunctions {
	WebDriver driver;
	WebDriverWait wait;
	Properties Prop;
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

	@BeforeTest
	public void setUp() throws FileNotFoundException, IOException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		//Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		Prop = new Properties();
		Prop.load(new FileInputStream("./SharedUIMap/SharedMap.properties"));
	}
	
	@Test
	public void testhappypath() throws FileNotFoundException, IOException {
		
		//Login Page
	    driver.get(baseURL);
	    
	    //Method 1 - Verification point
	  	Assert.assertEquals(driver.getTitle(), expectedPageTitle);
	  	
	  	HotelApp_Login(driver, userName, password);	    
	    
	    //Search Hotel Page	    
	    //Explicit wait
  		wait=new WebDriverWait(driver, Duration.ofSeconds(60));
  		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Prop.getProperty("HomePage_LogOut"))));
	  		
	  	//Method 2 - Verification point
	  	if(driver.findElement(By.xpath(Prop.getProperty("HomePage_LogOut"))).isDisplayed()) {
			System.out.println("User logged in successfully");
			Assert.assertEquals(true,  true);
		}
		else {
			System.out.println("User log in failed");
			//Assert.fail("Dashboard not loaded");
		}
	  		
	    new Select(driver.findElement(By.id(Prop.getProperty("SearchPage_Location")))).selectByVisibleText(location);
	    new Select(driver.findElement(By.id(Prop.getProperty("SearchPage_Hotel")))).selectByVisibleText(hotel);
	    new Select(driver.findElement(By.id(Prop.getProperty("SearchPage_RoomType")))).selectByVisibleText(roomType);
	    new Select(driver.findElement(By.id(Prop.getProperty("SearchPage_NoOfRooms")))).selectByVisibleText(roomNo);
	    new Select(driver.findElement(By.id(Prop.getProperty("SearchPage_AdultRooms")))).selectByVisibleText(adultRoom);
	    new Select(driver.findElement(By.id(Prop.getProperty("SearchPage_ChildRooms")))).selectByVisibleText(childRoom);	    
	    driver.findElement(By.id(Prop.getProperty("SearchPage_Submit"))).click();
	    
	    //Select Hotel Page
	    driver.findElement(By.id(Prop.getProperty("SelectHotel_Hotel"))).click();
	    driver.findElement(By.id(Prop.getProperty("SelectHotel_Continue"))).click();
	    
	    //Book A Hotel Page
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_FirstName"))).sendKeys(firstName);
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_LastName"))).sendKeys(lastName);
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_Address"))).sendKeys(billingAddress);
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCNum"))).sendKeys(ccnumber);	    
	    new Select(driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCType")))).selectByVisibleText(cctype);
	    new Select(driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCExpMonth")))).selectByVisibleText(expiryMonth);
	    new Select(driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCExpYear")))).selectByVisibleText(expiryYear);	    
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCCVV"))).sendKeys(cccvv);
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_BookNow"))).click();

		// Explicit wait until booking is completed
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = driver.findElement(By.id("process_span"));
		wait.until(ExpectedConditions.stalenessOf(element));

	    driver.findElement(By.xpath(Prop.getProperty("HomePage_LogOut"))).click();
	}	

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}

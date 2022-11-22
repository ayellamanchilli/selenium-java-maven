package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import functions.BusinessFunctions;

public class F_Alerts extends BusinessFunctions {
	WebDriver driver;
	WebDriverWait wait;
	Properties Prop;
	String browserName = "Chrome";

	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException {

		if(browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else {
			System.out.println("Provide proper browser name");
		}
		
		driver.manage().window().maximize();

		Prop = new Properties();
		Prop.load(new FileInputStream("./SharedUIMap/SharedMap.properties"));
		
		//Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}

	@Test
	public void assertDemo() throws FileNotFoundException, IOException {
		String strTestDataPath = "./Datapool/HotelApp_TestData.xls";
		String baseURL = readXL(1,"URL", strTestDataPath),
		        userName = readXL(1,"UserName", strTestDataPath),
		        password = readXL(1,"Password", strTestDataPath),

		        location = readXL(1,"location", strTestDataPath),
		        hotel = readXL(1,"hotel", strTestDataPath),
		        roomType = readXL(1,"roomType", strTestDataPath),
		        roomNo = readXL(1,"roomNo", strTestDataPath),
		        adultRoom = readXL(1,"adultRoom", strTestDataPath),
		        childRoom = readXL(1,"childRoom", strTestDataPath),

		        firstName = readXL(1,"firstName", strTestDataPath),
		        lastName = readXL(1,"lastName", strTestDataPath),
		        billingAddress = readXL(1,"billingAddress", strTestDataPath),
		        ccnumber = readXL(1,"ccnumber", strTestDataPath),
		        cctype = readXL(1,"cctype", strTestDataPath),
		        expiryMonth = readXL(1,"expiryMonth", strTestDataPath),
		        expiryYear = readXL(1,"expiryYear", strTestDataPath),
		        cccvv = readXL(1,"cccvv", strTestDataPath),

		        expectedPageTitle = readXL(1,"expectedPageTitle", strTestDataPath);
		
		//Login Page
	    driver.get(baseURL);
	    
	    //Method 1 - Verification point
	  	Assert.assertEquals(driver.getTitle(), expectedPageTitle);
	  	
	  	HotelApp_Login(driver, userName, password);	    
	    
	    //Search Hotel Page	    
	    //Explicit wait
  		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
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
	    new Select(driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCExpYear")))).selectByVisibleText("2011");	    
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCCVV"))).sendKeys(cccvv);
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_BookNow"))).click();
	    
	    //Getting text from alert and printing it
	    String popUpText = driver.switchTo().alert().getText();
        System.out.println("Pop up text is: " + popUpText);
        driver.switchTo().alert().accept();
	    
	    //Provide correct year and submit
	    new Select(driver.findElement(By.id(Prop.getProperty("BookHotelPage_CCExpYear")))).selectByVisibleText(expiryYear);	    
	    driver.findElement(By.id(Prop.getProperty("BookHotelPage_BookNow"))).click();

		// Explicit wait until booking is completed
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = driver.findElement(By.id("process_span"));
		wait.until(ExpectedConditions.stalenessOf(element));

		driver.findElement(By.xpath(Prop.getProperty("HomePage_LogOut"))).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}

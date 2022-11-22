package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageObjects.LoginPO;

import java.time.Duration;

public class H_WithPageObject {
    @Test
    public void HappyPath() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Create an object Login page
        LoginPO loginPage = new LoginPO(driver);

        driver.get("http://adactinhotelapp.com/");
        driver.manage().window().maximize();

        loginPage.setUsername("ADTraining0810");
        loginPage.setPassword("Password101");
        loginPage.clickLogin();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=\"Logout\"]")));

        driver.quit();
    }
}

package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageObjects.LoginPageFactory;

import java.time.Duration;

public class I_WithPageFactory {
    @Test
    public void HappyPath() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        LoginPageFactory objLogin = new LoginPageFactory(driver);

        driver.get("http://adactinhotelapp.com/");
        driver.manage().window().maximize();

        objLogin.setUsername("ADTraining0810");
        objLogin.setPassword("Password101");
        objLogin.clickLogin();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=\"Logout\"]")));

        driver.quit();
    }
}

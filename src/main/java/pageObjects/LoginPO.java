package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPO {
    WebDriver driver;
    By txt_Username = By.id("username");
    By txt_Password = By.id("password");
    By btn_Login = By.id("login");

    public LoginPO(WebDriver driver) {
        this.driver = driver;
    }

    public void setUsername(String usernameValue) {
        driver.findElement(txt_Username).sendKeys(usernameValue);
    }

    public String getUsername() {
        return driver.findElement(txt_Username).getAttribute("value");
    }

    public void setPassword(String passwordValue) {
        driver.findElement(txt_Password).sendKeys(passwordValue);
    }

    public void clickLogin() {
        driver.findElement(btn_Login).click();
    }
}

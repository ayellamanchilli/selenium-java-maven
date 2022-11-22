package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageFactory {
    WebDriver driver;

    @FindBy(id="username")
    WebElement txt_Username;

    @FindBy(id="password")
    WebElement txt_Password;

    @FindBy(id="login")
    WebElement btn_Login;

    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String usernameValue) {
        txt_Username.sendKeys(usernameValue);
    }

    public void setPassword(String passwordValue) {
        txt_Password.sendKeys(passwordValue);
    }

    public void clickLogin() {
        btn_Login.click();
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage
{
  WebDriver driver;
  //Constructor
    public LoginPage(WebDriver driver)
    {
        this.driver=driver;

    }
    //Locators
    private By userNameField= By.xpath("//input[@name='username']");
    private By passwordField=By.xpath("//input[@name='password']");
    private By loginBtn = By.xpath("//button[contains(.,Login)]");
    private By errorText= By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

    //Action on Locators
    public void openLoginPage()
    {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }
    public void enterUserName(String username)
    {
        driver.findElement(userNameField).sendKeys(username);

    }
    public void enterPassword(String password)
    {
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickLogin()
    {
        driver.findElement(loginBtn).click();
    }
    public String getErrorMessage()
    {
        return driver.findElement(errorText).getText();

    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DashboardPage
{
    WebDriver driver;
    //Constructor
    public DashboardPage(WebDriver driver)
    {
        this.driver=driver;
    }

    //Locators
    public By dashBoardField=By.xpath("//h6[contains(.,Dashboard')]");
    public By dashBoardItems=By.cssSelector("ul.oxd-main-menu >li span.oxd-text ");

    public List<WebElement>dashBoardItems()
    {
        return driver.findElements(dashBoardField);
    }

    public String fetchDashboardUrl()
    {
        return driver.getCurrentUrl();
    }

    public String dashBoardHeaderMessage()
    {
        return driver.findElement(dashBoardField).getText();
    }
    private boolean isDisplayed()
    {
        return driver.findElement(dashBoardField).isDisplayed();
    }

}

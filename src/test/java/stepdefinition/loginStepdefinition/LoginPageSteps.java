package stepdefinition.loginStepdefinition;

import hook.Hooks;
import io.cucumber.java.en.*;
import junit.framework.Assert;
import org.apache.hc.core5.util.Asserts;
import org.bouncycastle.jcajce.provider.symmetric.RC5;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashboardPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoginPageSteps
{
    WebDriver driver =Hooks.getDriver() ;
    LoginPage loginPage=new LoginPage(driver);
    DashboardPage dashboardPage=new DashboardPage(driver);
    @Given("User is on the login page")
    public void user_is_on_the_login_page()
    {
        Hooks.getTest().info("Navigation to laging page");
       loginPage.openLoginPage();
       Hooks.getTest().info("");

    }
    @When("User enter username as {string} and password as {string}")
    public void user_enter_username_as_and_password_as(String username, String password)
    {
        loginPage.enterUserName(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }
    @Then("User verified it redirected to the dashboard")
    public void user_verified_it_redirected_to_the_dashboard()
    {
        By dashboardField = dashboardPage.dashBoardField;
       String expectedUrl="https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
       String actualUrl=dashboardPage.fetchDashboardUrl();
        Assert.assertEquals("Url Do not match",expectedUrl,actualUrl);
        //driver.quit();
    }

    @Then("User verified it error message should be {string}")
    public void user_verified_it_error_message_should_be(String errorMsg)
    {
        String actualMsg= loginPage.getErrorMessage();
        Assert.assertEquals("Message didn't Match",errorMsg,actualMsg);
    }
    @When("user should see following Dashbord menu items:")
    public void user_should_see_following_dashbord_menu_items(List<String>expectedMenuItems)
    { //capture all sidebar menu items text(css selectors/xpaths adjusted for OrangeHRM UI)
        List<WebElement>menuElements=driver.findElements(By.cssSelector("ul.oxd-main-menu >li span.oxd-text "));
        //convert WebElements to List<String> by extracting visible text from each
        List<String> actualMenuItems = menuElements.stream().map(WebElement::getText).collect(Collectors.toList());
        //Assert each expected menu item is present in the actual menu items list
        for (String expectedItem :expectedMenuItems)
        {
            Assertions.assertTrue(actualMenuItems.contains(expectedItem),"Dashboard menu missing:"+expectedItem);
        }



    }
    @When("user tries login with following credentials:")
    public void user_tries_login_with_following_credentials(io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> credentials = dataTable.asLists(String.class);
        for (int i=1;i<credentials.size();i++)
        {
            driver.navigate().refresh();
            String username = credentials.get(i).get(0);
            String password = credentials.get(i).get(1);

            //Wait for username input to be visible, then enter text
            WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
            WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
            usernameInput.clear();
            usernameInput.sendKeys(username);
            passwordInput.clear();
            passwordInput.sendKeys(password);

            driver.findElement(By.xpath("//button[contains(.,' Login ')]")).click();
        }
    }
    @Then("user should verifies error massage   {string}")
    public void user_should_verifies_error_massage(String errorMessage) {
        String actualText = driver.findElement(By.xpath("//p[contains(@class,'oxd-alert-content-text')]")).getText();
        Assert.assertEquals(errorMessage , actualText);
    }
    @When("User enters below creds as Map:")
    public void user_enters_below_creds_as_map(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String,String>>dataList = dataTable.asMaps(String.class,String.class);
        for (Map<String,String>data :dataList)
        {
            driver.findElement(By.name("username")).sendKeys(data.get("UserName"));
            driver.findElement(By.name("password")).sendKeys(data.get("Password"));
            WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
            WebElement loginBtn =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,' Login ')]")));
            loginBtn.click();
        }
    }


}

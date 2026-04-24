package hook;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Hooks
{
    static WebDriver driver;
    static ExtentReports extent;
    static ExtentTest test;
    @Before(order = 0)
    public static void setUpExtent()
    {
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter("reports/extent-report.html");
        extent=new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("os",System.getProperty("os.name"));
        extent.setSystemInfo("Java Version",System.getProperty("java.version"));
    }
    @Before(order = 1)
public void launchBrowser()
    {
    System.out.println("Launching browser (order 1)");
}

    @Before(order=2)
    public void setup()
    {
        System.out.println("HOOK RUNNING");
        String browser="chrome";
        //lunch browser
        switch (browser.toLowerCase())
        {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver=new ChromeDriver();
                break;

            case "msedge":
                WebDriverManager.edgedriver().setup();
                driver=new EdgeDriver();
                break;

            case"firefox":
                WebDriverManager.firefoxdriver().setup();
                driver=new FirefoxDriver();
                break;

            default:
                driver=null;
                break;

        //implement wait for 10 sec


        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

    }
    @Before(order = 2)
    public void startExtentTest(Scenario scenario)
    {
        test =extent.createTest(scenario.getName());
    }
    @After(order = 1)
    public void closeBrowser() {
        System.out.println("Closing browser (order 1)");
    }
    @After(order = 2)
    public void tearDown(Scenario scenario)
    {
        if(scenario.isFailed())
        {
            try{
                TakesScreenshot ts=(TakesScreenshot) driver;
                byte[] screenShot=ts.getScreenshotAs(OutputType.BYTES);
                String screenShotPath = saveScreenShotToFile(screenShot,scenario);
                test.fail("Scenario Failed").addScreenCaptureFromPath(screenShotPath);
                scenario.attach(screenShot,"image/png","Failed Screenshot" );

            }
            catch (IOException e)
            {
                test.warning("Failed to capture screenshot:"+e.getMessage());
            }
        }else {
            test.pass("Scenario Passed");
        }
        extent.flush();

    }
    private String saveScreenShotToFile(byte[]bytes,Scenario scenario) throws IOException {
        String scenarioName =scenario.getName().replaceAll("[^a-zA-Z0-9]","_");
        String timestamp =new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName =scenarioName +"_"+timestamp +".png";
        Path path= Paths.get("reports/screenshort",fileName);
        Files.createDirectories(path.getParent());
        Files.write(path,bytes);
        return path.toString();
    }
    public static WebDriver getDriver()

    {
        if(driver !=null)
    {
         return driver;
    }
        return null;
    }
    public static ExtentTest getTest()
    {
        return  test;
    }
}

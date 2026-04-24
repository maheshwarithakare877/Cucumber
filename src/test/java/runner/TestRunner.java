package runner;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to your .feature files
        tags = "@Sprint1",//on,y runs scenarios tag with @Smoke
        glue = {"stepdefinition", "hook" } ,   // Package name for your step definition classes
        plugin = {"pretty", "html:target/cucumber-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},// Report configuration
        monochrome=true,
        dryRun = false
)
public class TestRunner
{
    //this class remain empty. It is used only as a holder for the above annotations
}

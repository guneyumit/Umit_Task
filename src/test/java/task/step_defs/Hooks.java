package task.step_defs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import task.utility.Driver;

import java.util.concurrent.TimeUnit;

import static task.utility.Driver.driver;

public class Hooks {

    @Before
    public void setup(){
        driver().manage().window().maximize();
        driver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    @After
    public void tearDown(Scenario scenario){

        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "image");
        }
        Driver.closeDriver();
    }



}

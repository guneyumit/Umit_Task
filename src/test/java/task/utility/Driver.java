package task.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<WebDriver>();

    private Driver() {

    }

    public static WebDriver driver() {

        if (driverPool.get() == null) {

//           if we pass the driver from terminal then use that one
//           if we do not pass the driver from terminal then use the one in properties file
            String browser =  ConfigurationReader.get("browser");

            switch (browser) {
                case "chrome":
                    WebDriver chromeDriver = new ChromeDriver();
                    driverPool.set(chromeDriver);
                    break;
                case "firefox":
                    driverPool.set(new FirefoxDriver());
                    break;
                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    driverPool.set(new EdgeDriver());
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    driverPool.set(new SafariDriver());
                    break;
            }

        }
        return driverPool.get();
    }

    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }

}

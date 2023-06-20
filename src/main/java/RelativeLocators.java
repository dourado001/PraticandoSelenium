import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

public class RelativeLocators {

    public static WebDriver driver;
    public static String browser = "Chrome";

    @Before
    public void setup(){
        if(browser.equals("Firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if(browser.equals("Chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
    }

    @Test
    public void testRelativeLocators(){
        driver.get("https://saucedemo.com");
        By emailLocator = RelativeLocator.with(By.tagName("input")).above(By.id("password"));
        driver.findElement(emailLocator).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        By loginButton = RelativeLocator.with(By.tagName("input")).below(By.id("password"));
        driver.findElement(loginButton).click();
    }

    @After
    public void afterdown(){
        driver.close();
    }
}


import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Demonstracao {

    WebDriver chromeDriver;

    @BeforeAll
    static void setup(){
        WebDriverManager.chromedriver().clearDriverCache().setup();
    }

    @BeforeEach
    void setupTest(){
        chromeDriver = new ChromeDriver();
    }

    @Test
    void procuraTenisJordanAmazon(){
        chromeDriver.get("https://amazon.com.br");
        chromeDriver.manage().window().maximize();
        chromeDriver.findElement(By.id("twotabsearchtextbox")).sendKeys("Jordan");
        chromeDriver.findElement(By.id("nav-search-submit-button")).click();
        String resultado = chromeDriver.findElement(By.xpath("//div[@id='search']//h1//span[contains(.,'resultados')]/..")).getText();
        assertThat(resultado, containsString("Jordan"));
        //comentario de teste
    }

    @AfterEach
    void teardown(){
        chromeDriver.close();
    }
}

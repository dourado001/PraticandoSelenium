import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class WebDriverInterfaceExample {

    public static WebDriver driver;
    public static String browser = "Chrome";

    @Before
    public void setup(){
        switch (browser) {
            case "Firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "Chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "Edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
        }

        login();
    }

    public void login(){
        driver.get("http://saucedemo.com");
        driver.manage().window().maximize();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String tituloProdutos = driver.findElement(By.xpath("//div[@class='header_secondary_container']//span[@class='title']")).getText();
        Assert.assertEquals("Products", tituloProdutos);
    }

    @Test
    public void compraUmaCamiseta(){
        driver.findElement(By.id("item_4_title_link")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        String produtoCarrinho = driver.findElement(By.xpath("//a[@id='item_4_title_link']//div")).getText();
        Assert.assertEquals("Sauce Labs Backpack", produtoCarrinho);
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Fulano");
        driver.findElement(By.id("last-name")).sendKeys("Siclano");
        driver.findElement(By.id("postal-code")).sendKeys("0552233");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();
        String headerFinalCompra = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();
        Assert.assertEquals("Thank you for your order!", headerFinalCompra);
    }

    @Test
    public void carrinhoVazio(){
        driver.findElement(By.id("item_4_title_link")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        String produtoCarrinho = driver.findElement(By.xpath("//a[@id='item_4_title_link']//div")).getText();
        Assert.assertEquals("Sauce Labs Backpack", produtoCarrinho);
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        List<WebElement> itemDisplayed = driver.findElements(By.xpath("//div[@id='cart_contents_container']//div[@class='cart_item']"));
        if(!itemDisplayed.isEmpty()){
            Assert.fail("Ainda há itens no carrinho");
        }
    }

    @Test
    public void compraTodosItens(){
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Fulano");
        driver.findElement(By.id("last-name")).sendKeys("Siclano");
        driver.findElement(By.id("postal-code")).sendKeys("0552233");
        driver.findElement(By.id("continue")).click();
        List<WebElement> itens = driver.findElements(By.xpath("//div[@class='cart_item_label']//a"));
        Assert.assertEquals(6,itens.size());
        driver.findElement(By.id("finish")).click();
        String headerFinalCompra = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();
        Assert.assertEquals("Thank you for your order!", headerFinalCompra);
    }

    @Test
    public void precoCompraCarrinhoVazio(){
        driver.findElement(By.id("shopping_cart_container")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Fulano");
        driver.findElement(By.id("last-name")).sendKeys("Siclano");
        driver.findElement(By.id("postal-code")).sendKeys("0552233");
        driver.findElement(By.id("continue")).click();
        String total = driver.findElement(By.xpath("//div[@id='checkout_summary_container']//div[@class='summary_info_label summary_total_label']")).getText();
        Assert.assertEquals("Total: $0.00", total);
    }

    @After
    public void afterdown(){
        driver.close();
    }
}

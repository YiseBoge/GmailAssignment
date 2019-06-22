import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailPrinter {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "/Users/yiseboge/chromedriver");

        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        try {

            driver.get("http://www.gmail.com");

            driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("email@email.com");
            driver.findElement(By.id("identifierNext")).click();
            WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(password));
            password.sendKeys("password");
            driver.findElement(By.id("passwordNext")).click();

            Thread.sleep(3000);
            System.out.println(driver.findElements(By.xpath("//*[@class='zA zE']")));

            List<WebElement> unread = driver.findElements(By.xpath("//*[@class='zA zE']"));
            System.out.println(unread.size());


            PrintWriter writer = new PrintWriter("emails.txt", StandardCharsets.UTF_8);
            writer.println("------------------------------------Unread Mails------------------------------------");
            for (WebElement webElement : unread) {
                writer.println("---- Mail " + unread.indexOf(webElement) + "----");
                WebElement from = webElement.findElement(By.xpath(".//*[@class='yW']"));
                writer.println("From : " + from.getText());

                WebElement subject = webElement.findElement(By.xpath(".//*[@class='bog']"));
                writer.println("Subject : " + subject.getText());
                writer.println("---- End of mail " + unread.indexOf(webElement) + "----\n");

            }
            writer.println("------------------------------------End of Unread Mails------------------------------------");
            writer.close();
        } finally {
            driver.close();
        }
    }
}

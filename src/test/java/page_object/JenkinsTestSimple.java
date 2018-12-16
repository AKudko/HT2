package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

public class JenkinsTestSimple {
    // Это -- т.н. BaseURL, от него будут строиться все относительные URL'ы
    String base_url = "http://localhost:8080";

    // Нужно для эмуляции verify* (т.н. soft-assertion)
    StringBuffer verificationErrors = new StringBuffer();

    // Сам драйвер
    WebDriver driver = null;

    @BeforeClass
    public void beforeClass() throws Exception {

        // Подключение драйвера
        System.setProperty("webdriver.chrome.driver", ".\\chrome\\chromedriver.exe");

        // Так можно запустить Chrome с пустой стартовой страницей
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));

        // Запуск драйвера (и браузера)
        driver = new ChromeDriver(capabilities);
    }
    @AfterClass
    public void afterClass()
    {
         driver.quit();

        // Если в "накопителе сообщений об ошибках" что-то есть,
        // крэшим тест с соответствующим сообщением:
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @Test
    public void firstTest (){
        driver.get(base_url);

        // 1-проверка: "Страница содержит ссылку «Manage Jenkins»
        // 1.	После клика по ссылке «Manage Jenkins» на странице появляется элемент dt
        // с текстом «Manage Users» и элемент dd
        // с текстом «Create/delete/modify users that can log in to this Jenkins».
        Collection<WebElement> forms = driver.findElements(By.tagName("form"));
        Assert.assertFalse(forms.isEmpty(), "No forms found!");

    }
@Test
    public void tstJenkins(){
    // 1-действие: "Открыть http://localhost:8080/"
    driver.get(base_url);
    // 1 страница содержит ссыдку «Manage Jenkins»

}




}

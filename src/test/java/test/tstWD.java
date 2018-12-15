package wd;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;



public class tstWD {

    // Это -- т.н. BaseURL, от него будут строиться все относительные URL'ы
    String base_url = "http://svyatoslav.biz";

    // Нужно для эмуляции verify* (т.н. soft-assertion)
    StringBuffer verificationErrors = new StringBuffer();

    // Полезно для управления настройками FF
    // FirefoxProfile profile = new FirefoxProfile();

    // Сам драйвер
    WebDriver driver = null;

    @BeforeClass
    public void beforeClass() throws Exception {

        // Подключение драйвера
        // System.setProperty("webdriver.gecko.driver", "D:/_work/Selenium_Java_3_4_0/geckodriver-v0.18.0-win64.exe");
        System.setProperty("webdriver.chrome.driver", ".\\chrome\\chromedriver.exe");

        // Так можно запустить FF с пустой стартовой страницей
        // profile.setPreference("browser.startup.homepage", "about:blank");

        // Так можно запустить Chrome с пустой стартовой страницей
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));

        // Запуск драйвера (и браузера)
        // driver = new FirefoxDriver(profile);
        driver = new ChromeDriver(capabilities);

        // Если нужно, можно выставить максимальный таймаут по операциям
        // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public void afterClass()
    {
        // Убиение Firefox (чтобы не крэшился сам по себе (см. ниже)).
        // Таким же образом можно убивать "накопившиеся в памяти" экземпляры geckodriver'ов.
		/*
		try {
			Runtime.getRuntime().exec("taskkill /f /IM firefox.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/

        // На WD 3.3.1 и 15-м geckodriver'е FF крэшится на этой команде:
        driver.quit();

        // Если в "накопителе сообщений об ошибках" что-то есть,
        // крэшим тест с соответствующим сообщением:
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @Test
    public void tstWt() {
        // 1-действие: "Открыть http://svyatoslav.biz/testlab/wt/"
        driver.get(base_url + "/testlab/wt/");

        // 1-проверка: "Страница содержит форму с полями «Имя», «Рост», «Вес», радиокнопкой «Пол» и
        // кнопкой отправки данных «Рассчитать». Также на странице есть соответствующие текстовые надписи."
        Collection<WebElement> forms = driver.findElements(By.tagName("form"));
        Assert.assertFalse(forms.isEmpty(), "No forms found!");

        Iterator<WebElement> i = forms.iterator();
        boolean form_found = false;
        WebElement form = null;

        while (i.hasNext()) {
            form = i.next();
            if 	((form.findElement(By.name("name")).getAttribute("type").equalsIgnoreCase("text"))&&
                    (form.findElement(By.name("height")).getAttribute("type").equalsIgnoreCase("text"))&&
                    (form.findElement(By.name("weight")).getAttribute("type").equalsIgnoreCase("text"))&&
                    (form.findElement(By.xpath("//input[@type=\"submit\"]")).getAttribute("value").equalsIgnoreCase("Рассчитать"))&&
                    (form.findElements(By.name("gender")).size()==2)&&
                    (form.findElements(By.xpath("//input")).size()==6)) {
                form_found = true;
                break;
            }
        }

        Assert.assertTrue(form_found, "No suitable forms found!");

        // Здесь нам пригодится эмуляция soft-assertion:

        String bodyText = driver.findElement(By.tagName("body")).getText();

        try {
            Assert.assertTrue(bodyText.contains("Имя"));
        } catch (Error e) {
            verificationErrors.append("No 'Имя' text string found: " + e.toString() + "\n");
        }

        try {
            Assert.assertTrue(bodyText.contains("Рост"));
        } catch (Error e) {
            verificationErrors.append("No 'Рост' text string found: " + e.toString() + "\n");
        }

        try {
            Assert.assertTrue(bodyText.contains("Вес"));
        } catch (Error e) {
            verificationErrors.append("No 'Вес' text string found: " + e.toString() + "\n");
        }

        try {
            Assert.assertTrue(bodyText.contains("Пол"));
        } catch (Error e) {
            verificationErrors.append("No 'Пол' text string found: " + e.toString() + "\n");
        }

        /*
        try {
            Assert.assertTrue(bodyText.contains("Рассчитать"));
        } catch (Error e) {
            verificationErrors.append("No 'Рассчитать' text string found: " + e.toString() + "\n");
        }
		*/

        // 2-действие: "В поле «Имя» ввести «username»."
        form.findElement(By.name("name")).clear();
        form.findElement(By.name("name")).sendKeys("username");

        // 2-проверка: "Значение появляется в поле."
        Assert.assertEquals(form.findElement(By.name("name")).getAttribute("value"), "username", "Unable to fill 'Имя' filed");


        // 3-действие: "В поле «Рост» ввести «50»."
        form.findElement(By.name("height")).clear();
        form.findElement(By.name("height")).sendKeys("50");

        // 3-проверка: "Значение появляется в поле."
        Assert.assertEquals(form.findElement(By.name("height")).getAttribute("value"), "50", "Unable to fill 'Рост' filed");


        // 4-действие: "В поле «Вес» ввести «3»."
        form.findElement(By.name("weight")).clear();
        form.findElement(By.name("weight")).sendKeys("3");

        // 4-проверка: "Значение появляется в поле."
        Assert.assertEquals(form.findElement(By.name("weight")).getAttribute("value"), "3", "Unable to fill 'Вес' filed");


        // 5-действие: "В радиокнопке «Пол» выбрать пол «М»."
        form.findElement(By.xpath("//input[@name='gender' and @value='m']")).click();

        // 5-проверка: "Вариант «М» выбран."
        Assert.assertTrue(form.findElement(By.xpath("//input[@name='gender' and @value='m']")).isSelected(), "Unable select 'М' gender");


        // 6-действие: "6. Нажать «Рассчитать»."
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        // 6-проверка: "6. Форма исчезает, в центральной ячейке таблицы появляется надпись «Слишком малая масса тела»."
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//form"), 0));

        Assert.assertEquals(driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]")).getText(), "Слишком малая масса тела");
    }
}
package page_object;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class JenkinsTestTask {
    // Это -- т.н. BaseURL, от него будут строиться все относительные URL'ы
    String base_url = "http://localhost:8080";

    // Нужно для эмуляции verify* (т.н. soft-assertion)
    StringBuffer verificationErrors = new StringBuffer();

    // Сам драйвер
    WebDriver driver = null;


    @FindBy(name = "j_username")
    private WebElement username;

    @FindBy(name = "j_password")
    private WebElement password;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement signInButton;


    @BeforeClass
    public void beforeClass() throws Exception {

        // Подключение драйвера
        System.setProperty("webdriver.chrome.driver", ".\\chrome\\chromedriver.exe");

        // Так можно запустить Chrome с пустой стартовой страницей
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));

        // Запуск драйвера (и браузера)
        driver = new ChromeDriver(capabilities);
        driver.get(base_url);
        PageFactory.initElements(driver, this);
        username.sendKeys("alex");
        password.sendKeys("1234");
        signInButton.click();

    }
    @AfterClass
    public void afterClass()
    {
       // driver.quit();

        // Если в "накопителе сообщений об ошибках" что-то есть,
        // крэшим тест с соответствующим сообщением:
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @Test
    public void tstFirst(){
        // 1-действие: "Открыть http://localhost:8080/"
        driver.get(base_url);
        // 1 страница содержит ссыдку «Manage Jenkins»
        Page1StartPage page1StartPage = new Page1StartPage(driver);
        // если ссылка есть и кликается, все ок - иначе пишем ошибку в  verificationErrors
       try {
           page1StartPage.clicLinkManageJenkins();
       }catch (NoSuchElementException e){
           verificationErrors.append("Link «Manage Jenkins» has not found");
       }
        Page2ManageJenkins page2ManageJenkins = new Page2ManageJenkins(driver);
        Assert.assertEquals(page2ManageJenkins.getDt().getText(), "Manage Users");
        Assert.assertEquals(page2ManageJenkins.getDd().getText(), "Create/delete/modify users that can log in to this Jenkins");
    }
    @Test(dependsOnMethods = { "tstFirst" })
    public void tstSecond(){
        Page2ManageJenkins page2ManageJenkins = new Page2ManageJenkins(driver);
        page2ManageJenkins.clickManageUsers();
        Page3ManageUsers page3ManageUsers = new Page3ManageUsers(driver);
        Assert.assertTrue(page3ManageUsers.isCreateUserEnabled(),"Link \"Create User\" is not avaliable");
    }
    @Test(dependsOnMethods = { "tstSecond" })
    public void tstThird(){
        Page3ManageUsers page3ManageUsers = new Page3ManageUsers(driver);
        page3ManageUsers.clickCreateUser();
        Page4CreateUser page4CreateUser = new Page4CreateUser(driver);
        PageFactory.initElements(driver, this);

        Assert.assertTrue(page4CreateUser.isFormPresentForReal(),"No suitable forms found!" );

    }


}

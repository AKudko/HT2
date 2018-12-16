package page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractSomePage {
    protected WebDriver driver;
    private WebDriverWait wait;

    public AbstractSomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
        PageFactory.initElements(driver, this);
    }

}

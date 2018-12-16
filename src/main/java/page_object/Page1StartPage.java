package page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page1StartPage extends AbstractSomePage{
    @FindBy(linkText = "Manage Jenkins")
    private WebElement linkManageJenkins;

    public Page1StartPage(WebDriver driver) {
        super(driver);
    }


    public WebElement getLinkManageJenkins() {
        return linkManageJenkins;
    }

    public void clicLinkManageJenkins(){
        linkManageJenkins.click();

    }

}

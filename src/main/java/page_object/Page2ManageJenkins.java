package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page2ManageJenkins extends AbstractSomePage{
    @FindBy(xpath = "//a[@title = 'Manage Users']")
    private WebElement linkManageUsers;
    @FindBy(xpath = "//div[@id='main-panel']/div[16]/a/dl/dt")
    private WebElement dt;
    @FindBy(xpath = "//div[@id='main-panel']/div[16]/a/dl/dd")
    private WebElement dd;

    public Page2ManageJenkins(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
    }
    public WebElement getDt (){
               return dt;
    }
    public WebElement getDd (){
        return dd;
    }
    public void clickManageUsers(){
        linkManageUsers.click();
    }
}

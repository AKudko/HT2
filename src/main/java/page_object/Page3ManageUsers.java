package page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Page3ManageUsers extends AbstractSomePage{
    @FindBy(linkText = "Create User")
    private WebElement linkCreateUser;

    public Page3ManageUsers(WebDriver driver) {
        super(driver);
    }

    public boolean isCreateUserEnabled(){
       return linkCreateUser.isEnabled();
    }


    public void clickCreateUser(){
        linkCreateUser.click();
    }
}

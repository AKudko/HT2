package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class Page5Users extends AbstractSomePage{
    @FindBy(tagName = "tr")
    private WebElement tr;

    @FindBy(tagName = "td")
    private WebElement td;

    @FindBy(id = "yui-gen1-button")
    private WebElement yes_button;



    public Page5Users(WebDriver driver) {
        super(driver);
    }

    public boolean checkUser(String username){
        boolean result = false;
        List<WebElement> tableRows = driver.findElements(By.tagName("tr"));
        if (tableRows.isEmpty()) {
            return false;
        }
        WebElement tableRow = null;


       for(int j=0; j < tableRows.size(); j++){
           tableRow = tableRows.get(j);
           if (tableRow.getText().contains(username)){
                result = true;
            }
        }
        return result;
    }
    public void deleteUser(String username){
        List<WebElement> tableRows = driver.findElements(By.tagName("tr"));
        WebElement tableRow = null;

        for(int j=0; j < tableRows.size(); j++){
            tableRow = tableRows.get(j);
            if (tableRow.getText().contains(username)){
                tableRow.findElement(By.xpath("//td[4]/a[2]")).click();
               System.out.println("USER DELETED");
                break;
            }
        }

    }
    public void confirmDeleteUser(){
        yes_button.click();
    }
}

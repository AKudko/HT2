package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Collection;
import java.util.Iterator;

public class Page4CreateUser extends AbstractSomePage {
    @FindBy(xpath = "//form[@action='/securityRealm/createAccountByAdmin']")
    private WebElement form;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password1")
    private WebElement password1;

    @FindBy(name = "password2")
    private WebElement password2;

    @FindBy(name = "fullname")
    private WebElement fullname;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(xpath = "//button[@type='Create User']")
    private WebElement create_user_button;

    public Page4CreateUser(WebDriver driver) {
        super(driver);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Create User [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/addUser"))) {
            throw new IllegalStateException("Wrong site page! Not Create User.");
        }
    }

    // Заполнение имени юзера.
    public Page4CreateUser setUsername(String value) {
        username.clear();
        username.sendKeys(value);
        return this;
    }

    // Заполнение пароля.
    public Page4CreateUser setPassword1(String value) {
        password1.clear();
        password1.sendKeys(value);
        return this;
    }
    // Подтверждение  пароля.
    public Page4CreateUser setPassword2(String value) {
        password2.clear();
        password2.sendKeys(value);
        return this;
    }

    // Ввод полного имени.
    public Page4CreateUser setFullname(String value) {
        fullname.clear();
        fullname.sendKeys(value);
        return this;
    }
    // Ввод email.
    public Page4CreateUser setEmail(String value) {
        email.clear();
        email.sendKeys(value);
        return this;
    }
    // Заполнение всех полей формы.
    public Page4CreateUser setFields(String username, String password1, String password2, String fullname, String email) {
        setUsername(username);
        setPassword1(password1);
        setPassword2(password2);
        setFullname(fullname);
        setEmail(email);
        return this;
    }

    // Отправка данных из формы.
    public Page4CreateUser createUser() {
        create_user_button.click();
        return this;
    }

    // Обёртка для упрощения отправки данных.
    public Page4CreateUser createUserWithSetFields(String username, String password1, String password2, String fullname, String email) {
        setFields(username, password1, password2, fullname, email);
        return createUser();
    }

    public boolean isFormPresentForReal() {
       // Третье (самое убогое, почти за гранью запрещённого) решение -- работает в 100% случаев - надежность это оочень важно)

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Collection<WebElement> forms = driver.findElements(By.tagName("form"));
        if (forms.isEmpty()) {
            return false;
        }

        Iterator<WebElement> i = forms.iterator();
        boolean form_found = false;
        WebElement form_temp = null;

        while (i.hasNext()) {
            form_temp = i.next();
            if (
                    (driver.findElement(By.name("username")).getAttribute("type").equalsIgnoreCase("text")) &&
                    (driver.findElement(By.name("password1")).getAttribute("type").equalsIgnoreCase("password")) &&
                    (driver.findElement(By.name("password2")).getAttribute("type").equalsIgnoreCase("password")) &&
                    (driver.findElement(By.name("fullname")).getAttribute("type").equalsIgnoreCase("text")) &&
                    (driver.findElement(By.name("email")).getAttribute("type").equalsIgnoreCase("text")) &&
                    ((driver.findElement(By.id("yui-gen1-button")).getTagName().equalsIgnoreCase("button")) &&
                            (driver.findElement(By.id("yui-gen1-button")).getText().equalsIgnoreCase("Create User")))
               ){
               form_found = true;
                break;
            }
        }

        return form_found;
    }

    public boolean isFieldsEmpty (){
        if(
                ( username.getAttribute("value") != null)||
                        (password1.getAttribute("value") != null)||
                        (password2.getAttribute("value") != null)||
                        (fullname.getAttribute("value") != null)||
                        (email.getAttribute("value") != null)
                ){

            return false;
        }
        return true;
    }


}

package co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {

    public static final Target USERNAME = Target.the("username field").located(By.id("user-name"));
    public static final Target PASSWORD = Target.the("password field").located(By.id("password"));
    public static final Target LOGIN_BUTTON = Target.the("login button").located(By.id("login-button"));
    public static final Target ERROR_CONTAINER = Target.the("login error container").located(By.cssSelector("div.error-message-container"));

}

package co.edu.udea.certificacion.saucedemo.saucedemo.tasks;

import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.EnterText;
import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.ClickOn;
import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.Pause;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class IniciarSesion implements Task {

    private final String username;
    private final String password;

    public IniciarSesion(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static IniciarSesion conCredenciales(String username, String password) {
        return Tasks.instrumented(IniciarSesion.class, username, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                new EnterText(LoginPage.USERNAME, username),
                Pause.forConfiguredDuration(),
                new EnterText(LoginPage.PASSWORD, password),
                Pause.forConfiguredDuration(),
                ClickOn.the(LoginPage.LOGIN_BUTTON),
                Pause.forConfiguredDuration()
        );
    }
}

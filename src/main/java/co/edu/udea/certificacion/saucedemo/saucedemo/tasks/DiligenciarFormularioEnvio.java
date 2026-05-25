package co.edu.udea.certificacion.saucedemo.saucedemo.tasks;

import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.EnterText;
import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.ClickOn;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CheckoutPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class DiligenciarFormularioEnvio implements Task {

    private final String firstName;
    private final String lastName;
    private final String postalCode;

    public DiligenciarFormularioEnvio(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static DiligenciarFormularioEnvio con(String firstName, String lastName, String postalCode) {
        return Tasks.instrumented(DiligenciarFormularioEnvio.class, firstName, lastName, postalCode);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                new EnterText(CheckoutPage.FIRST_NAME, firstName),
                new EnterText(CheckoutPage.LAST_NAME, lastName),
                new EnterText(CheckoutPage.POSTAL_CODE, postalCode),
                ClickOn.the(CheckoutPage.CONTINUE_BUTTON)
        );
    }
}

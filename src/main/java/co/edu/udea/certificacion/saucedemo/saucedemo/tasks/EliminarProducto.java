package co.edu.udea.certificacion.saucedemo.saucedemo.tasks;

import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.ClickOn;
import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.Pause;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class EliminarProducto implements Task {

    private final String productName;

    public EliminarProducto(String productName) {
        this.productName = productName;
    }

    public static EliminarProducto llamado(String productName) {
        return Tasks.instrumented(EliminarProducto.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(CartPage.removeButtonFor(productName)),
                Pause.forConfiguredDuration()
        );
    }
}

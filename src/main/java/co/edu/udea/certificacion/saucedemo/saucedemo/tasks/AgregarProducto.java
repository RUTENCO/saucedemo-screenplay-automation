package co.edu.udea.certificacion.saucedemo.saucedemo.tasks;

import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.ClickOn;
import co.edu.udea.certificacion.saucedemo.saucedemo.interactions.Pause;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CatalogPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class AgregarProducto implements Task {

    private final String productName;

    public AgregarProducto(String productName) {
        this.productName = productName;
    }

    public static AgregarProducto llamado(String productName) {
        return Tasks.instrumented(AgregarProducto.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(CatalogPage.addButtonFor(productName)),
                Pause.forConfiguredDuration()
        );
    }
}

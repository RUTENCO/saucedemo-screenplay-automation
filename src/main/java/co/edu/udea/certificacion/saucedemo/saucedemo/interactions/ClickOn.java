package co.edu.udea.certificacion.saucedemo.saucedemo.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Hace clic en un elemento con pausa configurable previa.
 */
public class ClickOn implements Interaction {

    private final Target target;

    public ClickOn(Target target) {
        this.target = target;
    }

    public static ClickOn the(Target target) {
        return new ClickOn(target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Pause.forConfiguredDuration(),
                Click.on(target)
        );
    }

    @Override
    public String toString() {
        return "ClickOn: " + target.getName();
    }
}

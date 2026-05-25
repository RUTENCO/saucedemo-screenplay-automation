package co.edu.udea.certificacion.saucedemo.saucedemo.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Hace scroll hasta un elemento con pausa configurable previa.
 * Usa click en el elemento para forzar scroll automático.
 */
public class ScrollToElement implements Interaction {

    private final Target target;

    public ScrollToElement(Target target) {
        this.target = target;
    }

    public static ScrollToElement the(Target target) {
        return new ScrollToElement(target);
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
        return "ScrollToElement: " + target.getName();
    }
}

package co.edu.udea.certificacion.saucedemo.saucedemo.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Escribe texto en un campo con pausa configurable previa.
 */
public class EnterText implements Interaction {

    private final Target target;
    private final String value;

    public EnterText(Target target, String value) {
        this.target = target;
        this.value = value;
    }

    public static EnterText into(Target target, String value) {
        return new EnterText(target, value);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Pause.forConfiguredDuration(),
                Enter.theValue(value).into(target)
        );
    }

    @Override
    public String toString() {
        return "EnterText: '" + value + "' into " + target.getName();
    }
}

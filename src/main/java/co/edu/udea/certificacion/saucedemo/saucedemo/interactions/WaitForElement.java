package co.edu.udea.certificacion.saucedemo.saucedemo.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Espera a que un elemento sea visible antes de permitir siguientes interacciones.
 */
public class WaitForElement implements Interaction {

    private static final int DEFAULT_TIMEOUT_SECONDS = 10;
    private final Target target;
    private final int timeoutSeconds;

    public WaitForElement(Target target, int timeoutSeconds) {
        this.target = target;
        this.timeoutSeconds = timeoutSeconds;
    }

    public static WaitForElement until(Target target) {
        return new WaitForElement(target, DEFAULT_TIMEOUT_SECONDS);
    }

    public static WaitForElement until(Target target, int timeoutSeconds) {
        return new WaitForElement(target, timeoutSeconds);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(target, isVisible()).forNoMoreThan(timeoutSeconds).seconds()
        );
    }

    @Override
    public String toString() {
        return "WaitForElement: " + target.getName() + " (timeout: " + timeoutSeconds + "s)";
    }
}

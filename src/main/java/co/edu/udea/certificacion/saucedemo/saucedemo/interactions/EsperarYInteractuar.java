package co.edu.udea.certificacion.saucedemo.saucedemo.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EsperarYInteractuar implements Interaction {

    private static final long DEFAULT_DELAY_MS = 750L;

    public enum ActionType { CLICK, ENTER }

    private final Target target;
    private final ActionType actionType;
    private final String value;

    public EsperarYInteractuar(Target target, ActionType actionType, String value) {
        this.target = target;
        this.actionType = actionType;
        this.value = value;
    }

    public static EsperarYInteractuar clickOn(Target target) {
        return new EsperarYInteractuar(target, ActionType.CLICK, null);
    }

    public static EsperarYInteractuar enterValue(Target target, String value) {
        return new EsperarYInteractuar(target, ActionType.ENTER, value);
    }

    private long configuredDelay() {
        String prop = System.getProperty("interaction.delay.ms");
        if (prop == null || prop.isEmpty()) {
            prop = System.getenv("INTERACTION_DELAY_MS");
        }
        try {
            return prop == null ? DEFAULT_DELAY_MS : Long.parseLong(prop);
        } catch (NumberFormatException e) {
            return DEFAULT_DELAY_MS;
        }
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        long delay = configuredDelay();
        try {
            if (delay > 0) Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (actionType == ActionType.CLICK) {
            actor.attemptsTo(
                    WaitUntil.the(target, isVisible()).forNoMoreThan(10).seconds(),
                    Click.on(target)
            );
        } else if (actionType == ActionType.ENTER) {
            actor.attemptsTo(
                    WaitUntil.the(target, isVisible()).forNoMoreThan(10).seconds(),
                    Enter.theValue(value).into(target)
            );
        }
    }

    @Override
    public String toString() {
        return "EsperarYInteractuar: " + actionType + " -> " + target.getName();
    }
}

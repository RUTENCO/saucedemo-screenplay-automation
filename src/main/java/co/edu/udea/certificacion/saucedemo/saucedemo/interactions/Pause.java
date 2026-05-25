package co.edu.udea.certificacion.saucedemo.saucedemo.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

/**
 * Pausa configurable entre interacciones para permitir auditoría visual.
 * Configurable vía: -Dinteraction.delay.ms=ms o INTERACTION_DELAY_MS env var.
 */
public class Pause implements Interaction {

    private static final long DEFAULT_DELAY_MS = 750L;
    private final long delayMs;

    public Pause(long delayMs) {
        this.delayMs = delayMs;
    }

    public static Pause forConfiguredDuration() {
        return new Pause(configuredDelay());
    }

    public static Pause forMillis(long delayMs) {
        return new Pause(delayMs);
    }

    private static long configuredDelay() {
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
        try {
            if (delayMs > 0) Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return "Pause for " + delayMs + "ms";
    }
}

package co.edu.udea.certificacion.saucedemo.saucedemo.questions;

import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CheckoutPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ElSubtotalDeLaCompra implements Question<Double> {

    public static ElSubtotalDeLaCompra visible() {
        return new ElSubtotalDeLaCompra();
    }

    @Override
    public Double answeredBy(Actor actor) {
        String text = Text.of(CheckoutPage.SUBTOTAL_LABEL).answeredBy(actor);
        String numericPart = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (numericPart.isEmpty()) {
            return 0.0;
        }
        return Double.valueOf(numericPart);
    }
}
package co.edu.udea.certificacion.saucedemo.saucedemo.questions;

import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CatalogPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class LaCantidadDeProductos implements Question<Integer> {

    public static LaCantidadDeProductos displayed() {
        return new LaCantidadDeProductos();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        try {
            return Integer.valueOf(Text.of(CatalogPage.CART_BADGE).answeredBy(actor).trim());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }
}

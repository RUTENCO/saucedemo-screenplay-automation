package co.edu.udea.certificacion.saucedemo.saucedemo.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class ElMensajeDeError implements Question<String> {

    private final Target target;

    public ElMensajeDeError(Target target) {
        this.target = target;
    }

    public static ElMensajeDeError visibleIn(Target target) {
        return new ElMensajeDeError(target);
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(target).answeredBy(actor);
    }
}

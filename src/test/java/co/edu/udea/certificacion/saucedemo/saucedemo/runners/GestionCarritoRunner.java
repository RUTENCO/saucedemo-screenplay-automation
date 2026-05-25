package co.edu.udea.certificacion.saucedemo.saucedemo.runners;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/gestion_carrito.feature",
        glue = "co.edu.udea.certificacion.saucedemo.saucedemo.stepdefinitions",
        plugin = {"pretty"}
)
public class GestionCarritoRunner {

}

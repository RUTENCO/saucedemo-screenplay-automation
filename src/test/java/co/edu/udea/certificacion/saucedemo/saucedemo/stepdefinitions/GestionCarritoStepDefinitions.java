package co.edu.udea.certificacion.saucedemo.saucedemo.stepdefinitions;

import co.edu.udea.certificacion.saucedemo.saucedemo.questions.ElMensajeDeError;
import co.edu.udea.certificacion.saucedemo.saucedemo.questions.ElSubtotalDeLaCompra;
import co.edu.udea.certificacion.saucedemo.saucedemo.questions.LaCantidadDeProductos;
import co.edu.udea.certificacion.saucedemo.saucedemo.tasks.AgregarProducto;
import co.edu.udea.certificacion.saucedemo.saucedemo.tasks.EliminarProducto;
import co.edu.udea.certificacion.saucedemo.saucedemo.tasks.IniciarSesion;
import co.edu.udea.certificacion.saucedemo.saucedemo.tasks.DiligenciarFormularioEnvio;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CheckoutPage;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CartPage;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.CatalogPage;
import co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces.LoginPage;
import co.edu.udea.certificacion.saucedemo.saucedemo.utils.UrlProvider;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class GestionCarritoStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After
    public void tearDown() {
    }

    @Given("the application is open")
    public void the_application_is_open() {
        theActorCalled("User").wasAbleTo(net.serenitybdd.screenplay.actions.Open.url(co.edu.udea.certificacion.saucedemo.saucedemo.utils.UrlProvider.getBaseUrl()));
    }

    @When("the User attempts to login with username {string} and password {string}")
    public void the_user_attempts_to_login(String username, String password) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.conCredenciales(username, password));
    }

    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String expected) {
        String text = ElMensajeDeError.visibleIn(LoginPage.ERROR_CONTAINER).answeredBy(theActorInTheSpotlight());
        assertThat(text, containsString(expected));
    }

    @Given("the User logs in with username {string} and password {string}")
    public void the_user_logs_in(String username, String password) {
        theActorCalled("User").wasAbleTo(net.serenitybdd.screenplay.actions.Open.url(co.edu.udea.certificacion.saucedemo.saucedemo.utils.UrlProvider.getBaseUrl()));
        theActorInTheSpotlight().attemptsTo(IniciarSesion.conCredenciales(username, password));
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CatalogPage.INVENTORY_LIST, isVisible()).forNoMoreThan(10).seconds());
    }

    @When("the User adds the product {string} to the cart")
    public void the_user_adds_the_product_to_the_cart(String product) {
        theActorInTheSpotlight().attemptsTo(AgregarProducto.llamado(product));
    }

    @When("the User removes the product {string} from the cart")
    public void the_user_removes_the_product_from_the_cart(String product) {
        // go to cart first
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(net.serenitybdd.screenplay.targets.Target.the("cart icon").locatedBy("#shopping_cart_container a")));
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CartPage.removeButtonFor(product), isVisible()).forNoMoreThan(10).seconds());
        theActorInTheSpotlight().attemptsTo(EliminarProducto.llamado(product));
        theActorInTheSpotlight().attemptsTo(Open.url(UrlProvider.getBaseUrl() + "inventory.html"));
    }

    @Then("the cart badge should show {string}")
    public void the_cart_badge_should_show(String expectedCount) {
        int count = LaCantidadDeProductos.displayed().answeredBy(theActorInTheSpotlight());
        assertThat(count, is(Integer.valueOf(expectedCount.trim())));
    }

    @When("the User continues checkout with first name {string}, last name {string} and postal code {string}")
    public void the_user_continues_checkout_with_shipping_data(String firstName, String lastName, String postalCode) {
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(net.serenitybdd.screenplay.targets.Target.the("cart icon").locatedBy("#shopping_cart_container a")));
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CartPage.CHECKOUT_BUTTON, isVisible()).forNoMoreThan(10).seconds());
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(CartPage.CHECKOUT_BUTTON));
        theActorInTheSpotlight().attemptsTo(DiligenciarFormularioEnvio.con(firstName, lastName, postalCode));
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CheckoutPage.SUBTOTAL_LABEL, isVisible()).forNoMoreThan(10).seconds());
    }

    @Then("the order subtotal should be greater than {string}")
    public void the_order_subtotal_should_be_greater_than(String minimumAmount) {
        double subtotal = ElSubtotalDeLaCompra.visible().answeredBy(theActorInTheSpotlight());
        assertThat(subtotal, org.hamcrest.Matchers.greaterThan(Double.valueOf(minimumAmount)));
    }

    @When("the User finishes the purchase")
    public void the_user_finishes_the_purchase() {
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CheckoutPage.FINISH_BUTTON, isVisible()).forNoMoreThan(10).seconds());
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(CheckoutPage.FINISH_BUTTON));
    }

    @Then("the order confirmation should contain {string}")
    public void the_order_confirmation_should_contain(String expectedMessage) {
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CheckoutPage.COMPLETE_HEADER, isVisible()).forNoMoreThan(10).seconds());
        String message = net.serenitybdd.screenplay.questions.Text.of(CheckoutPage.COMPLETE_HEADER).answeredBy(theActorInTheSpotlight());
        assertThat(message, containsString(expectedMessage));
    }

    @When("the User goes to checkout without providing shipping details")
    public void the_user_goes_to_checkout_without_providing_shipping_details() {
        // Ensure cart has at least one item
        theActorInTheSpotlight().attemptsTo(AgregarProducto.llamado("Sauce Labs Backpack"));
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(net.serenitybdd.screenplay.targets.Target.the("cart icon").locatedBy("#shopping_cart_container a")));
        theActorInTheSpotlight().attemptsTo(WaitUntil.the(CartPage.CHECKOUT_BUTTON, isVisible()).forNoMoreThan(10).seconds());
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(CartPage.CHECKOUT_BUTTON));
        // Click continue without filling
        theActorInTheSpotlight().attemptsTo(net.serenitybdd.screenplay.actions.Click.on(CheckoutPage.CONTINUE_BUTTON));
    }

}

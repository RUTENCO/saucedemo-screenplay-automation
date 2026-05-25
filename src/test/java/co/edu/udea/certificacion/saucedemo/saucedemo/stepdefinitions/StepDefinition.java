package co.edu.udea.certificacion.saucedemo.saucedemo.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class StepDefinition {

    @Before
    public void config(){
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("user");
    }

    @Given("This method is responsible for parameterizing the instantiation of chromedriver")    public void thisMethodIsResponsibleForParameterizingTheInstantiationOfChromedriver() {         OnStage.theActorInTheSpotlight().wasAbleTo(Open.browserOn().thePageNamed("pages.swaglabsUrl"));    }

}

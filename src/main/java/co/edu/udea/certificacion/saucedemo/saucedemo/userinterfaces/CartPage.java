package co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.util.Locale;

public class CartPage {

    public static final Target CHECKOUT_BUTTON = Target.the("checkout button").located(By.id("checkout"));

    public static Target removeButtonFor(String productName) {
        return Target.the("remove button for " + productName)
                .located(By.cssSelector(String.format("[data-test='remove-%s']", productKey(productName))));
    }

    private static String productKey(String productName) {
        return productName.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

}

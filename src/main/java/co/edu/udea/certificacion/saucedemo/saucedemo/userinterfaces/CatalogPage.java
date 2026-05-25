package co.edu.udea.certificacion.saucedemo.saucedemo.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.util.Locale;

public class CatalogPage {

    public static final Target INVENTORY_LIST = Target.the("inventory list").located(By.className("inventory_list"));
    public static final Target INVENTORY_CONTAINER = Target.the("inventory container").located(By.id("inventory_container"));
    public static final Target MENU_BUTTON = Target.the("menu button").located(By.id("react-burger-menu-btn"));
    public static final Target RESET_APP_STATE_LINK = Target.the("reset app state link").located(By.id("reset_sidebar_link"));
    public static final Target CART_BADGE = Target.the("cart badge").located(By.className("shopping_cart_badge"));

    public static Target addButtonFor(String productName) {
        return Target.the("add button for " + productName)
                .located(By.cssSelector(String.format("[data-test='add-to-cart-%s']", productKey(productName))));
    }

    private static String productKey(String productName) {
        return productName.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

}

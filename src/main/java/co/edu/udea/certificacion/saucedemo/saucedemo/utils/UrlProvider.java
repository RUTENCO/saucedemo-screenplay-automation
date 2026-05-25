package co.edu.udea.certificacion.saucedemo.saucedemo.utils;

public final class UrlProvider {

    private UrlProvider() {}

    public static String getBaseUrl() {
        // 1) System property: -DswaglabsUrl=...
        String url = System.getProperty("swaglabsUrl");
        if (url != null && !url.isEmpty()) return url.trim();

        // 2) Environment variable: SWAGLABS_URL
        url = System.getenv("SWAGLABS_URL");
        if (url != null && !url.isEmpty()) return url.trim();

        // 3) Fallback to default
        return "https://www.saucedemo.com/";
    }
}

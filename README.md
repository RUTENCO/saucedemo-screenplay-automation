# Taller Screenplay Pattern + Serenity BDD - Sauce Demo

## 📋 Descripción

Batería de pruebas automatizadas siguiendo el **Screenplay Pattern** y principios **FIRST** (Fast, Independent, Repeatable, Self-validating, Timely) usando:
- **Java 11+**
- **Serenity BDD 4.1.0**
- **Cucumber (Gherkin)**
- **Selenium WebDriver**

## 🏗 Arquitectura: Patrón Screenplay

### Estructura de Directorios

```
src/
├── main/java/co/edu/udea/certificacion/saucedemo/saucedemo/
│   ├── interactions/          # Acciones granulares y modulares
│   │   ├── Pause.java                    # Pausa configurable (delay auditoría)
│   │   ├── ClickOn.java                  # Click con pausa
│   │   ├── EnterText.java                # Entrada de texto con pausa
│   │   ├── WaitForElement.java           # Espera elementos visibles
│   │   └── ScrollToElement.java          # Scroll a elemento
│   ├── tasks/                 # Tareas de negocio (orquestación)
│   │   ├── IniciarSesion.java            # Login con credenciales
│   │   ├── AgregarProducto.java          # Agregar producto al carrito
│   │   ├── EliminarProducto.java         # Remover producto del carrito
│   │   └── DiligenciarFormularioEnvio.java # Llenar formulario checkout
│   ├── questions/             # Aserciones limpias
│   │   ├── ElMensajeDeError.java         # Extrae mensajes de error
│   │   └── LaCantidadDeProductos.java    # Obtiene cantidad del carrito
│   ├── userinterfaces/        # Mapeos de elementos UI
│   │   ├── LoginPage.java
│   │   ├── CatalogPage.java
│   │   ├── CartPage.java
│   │   └── CheckoutPage.java
│   └── utils/
│       └── UrlProvider.java              # Proveedor centralizado de URL
│
└── test/
    ├── java/co/edu/udea/certificacion/saucedemo/saucedemo/
    │   ├── stepdefinitions/
    │   │   └── GestionCarritoStepDefinitions.java  # Glue entre Gherkin y código
    │   └── runners/
    │       └── GestionCarritoRunner.java           # Configuración Cucumber/Serenity
    └── resources/
        ├── features/
        │   └── gestion_carrito.feature  # Escenarios en Gherkin
        └── serenity.conf               # Configuración Serenity
```

## 🎯 Escenarios Cubiertos

### Feature: `gestion_carrito.feature`

1. **Login Fallido** (`@login-failure`)
   - Valida manejo de credenciales inválidas
   - Verifica mensaje de error visible

2. **Flujo E2E Completo** (`@happy-path`)
   - Login válido → Agregar producto → Remover producto → Agregar 2 productos
   - Verifica contador del carrito = 2

3. **Checkout Bloqueado** (`@checkout-failure`)
   - Intenta continuar sin llenar datos de envío
   - Valida error: "First Name is required"

## 🚀 Cómo Ejecutar

### Compilación

```powershell
.\gradlew clean build
```

### Ejecutar Pruebas (sin delay)

```powershell
.\gradlew clean test
```

### Ejecutar con Delay Visible (Auditoría del Profesor)

**Delay de 1 segundo entre interacciones:**
```powershell
.\gradlew clean test -Dinteraction.delay.ms=1000
```

**Delay de 2 segundos (más lento para observación detallada):**
```powershell
.\gradlew clean test -Dinteraction.delay.ms=2000
```

### URL Configurable

La URL se lee de `serenity.conf` (actualmente `https://www.saucedemo.com/`).
Para ejecutar en URL distinta:

```powershell
.\gradlew clean test -DswaglabsUrl="https://www.saucedemo.com/"
```

## 📊 Reportes Serenity

Serenity genera los reportes automáticamente cada vez que ejecutas las pruebas con Gradle, por ejemplo con `clean test` o `clean build`.

### Generación del reporte

```powershell
.\gradlew clean test
```

Después de la ejecución, los reportes se generan en:

```
target/site/serenity/index.html
```

Para abrirlos rápidamente desde PowerShell:

```powershell
Start-Process "target/site/serenity/index.html"
```

Ábrelo en el navegador para ver:
- Resumen de pruebas (passes/fails)
- Detalles stepwise por escenario
- Capturas de pantalla de fallos
- Timeline de ejecución

## 🎨 Principios SOLID Aplicados

| Principio | Implementación |
|-----------|---|
| **S**ingle Responsibility | Cada `Interaction` hace 1 cosa (Click, Enter, Wait, Pause) |
| **O**pen/Closed | Nuevas `Questions` y `Tasks` sin modificar existentes |
| **L**iskov Substitution | Todas las `Interactions` implementan el contrato común |
| **I**nterface Segregation | `Target`, `Question<T>`, `Interaction` interfacesminimalistas |
| **D**ependency Inversion | Las `Tasks` dependen de abstracciones (`Interaction`) no implementaciones |

## 🔧 Configuración Delay/Sleep Innovador

La interacción `Pause.java` permite auditoría visual pausada:

```java
// En cualquier Task:
actor.attemptsTo(
    Pause.forConfiguredDuration(),  // Respeta -Dinteraction.delay.ms
    Click.on(target)                // Luego hace clic
);
```

**Variables de entorno soportadas:**
- `-Dinteraction.delay.ms=750` (Java system property) 
- `INTERACTION_DELAY_MS=750` (Environment variable)
- Default: 750ms si no se especifica

## 📝 Ejemplo de Flujo (Act/Assert)

```gherkin
Scenario: E2E cart happy path
  Given the User logs in with username "standard_user" and password "secret_sauce"
  When the User adds the product "Sauce Labs Backpack" to the cart
  Then the cart badge should show "1"
```

**Código correspondiente:**

```java
@Given("the User logs in with username {string} and password {string}")
public void the_user_logs_in(String username, String password) {
    theActorCalled("User")
        .wasAbleTo(Open.url(UrlProvider.getBaseUrl()));
    theActorInTheSpotlight()
        .attemptsTo(IniciarSesion.conCredenciales(username, password));
}

@When("the User adds the product {string} to the cart")
public void the_user_adds_product(String product) {
    theActorInTheSpotlight()
        .attemptsTo(AgregarProducto.llamado(product));
}

@Then("the cart badge should show {string}")
public void cart_badge_shows(String expectedCount) {
    int count = LaCantidadDeProductos.displayed()
        .answeredBy(theActorInTheSpotlight());
    assertThat(count, is(Integer.parseInt(expectedCount)));
}
```

## 🧪 Datos de Prueba (Ejemplos de la Feature)

| Username | Password | Estado |
|----------|----------|--------|
| `standard_user` | `secret_sauce` | ✅ Válido |
| `invalidUser` | `wrongpass` | ❌ Falla (para test negativo) |

Productos disponibles en Sauce Demo:
- Sauce Labs Backpack
- Sauce Labs Bike Light
- Sauce Labs Bolt T-Shirt
- Sauce Labs Fleece Jacket
- Sauce Labs Onesie
- Test.allTheThings() T-Shirt

## 📌 Notas Importantes

1. **FIRST Compliance**
   - ✅ **Fast**: Sin esperas innecesarias (default 750ms, configurable)
   - ✅ **Independent**: Cada escenario abre navegador limpio (Background)
   - ✅ **Repeatable**: No hay dependencia entre escenarios
   - ✅ **Self-validating**: Assertions declarativas con Hamcrest
   - ✅ **Timely**: Pruebas cercanas al ciclo de desarrollo

2. **Selectores Web**
   - LoginPage: `id="user-name"`, `id="password"`, `id="login-button"`
   - CartPage: Selectores dinámicos con `normalize-space(text())` para productos
   - CheckoutPage: `id="first-name"`, `id="last-name"`, `id="postal-code"`

3. **Delay Configurable**
   - Permite observación pausada durante la calificación del profesor
   - Recomendado: `-Dinteraction.delay.ms=1000` o `2000` para auditoría visual

## 📚 Recursos

- [Serenity BDD Docs](https://serenity-bdd.info/)
- [Cucumber/Gherkin](https://cucumber.io/docs/gherkin/)
- [Screenplay Pattern](https://serenity-bdd.info/docs/screenplay/screenplay_pattern)
- [Sauce Labs Demo](https://www.saucedemo.com/)

---

**Autor**: Taller Automatización de Pruebas - Screenplay Pattern
**Fecha**: Mayo 2026
**Plugin Base**: Bancolombia ScreenplayArchitecture

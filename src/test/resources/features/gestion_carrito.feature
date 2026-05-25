Feature: Cart management on Sauce Demo
  In order to validate the shopping cart flow
  As a registered user
  I want to add, remove products and complete checkout with proper validations

  Background:
    Given the application is open

  @login-failure
  Scenario Outline: Failed login with invalid credentials
    When the User attempts to login with username "<username>" and password "<password>"
    Then the error message should contain "<errorMessage>"

    Examples:
      | username    | password  | errorMessage                        |
      | error_user  | wrongpass | Epic sadface: Username and password do not match any user in this service |

  @happy-path @e2e
  Scenario Outline: E2E cart happy path - add and remove items
    Given the User logs in with username "<username>" and password "<password>"
    When the User adds the product "<product>" to the cart
    And the User removes the product "<product>" from the cart
    And the User adds the product "<firstProduct>" to the cart
    And the User adds the product "<secondProduct>" to the cart
    Then the cart badge should show "<expectedCount>"
    When the User continues checkout with first name "<firstName>", last name "<lastName>" and postal code "<postalCode>"
    Then the order subtotal should be greater than "0"
    When the User finishes the purchase
    Then the order confirmation should contain "<confirmationMessage>"

    Examples:
      | username      | password      | product                | firstProduct               | secondProduct              | expectedCount | firstName | lastName | postalCode | confirmationMessage         |
      | standard_user | secret_sauce  | Sauce Labs Backpack    | Sauce Labs Bike Light      | Sauce Labs Bolt T-Shirt    | 2             | John      | Doe      | 12345      | Thank you for your order!   |

  @checkout-failure
  Scenario Outline: Checkout blocked by empty shipping fields
    Given the User logs in with username "<username>" and password "<password>"
    When the User goes to checkout without providing shipping details
    Then the error message should contain "<errorMessage>"

    Examples:
      | username      | password      | errorMessage                     |
      | standard_user | secret_sauce  | First Name is required           |

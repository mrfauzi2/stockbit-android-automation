Feature: Shopping and Checkout Flow
  As a logged-in user
  I want to buy a product
  So that I can receive the item

  Background:
    Given I open the application
    And I navigate to the login page
    When I input username "bod@example.com" and password "10203040"
    And I tap the login button

  @checkout
  Scenario: Successful checkout flow
    Given I am on the product catalog page
    When I select the product "Sauce Lab Back Packs"
    And I tap the "Add to Cart" button
    And I tap the "Cart Icon" button
    And I tap the "Proceed To Checkout" button
    And I enter shipping details
      | name    | Budi Santoso          |
      | address | Jl. Jendral Sudirman  |
      | city    | Jakarta               |
      | zip     | 12345                 |
      | country | Indonesia             |
    And I enter payment details
      | name    | Budi Santoso          |
      | card    | 1234567890123456      |
      | exp     | 03/25                 |
      | cvv     | 123                   |
    # PERBAIKAN DI BAWAH INI (Pakai tanda kutip)
    And I tap the "Place Order" button
    Then I should see the checkout complete message "Checkout Complete"

  @negative @checkout
    Scenario: User cannot proceed to payment with empty shipping details
      Given I am on the product catalog page
      When I select the product "Sauce Lab Back Packs"
      And I tap the "Add to Cart" button
      And I tap the "Cart Icon" button
      And I tap the "Proceed To Checkout" button
      And I tap the "Place Order" button without filling data
      Then I should see error messages on the checkout form
Feature: Login Functionality on Sauce Labs App

  Background:
    Given I open the application
    And I navigate to the login page

  @positive
  Scenario: Successful login with valid credentials
    When I input username "bod@example.com" and password "10203040"
    And I tap the login button
    Then I should be redirected to the catalog page

  @negative
  Scenario: Failed login with locked out user
    When I input username "alice@example.com" and password "wrongpass"
    And I tap the login button
    Then I should see an error message "Sorry, this user has been locked out."
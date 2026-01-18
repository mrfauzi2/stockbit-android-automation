package com.stockbit.steps;

import com.stockbit.pages.LoginPage;
import com.stockbit.utils.Hooks;

import com.stockbit.pages.CatalogPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    // Panggil Driver dari Hooks
    LoginPage loginPage = new LoginPage(Hooks.driver);
    CatalogPage catalogPage = new CatalogPage(Hooks.driver);

    @Given("I open the application")
    public void iOpenTheApplication() {
        // Aplikasi otomatis terbuka oleh Hooks @Before
    }

    @And("I navigate to the login page")
    public void iNavigateToTheLoginPage() {
        catalogPage.goToLoginPage();
    }

    @When("I input username {string} and password {string}")
    public void iInputUsernameAndPassword(String user, String pass) {
        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
    }

    @And("I tap the login button")
    public void iTapTheLoginButton() {
        loginPage.clickLoginBtn();
    }

    @Then("I should be redirected to the catalog page")
    public void iShouldBeRedirectedToTheCatalogPage() {
        String actualTitle = catalogPage.getPageTitle();
        Assert.assertEquals(actualTitle, "Products");
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedMsg) {
        String actualMsg = loginPage.getErrorMessage();
        Assert.assertTrue(actualMsg.contains(expectedMsg));
    }
}
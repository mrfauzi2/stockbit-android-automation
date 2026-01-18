package com.stockbit.steps;

import com.stockbit.pages.CatalogPage;
import com.stockbit.pages.CheckoutPage;
import com.stockbit.utils.Hooks;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.Map;

public class ShoppingSteps {

    // 1. HANYA DEKLARASI (Jangan di-new di sini!)
    CatalogPage catalogPage;
    CheckoutPage checkoutPage;

    @Given("I am on the product catalog page")
    public void iAmOnTheProductCatalogPage() {
        // 2. INISIALISASI DI SINI (Saat ini driver sudah pasti hidup dari Hooks)
        catalogPage = new CatalogPage(Hooks.driver);
        checkoutPage = new CheckoutPage(Hooks.driver);

        // Validasi simpel
        Assert.assertEquals(catalogPage.getPageTitle(), "Products");
    }

    @When("I select the product {string}")
    public void iSelectTheProduct(String productName) {
        catalogPage.selectProduct(productName);
    }

    @And("I tap the {string} button")
    public void iTapTheButton(String btnName) {
        // Menggunakan switch case agar lebih rapi & handle huruf besar/kecil
        switch (btnName.toLowerCase()) {
            case "add to cart":
                catalogPage.clickAddToCart();
                break;
            case "cart icon":
                catalogPage.clickCartIcon();
                break;
            case "proceed to checkout":
                catalogPage.clickProceedToCheckout();
                break;
            case "place order":
                checkoutPage.clickPlaceOrder();
                break;
            default:
                throw new IllegalArgumentException("Button not defined: " + btnName);
        }
    }

    @And("I enter shipping details")
    public void iEnterShippingDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        checkoutPage.inputShipping(
                data.get("name"),
                data.get("address"),
                data.get("city"),
                data.get("zip"),
                data.get("country")
        );
    }

    @And("I enter payment details")
    public void iEnterPaymentDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        checkoutPage.inputPayment(
                data.get("name"),
                data.get("card"),
                data.get("exp"),
                data.get("cvv")
        );
    }

    @Then("I should see the checkout complete message {string}")
    public void iShouldSeeTheCheckoutCompleteMessage(String expectedMsg) {
        String actualMsg = checkoutPage.getCompleteMessage();
        Assert.assertEquals(actualMsg, expectedMsg);
    }
}
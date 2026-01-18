package com.stockbit.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CatalogPage {
    AndroidDriver driver;
    WebDriverWait wait;

    // --- LOCATORS ---

    // 1. Navigation & Header
    By menuIcon = AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV");
    By loginMenuItem = AppiumBy.xpath("//android.widget.TextView[@text='Log In']");
    By pageTitle = AppiumBy.xpath("//android.widget.TextView[@text='Products']");

    // 2. Product Detail Elements
    // Menggunakan XPath teks "Add to cart" agar lebih fleksibel
    By addToCartBtn = AppiumBy.xpath("//android.widget.Button[@text='Add to cart']");

    // 3. Cart & Checkout Elements
    By cartIcon = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV");
    By proceedBtn = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt");

    public CatalogPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // --- METHODS ---

    public void goToLoginPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuIcon)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginMenuItem)).click();
    }

    public String getPageTitle() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
        } catch (Exception e) {
            return "Title Not Found";
        }
    }

    public void selectProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));

        // --- STRATEGI BARU (Scroll & Click Image) ---
        // Kita menggunakan .descriptionContains() karena atribut 'content-desc'
        // menempel pada IMAGE (Gambar), bukan pada teks.
        // Ini akan memastikan yang diklik adalah gambarnya.

        String automatorString = "new UiScrollable(new UiSelector().scrollable(true))" +
                ".setAsVerticalList()" +
                ".setMaxSearchSwipes(5)" +
                ".scrollIntoView(new UiSelector().descriptionContains(\"" + productName + "\"))";

        WebElement productImage = driver.findElement(AppiumBy.androidUIAutomator(automatorString));
        productImage.click();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartBtn)).click();
    }

    public void clickCartIcon() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon)).click();
    }

    public void clickProceedToCheckout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(proceedBtn)).click();
    }
}
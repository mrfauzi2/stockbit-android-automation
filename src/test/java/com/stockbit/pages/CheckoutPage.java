package com.stockbit.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    AndroidDriver driver;
    WebDriverWait wait;

    // --- LOCATORS: SHIPPING ---
    By fullNameInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameET");
    By addressInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ET");
    By cityInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityET");
    By zipInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipET");
    By countryInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryET");
    By toPaymentBtn = AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    // --- LOCATORS: RESCUE LOGIN (Jika sesi habis) ---
    By loginUserRescue = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    By loginPassRescue = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
    By loginBtnRescue  = AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn");

    // --- LOCATORS: PAYMENT ---
    By cardNameInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    By cardNumberInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cardNumberET");
    By expDateInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/expirationDateET");
    By cvvInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/securityCodeET");
    By reviewOrderBtn = AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    // --- LOCATORS: FINAL REVIEW ---
    By placeOrderBtn = AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    // Locator Judul Sukses (Menggunakan ID agar stabil)
    By checkoutCompleteTitle = AppiumBy.id("com.saucelabs.mydemoapp.android:id/completeTV");

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // --- METHODS ---

    public void inputShipping(String name, String address, String city, String zip, String country) {
        try {
            // Cek apakah halaman shipping muncul dalam 3 detik?
            WebDriverWait fastWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            fastWait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
        } catch (Exception e) {
            // Jika tidak, cek apakah terlempar ke halaman Login?
            System.out.println("Shipping tidak muncul. Mengecek Login Page...");
            try {
                if (driver.findElement(loginBtnRescue).isDisplayed()) {
                    System.out.println("Login Ulang Otomatis...");
                    driver.findElement(loginUserRescue).sendKeys("bod@example.com");
                    driver.findElement(loginPassRescue).sendKeys("10203040");
                    driver.findElement(loginBtnRescue).click();
                }
            } catch (Exception ex) {
                // Ignore jika bukan halaman login
            }
        }

        // Lanjut input shipping
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput)).sendKeys(name);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(zipInput).sendKeys(zip);
        driver.findElement(countryInput).sendKeys(country);
        driver.findElement(toPaymentBtn).click();
    }

    public void inputPayment(String name, String card, String exp, String cvv) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardNameInput)).sendKeys(name);
        driver.findElement(cardNumberInput).sendKeys(card);
        driver.findElement(expDateInput).sendKeys(exp);
        driver.findElement(cvvInput).sendKeys(cvv);
        driver.findElement(reviewOrderBtn).click();
    }

    // ✅ Method ini WAJIB ADA agar tidak error 'cannot find symbol'
    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderBtn)).click();
    }

    // ✅ Method ini untuk verifikasi pesan sukses
    public String getCompleteMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutCompleteTitle)).getText();
    }
}
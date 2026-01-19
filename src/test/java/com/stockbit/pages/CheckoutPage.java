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

    // --- TAMBAHAN LOCATOR ERROR ---
    By fullNameError = AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameErrorTV");
    By addressError = AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ErrorTV");
    By cityError = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityErrorTV");
    By zipError = AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipErrorTV");
    By countryError = AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryErrorTV");

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // =========================================================================
    // HELPER: RESCUE LOGIC (Dipisahkan agar bisa dipakai di Positif & Negatif)
    // =========================================================================
    private void ensureUserIsOnCheckoutPage() {
        try {
            // Cek cepat: Apakah form nama ada? (Artinya kita aman di Checkout Page)
            WebDriverWait fastWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            fastWait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
        } catch (Exception e) {
            // Jika tidak ada, curiga kita terlempar ke Login Page
            System.out.println("⚠️ Halaman Checkout tidak ditemukan. Mengecek Login Page...");
            try {
                if (driver.findElement(loginBtnRescue).isDisplayed()) {
                    System.out.println("🔄 Sesi habis. Melakukan Login Ulang Otomatis...");

                    driver.findElement(loginUserRescue).sendKeys("bod@example.com");
                    driver.findElement(loginPassRescue).sendKeys("10203040");
                    driver.findElement(loginBtnRescue).click();

                    // Tunggu loading balik ke checkout (Hard wait sebentar untuk transisi)
                    Thread.sleep(2000);
                }
            } catch (Exception ex) {
                // Bukan halaman login juga? Biarkan flow lanjut dan error secara natural.
            }
        }
    }

    // =========================================================================
    // PUBLIC METHODS
    // =========================================================================

    public void inputShipping(String name, String address, String city, String zip, String country) {
        // 1. Pastikan kita ada di halaman Checkout (Login ulang jika perlu)
        ensureUserIsOnCheckoutPage();

        // 2. Lanjut input shipping
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput)).sendKeys(name);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(zipInput).sendKeys(zip);
        driver.findElement(countryInput).sendKeys(country);

        // 3. Gunakan method klik yang aman (pakai scroll)
        clickToPaymentButtonOnly();
    }

    public void clickToPaymentButtonOnly() {
        // 1. Pastikan kita ada di halaman Checkout (Login ulang jika perlu)
        // Ini penting untuk Negative Test jika sesi habis pas mau klik
        ensureUserIsOnCheckoutPage();

        try {
            driver.hideKeyboard(); // Tutup keyboard biar lega
        } catch (Exception e) {}

        // SCROLL OTOMATIS cari tombol ID 'paymentBtn'
        try {
            String scrollScript = "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/paymentBtn\"));";
            driver.findElement(AppiumBy.androidUIAutomator(scrollScript));
        } catch (Exception e) {
            // Abaikan kalau tombol sudah kelihatan tanpa scroll
        }

        // Klik tombolnya
        wait.until(ExpectedConditions.visibilityOfElementLocated(toPaymentBtn)).click();
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

    public boolean isErrorDisplayed() {
        try {
            // Cek apakah minimal satu error muncul (misal di Nama)
            return wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
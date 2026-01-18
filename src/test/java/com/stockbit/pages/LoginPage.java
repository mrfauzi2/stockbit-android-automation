package com.stockbit.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    AndroidDriver driver;
    WebDriverWait wait;

    // Locators (Menggunakan ID dari aplikasi Sauce Labs)
    By usernameInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    By passwordInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
    By loginButton = AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn");
    By errorMessage = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV"); // Contoh ID error

    // Constructor
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginBtn() {
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
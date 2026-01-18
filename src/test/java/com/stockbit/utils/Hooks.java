package com.stockbit.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Hooks {

    public static AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("emulator-5554");

        // Path APK kamu
        options.setApp("E:/AndroidStudio/stockbit-android-automation/stockbit-android-automation/apps/mda-1.0.13-15.apk");

        // --- FIX PENTING (Tambahkan ini) ---
        // Ini memerintahkan Appium untuk menunggu Activity APAPUN yang muncul pertama kali.
        // Tanpa ini, Appium akan bingung saat Splash Screen hilang terlalu cepat/lambat.
        options.setAppWaitActivity("*");

        // Opsional: Tambah durasi tunggu install/launch app (misal emulator lambat)
        options.setAppWaitDuration(Duration.ofSeconds(50));

        // Inisialisasi Driver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
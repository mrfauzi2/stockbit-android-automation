package com.stockbit.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.stockbit", // Penting: Scan semua folder di bawah com.stockbit
        tags = "@checkout",    // Jalankan skenario checkout
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
\# Stockbit Android Automation Test



Project ini adalah solusi Technical Test untuk posisi QA Engineer. Project ini mengotomatisasi pengujian aplikasi Android "My Demo App" (Sauce Labs) menggunakan \*\*Appium\*\*, \*\*Java\*\*, dan \*\*Cucumber Framework\*\* dengan pola desain \*\*Page Object Model (POM)\*\*.
Rekaman testing : https://youtu.be/oyO08rLfhr8 



\## 🛠 Tech Stack



\* \*\*Language:\*\* Java (JDK 11+)

\* \*\*Framework:\*\* Cucumber BDD

\* \*\*Mobile Automation:\*\* Appium Java Client 8.x

\* \*\*Build Tool:\*\* Maven

\* \*\*Pattern:\*\* Page Object Model (POM)

\* \*\*Assertion:\*\* TestNG / JUnit



\## 📂 Project Structure



```text

src/test/java/com/stockbit

├── pages       # Page Object Classes (Locator \& Actions)

│   ├── LoginPage.java

│   ├── CatalogPage.java

│   └── CheckoutPage.java

├── steps       # Step Definitions (Cucumber Logic)

│   ├── LoginSteps.java

│   └── ShoppingSteps.java

├── runners     # Test Runners

│   └── TestRunner.java

└── utils       # Utilities \& Hooks (Driver Setup)

&nbsp;   └── Hooks.java



src/test/resources/features   # Gherkin Feature Files

├── login.feature

└── Shopping.feature


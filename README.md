A Maven test automation framework in Selenium using Tools QA’s demo shopping website (https://www.saucedemo.com/) as the System Under Test (SUT).
The primary objective is to automate a valid test scenario where a user successfully places an order of the specified products.
Test data must be read from an Excel spreadsheet where:
1.	there is an invalid user login information (test ends on login page)
2.	the same valid user data is used to login again, add another item “Sauce Labs Onesie” to the cart, proceed to checkout, and complete an order.
Project details
•	The Maven project contains the following packages with all relevant classes:
o	Data
o	Utilities
o	Application (Login, Products, Cart, Checkout, Confirmation)
o	Reporting
•	Parameterise your URL and browser using TestNG.
•	The test script should run on (Chrome).
•	Validation must be on all pages (i.e. successful login, item added to cart, etc.)
•	Use ExtentReports to display test evidence and results with screenshots for all steps.
•	The report should show a pass and fail results based on user input (test data).
NB: A valid test user must be manually registered first before being used as test data. You may use a fake address and cell phone number in the final confirmation for placing them order.

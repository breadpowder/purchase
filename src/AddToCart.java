package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddToCart {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.newegg.ca/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddToCart() throws Exception {
    driver.get(baseUrl + "/Product/Product.aspx?Item=9SIA9U16WU4929");
    try {
      assertEquals(driver.findElement(By.xpath("(//button[@type='button'])[61]")).getText(), "ADD TO CART");
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("(//button[@type='button'])[61]")).click();
    driver.findElement(By.cssSelector("div.item-button-area > button.btn.btn-primary")).click();
    driver.findElement(By.linkText("Secure Checkout")).click();
    driver.findElement(By.id("UserName")).clear();
    driver.findElement(By.id("UserName")).sendKeys("breadpowder@gmail.com");
    driver.findElement(By.id("UserPwd")).clear();
    driver.findElement(By.id("UserPwd")).sendKeys("Qianggou1");
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.id("cvv2Code")).clear();
    driver.findElement(By.id("cvv2Code")).sendKeys("1234");
    driver.findElement(By.id("term")).click();
    try {
      assertEquals(driver.findElement(By.id("SubmitOrder")).getText(), "Place Order");
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

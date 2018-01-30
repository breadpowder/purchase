# -*- coding: utf-8 -*-
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import NoAlertPresentException
import unittest, time, re

class AddToCart(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        self.driver.implicitly_wait(30)
        self.base_url = "https://www.newegg.ca/"
        self.verificationErrors = []
        self.accept_next_alert = True
    
    def test_add_to_cart(self):
        driver = self.driver
        driver.get(self.base_url + "/Product/Product.aspx?Item=9SIA9U16WU4929")
        try: self.assertEqual("ADD TO CART", driver.find_element_by_xpath("(//button[@type='button'])[61]").text)
        except AssertionError as e: self.verificationErrors.append(str(e))
        driver.find_element_by_xpath("(//button[@type='button'])[61]").click()
        driver.find_element_by_css_selector("div.item-button-area > button.btn.btn-primary").click()
        driver.find_element_by_link_text("Secure Checkout").click()
        driver.find_element_by_id("UserName").clear()
        driver.find_element_by_id("UserName").send_keys("breadpowder@gmail.com")
        driver.find_element_by_id("UserPwd").clear()
        driver.find_element_by_id("UserPwd").send_keys("Qianggou1")
        driver.find_element_by_id("submit").click()
        driver.find_element_by_id("cvv2Code").clear()
        driver.find_element_by_id("cvv2Code").send_keys("1234")
        driver.find_element_by_id("term").click()
        try: self.assertEqual("Place Order", driver.find_element_by_id("SubmitOrder").text)
        except AssertionError as e: self.verificationErrors.append(str(e))
    
    def is_element_present(self, how, what):
        try: self.driver.find_element(by=how, value=what)
        except NoSuchElementException as e: return False
        return True
    
    def is_alert_present(self):
        try: self.driver.switch_to_alert()
        except NoAlertPresentException as e: return False
        return True
    
    def close_alert_and_get_its_text(self):
        try:
            alert = self.driver.switch_to_alert()
            alert_text = alert.text
            if self.accept_next_alert:
                alert.accept()
            else:
                alert.dismiss()
            return alert_text
        finally: self.accept_next_alert = True
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()

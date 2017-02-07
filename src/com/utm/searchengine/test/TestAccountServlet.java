package com.utm.searchengine.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestAccountServlet {

	static WebDriver driver;
    static Wait<WebDriver> wait;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get("http://1-dot-search-1239.appspot.com/login");
        WebElement user = driver.findElement(By.name("user"));
    	WebElement password = driver.findElement(By.name("password"));
    	user.click();
    	user.clear();
    	user.sendKeys("pushi");
    	password.click();
    	password.clear();
    	password.sendKeys("123123");
    	driver.findElement(By.id("Submit")).click();
    	wait = new WebDriverWait(driver, 30);
    	driver.navigate().to("http://1-dot-search-1239.appspot.com/account?user=pushi");
    }
    
    @Test
    public void testDoGet(){
    	System.out.println(driver.getTitle());
    	assertTrue(driver.getTitle().equals("My Account"));
    }
    
    @Test
    public void testDoPost(){
    	driver.findElement(By.name("edit")).click();
    	wait = new WebDriverWait(driver, 30);
    	System.out.println(driver.getCurrentUrl());
    	assertTrue(driver.getCurrentUrl().contains("Edit"));
    }
}
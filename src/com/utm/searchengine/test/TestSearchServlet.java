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

public class TestSearchServlet {

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
    }
    
    @Test
    public void testDoGet(){
    	assertTrue(driver.getTitle().equals("Search"));
    }
    
    @Test
    public void testDoPost(){
    	WebElement query = driver.findElement(By.name("keyword"));
    	query.click();
    	query.clear();
    	query.sendKeys("sdfsdfasdsfweqfrweasd");
    	driver.findElement(By.id("search")).click();
    	assertTrue(driver.getPageSource().contains("Your search did not match any documents."));
    }
}

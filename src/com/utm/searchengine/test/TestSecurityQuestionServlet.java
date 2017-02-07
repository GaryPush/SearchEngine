package com.utm.searchengine.test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSecurityQuestionServlet {
    static WebDriver driver;
    static Wait<WebDriver> wait;
    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get("http://1-dot-search-1239.appspot.com/securityQuestion?user=pushi&question=How%20are%20you?");
        
    }
    
    @Test
    public void testDoGet(){
        
    	assertTrue(driver.getTitle().equals("securityQuestion"));
    }
    
    @Test
    public void testDoPost(){
        driver.findElement(By.id("Submit")).click();
        wait = new WebDriverWait(driver, 30);
    	assertTrue(driver.getPageSource().contains("Please enter the answer of your security question."));
    	
    	
    }
}
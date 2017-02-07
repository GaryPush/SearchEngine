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

public class TestRecoverPasswordServlet {
    static WebDriver driver;
    static Wait<WebDriver> wait;
    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get("http://1-dot-search-1239.appspot.com/confirmUser");
	WebElement user = driver.findElement(By.name("user"));
    	user.click();
    	user.clear();
    	user.sendKeys("pushi");
        wait = new WebDriverWait(driver, 30);
        driver.findElement(By.id("Submit")).click();
	WebElement answer = driver.findElement(By.name("answer"));
		answer.click();
		answer.clear();
		answer.sendKeys("Fine");
        driver.findElement(By.id("Submit")).click();
        wait = new WebDriverWait(driver, 30);
    	
    }
    
    @Test
    public void testDoGet(){
        
    	System.out.println(driver.getTitle());
    	assertTrue(driver.getTitle().equals("recoverPassword"));
    }
    
    @Test
    public void testDoPost(){
	
        driver.findElement(By.id("Submit")).click();
        wait = new WebDriverWait(driver, 30);
	assertTrue(driver.getPageSource().contains("Please enter your new password."));
    	
    	
    }
}

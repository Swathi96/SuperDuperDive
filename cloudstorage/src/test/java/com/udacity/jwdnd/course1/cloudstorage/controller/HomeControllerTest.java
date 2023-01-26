package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeControllerTest {

	private WebDriver driver;

	private int port;

	public HomeControllerTest(WebDriver webDriver, int port) {
		PageFactory.initElements(webDriver, this);
		driver = webDriver;
	}

	public void clickLogout() throws Exception {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
		WebElement title = driver.findElement(By.id("logout"));
		title.click();
		assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

	}

}

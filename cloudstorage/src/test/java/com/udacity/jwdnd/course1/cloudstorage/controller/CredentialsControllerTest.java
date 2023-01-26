package com.udacity.jwdnd.course1.cloudstorage.controller;

import static java.lang.Thread.sleep;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialsControllerTest {

	@FindBy(id = "nav-credentials-tab")
	private WebElement credentialTab;

	@FindBy(id = "add-credential")
	private WebElement addCredentialButton;

	@FindBy(id = "edit-credential")
	private WebElement editCredentialButton;

	private final JavascriptExecutor js;

	private WebDriver driver;

	public CredentialsControllerTest(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
		js = (JavascriptExecutor) webDriver;
		driver = webDriver;
	}

	public void clickCredentialTab() {
		credentialTab.click();
	}

	public void clickAddCredentialButton() {
		js.executeScript("arguments[0].click();", addCredentialButton);
	}

	public void clickEditCredentialButton() {
		js.executeScript("arguments[0].click();", editCredentialButton);
	}

	public void addCredential() throws Exception {
		clickCredentialTab();
		clickAddCredentialButton();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement title = driver.findElement(By.id("credential-url"));
		title.click();
		title.sendKeys("url");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement desc = driver.findElement(By.id("credential-username"));
		desc.click();
		desc.sendKeys("username");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.click();
		password.sendKeys("password");

		waitEvent();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential-button")));
		WebElement CredentialSubmit = driver.findElement(By.id("save-credential-button"));
		CredentialSubmit.click();
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("create"));
	}

	public void editCredential() throws Exception {

		addCredential();
		waitEvent();
		driver.findElement(By.id("click")).click();
		waitEvent();
		clickCredentialTab();
		waitEvent();
		String xPathWithVariable = "(//button[@id='edit-credential'])" + "[" + 1 + "]";
		driver.findElement(By.xpath(xPathWithVariable)).click();
		waitEvent();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement title = driver.findElement(By.id("credential-url"));
		title.click();
		title.sendKeys("url");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement desc = driver.findElement(By.id("credential-username"));
		desc.click();
		desc.sendKeys("username");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.click();
		password.sendKeys("password");
		waitEvent();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential-button")));
		WebElement CredentialSubmit = driver.findElement(By.id("save-credential-button"));
		CredentialSubmit.click();
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("update"));
	}

	private void waitEvent() throws Exception {
		try {
			sleep(2000);
		} catch (Exception e1) {
			throw new Exception("Exception while waiting for next element visibility " + e1.getMessage());
		}
	}

	public void deleteCredential() throws Exception {
		addCredential();
		waitEvent();
		driver.findElement(By.id("click")).click();
		waitEvent();
		clickCredentialTab();
		waitEvent();
		String xPathWithVariable = "(//a[@id='delete-credential'])" + "[" + 1 + "]";
		driver.findElement(By.xpath(xPathWithVariable)).click();
		waitEvent();
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("delete"));
	}

}

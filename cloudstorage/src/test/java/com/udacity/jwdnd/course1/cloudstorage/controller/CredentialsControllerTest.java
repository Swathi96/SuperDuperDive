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

	@FindBy(id = "credential-url")
	private WebElement credentialUrl;

	@FindBy(id = "credential-username")
	private WebElement credentialUsername;

	@FindBy(id = "credential-password")
	private WebElement credentialPassword;

	@FindBy(id = "credentialSubmit")
	private WebElement credentialSubmitButton;

	@FindBy(id = "credentialUrl")
	private WebElement credentialUrlData;

	@FindBy(id = "credentialUsername")
	private WebElement credentialUsernameData;

	@FindBy(id = "credentialPassword")
	private WebElement credentialPasswordData;

	@FindBy(id = "edit-credential")
	private WebElement editCredentialButton;

	@FindBy(id = "delete-credential")
	private WebElement deleteCredentialButton;

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

	public void clickDeleteCredentialButton() {
		js.executeScript("arguments[0].click();", deleteCredentialButton);
	}

	public void inputCredentialUrl(String url) {
		js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
	}

	public void inputCredentialUsername(String username) {
		js.executeScript("arguments[0].value='" + username + "';", credentialUsername);
	}

	public void inputCredentialPassword(String password) {
		js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
	}

	public void submitCredential() {
		credentialSubmitButton.submit();
	}

	public String getCredentialUrl() {
		return credentialUrlData.getAttribute("innerHTML");
	}

	public String getCredentialUsername() {
		return credentialUsernameData.getAttribute("innerHTML");
	}

	public String getCredentialPassword() {
		return credentialPasswordData.getAttribute("innerHTML");
	}

	public boolean elementExists(By locatorKey, WebDriver driver) {
		try {
			driver.findElement(locatorKey);

			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean credentialsExist(WebDriver driver) {
		return !elementExists(By.id("tblCredentialUrl"), driver)
				&& !elementExists(By.id("tblCredentialUsername"), driver)
				&& !elementExists(By.id("tblCredentialPassword"), driver);
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
			throw new Exception("Exception while waiting for next element visibility");
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

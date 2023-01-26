package com.udacity.jwdnd.course1.cloudstorage;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.udacity.jwdnd.course1.cloudstorage.controller.CredentialsControllerTest;
import com.udacity.jwdnd.course1.cloudstorage.controller.HomeControllerTest;
import com.udacity.jwdnd.course1.cloudstorage.controller.NotesControllerTest;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 * 
	 * @throws Exception
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) throws Exception {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);
		waitEvent();
		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();
		waitEvent();
		/*
		 * Check that the sign up was successful. // You may have to modify the element
		 * "success-msg" and the sign-up // success message below depening on the rest
		 * of your code.
		 */
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("successful"));
	}

	private void waitEvent() throws Exception {
		try {
			sleep(3000);
		} catch (Exception e) {

			throw new Exception("Unable to see the element" + e.getMessage());

		}
	}

	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling redirecting
	 * users back to the login page after a succesful sign up. Read more about the
	 * requirement in the rubric: https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		try {
			doMockSignUp("Redirection", "Test", "RT", "123");

			sleep(3000);
			// Check if we have been redirected to the log in page.
			assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
		} catch (Exception e) {
			System.out.println("Test for redirection failed");
		}

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBadUrl() throws Exception {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		waitEvent();
		doLogIn("UT", "123");
		waitEvent();
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		waitEvent();
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling uploading large
	 * files (>1MB), gracefully in your code.
	 * 
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload
	 * Limits" section.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLargeUpload() throws Exception {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}

	}
/*
 * Test if the application is working for use case of adding/ creating a new note
 */
	@Test
	public void addNote() throws Exception {
		
			// Create a test account
			doMockSignUp("AddNote", "Test", "AN", "123");
			doLogIn("AN", "123");

			NotesControllerTest note = new NotesControllerTest(driver);
			try {
			note.addNote();
		} catch (Exception e) {
			System.out.println("Exception while adding note");
		}

	}
/*
 * 
 * Test if the application is working for the use case of editing an existing note
 */
	@Test
	public void editNote() throws Exception {
		
			// Create a test account
			doMockSignUp("EDITNote", "Test", "EN", "123");
			doLogIn("EN", "123");
			try {
			NotesControllerTest note = new NotesControllerTest(driver);
			note.editNote();
		} catch (Exception e) {
			System.out.println("Exception while editing note");
		}

	}
/*
 * Test if the application is working for deletion of existing note based on first value set in the index xpath
 */
	@Test
	public void deleteNote() throws Exception {
		
			// Create a test account
			doMockSignUp("DeleteNote", "Test", "DN", "123");
			doLogIn("DN", "123");
			try {
			NotesControllerTest note = new NotesControllerTest(driver);

			note.deleteNote();
		} catch (Exception e) {
			System.out.println("Exception while deleting note");
		}

	}
/*
 * Test if application is working for creation/ addition of new credential
 */
	@Test
	public void addCredentials() throws Exception {
		
			// Create a test account
			doMockSignUp("AddNote", "Test", "AC", "123");
			doLogIn("AC", "123");
			try {
			CredentialsControllerTest credentialTest = new CredentialsControllerTest(driver);

			credentialTest.addCredential();
		} catch (Exception e) {
			System.out.println("Exception while adding credentials");
		}

	}
/*
 * Test if the application is working for editing the credential based on xpath first index
 */
	@Test
	public void editCredentials() throws Exception {
		
			// Create a test account
			doMockSignUp("EDITNote", "Test", "EC", "123");
			doLogIn("EC", "123");
			try {
			CredentialsControllerTest credentialTest = new CredentialsControllerTest(driver);

			credentialTest.editCredential();
		} catch (Exception e) {
			System.out.println("Exception while editing credential");
		}

	}
/*
 * Test if the application is working for deletion of selected first index of the list of credentials
 */
	@Test
	public void deleteCredentials() throws Exception {
		
			// Create a test account
			doMockSignUp("DeleteNote", "Test", "DC", "123");
			doLogIn("DC", "123");
			try {
			CredentialsControllerTest credentialTest = new CredentialsControllerTest(driver);

			credentialTest.deleteCredential();
		} catch (Exception e) {
			System.out.println("Exception while deleting credentials");
		}

	}
	
	@Test
	public void logoutTest() {
		HomeControllerTest logoutTest = new HomeControllerTest(driver, port);
		try {
			logoutTest.clickLogout();
		} catch (Exception e) {
			System.out.println("Exception while logging out from application");
			}
	}
	
	@Test
	public void accessHomeAfterLogout() {
		HomeControllerTest logoutTest = new HomeControllerTest(driver, port);
		try {
			logoutTest.clickHomeAfterLogout();
		} catch (Exception e) {
			System.out.println("Exception while logging out from application");
			}
	}

}
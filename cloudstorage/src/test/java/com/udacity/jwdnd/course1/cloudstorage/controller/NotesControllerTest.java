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

public class NotesControllerTest {

	private final JavascriptExecutor js;

	@FindBy(id = "nav-notes-tab")
	private WebElement noteTab;

	@FindBy(id = "add-note")
	private WebElement addNoteButton;

	@FindBy(id = "note-title")
	private WebElement noteTitle;

	@FindBy(id = "note-description")
	private WebElement noteDescription;

	@FindBy(id = "notetitle")
	private WebElement noteTitleData;

	@FindBy(id = "notedescription")
	private WebElement noteDescriptionData;

	@FindBy(id = "noteSubmit")
	private WebElement noteSubmitButton;

	@FindBy(id = "edit-note")
	private WebElement noteEditButton;

	@FindBy(id = "delete-note")
	private WebElement noteDeleteButton;

	private WebDriver driver;

	public NotesControllerTest(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
		js = (JavascriptExecutor) webDriver;
		driver = webDriver;
	}

	public void clickNoteTab() {
		noteTab.click();
	}

	public void addNote() throws Exception {
		clickNoteTab();
		clickAddNoteButton();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement title = driver.findElement(By.id("note-title"));
		title.click();
		title.sendKeys("Note1");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.click();
		desc.sendKeys("NoteDescription");

		waitEvent();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-button")));
		WebElement noteSubmit = driver.findElement(By.id("save-note-button"));
		noteSubmit.click();
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("successfully"));
	}

	public void editNote() throws Exception {

		addNote();
		waitEvent();
		driver.findElement(By.id("click")).click();
		waitEvent();
		clickNoteTab();
		waitEvent();
		String xPathWithVariable = "(//button[@id='edit-note'])" + "[" + 1 + "]";
		driver.findElement(By.xpath(xPathWithVariable)).click();
		try {
			sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement title = driver.findElement(By.id("note-title"));
		title.click();
		title.sendKeys("Note1");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.click();
		desc.sendKeys("NoteDescription-changed");

		waitEvent();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-button")));
		WebElement noteSubmit = driver.findElement(By.id("save-note-button"));
		noteSubmit.click();
		waitEvent();
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("update"));
	}

	public void deleteNote() throws Exception {

		addNote();
		waitEvent();
		driver.findElement(By.id("click")).click();
		waitEvent();
		clickNoteTab();
		waitEvent();
		String xPathWithVariable = "(//a[@id='delete-note'])" + "[" + 1 + "]";
		driver.findElement(By.xpath(xPathWithVariable)).click();
		try {
			sleep(3000);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("delete"));
	}

	private void waitEvent() throws Exception {
		try {
			sleep(2000);
		} catch (Exception e) {
			throw new Exception("Exception while waiting for next element visibility");
		}
	}

	public void clickAddNoteButton() {
		js.executeScript("arguments[0].click();", addNoteButton);
	}

	public void inputNoteTitle(String title) {
		js.executeScript("arguments[0].value='" + title + "';", noteTitle);
	}

	public void inputNoteDescription(String description) {
		js.executeScript("arguments[0].value='" + description + "';", noteDescription);
	}

	public void submitNote() {
		noteSubmitButton.submit();
	}

	public String getNoteTitle() {
		return noteTitleData.getAttribute("innerHTML");
	}

	public String getNoteDescription() {
		return noteDescriptionData.getAttribute("innerHTML");
	}

	public void clickNoteEditButton() {
		js.executeScript("arguments[0].click();", noteEditButton);
	}

	public void clickNoteDeleteButton() {
		js.executeScript("arguments[0].click();", noteDeleteButton);
	}

	public boolean elementExists(By locatorKey, WebDriver driver) {
		try {
			driver.findElement(locatorKey);

			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean notesExist(WebDriver driver) {
		return !elementExists(By.id("notetitle"), driver) && !elementExists(By.id("notedescription"), driver);
	}

}

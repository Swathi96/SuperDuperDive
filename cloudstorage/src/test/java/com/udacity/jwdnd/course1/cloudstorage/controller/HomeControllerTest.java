package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeControllerTest {

    @FindBy(id="logout")
    WebElement logoutButton;

    public HomeControllerTest(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void clickLogoutButton(){
        logoutButton.click();
    }}

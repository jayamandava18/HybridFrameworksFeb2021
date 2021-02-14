package com.qa.linkedin.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;



public class LinkedinHomePage extends BasePageWeb {

	//create a constructor
	public LinkedinHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		}

	private  Logger log = Logger.getLogger(LinkedinHomePage.class);
			
	@FindBy(css="a.nav__logo-link")
	private WebElement LinkedinLogo;
	
	@FindBy(linkText="Sign in")
	private WebElement SignInLink;
	
	@FindBy(xpath = "//h1[contains(@class,'header__content__heading ')]")
	private WebElement signinHeaderText;
	
	@FindBy(id="username")
	private WebElement userNameEditbox;
	
	@FindBy(id="password")
	private WebElement passWordEditbox;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement signInButton;
	
	//private WebElement skipBtn;
	
	String homePageTitle="LinkedIn: Log In or Sign Up";
	String signPageTitle="LinkedIn Login, Sign in | LinkedIn";
	
	public void verifyLinkedinHomePageTitle() {
		log.debug("wait for the linkedin home page title");
		wait.until(ExpectedConditions.titleContains(homePageTitle));
		Assert.assertEquals(driver.getTitle(), homePageTitle);
		}
	public void verifyLinkedinLogo() {
		log.debug("wait for the linkedin logo");
		wait.until(ExpectedConditions.visibilityOf(LinkedinLogo));
		Assert.assertTrue(LinkedinLogo.isDisplayed());
		}
	public void clickSigninLink() throws Exception {
		log.debug("click on sign in link in homepage");
		click(SignInLink);
		}
	public void verifyLinkedinSigninPageTitle() {
		log.debug("wait for the linkedin signin page title");
		wait.until(ExpectedConditions.titleContains(signPageTitle));
		Assert.assertEquals(driver.getTitle(), signPageTitle);
		}
	public void verifysigninHeaderText() {
		log.debug("wait for the signin Header Text");
		wait.until(ExpectedConditions.visibilityOf(signinHeaderText));
		Assert.assertTrue(signinHeaderText.isDisplayed());
		}
	public void clickSigninButton() throws Exception {
		log.debug("click on sign Button in signin process page");
		click(signInButton);
		}
	/*public void clickSkipButton() throws Exception {
		log.debug("click on skip in Button in homepage");
		click(skipBtn);
		}*/
	
	public LinkedinLoggedinPage doLogin(String uname, String pwd) throws Exception {
		log.debug("Start login process...");
		sendKey(userNameEditbox, uname);
		sendKey(passWordEditbox, pwd);
		clickSigninButton();
	/*try{
			if (isPresentElement(skipBtn)) {
				click (skipBtn);
			}
	}catch(Exception e) {
		e.printStackTrace();
	}*/
		
		return new LinkedinLoggedinPage(driver);
		}
	
	
	
}

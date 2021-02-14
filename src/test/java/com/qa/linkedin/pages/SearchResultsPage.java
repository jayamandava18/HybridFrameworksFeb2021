package com.qa.linkedin.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class SearchResultsPage extends BasePageWeb {

	public SearchResultsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	private Logger log = Logger.getLogger(SearchResultsPage.class);

	@FindBy(xpath = "//div[contains(@class,'search-results__cluster-bottom-banner')]/a")
	private WebElement seeAllPeopleResults;

	@FindBy(xpath = "//div[contains(@class,'pb2 t-black--light t-14')]")
	private WebElement SearchResultsText;

	@FindBy(xpath = "//ul[@class='global-nav__primary-items']/li/a")
	private WebElement homeTab;

	String SearchResultsPageTitle = " Search | LinkedIn";

	public void validateSearchResultsPageTitle() {
		log.debug("Wait for search results page title");
		wait.until(ExpectedConditions.titleContains(SearchResultsPageTitle));
		Assert.assertTrue(driver.getPageSource().contains(SearchResultsPageTitle));
	}

	public long getResultsCount() {
		log.debug("Performing fetch results count for the given people");
		try {
			if (isPresentElement(seeAllPeopleResults)) {
				click(seeAllPeopleResults);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("wait for the search results text");
		wait.until(ExpectedConditions.visibilityOf(SearchResultsText));
		log.debug("Get the search results text from webpage");
		String txt = SearchResultsText.getText();
		System.out.println("Search results text is: " + txt);
		// txt="About 640,000 results"
		String[] str = txt.split(" ");
		// str[]=["About","640,000","results"]
		log.debug("Results count in string format= " + str[1]);
		String finalStringCount = str[1].replace(",", "");
		long count = Long.parseLong(finalStringCount);
		return count;
	}

	public void clickHomeTab() throws Exception {
		log.debug("click on home tab");
		clickUsingJsExecutor(homeTab);
	}

}

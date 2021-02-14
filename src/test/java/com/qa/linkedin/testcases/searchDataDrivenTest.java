package com.qa.linkedin.testcases;

import org.testng.annotations.Test;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.pages.LinkedinHomePage;
import com.qa.linkedin.pages.LinkedinLoggedinPage;
import com.qa.linkedin.pages.SearchResultsPage;
import com.qa.linkedin.util.ExcelUtils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;

public class searchDataDrivenTest  extends TestBase {

	private Logger log = Logger.getLogger(SearchResultsPage.class);
	private String path=System.getProperty("user.dir")+"\\src\\test\\java\\com\\qa\\linkedin\\data\\searchData.xlsx";
	LinkedinHomePage lHmPage;
	LinkedinLoggedinPage lLoPage;
	SearchResultsPage sRePage;
	
	@DataProvider
	public Object[][] getData() throws InvalidFormatException, IOException {
	Object[][] data=new ExcelUtils().getTestData(path,"Sheet1");
		return data;
	}

	@BeforeClass
	public void beforeClass() throws Exception {
		log.debug("Pages initilization..");
		lHmPage=new LinkedinHomePage(driver) ;
		lLoPage=new LinkedinLoggedinPage(driver);
		sRePage=new SearchResultsPage(driver) ;
		log.debug("calling title verification");
		lHmPage.verifyLinkedinHomePageTitle();
		lHmPage.verifyLinkedinLogo();
		lHmPage.clickSigninLink();
		lHmPage.verifysigninHeaderText();
		lLoPage=lHmPage.doLogin(readPropertyValue("username"), readPropertyValue("password"));
		//lLoPage.verifyProfileRailCard();
	}

	@AfterClass
	public void afterClass() throws Exception {
		log.debug("perform the logout operation from appliction");
		lLoPage.doLogout();
		lHmPage.verifyLinkedinHomePageTitle();
	}

	@Test(dataProvider = "getData")
	public void SearchDataDrivenTest(String keyword) throws Exception {
	log.debug("Started executing the search test for people: "+keyword);
	sRePage= lLoPage.doPeopleSearch(keyword);
	sRePage.validateSearchResultsPageTitle();
	long cnt= sRePage.getResultsCount();
	log.debug("Serch results count for "+keyword+"is: "+cnt);
	log.debug("Click on HomeTab to go to loggedin page");
	sRePage.clickHomeTab();
	log.debug("************iteration done*************");
	}

}

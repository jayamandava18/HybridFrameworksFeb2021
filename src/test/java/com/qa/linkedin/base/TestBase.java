package com.qa.linkedin.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	private Logger log = Logger.getLogger(TestBase.class);
	public static WebDriver driver = null;
	public WebDriverWait wait = null;
	public Properties prop = null;

	public String readPropertyValue(String key) throws IOException {
		log.info("Create Object for Properties ");
		prop = new Properties();
		log.debug("Read the Properties File");
		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\java\\com\\qa\\linkedin\\config\\config.properties");
			log.info("Log All Properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);

	}

	@BeforeSuite
	public void setup() throws IOException {
		log.debug("Launching the browser and application");
		String browserName = readPropertyValue("browser");
		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		log.debug("Maximum the window");
		driver.manage().window().maximize();
		log.debug("Add implicit wait");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.debug("Create object for the WebDriverWait explicitwait command");
		wait = new WebDriverWait(driver, 30);
		log.debug("Launch the application URL");
		driver.get(readPropertyValue("applicationUrl"));
	}

	@AfterSuite
	public void teraDown() {
		log.debug("Close the browser");
		if (driver != null) {
			driver.quit();
		}
	}

}
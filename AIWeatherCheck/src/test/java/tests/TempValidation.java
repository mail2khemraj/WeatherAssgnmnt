package tests;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import page.NDTV;
import utils.TooMuchTempDiffException;
import utils.Utility;
import webdriver.WebDriverActions;

public class TempValidation {
	
	static WebDriver driver;
	
	@Parameters({"baseURL", "apiBaseURI"})
	@BeforeClass()
	public void initialize(String baseURL, String apiBaseURI) {
		String driverPath = System.getProperty("user.dir") + File.separator + "driver" + File.separator + "ChromeDriver.exe";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
		driver.manage().window().maximize();
		RestAssured.baseURI = apiBaseURI;
	}
	
	@Parameters({"cityName", "appID", "maxTempDiff"})
	@Test
	public void tempratureValidation(String cityName, String appID, String maxTempDiff) throws ParseException, TooMuchTempDiffException {
		
		NDTV pageNDTV = new NDTV();
		WebDriverActions wdActions = new WebDriverActions();
		Utility utl = new Utility();
		String strTemporary = "";
		long tempFromWeb = 0;
		long tempFromAPI = 0;
		long diffInTemp = 0;
		long maxTempDiffl = 0;
		
		//Phase 1
		wdActions.click(driver, pageNDTV.lnkNoThanks);
		wdActions.click(driver, pageNDTV.subMenu);
		wdActions.click(driver, pageNDTV.lnkWeather);
		wdActions.type(driver, cityName, pageNDTV.boxSearch);
		if(wdActions.fetchText(driver, pageNDTV.getCityTempratureDiv(cityName)).equals("")) {
			Assert.assertTrue(true, "As we are not able to fetch the temprature for given city, it is validated that without choosing "
					+ " city is not available on the map with temprature information.");
		}else {
			Assert.fail("As we are able to fetch the temprature for given city, it is validated that without choosing "
					+ " city is still available on the map with temprature information.");
		}
		
		
		wdActions.selectCheckbox(driver, pageNDTV.getCityInputBox(cityName));
		strTemporary = wdActions.fetchText(driver, pageNDTV.getCityTempratureDiv(cityName));
		if(!(strTemporary.equals(""))) {
			strTemporary = strTemporary.replaceAll("[^a-zA-Z0-9]", "");
			tempFromWeb = Long.parseLong(strTemporary);
			System.out.println(tempFromWeb+" is the city temprature fetched from web");		
			Assert.assertTrue(true, "As we are able to fetch the temprature for given city, it is validated that after Searching and "
					+ "selecing any city the map reveals temprature details. ");
		}else {
			Assert.fail("As we are not able to fetch the temprature for given city, it is validated that after Searching and "
					+ "selecing any city the map does not reveals temprature details. ");
		}
		
		
		
		wdActions.click(driver, pageNDTV.getCityTempratureDiv(cityName));
		if(wdActions.checkElementPresence(driver, pageNDTV.tempLeaf))
			Assert.assertTrue(true, "Temprature leaftlet is getting displayed");
		else
			Assert.fail("Temprature leaflet is not getting displayed.");

		
		
		
		
		//Phase 2
		Response respnse = RestAssured.given().request().queryParam("q", cityName)
						.queryParam("appid", appID)
						.queryParam("units", "metric")
						.relaxedHTTPSValidation()
						.get("/weather");
		
		tempFromAPI = utl.parseJsonNGetTemp(respnse.prettyPrint());
		System.out.println(tempFromAPI+" is the city temprature fetched from API");
		
		
		
		//Comparing logic
		diffInTemp = tempFromWeb - tempFromAPI;
		System.out.println("diffInTemp: "+diffInTemp);
		maxTempDiffl = Long.parseLong(maxTempDiff);
		if(diffInTemp<=maxTempDiffl && diffInTemp>=0) {
			Assert.assertTrue(true, "Temprature difference from the web and api is within the maximum difference limit. Test Passed!!!");
		}else {
			throw new TooMuchTempDiffException("Temprature difference is outside the specified limit");
		}
		
		
	}
	
	
	
	@AfterClass()
	public void tearDown() {
		driver.quit();
	}
	
	

}

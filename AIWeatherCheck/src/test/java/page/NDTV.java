package page;

import org.openqa.selenium.By;

public class NDTV {

	
	
	
	public By lnkNoThanks = By.xpath("//a[@class='notnow']");
	public By subMenu = By.id("h_sub_menu");
	public By lnkWeather = By.linkText("WEATHER");
	public By boxSearch = By.id("searchBox");
	public By tempLeaf = By.xpath("//div[@class='leaflet-popup-content-wrapper']");
	
	
	
	public By getCityInputBox(String cityName) {
		return By.id(cityName);
	}
	
	
	public By getCityTempratureDiv(String cityName) {
		return By.xpath("//div[@title='"+cityName+"']//span[@class='tempRedText']");
	}
}

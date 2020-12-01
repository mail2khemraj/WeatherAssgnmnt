package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverActions {
	
	WebDriverWait wait;
	
	public void click(WebDriver driver, By locator) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();
		
	}
	
	
	public String fetchText(WebDriver driver, By locator) {
		String text="";
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			text = (driver.findElement(locator).getText());	
		}catch(TimeoutException TOE) {
			System.out.println("Element not present at the moment");
		}
		return text;
	}
	
	
	
	public void type(WebDriver driver, String textToType, By locator) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		driver.findElement(locator).sendKeys(textToType);
	}
	
	
	public void selectCheckbox(WebDriver driver, By locator) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		WebElement chkbox = driver.findElement(locator);
		if(!(chkbox.isSelected()))
			chkbox.click();
	}
	
	
	public boolean checkElementPresence(WebDriver driver, By locator) {
		boolean result = false;
		try{
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			result = true;
		}catch(TimeoutException TOE) {
			System.out.println("Element not present at the moment");
		}
		return result;
	}

}

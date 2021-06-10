package utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverSetup {
	 static WebDriver driver;
	
	 public  WebDriver InvokeBrowser(String browser) throws Exception {           
		if (browser.equalsIgnoreCase("safari")) { 
			driver = new SafariDriver(); //implementing safari browser                   
		} 
		else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "/Users/vishnureddy/eclipse-workspace/Miniproject/src/test/java/Driver/chromedriver"); //implementing chrome browser        
			driver = new ChromeDriver();                                
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "/Users/vishnureddy/eclipse-workspace/Miniproject/src/test/java/Driver/geckodriver"); //implementing firefox browser
		}
		driver.manage().window().maximize(); //maximizing the window                       
		return driver;
		}

}

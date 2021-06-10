package Output;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
	WebDriver driver;
	public Screenshot(WebDriver driver){
		this.driver = driver; //assigning the driver 
	}
	
	public void takeScreenShot(String ssname) throws IOException {
		TakesScreenshot scrShot =((TakesScreenshot)driver); //casting driver into TakesScreenShot
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE); //taking screenshot of particular page 
		File DesFile = new File("/Users/vishnureddy/eclipse-workspace/Miniproject/src/test/java/Output/"+ssname+".png"); //defining the external file to be copied
		FileUtils.copyFile(SrcFile, DesFile); //copying the screenshot file to other external file 
	}

}

package pages;
 
import org.testng.annotations.Test;

import InputReader.ExcelInput;
import utils.DriverSetup;

import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class HotelBookTest {
	WebDriver driver;
	Booking bng;
  @Test
  public void f() throws IOException, InterruptedException {
	    bng.SetUrl();   //opening the specific
		bng.SetCity();  //action to set value of specific city
		bng.SetCheckInCheckOutDate(); //action to set value of specific checkin and checkout date
		bng.SetAdults(); // action to set the no of adults 
		bng.SetRooms();  // action to set the no of rooms
		bng.Search();    // action to search for hotels with above mentioned features
		bng.SortHotels(); //action to sort the hotels based on ratings
		bng.GetRentDetails(); //action to get the rent details of the hotel
		bng.RatingsDecendingCheck(); //action to check whether the hotels are in decending order
  }
  @BeforeClass
  public void beforeClass() throws Exception {
	    DriverSetup ds = new DriverSetup();  
		ExcelInput in = new ExcelInput();
		this.driver = ds.InvokeBrowser(in.getBrowser()); //defining the type of brower through external excel sheet
		bng = new Booking(driver); 
  }

  @AfterClass
  public void afterClass() {
	  bng.close();
  }

}

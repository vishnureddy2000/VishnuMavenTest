package pages;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.google.common.collect.Ordering;

import InputReader.ExcelInput;
import Output.Screenshot;
import Output.TextOutput;


public class Booking {	
	
	//hello
	List<WebElement> hotelNames; //creating list to store the hotels.
	List<Double> ratings = new ArrayList<Double>(); //creating list to store the ratings of the hotels
	WebDriver driver;
	String city;
	ExcelInput in = new ExcelInput();
	TextOutput out = new TextOutput();
	Screenshot ss;
	
	public Booking(WebDriver driver) {
		this.driver = driver;
	}
	
	public void SetUrl() {
		driver.get(in.getUrl()); //Open the specific url
	}
	
	public void SetCity() throws InterruptedException{	
		city = in.getCity();    //get the name of city from external excel sheet
		driver.findElement(By.id("querytext")).sendKeys(city); //send city name in specific field
		TimeUnit.SECONDS.sleep(5);
	}
	
	public static String DateFormat(String day) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy"); //Creating date object of Date format in the excel sheet 
        Date input = fmt.parse(day); //Converting string to date 
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd"); //Creating date object of Date format required for the locating the date element
        String date = fm.format(input); //converting from input date format to required date format
        return date;
		
	}
	
	public void SetCheckInCheckOutDate() throws ElementNotInteractableException{
		try{
			driver.findElement(By.xpath("//span[text()=\"Check in\"]")).click();
			String checkInDate = DateFormat(in.getCheckInDate()); //fetching and converting the string to Check in date format from excel sheet
			String checkOutDate = DateFormat(in.getCheckOutDate()); //fetching and converting the string to Check out date format from excel sheet
			List<WebElement> cal = driver.findElements(By.className("cal-month")); //Finding the elements by the classname		
			cal.get(0).findElement(By.xpath("//td//time[@datetime='"+checkInDate+"']")).click(); //Selecting the checkIn date		
			cal.get(1).findElement(By.xpath("//td//time[@datetime='"+checkOutDate+"']")).click(); //Selecting the checkOut date
		}
		catch(Exception e){
			System.out.println("Exception in passing checkin checkout Date"+e);
		}
	}
	
	public void SetAdults() throws IOException, InterruptedException{
		int adultcount = in.getAdultCount();  //getting the number of adults from excel sheet
		driver.findElement(By.id("adults-input")).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), String.valueOf(adultcount)); //clearing the adult field and sending the value from excel sheet
		TimeUnit.SECONDS.sleep(5);	//time to wait for the browser to load
	}
	
	public void SetRooms() throws InterruptedException, IOException {
		ss = new Screenshot(driver); //creating object of the Screenshot class
		int roomcount = in.getRoomCount(); //getting the number of rooms from excel sheet
		driver.findElement(By.id("rooms-input")).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), String.valueOf(roomcount));  //clearing the room field and sending the value from excel sheet
		ss.takeScreenShot("bookingpagescreenshot"); //getting the screenshot of search page before navigating to the next page
		
	}
				
	public void Search() throws IOException{
		driver.findElement(By.className("search-button__label")).click(); //Click search to navigate to the hotels page
	}
	
	public void SortHotels() throws InterruptedException, IOException{
		Select sort = new Select(driver.findElement(By.name("mf-select-sortby")));  //Creating the Select Object of the drop down menu
		sort.selectByIndex(4); // Selecting sort hotels by ratings only 
		
	}
	
	public void checkHotelName() {
		int hotelNameFlag = 0; //flag to check whether the hotel name contains specific city
		for(int i = 0;i < 5;i++) { // check for the first five hotels only
			String hotelName = hotelNames.get(i).getText(); // getting the hotel name from the hotel list
			String[] place = hotelName.split(" "); // splitting the hotel name by spaces" "
	        List<String> places = Arrays.asList(place); // converting string array to list of string
	        if(!(places.contains(city.toLowerCase())||places.contains(city.substring(0, 1).toUpperCase() + city.substring(1))|| places.contains(city.toUpperCase()))) {
	        	hotelNameFlag = 1; //check whether the hotel name contains city eg. Mumbai or mumbai or MUMBAI
	        }
		}
		if(hotelNameFlag == 0) {
			System.out.println("Hotel contain specific city name - " + city); //printing if the hotel names contain the specific city
		}
		else System.out.println("Hotel does not contain specific city name - " + city); //printing if the hotel names does not contain the specific city
	}
	public void GetRentDetails() throws InterruptedException, IOException{
		int nextPageFlag = 0; //flag to check whether there is next page of hotels are there or not
		int checkHotel = 1; // flag to check the hotel name of the first page hotels
		try{
			while(nextPageFlag == 0){
				TimeUnit.SECONDS.sleep(5);	
				hotelNames = driver.findElements(By.xpath("//span[@data-qa='item-name']"));  //fetching the web elements contains the hotel name
				List<WebElement> rents = driver.findElements(By.xpath("//strong[@data-qa='recommended-price']")); //fetching the web elements contains the hotel prices
				List<WebElement> listOfRatings = driver.findElements(By.xpath("//span[@itemprop='ratingValue']")); //fetching the web elements contains the hotel ratings
				if (checkHotel==1) {
					checkHotelName(); //calling the check hotel name contains the specific city name of the first page
					checkHotel++;
				}
				for(int i = 0;i < hotelNames.size();i++) {
					String hotelName = hotelNames.get(i).getText(); //extracting the hotel name for the specific page
					double hotelRating = Double.parseDouble(listOfRatings.get(i).getText()); //extracting the hotel rating and parsing them from string to double
					ratings.add(hotelRating); //hotelRating is added to ratings list
					String rent = rents.get(i).getText().substring(1); //extracting the rent details
					String hotelrent = hotelName+" "+"₹"+String.valueOf(rent) ; //creating the String concat of hotelname and rent
					out.ratingswrite(hotelrent); //passing the hotel and rent values to the external text file		
					
				}
				if(driver.findElements(By.xpath("//button[contains(@class, 'btn--next')]")).isEmpty() == false){
					driver.findElement(By.xpath("//button[contains(@class, 'btn--next')]")).click();	//looping the next button element to navigate to the next page
				}
				else
					nextPageFlag = 1; 
			}
		}
		catch(Exception e){
			System.out.println("Error in Getting the ratings of the hotels"+e);
		}
		out.close(); //closing the external text file
	}
	
	public void RatingsDecendingCheck() throws InterruptedException{
		int descendingFlag = 0; //flag to check whether the rating are descending order
		Iterator<Double> iter = ratings.iterator(); //creating the iterator of rating to loop
	    double current0 = iter.next(); //assigning the first element as current0
	    double current1;
	    while (iter.hasNext()) {
	        current1 = iter.next(); //assigning the second element as current1
	        if (current1 > current0) {
	            descendingFlag = 1; //flag equal to 1 if not in descending order
	        }
	        current0 = current1; //assigning the current0 to next value of ratings
	    }
	    if(descendingFlag == 0) {
	    	System.out.println("Ratings are in decending order"); 
	    }
	    else System.out.println("Ratings are not in decending order");
	}
	
	public void close(){
		try {
			ss.takeScreenShot("hotelspagescreenshot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //taking screenshot of the sorted hotels page
		driver.close(); //closing the driver
	}
}

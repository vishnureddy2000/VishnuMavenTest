package InputReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelInput {
	XSSFWorkbook wb = null; //creating workbook object for the excel file
	XSSFSheet sheet = null; //creating sheet object for the excel file
	
	public ExcelInput(){
		FileInputStream fs = null;
		try {
			fs = new FileInputStream("/Users/vishnureddy/eclipse-workspace/Miniproject/src/test/java/InputReader/input.xlsx"); //assigning file to the external excel sheet 
		} 
		catch (FileNotFoundException e) {
			System.out.println("Error in opening the excel file" + e);
		}
		try {
			wb = new XSSFWorkbook(fs); //Assigning the workbook of the excel sheet
		} 
		catch (IOException e) {
			System.out.println("Error in opening the assigning workbook file" + e);
		}
		this.sheet =  wb.getSheet("Sheet1"); //assigning the sheet of the workbook
	}
	
	public String getCity() {	
		return sheet.getRow(1).getCell(1).getStringCellValue(); //getting the city to be searched for hotel
	}
	
	public int getAdultCount() {
		return (int) sheet.getRow(4).getCell(1).getNumericCellValue(); //getting the no of adults 
	}
	
	public int getRoomCount() {
		return (int) sheet.getRow(5).getCell(1).getNumericCellValue(); //getting the no of rooms to be rented
	}
	
	public  String getUrl() {
			return sheet.getRow(0).getCell(1).getStringCellValue(); //getting the url of the website
	}
	
	public String getBrowser() {
			return sheet.getRow(6).getCell(1).getStringCellValue(); //getting the browser 
	}
	
	public String getCheckInDate() {
		return  sheet.getRow(2).getCell(1).getStringCellValue(); //getting check in date
		
	}
	
	public String getCheckOutDate() {
		return sheet.getRow(3).getCell(1).getStringCellValue(); //getting check out date
	}
	
}

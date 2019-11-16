package MSiA_422_HW5;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Appointment {
	/*
	 * Description: This is a super class that defines the Appointment object
	 * Attributes: Each appointment has a description and date info
	 * Subclasses: OneTime, Monthly, Daily
	 */
	// Define attributes
	private String description;
	private Date date;
	
	// Define the occursOn method that is used to determine if an appointment occurs on a given day
	// This method is overriden by each of the subclasses
	public abstract boolean occursOn(int yearCheck, int monthCheck, int dayCheck);
	
	// Constructor requires just a description 
	public Appointment(String description) {
		this.setDescription(description);
	}
	
	//setters and getters
	private void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Date getStartDate() {
		return this.date;
	}
	
	public Date getEndDate() {
		return this.date;
	}

	public String dateToString(Date date) {
		/*
		 * Description: Function to convert a date into a string of a desired format
		 * Used: Used during writing of appointments to text or csv 
		 */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = simpleDateFormat.format(date);  
        return strDate;
	}
}
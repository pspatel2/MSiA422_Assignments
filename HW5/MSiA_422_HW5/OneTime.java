package MSiA_422_HW5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class OneTime extends Appointment{
	/*
	 * Description: This is a subclass of the Appointment class. It allows a user to create/add a appointment object that has a single occurrence
	 * 
	 */
	private Date date;
	
	// OneTime constructor requires day, month, year, and description inputs corresponding to the appointment
	public OneTime(int day, int month, int year, String description) throws ParseException {
		super(description);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date oneTimeDate = simpleDateFormat.parse(String.format("%d/%d/%d", day, month, year));
		this.date = oneTimeDate;
	}
	
	@Override
	public Date getStartDate() {
		return(this.date);
	}
	
	@Override
	public Date getEndDate() {
		return(this.date);
	}
	
	@Override
	public boolean occursOn(int yearCheck, int monthCheck, int dayCheck) {
		/*
		 * Description: Overrides the occursOn method in the Appointment class to specifically search for OneTime events
		 */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean apptOccurs = false;
        Date checkDate;
		try {
			checkDate = simpleDateFormat.parse(String.format("%d/%d/%d", dayCheck, monthCheck, yearCheck));
	        apptOccurs = this.date.equals(checkDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return apptOccurs;
	}	
}

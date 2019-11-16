package MSiA_422_HW5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Monthly extends Appointment{
	private Date date1;
	private Date date2;
	
	// Monthly constructor requires a description input along with inputs for the start day, month, and year as well as end month and year corresponding to the appointment
	public Monthly(int day1, int month1, int year1, int month2, int year2, String description) throws ParseException {
		super(description);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.date1 = (simpleDateFormat.parse(String.format("%d/%d/%d", day1, month1, year1)));
		this.date2 = (simpleDateFormat.parse(String.format("%d/%d/%d", day1, month2, year2)));
	}
	
	@Override
	public boolean occursOn(int yearCheck, int monthCheck, int dayCheck) {
		/*
		 * Description: Overrides the occursOn method in the Appointment class to specifically search for Monthly events
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		boolean apptOccurs = false;
		Date checkDate;
		try {
			checkDate = simpleDateFormat.parse(String.format("%d/%d/%d", dayCheck, monthCheck, yearCheck));
			if(checkDate.before(this.date2) && checkDate.after(this.date1)){
				apptOccurs = true;
			}
			else {
				apptOccurs = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return apptOccurs;
	}
	
	@Override
	public Date getStartDate() {
		return(this.date1);
	}
	
	@Override
	public Date getEndDate() {
		return(this.date2);
	}	
}
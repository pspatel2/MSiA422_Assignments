package MSiA_422_HW5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Daily extends Appointment{
	private Date date1;
	private Date date2;
	
	// Daily constructor requires a description input along with inputs for the start and end day, month, and years corresponding to the appointment
	public Daily(int day1, int month1, int year1, int day2, int month2, int year2, String description) throws ParseException {
		super(description);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.date1 = (simpleDateFormat.parse(String.format("%d/%d/%d", day1, month1, year1)));
		this.date2 = (simpleDateFormat.parse(String.format("%d/%d/%d", day2, month2, year2)));
	}
	
	@Override
	public boolean occursOn(int yearCheck, int monthCheck, int dayCheck) {
		/*
		 * Description: Overrides the occursOn method in the Appointment class to specifically search for Daily events
		 */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean apptOccurs = false;
		try {
			Date checkDate = simpleDateFormat.parse(String.format("%d/%d/%d", dayCheck, monthCheck, yearCheck));
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
package MSiA_422_HW5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestAppt {
	/*
	 * Description: This class contains many functionalities for creating and interacting with Appointment objects.
	 * Namely, a while loop a ran with menu options until the user decides to exit
	 * Functionalities:
	 * - create a new appointment calendar
	 * - write out the appointment calendar created
	 * - read in appointment calendars created with this program
	 * - add one-time, daily, or monthly appointments to an appointment calendar
	 * - search an appointment calendar for appointments on a given date
	 * - view all appointments in the current calendar loaded
	 */
	public static void main(String[] args) throws ParseException {
		/*
		 * Description: main method allows for creating a new or loading an existing appointment calendar and then brings up the menu for interacting with the calendar
		 * 
		 */
		while(true) {
			
		System.out.println("Would you like to begin a new appointment calendar or load a saved one? Please enter 1 to start new or enter 2 to load.");
		Scanner scan = new Scanner(System.in);
	    String initialInput = scan.nextLine();
	    String path = "appts.txt";
	    
	    if(initialInput.equals("1")) {
	    	ArrayList<Appointment> calendar = new ArrayList<>();
	    	while(true) {
	    		printMenu(calendar);
	    	}
	    }
	    
	    else if(initialInput.equals("2")) {
	    	//TODO--add functionality to read in txt
	    	try {
	    		ArrayList<Appointment> calendar = readData();
		    	while(true) {
		    		printMenu(calendar);
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
		else {
			System.out.println("Not a valid choice, please try again");
		}
	}
	    
}
	
	private static void printMenu(ArrayList<Appointment> calendar) throws ParseException {
		/*
		 * Description: This method print the menu of options for interacting with the appointment calendar and then performs an action based on the input
		 */
		String DASHES = new String(new char[80]).replace("\0", "-");
		System.out.println(DASHES);
		System.out.println("Please select one of the following options:");
		System.out.println("Enter 1 to add an appointment to your calendar");
		System.out.println("Enter 2 check for any scheduled appointments on a particular day");
		System.out.println("Enter 3 to save current appointment calendar");
		System.out.println("Enter 4 to view the current appointment calendar");
		System.out.println("Enter 'exit' to end the program");
		System.out.println(DASHES);
		Scanner scanMenu = new Scanner(System.in);
		String menuSelection = scanMenu.next();
		
		// call the addAppointment method if that selection is made by user
		if (menuSelection.equals("1")) {
			addAppointment(calendar);
			}
		
		// search the calendar for a given date of interest by calling the occursOn method for Appointment objects
		else if (menuSelection.equals("2")) {
			System.out.println("Please enter the date of interest in format, mm/dd/yyyy:");
			Scanner scan1 = new Scanner(System.in);
			try {
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String dateIn = scan1.next();
				LocalDate dateEntered = LocalDate.parse(dateIn, dateTimeFormatter);
				int year  = dateEntered.getYear();
				int month = dateEntered.getMonthValue();
				int day   = dateEntered.getDayOfMonth();
			
				ArrayList<Appointment> occursOnDate = new ArrayList<>();
				for (Appointment app : calendar) {
					if (app.occursOn(year,month,day) == true) {
						occursOnDate.add(app);
					}
				}
				if (occursOnDate.size()!= 0 ) {
					System.out.println("The following appointments occurs on this date:");
					for (Appointment app : occursOnDate) {
						System.out.println(app.getDescription());
					}
				}
				else {
					System.out.println("There are no appointments on this date.");
				}
			} catch(Exception e){
				System.out.println("Date parsing failed, there is an issue with the format of your date input, please follow the prompted format.");
			}
				
		}	
		// writes out the appointment calendar to a txt file to the user provided destination
		else if (menuSelection.equals("3")) {
			System.out.println("Please enter the filepath including filename:");
			Scanner scanpath = new Scanner(System.in);
			String pathname = scanpath.nextLine();
			String header = "DESC,START_DT,END_DT,APPT_TYPE";
			File file = new File(pathname);
			String[] writeLines = new String[calendar.size()];
			String appType = new String();
			for (int k = 0; k < calendar.size(); k++) {
				Appointment cal = calendar.get(k);
				if (cal instanceof OneTime) {
					appType = "OneTime";
				}
				else if (cal instanceof Monthly) {
					appType = "Monthly";
				}
				else {
					appType = "Daily";
				}
				
				writeLines[k] = cal.getDescription() + ","+ cal.dateToString(cal.getStartDate())+ ","+cal.dateToString(cal.getEndDate())+ ","+appType;
			}
			try {
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathname));
					bufferedWriter.write(header+"\n");
					for (String line : writeLines) {
						bufferedWriter.write(line+"\n");
					}
					bufferedWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Please verify that you provided a path in the correct syntax ");
			}

		}
		// returns all appointments if the user desires to view all
		else if(menuSelection.equals("4")) {
			if (calendar.size()!= 0 ) {
				System.out.println("Here are all your appointments");
				String appType = new String();
				for (Appointment app : calendar) {
					if (app instanceof OneTime) {
						appType = "OneTime";
					}
					else if (app instanceof Monthly) {
						appType = "Monthly";
					}
					else {
						appType = "Daily";
					}
					System.out.println(app.getDescription()+", "+appType+" start date: "+app.dateToString(app.getStartDate())+" end date: "+app.dateToString(app.getEndDate()));
				}
				}		
		}
		else if (menuSelection.equals("exit")) {
			System.exit(0);
			}
		else {
			System.out.println("Not a valid choice");
		}
	}
	
	private static void addAppointment(ArrayList<Appointment> calendar) throws ParseException {
		/*
		 * Description: This method allows the addition of a one-time, daily, or monthly appointment to the currently open appointment calendar
		 */
		System.out.println("What type of appointment would you like to add?:");
		System.out.println("Enter 1 to add a one time appointment");
		System.out.println("Enter 2 to add a recurring monthly appointment");
		System.out.println("Enter 3 to add a recurring daily appointment");
		Scanner scanAppt = new Scanner(System.in);
		String apptType = scanAppt.next();
		
		if(apptType.equals("1")) {
			System.out.println("Please enter the description for the appointment:");
			Scanner scan1 = new Scanner(System.in);
			String desc = scan1.nextLine();
			
			System.out.println("Please enter the appointment date in the format, mm/dd/yyyy:");
			Scanner scan2 = new Scanner(System.in);
			String dateIn = scan2.next();
			try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate dateEntered = LocalDate.parse(dateIn, dateTimeFormatter);
			int year  = dateEntered.getYear();
			int month = dateEntered.getMonthValue();
			int day   = dateEntered.getDayOfMonth();
			
			Appointment app = new OneTime(day,month,year,desc);
			calendar.add(app);
			} catch(DateTimeParseException e) {
				System.out.println("Please verify you input the correct format for this field, MM/dd/yyyy");
			}
		}
		
		else if(apptType.equals("2")) {
			
			System.out.println("Please enter the description for the appointment:");
			Scanner scan1 = new Scanner(System.in);
			String desc = scan1.nextLine();
			
			System.out.println("Please enter the appointment starting date in the format, mm/dd/yyyy:");
			Scanner scan2 = new Scanner(System.in);
			String dateIn = scan2.next();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate dateStart = LocalDate.parse(dateIn, dateTimeFormatter);
			
			int startDay   = dateStart.getDayOfMonth();
			
			System.out.println("Please enter the appointment end date in the format, mm/yyyy:");
			Scanner scan3 = new Scanner(System.in);
			String dateIn2 = scan3.next();
			DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("MM/yyyy");
			try {
			YearMonth yearMonth = YearMonth.parse(dateIn2,dateTimeFormatter2);
			LocalDate dateEnd = yearMonth.atDay(startDay);
			int startYear  = dateStart.getYear();
			int startMonth = dateStart.getMonthValue();
			int endYear  = dateEnd.getYear();
			int endMonth = dateEnd.getMonthValue();
			Appointment app = new Monthly(startDay,startMonth,startYear,endMonth,endYear,desc);
			calendar.add(app);
			} catch(DateTimeParseException e) {
				System.out.println("Please verify you input the correct format for this field, mm/yyyy:");
			}
		}
		
		else if(apptType.equals("3")) {
			System.out.println("Please enter the description for the appointment:");
			Scanner scan1 = new Scanner(System.in);
			String desc = scan1.nextLine();
			
			System.out.println("Please enter the appointment starting date in the format, mm/dd/yyyy:");
			Scanner scan2 = new Scanner(System.in);
			String dateIn = scan2.next();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate dateStart = LocalDate.parse(dateIn, dateTimeFormatter);
			
			System.out.println("Please enter the appointment end date in the format, mm/dd/yyyy:");
			Scanner scan3 = new Scanner(System.in);
			String dateIn2 = scan3.next();
			try {
			LocalDate dateEnd = LocalDate.parse(dateIn2, dateTimeFormatter);
			
			int startYear  = dateStart.getYear();
			int startMonth = dateStart.getMonthValue();
			int startDay   = dateStart.getDayOfMonth();
			int endYear  = dateEnd.getYear();
			int endMonth = dateEnd.getMonthValue();
			int endDay = dateEnd.getDayOfMonth();
			
			Appointment app = new Daily(startDay,startMonth,startYear,endDay,endMonth,endYear,desc);
			calendar.add(app);
			} catch(DateTimeParseException e) {
				System.out.println("Please verify you input the correct format for this field, MM/dd/yyyy");
			}
		}
		else {
			System.out.println("Not a valid choice, returning to main menu");
		}
	}
	
	private static ArrayList<Appointment> readData() throws IOException {
		/*
		 * Description: This method reads in an existing appointment calendar based on the path provided by the user
		 */
		System.out.println("Please enter the filepath including filename:");
		Scanner scanpath = new Scanner(System.in);
		String pathname = scanpath.nextLine();
	 	
		ArrayList<Appointment> apps = new ArrayList<Appointment>();
	 	File file = new File(pathname);
	 	
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line = bufferedReader.readLine(); //skip header
		 	while ((line = bufferedReader.readLine()) != null) {
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		 		String desc = line.split(",")[0];
		 		
		 		LocalDate startDate = LocalDate.parse(line.split(",")[1], dateTimeFormatter);
		 		LocalDate endDate = LocalDate.parse(line.split(",")[2], dateTimeFormatter);
		 		
				int startYear  = startDate.getYear();
				int startMonth = startDate.getMonthValue();
				int startDay   = startDate.getDayOfMonth();
				int endYear  = endDate.getYear();
				int endMonth = endDate.getMonthValue();
				int endDay = endDate.getDayOfMonth();
		 		
		 		String appType = line.split(",")[3];

				if (appType.equals("OneTime")) {
					apps.add(new OneTime(startDay,startMonth,startYear,desc));
				}
				else if (appType.equals("Monthly")) {
					apps.add(new Monthly(startDay,startMonth,startYear,endMonth,endYear,desc));
				}
				else {
					apps.add(new Daily(startDay,startMonth,startYear,endDay,endMonth,endYear,desc));
				}
		 	}
	      }catch (Exception e) {
	    	  System.out.println("Read failed, verify that the provided path is correct");
	      }       
	    return(apps);
	}
}

package MSiA_422_HW4;

import java.util.Scanner;

public class CurrencyConverter {
/*
 * Summary: class serves to perform currency conversions between USD and Japanese Yen or vice-versa
 * 
 * Description: An option menu is looped through that has 3 options, changing the exchange rate, performing a conversion, or exiting the program
 */
	
	public static void main(String[] args) {
		/*
		 * main method calls the run_currency program which provides the context menu
		 */
		run_currency_program();
		}
	
	
	private static void run_currency_program() {
		/*
		 * Summary: this method calls the method for setting the initial exchange rate. Then it calls printmenu() method that runs the context menu on a loop until exit is input
		 */
		double curr_rate = set_initial_rate();
	    while(true){
	    	printmenu(curr_rate);
	    	}
	    }
	
	private static void printmenu(double rate_use) {
		/*
		 * Summary: this method prints the context menu which has three options (changing the exchange rate, performing a conversion, or exiting the program) that lead to other methods/options
		 * Inputs: requires an initial double to be input for the exchange rate between USD and Japanese Yen
		 */	
		String DASHES = new String(new char[80]).replace("\0", "-");
		System.out.println(DASHES);
		System.out.println("Enter 1 to change the currency rate");
		System.out.println("Enter 2 to perform a currency conversion calculation");
		System.out.println("Enter 'exit' to end the program");
		System.out.println(DASHES);
		Scanner scan_menu = new Scanner(System.in);
		String menu_selection = scan_menu.next();
		
		//Based on the user selection, a specific method is called to perform the next task
		if (menu_selection.equals("1")) {
			double curr_rate = change_curr_rate(rate_use);
			System.out.println(curr_rate);
			}
		
		else if (menu_selection.equals("2")) {
			convert_amt(rate_use);
			}
		
		else if (menu_selection.equals("exit")) {
			System.exit(0);
			}
		else {
			System.out.println("Not a valid choice");
		}
		}
	
	private static double change_curr_rate(double prev_rate) 
	{
		/*
		 * Summary: This method changes the exchange rate between USD and Japanese Yen based on user input. If the input is not acceptable, the rate remains as what is was before this method was called
		 * Inputs: The current rate being used
		 * Outputs: The new exchange rate
		 */	
		double new_rate = prev_rate;
		System.out.println("Enter price for one US Dollar in Japanese Yen");
		Scanner scan = new Scanner(System.in);
		String input_rate = scan.next();
	    try {
	    	new_rate = Double.parseDouble(input_rate);
	    	}
	    catch(Exception e) {
	    	System.out.println("Input was not an appropriate real number, please try again following through the menu.");
	    	}
	    System.out.println("Current exchange rate being used is 1 USD = "+new_rate+" JPY");
	    return new_rate;
	}
	
	private static double set_initial_rate() 
	{
		/*
		 * Summary: This method is called to have the user set an initial USD to JPY exchange rate
		 * Outputs: Input USD to JPY as a double
		 */	
	    double curr_rate = 0;
		Scanner scan = new Scanner(System.in);
	    System.out.println("Enter price for one US Dollar in Japanese Yen");
	    String input_rate = scan.next();
	    while(true) 
	    {
		    try
		    	{
		    	curr_rate = Double.parseDouble(input_rate);
		    	break;
		    	}
		    catch(Exception e) 
		    	{
		    	System.out.println("Input was not an appropriate choice, please try again following through the menu.");
		    	}	
	    }
	    return curr_rate;
	}
	
	private static void convert_amt(double curr_rate) 
	{
		/*
		 * Summary: This method converts an input amount of currency from USD to JPY or JPY to USD
		 */	
		System.out.println("Type 1 to convert from USD to Yen or type 2 to convert from Yen to USD");
		Scanner scan_conv = new Scanner(System.in);
		String conv_choice = scan_conv.next();
		if (conv_choice.equals("1")) 
		{
			System.out.println("Type the amount in USD");
			Scanner amt_in = new Scanner(System.in);
			String amt = amt_in.next();
		    try
	    	{
		    	double amount = Double.parseDouble(amt);
				double final_amt = curr_rate*amount;
				System.out.printf("The converted amount in Yen is %.2f: %n", final_amt);
	    	}
		    catch(Exception e) 
	    	{
		    	System.out.println("Input was not an appropriate choice, please try again following through the menu");
	    	}	
		}
		
		else if (conv_choice.equals("2")) 
		{
			System.out.println("Type the amount in JPY");
			Scanner amt_in = new Scanner(System.in);
			String amt = amt_in.next();
		    try
	    	{
		    	double amount = Double.parseDouble(amt);
				double final_amt = curr_rate*amount;
				System.out.printf("The converted amount in USD is %.2f: %n", final_amt);
	    	}
		    catch(Exception e) 
	    	{
		    	System.out.println("Input was not an appropriate choice, please try again following through the menu");
	    	}	
		}
		else 
		{
			System.out.println("Input was not an appropriate choice, please try again following through the menu");
		}
	}
}
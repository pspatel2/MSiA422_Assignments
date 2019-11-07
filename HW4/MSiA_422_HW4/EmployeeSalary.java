package MSiA_422_HW4;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.*;
public class EmployeeSalary 
{
	/*
	 * Summary: class serves to explore and perform analysis on a provided employee file
	 * Description: For the given input file, this class prints the number of employees in the file, the employee with the highest salary, the average salary, the average age, and writes out the employees ordered by salary
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
//		String path_to_file = "C:\\Users\\pspat\\Documents\\MSiA\\MSiA_422\\MSiA422_Assignments\\HW4\\";
		String full_path = "employees.txt";
//		String full_path = path_to_file+"\\"+file_name;
		
		List<ArrayList<String>> data = read_data(full_path);
		count_employees(data);
		highest_salary_emp(data);
		Double avg_val = average_salary(data);
		System.out.printf("The average salary of the employees in this file is: %.2f ", avg_val);
		System.out.println();
		emps_above_avg_sal(data);
		get_avg_age(data);
		sort_write(data,full_path);
		System.out.println("Program closing...");
	}
	
	private static List<ArrayList<String>> read_data(String pathname) throws IOException {
		/*
		 * Summary: reads in the given txt file and parses the data into an ArrayList of ArrayLists
		 * Inputs: path to the txt file to be read
		 * Outputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 */		
	 	File file = new File(pathname);
	 	
	 	ArrayList<String> employees = new ArrayList<String>();
	 	ArrayList<String> dob = new ArrayList<String>();
	 	ArrayList<String> salary = new ArrayList<String>();
		List<ArrayList<String>> emp_data = new ArrayList<ArrayList<String>>();
	 	
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
		 	String line; // skip header
		 	while ((line = bufferedReader.readLine()) != null) {
		 		employees.add(line.split(",")[0]);
		 		dob.add(line.split(",")[1]);
		 		salary.add(line.split(",")[2]);
		 	}
		 	emp_data.add(employees);
		 	emp_data.add(dob);
		 	emp_data.add(salary);
	 	}
	 	return emp_data;
	 }
	
	private static void count_employees(List<ArrayList<String>> data) {
		/*
		 * Summary: Counts the number of employees from the text file
		 * Inputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 * Outputs: Count of employees in the file
		 */		
		ArrayList<String> employees = data.get(0);
		System.out.println("There are "+ employees.size()+ " employees in the file");
	}
	
	private static void highest_salary_emp(List<ArrayList<String>> data) {
		/*
		 * Summary: Counts the number of employees from the text file
		 * Inputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 * Outputs: Count of employees in the file
		 */	
		ArrayList<String> employees = data.get(0);
		ArrayList<String> salary = data.get(2);
		ArrayList<Double> salary_dbl = get_double_array(salary);
		Double maxSal = Collections.max(salary_dbl);
		Integer maxSalIdx = salary_dbl.indexOf(maxSal);
		System.out.println("The employee with the max salary is:"+ employees.get(maxSalIdx));
	}
	
	private static void emps_above_avg_sal(List<ArrayList<String>> data) {
		/*
		 * Summary: Determines which employees make above the average salary 
		 * Inputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 * Outputs: List of employees who make more than the average salary in the file
		 */	
		ArrayList<String> employees = data.get(0);
		ArrayList<String> salary = data.get(2);
		ArrayList<Double> salary_dbl = get_double_array(salary);
		Double avgSal = average_salary(data);
		
		List<String> emps_abv_avg=new ArrayList<String>();
		for(int i=0; i < salary_dbl.size(); i++) {
			Double sal = salary_dbl.get(i);
			if(sal>avgSal) {
				emps_abv_avg.add(employees.get(salary_dbl.indexOf(sal)));
			}
		}
		System.out.println("There are " + emps_abv_avg.size()+ " employees from the file make more than the average salary. They are:"+emps_abv_avg);
	}
	
	private static ArrayList<Double> get_double_array(ArrayList<String> stringArray) {
		/*
		 * Summary: Convert ArrayList<String> to ArrayList<Double>
		 * Inputs: ArrayList<String>
		 * Outputs: Original input but in ArrayList<Double>
		 */	
        ArrayList<Double> result = new ArrayList<Double>();
        for(String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Double.parseDouble(stringValue));
            } catch(NumberFormatException nfe) {
               //System.out.println("Could not parse " + nfe);
                System.out.println("NumberFormat"+ "Parsing failed! " + stringValue + " can not be an integer");
            } 
        }       
        return result;
    }
	
	private static ArrayList<LocalDate> get_date_array(ArrayList<String> stringArray) {
		/*
		 * Summary: Convert ArrayList<String> to ArrayList<LocalDate>
		 * Inputs: ArrayList<String>
		 * Outputs: Original input but in ArrayList<LocalDate>
		 */	
		ArrayList<LocalDate> result = new ArrayList<LocalDate>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for(String stringValue : stringArray) {
            try {
                result.add(LocalDate.parse(stringValue, dateTimeFormatter));
            	} 
            catch(DateTimeParseException  e) {
               //System.out.println("Could not parse " + e);
                System.out.println("LocalDate"+ "Parsing failed! " + stringValue + " can not be a LocalDate");
            } 
        }       
        return result;
    }
	
	private static Double average_salary(List<ArrayList<String>> data) {
		/*
		 * Summary: Compute average of the salary data from the txt file
		 * Inputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 * Outputs: Average of the salaries from the file
		 */	
		ArrayList<String> salary = data.get(2);
		ArrayList<Double> salary_dbl = get_double_array(salary);

		Double sum = 0.0;
		
		for (Double sal : salary_dbl) {
		        sum += sal;
		    }
		
		Double avgSal = sum.doubleValue()/salary_dbl.size();
		return avgSal;
		}
	
	private static void get_avg_age(List<ArrayList<String>> data) {	
		/*
		 * Summary: Computes the average age of employees within the file based on date of births
		 * Inputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 * Outputs: Average age (as a double) of employees within the file
		 */	
		ArrayList<String> dob = data.get(1);
		ArrayList<LocalDate> dob_dates = get_date_array(dob);
		LocalDate today_date = LocalDate.now();
		ArrayList<Integer> ages =  new ArrayList<Integer>();
		for(LocalDate dobVal : dob_dates) {
			Period p = Period.between(dobVal, today_date);
			ages.add(p.getYears());
		}
		
		Double sum = 0.0;
		
		for (Integer age : ages) {
	        sum += age;
	    }
		Double avgAge = sum/ages.size();
		System.out.printf("The average age of the employees in this file is: %.2f", avgAge);
		System.out.println(); 
	}
	
	private static void sort_write(List<ArrayList<String>> data,String pathname) throws IOException {
		/*
		 * Summary: Writes out a CSV file with employee names sorted by salary (descending)
		 * Inputs: ArrayList of ArrayList<String> containing names, date of births, and salary
		 * Outputs: CSV file to provided path
		 */	
		ArrayList<String> emp_names = data.get(0);
		ArrayList<String> emp_names_sorted = new ArrayList<String>();
		ArrayList<String> sal = data.get(2);
		ArrayList<Double> sal_dbl = get_double_array(sal);
//		Collections.sort(sal_dbl,Collections.reverseOrder());
		
	    Map<Double, Integer> map = new TreeMap<Double, Integer>();
	    for (int i = 0; i < sal_dbl.size(); ++i) {
	        map.put(sal_dbl.get(i), i);
	    }
	    Map<Double, Integer> descMap = new TreeMap<>(Collections.reverseOrder());
	    descMap.putAll(map);
	    Collection<Integer> indices = descMap.values();
	    Collection<Double> sal_vals = descMap.keySet();
	    int[] idx_array = indices.stream().mapToInt(i->i).toArray();
	    double[] sal_sorted = sal_vals.stream().mapToDouble(i->i).toArray();
		for(int i=0; i<descMap.values().size();i++) {
			emp_names_sorted.add(emp_names.get(idx_array[i]));
		}
		//writing the output file
		String header = "NAME,SALARY";
		
		String[] writeLines = new String[emp_names_sorted.size()];
		for (int k = 0; k < emp_names_sorted.size(); k++) {
			writeLines[k] = emp_names_sorted.get(k) + "," + sal_sorted[k];
		}
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathname.replace("txt", "csv")));
		bufferedWriter.write(header+"\n");
		for (String line : writeLines) {
			bufferedWriter.write(line+"\n");
		}
		bufferedWriter.close();
		System.out.println("A csv file has been written out to your provided path. It is called employees.csv. It contains the employee names sorted by salary in descending order"); 
	}

}

	

/*
********************************************************************************
* Student Name: Mark Land                             Moorpark College         *
* Program Name: Hw01b.java                            CS M10J (Intro Java)     *
* Comment     : Files, Loops, Method Calls Program    Intro to Prog using Java *
*               homework 01b, stock tickers ver2      Fall of 2015             *
*               Due on 10-6-15                        Prof. John C. Reynolds   *
********************************************************************************
*/

/*
Note to Prof. John Reynolds:

This project can handle any quantity of stock ticker names and any quantity of
each of their prices. Thus, it's overly-complicated again, as I struggle to just
get it to work; be functional. Hopefully, as I learn more of Java (and I develop
better programming logic), I can go back and streamline it (more efficient, more
clean, concise, simplify, better, etc).

It does require that each of the tickers (and their prices) be separated by a
newline, and the ticker names and their prices separated by spaces, and I used
2D arrays for it, so no appending~adding more items to it (have to edit the data
stocks file, if you want to add~remove items), for a poor quick simple example:

AAAA 10.00 11.11 12.56
bbbb 10.34 12.54 99.9999 1.00 00.35 5 5 5 5 5
CcCc 77.66 44.88
dDDdDDd 10.00 10.00 10.00 10.00 10.00 10.00 10.00 10.00 10.00 10.00 10.000001
*/

/*
Program Purpose:

The purpose of this program is to read a data text file, using a menu, find,
get, calculate, and display: max, min, and avg of a specific stock, the stock
ticker with highest price or lowest price, select~change the data text file
being used by the program, depending on the user's choice~selection, and to
be able quit the program.
*/

// Packages~Libraries~Modules:

import java.util.Scanner;
import java.util.Vector;
import java.io.FileReader;
import java.io.FileNotFoundException;

// Classes:

public class Hw01b
{
	// Class-Global Constants ~ Variables ~ Object Instantiation:
	
	static Scanner console = new Scanner(System.in);
	
	static boolean first_time = true;
	
	static char menu_choice;
	
	static double ticker_price, price_input, average, maximum, minimum,
		highest_price, lowest_price, sum = 0.0;
	
	static String ticker_name, name_input, highest_name, lowest_name,
		file_name = "stocks.txt";
	
	static final String menu_display = "\n\nEnter \'1\' to get [M]ax, min, " +
		"and avg of a stock\nEnter \'2\' to get stock ticker with [H]ighest " +
		"price\nEnter \'3\' to get stock ticker with [L]owest price\nEnter " +
		"\'4\' to [C]hange the stock file name\nEnter \'0\' to [Q]uit the " +
		"program\n\n\nYour choice: ";
		
	static int rows, columns, row_count, column_count;
	
	static Vector<String> stock_name_vector = new Vector<String>();
	
	static String[][] stock_data_matrix;

	// Methods~Functions:

	public static void main(String[] args) throws FileNotFoundException
	{
		Header();
		Purpose();
		Program();
	}
	
	public static void Header()
	{
		System.out.println("\n\n****************************************" +
			"****************************************");
		System.out.println("* Student Name: Mark Land               " +
			"              Moorpark College         *");
		System.out.println("* Program Name: Hw01b.java              " +
			"              CS M10J (Intro Java)     *");
		System.out.println("* Comment     : Files, Loops, Method Cal" +
			"ls Program    Intro to Prog using Java *");
		System.out.println("*               homework 01b, stock tick" +
			"ers ver2      Fall of 2015             *");
		System.out.println("*               Due on 10-6-15          " +
			"              Prof. John C. Reynolds   *");
		System.out.println("****************************************" +
			"****************************************\n\n");
	}
	
	public static void Purpose()
	{
		System.out.print("The purpose of this program is to read a data text " +
			"file, using a menu, find, get\n, calculate, and display: max, " +
			"min, and avg of a specific stock, the stock \nticker with highest "
			+ "price or lowest price, select~change the data text file \nbeing "
			+ "used by the program, depending on the user's choice~selection, "
			+ "and to be\nable quit the program.\n");
	}
	
	public static void Program() throws FileNotFoundException
	{	
		Read_File_Data();
		Menu();
	}
	
	public static void Menu() throws FileNotFoundException
	{
		do
		{
			System.out.print(menu_display);
			String local_string = console.next();
			menu_choice =
				local_string.length() == 1 ? local_string.charAt(0) : '\u0000';
			switch (menu_choice)
			{
				case '1':
				case 'm':
				case 'M':
					Get_Stock_Stats();
					break;
				case '2':
				case 'h':
				case 'H':
					Return_Stock_High_Low(2);
					break;
				case '3':
				case 'l':
				case 'L':
					Return_Stock_High_Low(3);
					break;
				case '4':
				case 'c':
				case 'C':
					System.out.print("\n\nEnter a stock data file\'s name: ");
					file_name = console.next();
					Read_File_Data();
					break;
				case '0':
				case 'q':
				case 'Q':
					System.out.print("\n\nThank you for using this " +
					"program, have a nice day, goodbye.\n\n");
					break;
				default:
					System.out.print("\n\nWrong input, try again\n\n");
			}
		}
		while ((menu_choice != '0') && (menu_choice != 'q') &&
			(menu_choice != 'Q')); /* DeMorgan's Laws (from my Python class
			text book, pg. 250, for reference for myself):
				not (a || b) == (not a) && (not b)
				not (a and b) == (not a) || (not b)
			-- I still can't yet grasp the how the logic of it works, sighs
			-- Luckily, there was no logic issue with having 3 conditionals:
				I was worried that having three of them would be affected by
				DeMorgan's Laws' logic, which I wouldn't be able to deal with
				as I don't even understand how the logic works with only 2 of
				them (the conditionals), luckily the logic remains the same */
		System.exit(0);
	}
	
	public static void Read_File_Data() throws FileNotFoundException
	{	
		// This function gets and stores the file's data into a 2D Array, and
		//		to reduce operations, I find~get the highest and lowest values
		//		and I add the ticker name values to the vector for holding them:
	
		Scanner read_file = new Scanner(new FileReader(file_name));
		
		if (read_file.hasNext())
		{			
			while (read_file.hasNextLine() && read_file.nextLine() != null)
			{
				rows++;
			}
			
			read_file.close();
			
			String[][] stock_data_matrix = new String[rows][];
			
			Scanner read_file_again = new Scanner(new FileReader(file_name));
			
			for (int row_count = 0; row_count < stock_data_matrix.length;
				row_count++)
			{
				String local_string = read_file_again.nextLine();
				String[] local_array = local_string.split(" ");
				
				for (int count = 0; count < local_array.length; count++)
				{
					columns++;
				}
					
				stock_data_matrix[row_count] = new String[columns];
				
				columns = 0;
			}
			
			read_file_again.close();
				
			Scanner read_file_again_again =
				new Scanner(new FileReader(file_name));
			
			for (int row_count = 0; row_count < stock_data_matrix.length;
				row_count++)
			{
				for (int column_count = 0; column_count <
					stock_data_matrix[row_count].length; column_count++)
				{
					stock_data_matrix[row_count][column_count] =
						read_file_again_again.next();
					
					String local_str =
						stock_data_matrix[row_count][column_count];
						
					Get_Stock_High_Low(local_str);
					
					if (Try_Parse_Double(local_str) &&
						Double.parseDouble(local_str) == highest_price)
					{
						highest_name = stock_data_matrix[row_count][0];
					}
					else if (Try_Parse_Double(local_str) &&
						Double.parseDouble(local_str) == lowest_price)
					{
						lowest_name = stock_data_matrix[row_count][0];
					}
				}
			}
			read_file_again_again.close();
		}
		else
		{
			read_file.close();
			
			System.out.print("\n\nThere's no data in the file for this program."
				+ " Try re-running this program using \na different file or "
				+ "fixing up your file, or the default file (stocks.txt)\n\n");
				
			System.exit(0);
		}
		
		rows = 0;
		columns = 0;
		
		row_count = 0;
		column_count = 0;
		
		first_time = true;
	}
	
	public static void Get_Stock_Stats()
	{
		System.out.print("\n\nEnter a stock ticker: ");
		name_input = console.next();
		
		int local_count = 1;
		
		for (String str : stock_name_vector)
		{
			System.out.print("\nMark_1\n");
			if (name_input.compareToIgnoreCase(str) == 0)
			{
				System.out.print("\nMark_2\n");
				ticker_name = name_input.toUpperCase();
				
				System.out.print("\n" + name_input + "\n");
				System.out.print("\n" + ticker_name + "\n");
			
				for (int row_count = 0; row_count < stock_data_matrix.length;
					row_count++)
				{
					System.out.print("\nMark_3\n");
					if (ticker_name == stock_data_matrix[row_count][0])
					{
						System.out.print("\nMark_4\n");
						for (int column_count = 0;
							column_count < stock_data_matrix[row_count].length;
							column_count++)
						{
							String local_str =
								stock_data_matrix[row_count][column_count];
							
							if (Try_Parse_Double(local_str) ||
								Try_Parse_Double(local_str))
							{
								Double local_dbl =
									Double.parseDouble(local_str);
						
								sum += local_dbl;
						
								if (first_time)
								{
									maximum = local_dbl;
									minimum = local_dbl;
									first_time = false;
								}
								else if (maximum < local_dbl)
								{
									maximum = local_dbl;
								}
								else if (minimum > local_dbl)
								{
									minimum = local_dbl;
								}
							}
						}
					
						average = sum / column_count;
					
						break;
					}
				}
			
				System.out.printf("%n%n%s min: %.2f max: %.2f avg: $.2f",
					ticker_name, minimum, maximum, average);
			
				row_count = 0;
				column_count = 0;
				sum = 0.0;
				
				first_time = true;
				
				break;
			}
			else
			{
				local_count++;
				
				if (local_count > stock_name_vector.size())
				{
					System.out.print("\n\n" + name_input + "was not found\n\n");
				}
			}
		}
	}
	
	public static void Get_Stock_High_Low(String str)
	{
		if (Try_Parse_Double(str) || Try_Parse_Integer(str))
		{
			Double dbl = Double.parseDouble(str);
			
			if (first_time)
			{
				highest_price = dbl;
				lowest_price = dbl;
				first_time = false;
			}
			else if (highest_price < dbl)
			{
				highest_price = dbl;
			}
			else if (lowest_price > dbl)
			{
				lowest_price = dbl;
			}
		}
		else
		{
			stock_name_vector.addElement(str);
		}
	}
	
	public static void Return_Stock_High_Low(int choice)
	{
		if (choice == 2)
		{
			System.out.printf("%n%n%s has highest price of %.2f%n%n",
				highest_name, highest_price);
		}
		else if (choice == 3)
		{
			System.out.printf("%n%n%s has lowest price of %.2f%n%n",
				lowest_name, lowest_price);
		}
	}
	
	public static boolean Try_Parse_Double(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
	
	public static boolean Try_Parse_Integer(String str)
	{
		try
		{
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}

/* Huynh_Anthony_A4.java
 * Anthony-Tien Nhat Huynh
 * 26 November 2017 
 * 
 * This program allows users to build a special tree from leaves to root. 
 * The input will be the leaves.
 * The program will combine the lowest value leaves and create its parents.
 * This is looped till the root is reached.
 * The output will the root along with its frequencies value. 
 *
 */

import java.util.Arrays; // Arrays is used to copy arrays
import java.util.Scanner; // Scanner is used for input values. 

public class Huynh_Anthony_A4{
	
	// uniqueCharsMethod() will take in a String. 
	// It will return an integer. 
	// The purpose is to see the total number of unique characters in the given user input.
	public static int uniqueCharsMethod(String userInput) {
	    String uniqueChars = "";
	    int numUniqueChars = 0;
	    for (int i = 0; i < userInput.length(); i++) {
	    	// The if-statement checks if the character is unique.
	        if (!uniqueChars.contains(String.valueOf(userInput.charAt(i)))) {
	        	uniqueChars += userInput.charAt(i);
	        	numUniqueChars += 1; 
	        }
	    }
	    
	    return numUniqueChars;
	}
	
	// initializeArrays() take in a String, a String array, and integer array.
	// It will not return anything, however change the values for each element of the arrays passed. 
	// The purpose of this is to initialize two of the arrays for the beginning of the program. 
	// (modified version of uniqueCharsMethod() above.)
	public static void initializeArrays(String userInput, String[] uniqueCharsArray, int[] frequency) {
	    String uniqueChars = "";
	    int numUniqueChars = 0;
	    
	    // The for-loop below will be initializing uniqueCharsArray.
	    // It will also count the total number of unique characters. 
	    for (int i = 0; i < userInput.length(); i++) {
	        if (!uniqueChars.contains(String.valueOf(userInput.charAt(i)))) {
	        	uniqueChars += userInput.charAt(i);
	        	uniqueCharsArray[numUniqueChars] = String.valueOf(uniqueChars.charAt(numUniqueChars));
	        	numUniqueChars += 1; 
	        }
	    }
	    
	    // set all frequency elements to zero in order to load the correct values.
	    for (int i = 0; i < frequency.length; i++) {
	    	frequency[i] = 0; // making all the elements of frequency equal to 0. 
	    }
	    
	    // setting the correct values for frequencies
	    for (int i = 0; i < uniqueCharsArray.length; i++) {
		    for (int j = 0; j < userInput.length(); j++) {
			       if( uniqueCharsArray[i].equals(String.valueOf(userInput.charAt(j))) )
			       {
			    	   frequency[i] += 1; // if the string is seen, it will be added to its corresponding frequency element.
			       }
		    }
	    }
	}
	
	
	// below is from program 2 but modified
	// organizeArrays() will take in one string array and an integer array. 
	// It will return not return anything; however, change the order of the elements in the array. 
	// The purpose for this method is to sort the array so we can later combine the first and second elements. 
	
	public static void organizeArrays(String[] uniqueCharsArray, int[] frequency){
		
			int minimumElement = 0;
			String tempName; 
			int tempValue; 
			
			//Selection Sorting the arrays.
			for (int j = 0; j < frequency.length - 1 ;j++)
			{
				minimumElement = j;
				for (int k = j + 1; k < frequency.length ;k++)
				{
					if (frequency[k] < frequency[minimumElement])
					{
						minimumElement = k;
					}
				}
				tempValue = frequency[j];
				tempName = uniqueCharsArray[j];
				frequency[j] = frequency[minimumElement];
				uniqueCharsArray[j] = uniqueCharsArray[minimumElement];
				frequency[minimumElement] = tempValue;
				uniqueCharsArray[minimumElement] = tempName; 
			}
	}

	
 //above is from program 2
	
	
	// displayArrays() will take in one string array and an integer array. 
	// It will return not return anything.
	// The purpose for this method is to display the nodes and its frequency in a table.
	public static void displayArrays(String[] uniqueCharsArray, int[] frequency){
		
		//Displaying the items with their nodes, and their frequency. 
		System.out.printf("%-25s%-1s%9s\n", "Nodes","", "Frequency");
		System.out.println("-----------------------------------");
		

		for (int l = 0; l < frequency.length ;l++)
		{

			for (int m = 0; m < frequency.length ;m++)
			{
				if (uniqueCharsArray[l] == uniqueCharsArray[m])
				{
					System.out.printf("%-4s%-10s%17s\n", uniqueCharsArray[m], "", frequency[m]);
				}
			}
		}
	}

	// combineArrays() will take in one string array and an integer array. 
	// It will return not return anything; however, combine the first two elements and put it in element 0.
	// The purpose for this method is to combine the first two elements (creating a parent node).
	public static void combineArrays(String[] uniqueCharsArray, int[] frequency){
		uniqueCharsArray[0] = uniqueCharsArray[0] + uniqueCharsArray[1];
		frequency[0] = frequency[0] + frequency[1];
	}

	

public static void main(String[] args){
		Scanner input = new Scanner(System.in); // To enable Scanner for input.
		
		String userInput = "";
		String junk = "";
		boolean loop = false; 
		
		do {
			loop = false;
			System.out.println("Please enter some text: ");
			userInput = input.nextLine();
			if (userInput.equals("") || userInput.equals(" ") || userInput.isEmpty() ) { // Empty string check
	            System.out.println("Sorry, that is invalid. Empty text is not allowed. \n Try again.");
	            loop = true;
			}
			else
			{
				userInput = userInput.toLowerCase(); // Convert all cases in user input to lowercase for later. 

				System.out.println("You have entered (converted to lowercase): ");
				System.out.println(userInput);
				System.out.println("-----------------------------------------------");
				int numUniqueChars = uniqueCharsMethod(userInput);
				String[] uniqueCharsArray = new String[numUniqueChars]; // Creating the array for the nodes. By using length, creates more spaces than needed.
				int[] frequency = new int[numUniqueChars];
				
				initializeArrays(userInput, uniqueCharsArray, frequency); //initializes the arrays
				//arrays are unorganized at this point. 
				/*
			    for (int i = 0; i < uniqueCharsArray.length; i++) {
			    	System.out.println(uniqueCharsArray[i]);
			    }
			    for (int i = 0; i < frequency.length; i++) {
			    	System.out.println(frequency[i]);
			    }
			    */
				System.out.println("Initial Nodes:");
				organizeArrays(uniqueCharsArray, frequency);
				displayArrays(uniqueCharsArray, frequency);
				System.out.println("--------------------------------");
				System.out.println("Type any key and then [ENTER] to continue");
				userInput = input.nextLine();
				
				boolean loopInner = false; //This boolean is use to prevent infinite loop for the loop below.
				
				do { //This loop will combine the lowest two elements. 
					loopInner = false; 
					//check if there is two elements. if so, combine the two and print. Exit program gracefully. 
					if (uniqueCharsArray.length == 2)
					{
						System.out.println("Root:");
						
						combineArrays(uniqueCharsArray, frequency);
						uniqueCharsArray = Arrays.copyOf(uniqueCharsArray, uniqueCharsArray.length - 1);
						frequency = Arrays.copyOf(frequency, frequency.length - 1);
						
						System.out.printf("%-25s%-1s%9s\n", "Nodes","", "Frequency");
						System.out.println("-----------------------------------");

						System.out.printf("%-25s%-1s%9s\n", uniqueCharsArray[0], "", frequency[0]);
						loopInner = false; 
						loop = false; 
					}
					else if (uniqueCharsArray.length == 1)
					{
						System.out.println("The Initial Node is also the Root:");
						System.out.printf("%-25s%-1s%9s\n", "Nodes","", "Frequency");
						System.out.println("-----------------------------------");

						System.out.printf("%-25s%-1s%9s\n", uniqueCharsArray[0], "", frequency[0]);
						loopInner = false; 
						loop = false; 
					}
					else 
					{ 	// if check see that there are more than two elements, combine lowest two and loop again. 
						combineArrays(uniqueCharsArray, frequency);
						for (int i = 1; i < uniqueCharsArray.length - 1; i++)
						{
							uniqueCharsArray[i] = uniqueCharsArray[i + 1];
							frequency[i] = frequency[i + 1];
						}
						uniqueCharsArray = Arrays.copyOf(uniqueCharsArray, uniqueCharsArray.length - 1);
						frequency = Arrays.copyOf(frequency, frequency.length - 1);
						organizeArrays(uniqueCharsArray, frequency);
						displayArrays(uniqueCharsArray, frequency);
						System.out.println("--------------------------------");
						System.out.println("Type any key and then [ENTER] to continue");
						junk = input.nextLine();
						loopInner = true; 
					}
					
				}while(loopInner);
			}
		
		}while(loop);
		System.out.println("-----------------------------------");
		System.out.println("Goodbye!");
	}
}

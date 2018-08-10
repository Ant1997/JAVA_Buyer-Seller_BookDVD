/* Huynh_Anthony_A5.java
 * Anthony-Tien Nhat Huynh
 * 26 November 2017 
 * 
 * This combines assigment 2 and 3.
 *
 */

import java.io.*;
import java.text.DecimalFormat;
// Scanner is used for input values. 
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Huynh_AnthonyA5{
	
	public static void mainMenu(){

			System.out.println("**Welcome to the Comets Books and DVDs Store**\n" + 
					"Please select your role:\n" + 
					"A – store manager\n" + 
					"B – customer\n" + 
					"C – exit store\n" + 
					"******************************************************************************\n");
	}
	public static boolean loginValidation(String[] userName, String[] passWord){
		Scanner input = new Scanner(System.in);
		String attemptUsername, attemptPassword; 
		boolean existUsername = false;
		int userNameIndex = -1; 
		boolean matchPassword = false; 
		
		System.out.println("Please enter your username: ");
		//boolean isNonEmptyString (String s); USE VALIDATION!
		attemptUsername = input.nextLine();
		System.out.println("Please enter your password: ");
		//boolean isNonEmptyString (String s); USE VALIDATION!
		attemptPassword = input.nextLine();
		
		for (int i = 0; i < userName.length; i++)
		{
			if (userName[i].contentEquals(attemptUsername))
			{
				//System.out.println("Correct username!");
				existUsername = true; 
				userNameIndex = i; 
			}
		}
		if (existUsername)
		{
			if (passWord[userNameIndex].contentEquals(attemptPassword) )
			{
				//System.out.println("Correct password!");
				matchPassword = true; 
				return true; 
			}
		}
		return false;
}

//Below is for the managers-------------------------------------------------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void displayMenu(){
		String [] displayMenuTexts = {"**Welcome to the Comets Books and DVDs Store (Catalog Section)**\n",
		"Choose from the following options:", "1 - Add Book ",
		"2 - Add AudioBook", "3 - Add DVD ", 
		"4 - Remove Book", "5 - Remove DVD ", "6 - Display Catalog", "7 - Create backup file", 
		"9 - Exit store"};

		for (int i = 0; i < displayMenuTexts.length ;i++)
		{
			System.out.println(displayMenuTexts[i]);
		}
	}

	// Creating an abstract class for CatalogItem- 
	// (Instruction said that this class is not concrete).
	
	public static abstract class CatalogItem implements Comparable<CatalogItem>{
		private String title;
		private double price; 
		
		public CatalogItem(String title, double price) {
			this.title = title; 
			this.price = price; 
		}
		
		public String getTitle(){
			return this.title; 
		}
		
		public double getPrice(){
			return this.price; 
		}
		
		void setPrice(double SetPriceNumber){
			this.price = SetPriceNumber; 
		}
		
		@Override 
		public int compareTo(CatalogItem anotherCatalogItem) {
			if (this.getPrice() == anotherCatalogItem.getPrice()){
				return 0;
			}
			else if(this.getPrice() < anotherCatalogItem.getPrice()) {
				return -1;
			}
			else{
				return 1;
			}
		}

	}
	
	// Creating a class for Book
	public static class Book extends CatalogItem{
		DecimalFormat df = new DecimalFormat("#.##"); // Format for price.
		private int ISBN; 
		private String author;
		private double runningTime = 0.0;
		
		public Book(String title, double price, int ISBN, String author, double runningTime) {
			super(title, price); //This is from CatalogItem class
			this.ISBN = ISBN; 
			this.author = author;
			this.runningTime = runningTime; 
		}
		
		public int getISBN(){
			return this.ISBN; 
		}
		
		public String getAuthor(){
			return this.author; 
		}
	
		public double getRunningTime(){
			return this.runningTime; 
		}
		
		// toString will be used for Option 6. It is used in the function displayCatalog()
		public String toString() {
			return "Title: " + this.getTitle() + " | Author: " + this.getAuthor() + " | Price: $" + df.format(this.getPrice()) + " | ISBN: " + this.getISBN() + "\n";
		}
		
	}
	
	// Creating a class for AudioBook.
	public static class AudioBook extends Book{
		DecimalFormat df = new DecimalFormat("#.##"); // Format for price.
		public AudioBook(String title, double price, int ISBN, String author, double runningTime) {
			super(title, price, ISBN, author, runningTime);
			this.setPrice(this.getPrice() * 0.90); 
		}
		
		public String toString() {
			return "Title: " + this.getTitle() + " | Author: " + this.getAuthor() + " | Price: $" + df.format(this.getPrice()) + " | ISBN: " + this.getISBN() + 
					" | RunningTime: " + this.getRunningTime() + "\n";
		}
		
	}
	
	// Creating a class for DVD. 
	public static class DVD extends CatalogItem{
		DecimalFormat df = new DecimalFormat("#.##"); // Format for price.
		private int year, dvdCode; 
		private String director;
		
		public DVD(int year, int dvdCode, String director, String title, double price) {
			super(title, price);
			this.dvdCode = dvdCode; 
			this.director = director;
			this.year = year; 
		}
		
		public int getDvdCode(){
			return this.dvdCode; 
		}
		
		public String getDirector(){
			return this.director; 
		}
		
		public int getYear(){
			return this.year; 
		}
		
		public String toString() {
			return "Title: " + this.getTitle() + " | Director: " + this.getDirector() + " | Price: $" + df.format(this.getPrice()) + " | Year: " + this.getYear() + 
					" | DvdCode: " + this.getDvdCode() + "\n";
		}
		
	}	
	
	// This function checks for valid inputs from Books/Audio books. 
	// What it checks: if the book adding is already there, if the price is non-negative, 
	// if title given is not empty, if ISBN is non-negative/not empty, if author is not empty string,
	// if runningTime is non-negative. 
	public static void addBookCheck(int itemNum, ArrayList<Book> bookArrayList) {
		Scanner input = new Scanner(System.in); // To enable Scanner for input.
		String title, author; 
		String ISBNString = "";
				String priceString = "";
						String runningTimeString = "";
		double price = -1.00;
		double runningTime = -1.00;
		int ISBN = 0; 
		boolean go = true; 
		String junk = "";
		
		do { // Checking ISBN number if it exist or not positive.
			boolean positive = false;
			boolean exist = false;
			boolean ISBNValid = false;
			do {
				System.out.println("Please enter the ISBN: ");
				ISBNString = input.nextLine();
			try {
				ISBN = Integer.parseInt(ISBNString);
				//junk = input.nextLine(); // TESTING
				ISBNValid = true;
			}catch (NumberFormatException e) {
				System.out.println("Please enter only integers");
			} 
			}while(!ISBNValid);
			
			if (ISBN >= 0)
			{
				positive = true; 
				go = true;
				for (Book books : bookArrayList)
				{
					if( books.getISBN() == ISBN )
					{
						exist = true; 
					}
					
				}
			}
			else 
			{
				go = false; 
			}
			
			if (!positive)
			{
				System.out.println("The ISBN entered was not positive. Please try again.");
			}
			else if (exist)
			{
				System.out.println("The book is already in the catalog.");
				return; 
			}
			
		} while (!go); // end of checking ISBN. 
		
		//----------------------------------Title--------------------------------------------------// 
		do { // Checking Title if it is empty or not. 
			System.out.println("Please enter the title of the book: ");
			title = input.nextLine();
			if ((title == null) || (title.isEmpty() ) )
			{
				go = false; 
				System.out.println("The title entered was empty. Please enter a non-empty string title.");
			}
		} while(!go); // end of checking Title
		//----------------------------------Price--------------------------------------------------//
		do { // Checking Price if not positive. 
			
			boolean priceValid = false;
			do {
				System.out.println("Please enter the price of the book: ");
				priceString = input.nextLine();
			try {
				price = Double.parseDouble(priceString);
				priceValid = true;
			}catch (NumberFormatException e) {
				System.out.println("The price entered was invalid. Please try again.");
			} 
			}while(!priceValid);
			
			if (price < 0)
			{
				go = false; 
				System.out.println("The price entered was invalid. Please try again.");
			}
		} while(!go); // end of checking Price
		//----------------------------------Author--------------------------------------------------//
		do { // Checking Author if empty. 
			System.out.println("Please enter the author of the book: ");
			author = input.nextLine();
			if ((author == null) || (author.isEmpty() ) )
			{
				go = false; 
				System.out.println("The author's name entered was empty. Please enter a non-empty string title.");
			}
		} while(!go); // end of checking author
		//----------------------------------RunningTime--------------------------------------------------//
		if (itemNum != 1) {
		do { // Checking runningTime if positive. 

			boolean timeValid = false;
			do {
				System.out.println("Please enter the running time of the book: ");
				runningTimeString = input.nextLine();
			try {
				runningTime = Double.parseDouble(runningTimeString);
				timeValid = true;
			}catch (NumberFormatException e) {
				System.out.println("The running time entered was invalid. Please try again. ");
			} 
			}while(!timeValid);
			
			
			if (runningTime <= 0) // does it make sense to have 0 as running time? 
			{
				go = false; 
				System.out.println("The running time entered was invalid. Please try again. ");
			
			}
		} while(!go); // end of checking runningTime
		}
		//----------------------------------Check if book or audio book--------------------------------------------------//
		if (itemNum == 1)
		{
			//Is book. Add book to arrayList
			Book newBook = new Book(title, price, ISBN, author, runningTime);
			bookArrayList.add(newBook);
		}
		else
		{
			//Is audiobook. Add audio to book arrayList.
			AudioBook newBook = new AudioBook(title, price, ISBN, author, runningTime);
			bookArrayList.add(newBook);
		}
	}
	
	public static void addDVDCheck(ArrayList<DVD> dvdArrayList) {
		//year, dvdCode, director, title, price
		Scanner input = new Scanner(System.in); // To enable Scanner for input.
		String title, director; 
		double price = -1.00;
		int dvdCode = -1;
		int year = -1; 
		boolean go = true; 
		String junk = "";
		
		do { // Checking dvdCode number if it exist or not positive.
			boolean positive = false;
			boolean exist = false;
			
			boolean dvdCodeValid = false;
			String dvdCodeString = "";
			do {
				System.out.println("Please enter the DVD Code: ");
				dvdCodeString = input.nextLine();
			try {
				dvdCode = Integer.parseInt(dvdCodeString);
				dvdCodeValid = true;
			}catch (NumberFormatException e) {
				System.out.println("The dvd code entered was invalid. Please try again. ");
			} 
			}while(!dvdCodeValid);
			
			if (dvdCode >= 0)
			{
				positive = true; 
				go = true;
				for (DVD dvds : dvdArrayList)
				{
					if( dvds.getDvdCode() == dvdCode )
					{
						exist = true; 
					}
					
				}
			}
			else 
			{
				go = false; 
			}
			
			if (!positive)
			{
				System.out.println("The DVD Code entered was not positive. Please try again.");
			}
			else if (exist)
			{
				System.out.println("The DVD is already in the catalog.");
				return; 
			}
			
		} while (!go); // end of checking DVDCode. 
		
		//----------------------------------Title--------------------------------------------------// 
		do { // Checking Title if it is empty or not. 
			System.out.println("Please enter the title of the DVD: ");
			title = input.nextLine();
			if ((title == null) || (title.isEmpty() ) )
			{
				go = false; 
				System.out.println("The title entered was empty. Please enter a non-empty string title.");
			}
		} while(!go); // end of checking Title
		//----------------------------------Price--------------------------------------------------//
		do { // Checking Price if not positive. 
				
				boolean priceValid = false;
				String priceString = "";
				do {
					System.out.println("Please enter the price of the DVD: ");
					priceString = input.nextLine();
				try {
					price = Double.parseDouble(priceString);
					priceValid = true;
				}catch (NumberFormatException e) {
					System.out.println("The dvd price entered was invalid. Please try again. ");
				} 
				}while(!priceValid);
			
			if (price < 0)
			{
				go = false; 
				System.out.println("The price entered was invalid. Please try again.");
			}
		} while(!go); // end of checking Price
		//----------------------------------Director--------------------------------------------------//
		do { // Checking Director if empty. 
			System.out.println("Please enter the director of the DVD: ");
			director = input.nextLine();
			if ((director == null) || (director.isEmpty() ) )
			{
				go = false; 
				System.out.println("The director's name entered was empty. Please enter a non-empty string title.");
			}
		} while(!go); // end of checking director
		//----------------------------------Year--------------------------------------------------//
		do { // Checking year if positive. 

			boolean yearValid = false;
			String yearString = "";
			do {
				System.out.println("Please enter the year of the DVD: ");
				yearString = input.nextLine();
			try {
				year = Integer.parseInt(yearString);
				yearValid = true;
			}catch (NumberFormatException e) {
				System.out.println("The dvd year entered was invalid. Please try again. ");
			} 
			}while(!yearValid);
			
			if (year <= 0) // does it make sense to have 0 as running time? 
			{
				go = false; 
				System.out.println("The year entered was invalid. Please try again. ");
			
			}
		} while(!go); // end of checking runningTime
		
			DVD newDVD = new DVD(year, dvdCode, director, title, price);
			dvdArrayList.add(newDVD);

	}
	
	public static void removeItem(int itemNum, ArrayList<Book> bookArrayList, ArrayList<DVD> dvdArrayList) {
		int valueToRemove; // either ISBN or DVD code 
		Scanner input = new Scanner(System.in); // To enable Scanner for input.
		boolean go = true; 
		
		if (itemNum == 4) //This is books
		{
				System.out.println("Please enter the ISBN in order to remove the book: ");
				valueToRemove = input.nextInt();
				
				boolean exist = false;
				for (Book books : bookArrayList)
				{
					if( books.getISBN() == valueToRemove )
					{
						exist = true; 
					}
						
				}
				
				if (!exist)
				{
					System.out.println("The book does not exist.");
					go = true; 
					return; 
				}
				else 
				{
					int index = 0; 
					int removeIndex = 0; 
					for (Book books : bookArrayList)
					{
						if( books.getISBN() == valueToRemove )
						{
							removeIndex = index; 
							go = false; 
						}
						index++;
							
					}
					bookArrayList.remove(removeIndex); // will this remove the object from arrayList book
					System.out.println("The book has been removed.");
					//PRINTS THE CATALOG
					displayCatalog(bookArrayList, dvdArrayList);
				}
				
		}
		else
		{
				
				System.out.println("Please enter the DVD code that you want to remove from the catalog: ");
				valueToRemove = input.nextInt();
				
				boolean exist = false;
				for (DVD dvds : dvdArrayList)
				{
					if( dvds.getDvdCode() == valueToRemove )
					{
						exist = true; 
					}
						
				}
				
				if (!exist)
				{
					System.out.println("The DVD does not exist.");
					go = true; 
					return; 
				}
				else 
				{
					int index = 0; 
					int removeIndex = 0; 
					for (DVD dvds : dvdArrayList)
					{
						if( dvds.getDvdCode() == valueToRemove )
						{
							removeIndex = index; 
							go = false; 
						}
						index++;
							
					}
					dvdArrayList.remove(removeIndex); // will this remove the object from arrayList dvd
					System.out.println("The dvd has been removed.");
					//PRINTS THE CATALOG
					displayCatalog(bookArrayList, dvdArrayList);
				}
		}
	}
	
	public static void displayCatalog(ArrayList<Book> bookArrayList, ArrayList<DVD> dvdArrayList){

		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("Books:\n");
		for (Book books : bookArrayList)
		{
			if (!(books instanceof AudioBook))
			System.out.println(books.toString());
		}
		// How to get audio book?
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("Audio Books:\n");
		for (Book books : bookArrayList)
		{
			if (books instanceof AudioBook)
			System.out.println(books.toString());
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		System.out.println("DVDs:\n");
		for (DVD dvds : dvdArrayList)
		{
			System.out.println(dvds.toString());
		}
		
	}

	public static void option7(ArrayList<Book> bookArrayList, ArrayList<DVD> dvdArrayList){
		String userDirectory = System.getProperty("user.dir");
		String catalogName = "catalog_backup_";
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy_MM_dd_hh_mm_ss");
        Date date = new Date();
        String catalogNameDate = dateFormat.format(date);
		String catalogTxt = ".txt";
		System.out.println(userDirectory + "\\" + catalogName + catalogNameDate + catalogTxt);
		String data = "";
          
		data += "-----------------------------------------------------------------------------------------------------------------\n\r";
		data += "\n\rBooks:\n\r";
		for (Book books : bookArrayList)
		{
			if (!(books instanceof AudioBook))
			data += "\n\r" + books.toString() + "\n\r";
		}
		// How to get audio book?
		data += "\n\r-----------------------------------------------------------------------------------------------------------------\n\r";
		data += "\n\rAudioBooks:\n\r";
		for (Book books : bookArrayList)
		{
			if (books instanceof AudioBook)
				data += "\n\r" + books.toString() + "\n\r";
		}
		data += "\n\r-----------------------------------------------------------------------------------------------------------------\n\r";
		data += "\nDVDs:\n\r";
		for (DVD dvds : dvdArrayList)
		{
			data += "\n\r" + dvds.toString() + "\n\r";
		}
		
		
		File backupFile = new File(userDirectory + "\\" + catalogName + catalogNameDate + catalogTxt);
        FileWriter writer;
        
		try {
			if (backupFile.createNewFile()){
			    System.out.println("Backfile is created.");
			  }
			else{
			    System.out.println("There is already a backfile.");
			}
		} catch (IOException e1) {
			System.out.println("Error occurred when creating a backup file.");
		}

		try {
			writer = new FileWriter(backupFile);
	        writer.write(data);
	        writer.close();
		} catch (IOException e) {
			System.out.println("Error occurred when writing in backup file.");
		}
		
	}
	
	public static void manager(ArrayList<Book> bookArrayList, ArrayList<DVD> dvdArrayList)
	{
		Scanner input = new Scanner(System.in); 
		boolean optionExist = false; 
		int optionNumber = -1; 
		String junk = " "; // junk is used to allow a pause for the user. It helps organizing each input values later on/ 
		
		do { 
			displayMenu(); 
			optionNumber = input.nextInt(); // this will get the option number from the user that corresponds with the menu.
			
			//If the optionNumber is not in the range of 1 and 6 (inclusive) or 9, then the block of code will not run.
			// This will print "The option is not acceptable.", set optionExist to false
			// and go straight back to the do-while loop.
			
			if ((optionNumber >= 1 && optionNumber <= 7) || (optionNumber  == 9)) // Make sure the option inputs are from 1 to 7 or 9.
			{
				optionExist = true;  // this is used just in case the program going in an infinite loop. 
				
				if (optionNumber == 1) // option 1, add book only if book is not already there. 
				{
					// CODE HERE TO ADD BOOKS. 
					// variableName.add(OBJECT/DATATYPE) OBJECT/DATATYPE newObject = new OBJECT/DATATYPE(parameters for the object);
					addBookCheck(optionNumber, bookArrayList);
					System.out.println("Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false;
					
				}
				else if (optionNumber == 2) // option 2, add audio books.
				{
					// CODE HERE TO ADD AUDIO BOOKS.
					addBookCheck(optionNumber, bookArrayList);
					System.out.println("Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false;
				}
				else if (optionNumber == 3) // option 3, add DVD.
				{
					// CODE HERE TO ADD DVDS
					addDVDCheck(dvdArrayList);
					System.out.println("Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false; 
				}//end else if | option 3
				
				else if (optionNumber == 4)  // OPTION 4, REMOVE BOOKS
				{
					removeItem(optionNumber,bookArrayList, dvdArrayList);
					System.out.println("Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false; 
					
				}// end else if | option 4

				
				else if (optionNumber == 5) // OPTION 5, REMOVE DVDS
				{
					removeItem(optionNumber,  bookArrayList, dvdArrayList);
					System.out.println("Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false;
				}
				else if (optionNumber == 6) // OPTION 6, DISPLAY CATALOG. 
				{
					displayCatalog(bookArrayList, dvdArrayList);
					System.out.println("Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false;
				}
				else if (optionNumber == 7) // OPTION 7, BACKUP FILE. 
				{
					option7(bookArrayList, dvdArrayList);
					System.out.println("NEED TO DO LATER: Press any key follow by an [ENTER] to return to menu");
					junk = input.next(); 
					optionExist = false;
				}
				else if (optionNumber == 9) // OPTION 9, EXIT PROGRAM. 
				{
					System.out.println("Goodbye!");
					optionExist = true;
				} 

			} // if(option = 1-6 and 9)
			else
			{
				System.out.println("The option is not acceptable.");
				System.out.println("Press any key follow by an [ENTER] to continue");
				junk = input.next(); 
				optionExist = false;
			}
			
		}while(optionExist == false);
	}
	
//Above is for the managers-------------------------------------------------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//Below is for the customer-------------------------------------------------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void displayCustomerMenu(){
		String [] displayMenuTexts = {"**Welcome to the Comets Books and DVDs Store**\n",
		"Choose from the following options:", "1 - Browse books inventory (price low to high) ",
		"2 - Browse DVDs inventory (price low to high)", "3 - Add a book to the cart ", 
		"4 - Add a DVD to the cart", "5 - Delete book from cart ", "6 - Delete a DVD from cart",
		"7 - View Cart", "8 - Checkout ", "9 - Done Shopping"};

		for (int i = 0; i < displayMenuTexts.length ;i++)
		{
			System.out.println(displayMenuTexts[i]);
		}
	}
	
	public static void displayOrganizeCatalog(ArrayList<Book> bookArrayList, ArrayList<DVD> dvdArrayList, String catalogType){

		if (catalogType == "Books")
		{
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			System.out.println("Books:\n");
			for (Book books : bookArrayList)
			{
				if (!(books instanceof AudioBook))
				System.out.println(books.toString());
			}
			// How to get audio book?
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			System.out.println("Audio Books:\n");
			for (Book books : bookArrayList)
			{
				if (books instanceof AudioBook)
				System.out.println(books.toString());
			}
		}
		else {
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			System.out.println("DVDs:\n");
			for (DVD dvds : dvdArrayList)
			{
				System.out.println(dvds.toString());
			}
		}
		
	}
	
	public static void displayCart(ArrayList<CatalogItem> shoppingCart){
			for (CatalogItem items : shoppingCart)
			{
				System.out.println(items.toString());
			}
	}
	
	public static void displayChecking(ArrayList<CatalogItem> shoppingCart){
		double total = 0;
		for (CatalogItem items : shoppingCart)
		{
			System.out.println(items.toString());
			total += items.getPrice();
		}
		System.out.println("Your total is: " + total);
		System.out.println("Thank you for shopping!");
		shoppingCart.clear();
}

	
	public static void customer(ArrayList<Book> bookArrayList, ArrayList<DVD> dvdArrayList, ArrayList<CatalogItem> shoppingCart) {
		Scanner input = new Scanner(System.in); 
		boolean optionExist = false; 
		int optionNumber = -1; 
		String junk = "";
	
	do { 
		displayCustomerMenu(); 
		optionNumber = input.nextInt(); // this will get the option number from the user that corresponds with the menu.
		
		
		if (optionNumber >= 1 && optionNumber <= 9)
		{
			optionExist = true;  // this is used just in case the program going in an infinite loop. 
			
			if (optionNumber == 1) // option 1, displays all books and its corresponding prices/inventory #
			{
				Collections.sort(bookArrayList);
				displayOrganizeCatalog(bookArrayList, dvdArrayList, "Books");
				System.out.println("Press any key follow by an [ENTER] to return to menu");
				junk = input.next(); 
				optionExist = false;
				
			}
			
			else if (optionNumber == 2) // option 2, displays all dvds and its corresponding prices/inventory #
			{
				Collections.sort(dvdArrayList);
				displayOrganizeCatalog(bookArrayList, dvdArrayList, "Dvds but i can put anything and it will only print DVDs");
				System.out.println("Press any key follow by an [ENTER] to return to menu");
				junk = input.next(); 
				optionExist = false;
			}
			else if (optionNumber == 3) // option 3, allow user to add books in cart. 
			{
				int ISBN = 1; 
				do {
					int itemInCart = shoppingCart.size();
					boolean exist = false; 
					System.out.println("Total items in cart: " + itemInCart);
					String ISBNString = "1";
					boolean ISBNValid = false; 
					
					do {
						System.out.println("Please enter the ISBN: ");
						ISBNString = input.nextLine();
					try {
						ISBN = Integer.parseInt(ISBNString);
						//junk = input.nextLine(); // TESTING
						ISBNValid = true;
					}catch (NumberFormatException e) {
						System.out.println("Please enter only integers");
					} 
					}while(!ISBNValid);
					
					for (Book books : bookArrayList)
					{
						if (books.getISBN() == ISBN)
						exist = true; 
					}
					if (!exist && ISBN != -1)
					{
						System.out.println("Sorry, but we do not have the ISBN: " + ISBN + " in our inventory. Please try again.");
					}
					else if(exist)
					{
						for (Book books : bookArrayList)
						{
							if (books.getISBN() == ISBN)
							{
							shoppingCart.add(books);
							System.out.println("The book " + books.getTitle() + " was added to your cart.");
							}
						}
					}
				}while(ISBN != -1);
				optionExist = false;
				
			}//else if 
			
			
			else if (optionNumber == 4)  // option 4, allow user to add dvds in cart. 
			{ 				
				int dvdCode = 1; 
				do {
					int itemInCart = shoppingCart.size();
					boolean exist = false; 
					System.out.println("Total items in cart: " + itemInCart);
					String dvdCodeString = "1";
					boolean dvdCodeValid = false; 
					
					do {
						System.out.println("Please enter the ISBN: ");
						dvdCodeString = input.nextLine();
					try {
						dvdCode = Integer.parseInt(dvdCodeString);
						//junk = input.nextLine(); // TESTING
						dvdCodeValid = true;
					}catch (NumberFormatException e) {
						System.out.println("Please enter only integers");
					} 
					}while(!dvdCodeValid);
					
					for (DVD dvds : dvdArrayList)
					{
						if (dvds.getDvdCode() == dvdCode)
						exist = true; 
					}
					if (!exist && dvdCode != -1)
					{
						System.out.println("Sorry, but we do not have the DVD code: " + dvdCode + " in our inventory. Please try again.");
					}
					else if(exist)
					{
						for (DVD dvds : dvdArrayList)
						{
							if (dvds.getDvdCode() == dvdCode)
							{
							shoppingCart.add(dvds);
							System.out.println("The DVD " + dvds.getTitle() + " was added to your cart.");
							}
						}
					}
				}while(dvdCode != -1);
				optionExist = false;
			}//else if

			
			else if (optionNumber == 5) // OPTION 5, REMOVE BOOK FROM CART
			{
				int ISBN = 1; 
				do {
					int itemInCart = shoppingCart.size();
					boolean exist = false; 
					System.out.println("Total items in cart: " + itemInCart);
					String ISBNString = "1";
					Book bookRemove = new Book("", 0, 0, "", 0.0);
					boolean ISBNValid = false; 
					
					do {
						System.out.println("Please enter the ISBN: ");
						ISBNString = input.nextLine();
					try {
						ISBN = Integer.parseInt(ISBNString);
						//junk = input.nextLine(); // TESTING
						ISBNValid = true;
					}catch (NumberFormatException e) {
						System.out.println("Please enter only integers");
					} 
					}while(!ISBNValid);
					
					for (Book books : bookArrayList)
					{
						if (books.getISBN() == ISBN)
						{
						exist = true; 
						bookRemove = books;
						}
					}
					if (!exist && ISBN != -1)
					{
						System.out.println("You do not have the book in your cart.");
					}
					else if (exist){
						exist = false; 
						Iterator<CatalogItem> iter = shoppingCart.iterator();

						while (iter.hasNext()) {
						    CatalogItem str = iter.next();

						    if (str.equals(bookRemove))
						    {
						    	System.out.println("The book was removed from your cart.");
						        iter.remove();
						    }
						}
					}
					else
					{
						ISBN = -1; 
					}
				}while(ISBN != -1);
				optionExist = false;
			}
			else if (optionNumber == 6) // OPTION 6, REMOVE DVD FROM CART
			{ 
				int dvdCode = 1; 
				do {
					int itemInCart = shoppingCart.size();
					boolean exist = false; 
					System.out.println("Total items in cart: " + itemInCart);
					String dvdCodeString = "1";
					DVD dvdRemove = new DVD(0,0,"","",0.0);
					boolean dvdValid = false; 
					
					do {
						System.out.println("Please enter the DVD Code: ");
						dvdCodeString = input.nextLine();
					try {
						dvdCode = Integer.parseInt(dvdCodeString);
						//junk = input.nextLine(); // TESTING
						dvdValid = true;
					}catch (NumberFormatException e) {
						System.out.println("Please enter only integers");
					} 
					}while(!dvdValid);
					
					for (DVD dvds : dvdArrayList)
					{
						if (dvds.getDvdCode() == dvdCode)
						{
						exist = true; 
						dvdRemove = dvds;
						}
					}
					if (!exist && dvdCode != -1)
					{
						System.out.println("You do not have the DVD in your cart.");
					}
					else if (exist){
						exist = false; 
						Iterator<CatalogItem> iter = shoppingCart.iterator();

						while (iter.hasNext()) {
						    CatalogItem str = iter.next();

						    if (str.equals(dvdRemove))
						    {
						    	System.out.println("The DVD was removed from your cart.");
						        iter.remove();
						    }
						}
					}
					else
					{
						dvdCode = -1; 
					}
				}while(dvdCode != -1);
				optionExist = false;
			}
			else if (optionNumber == 7) // OPTION 7, CLEARS OUT CART. 
			{ 
				displayCart(shoppingCart);
				optionExist = false;
				System.out.println("Press any key follow by an [ENTER] to continue");
				junk = input.next(); 
			}
			else if (optionNumber == 8) // OPTION 8, CHECK OUT
			{
				displayChecking(shoppingCart);
				optionExist = true;
			}
			else if (optionNumber == 9) // OPTION 9, EXIT LOOP. 
			{

				System.out.println("Thanks you! Good bye!");
				optionExist = true; 
			}
		} // if(option = 1-9)*/
		else
		{
			System.out.println("The option is not acceptable.");
			System.out.println("Press any key follow by an [ENTER] to continue");
			junk = input.next(); 
			optionExist = false;
		} 
		
		
	}while(optionExist == false);
}
	
//Above is for the customer-------------------------------------------------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public static void main(String[] args){
		DecimalFormat df = new DecimalFormat("#.##");
		String userDirectory = System.getProperty("user.dir");
		String cred = userDirectory + "\\credentials.txt"; // \\src\\credentials.txt
		File credential = new File(cred);
	
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(credential));
			int numberUsers = 0;
			
			String line = null;
			
			//while loop will count how many managers logins there are: 
			// *the while loop will only count the total of lines in the credential.txt file. 
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				numberUsers++; // this increment the numberUsers to track total logins 
			}
			//System.out.println("Currently, there are " + numberUsers + " manager usernames."); //Just to check if the loop worked the way it had intended. 
			br.close(); //close to reset buffer later
			String[] logins = new String[numberUsers]; //now this array is to keep the username and the password (each line of the credentials)
			br = new BufferedReader(new FileReader(credential)); //resetting buffer
			
			int i = 0;  // the while loop below will set the logins array to each line of the credential.txt
			while ((line = br.readLine()) != null) {
				logins[i] = line; 
				i++;
			}
		
			br.close();
			
			
			// The arrays and for loop is to store the usernames and passwords.
			String[] userName = new String[numberUsers]; 
			String[] passWord = new String[numberUsers]; 
			
			for (int j = 0; j < logins.length; j++)
			{
				int comma = logins[j].indexOf(',');
				userName[j] = logins[j].substring(0,comma);
				//System.out.println(userName[j]);
				passWord[j] = logins[j].substring(comma + 1);
				//System.out.println(passWord[j]);
			}
			
			//Credentials loaded.
			Scanner input = new Scanner(System.in); // To enable Scanner for input.
			//ArrayList<Book> bookArrayList = new ArrayList<Book>(); // Declaring Books/Audiobooks arraylist
			//ArrayList<DVD> dvdArrayList = new ArrayList<DVD>(); // Declaring DVD arraylist
			String junk = " "; // junk is used to allow a pause for the user. It helps organizing each input values later on/ 
			String mainMenuInput;
			ArrayList<Book> bookArrayList = new ArrayList<Book>(); // Declaring Books/Audiobooks arraylist
			ArrayList<DVD> dvdArrayList = new ArrayList<DVD>(); // Declaring DVD arraylist
			ArrayList<CatalogItem> shoppingCart = new ArrayList<CatalogItem>();
			
			boolean completeExit = false; 
			do {
					completeExit = true; //precaution so it does not loop
					//Showing main menu
					mainMenu();
					mainMenuInput = input.next(); // this will get the option number from the user that corresponds with the menu.
					
					if ((mainMenuInput.contentEquals("A")) || (mainMenuInput.contentEquals("B")) || (mainMenuInput.contentEquals("C")) )
					{
						completeExit = false; // don't leave main menu just yet
						//System.out.println("Correct inputs");
						if ((mainMenuInput.contentEquals("A")))
						{
							//System.out.println("A - store manager was selected.");
							if (loginValidation(userName, passWord))
							{
								//THIS IS WHERE THE MANAGER STUFF HAPPENS! 
								//System.out.println("Login Successful!");
								manager(bookArrayList, dvdArrayList); //MANAGER MENU
								
							}
							else {
								System.out.println("Unrecognized Credentials. \nPress any key follow by [ENTER] to return to main menu.");
								junk = input.next(); 
							}
							
						}
						else if ((mainMenuInput.contentEquals("B")) ) //BEGINNING OF CUSTOMERS
						{
							System.out.println("B - customer was selected.");
							customer(bookArrayList, dvdArrayList, shoppingCart);
							
						} //ENDING OF IF MAINMENUINPUT EQUALS B.
						else {
							System.out.println("Exitting the store. Goodbye.");
							completeExit = true; // only true path to leave the main menu
						}
					}
					else
					{
						completeExit = false;
						System.out.println(mainMenuInput);
						System.out.println("Please choose the following options: A, B, C");
					}
					
				}while(!completeExit);
			
			
			
			
			
		} catch (IOException e) {
			System.out.println("Please put the credentials.txt in: " + userDirectory);
			
		}
		//Scanner input = new Scanner(System.in); // To enable Scanner for input.
		
	
	}
}


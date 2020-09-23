import java.util.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author marcela gomez
 * CSC 143 Project 4 - BookLinkedList (aka:Catalog) Client
 *
 */
public class BookLinkedListClient {  

	public static void main(String[] args) throws FileNotFoundException  {
		String selection = "";
		BookLinkedList catalog = new BookLinkedList();
		catalogBuilder("booklist.txt", catalog);
		Scanner console = new Scanner(System.in);
		intro();
		
		while(selection != "Q") {
			startMenu();
			System.out.print("Enter a function: ");
			//.nextLine() excludes any line separator at the end
			//.trim() was used to remove the enter key stroke "/n".
			selection = console.nextLine().trim();
			processSelection(selection, catalog, console);
			System.out.println();
			//System.out.println(catalog);
		}	
		console.close();
		System.out.println("Thank you for visiting Little Library.");
	}
	public static void catalogBuilder(String fileName, BookLinkedList catalog) 
			throws FileNotFoundException {
		Scanner input = new Scanner(new File(fileName));  
		String[] L = new String[6];
		while(input.hasNextLine()) {
			L =	input.nextLine().split("\t");  
			int year = Integer.parseInt(L[4]);
			double price = Double.parseDouble(L[5]);
			Book temp = new Book(L[0], L[1], L[2], L[3], year, price) ;
			catalog.add(temp);
		}
		System.out.println(catalog);
		input.close();
	}
	public static void intro () {
		System.out.println("Welcome to the Little Library Book Catalog");
		System.out.println();
		System.out.println("\tYou can search, add, or remove books.");
		System.out.println("\tLet's get started!");
		System.out.println();
	}
	public static void startMenu() {
		System.out.println("\tA -Add a book.");
		System.out.println("\tS -Search for a book.");
		System.out.println("\tR -Remove a book.");
		System.out.println("\tQ -To Quit");
		System.out.println();	
	}
	public static void searchType() {
		System.out.println("\tSearch Methods Include: ");
		System.out.println();
		System.out.println("\t\tF for first name.");
		System.out.println("\t\tL for last name;");
		System.out.println("\t\tI for ISBN-10");
		System.out.println("\t\tT for title");
		System.out.println("\t\tM for main menu");
		System.out.println();
	}
	//Gets user input when prompted by a menu.
	public static String getSelection() {
		Scanner input = new Scanner(System.in);
		System.out.print("\tSelect a function: ");
		String selection = input.next().toUpperCase();
		return selection;
	}
	//Title is a longer string so used this method to handle this case apart.
	public static String getTitle() {
		Scanner input = new Scanner(System.in);
		System.out.print("\tTitle: ");
		//input.close();
		return input.nextLine();
	}
	// Switch case moves user through the action and calls method to perform
	//the chosen task.  Might be redundant comment as title was clear I felt.
	private static void processSelection(String selection, BookLinkedList catalog, Scanner console) 
				throws FileNotFoundException {
		switch (selection) {
		case "A":
			System.out.println("You chose add a book.");
			addBook(catalog, console);
			break;
		case "R":
			System.out.println("You chose remove a book.");
			removeBook(catalog, console);
			break;
		case "S":
			System.out.println("You chose search for a book.");
			searchType();
			selection = getSelection();
			searchBook(selection, catalog, console);
			break;
		case "Q":
			saveToCatalog("booklist.txt", "booklist2.txt", catalog, console);
			System.out.println("Thank you for visiting Little Library.");
			break;
		default:
			System.out.println("Try again, not a valid choice.");
			break;
		}
	}
	//Post: book is added if not already in the catalog.
	//Post: error message returned if invalid entry is made. 
	public static Book addBook(BookLinkedList catalog, Scanner console) 
				throws FileNotFoundException {
		//.nextLine() excludes any line separator at the end
		// book info input by user.
		System.out.println("\tPlease enter book details.");
		System.out.print("\tISBN-10: ");
		String isbn = console.nextLine();
		System.out.print("\tAuthor's first name: ");
		String firstName = console.nextLine();
		System.out.print("\tAuthor's last name: ");
		String lastName = console.nextLine();
		//int year = returnSafeYear(console);
		System.out.print("\tYear Published: ");
		int year = Integer.parseInt(console.nextLine());
		double price = returnSafePrice(console);
		String title = getTitle();
		
		Book temp = catalog.searchByISBN(isbn);
		if(temp == null ) {
				temp = new Book(isbn, lastName, firstName, title, year, price);
				catalog.add(temp);
				System.out.println(catalog);
				saveToCatalog("booklist.txt", "booklist2.txt", catalog, console);
				System.out.println("Your book was added.");
				System.out.println(catalog);
		}else {
			System.out.println("Book exists in the Catalog.");
		}
		return temp;
	}
	
	//Catch/Try here was used to get user to put in correct price.
	//When accidently adding 4/5 instead of 4.5 an error occurred.
	//Per the interweb, a catch/try method is used to avoid user error.
	//Allow user to enter the price again correctly.
	public static double returnSafePrice(Scanner console) {
		boolean isGoodPrice = false;
		double returnPrice = 0.0;
		while(!isGoodPrice) {
			try {
				System.out.print("\tPrice: ");
				returnPrice = Double.parseDouble(console.nextLine());
				isGoodPrice = true;
			}
			catch (NumberFormatException E) {
				System.out.println("Invalid Entry, Try Again.");
			}
		}
		return returnPrice;
	}
	
	//removeBook, does just that. User can remove by title or ISBN
	public static void removeBook(BookLinkedList catalog, Scanner console) {
		System.out.println("\tChoose removal method.");	
		System.out.println("\tA - remove by title");
		System.out.println("\tB - remove by ISBN-10");
		String selection = getSelection();
		if(selection.equals("A")) {
			selection = "title";
			String title = getBookInfo(selection, console);  // gets title input from user
			Book data = catalog.searchByTitle(title);
			System.out.println(title);
			int index = catalog.indexOf(data); //gets index from catalog
			System.out.println(index);
			catalog.remove(index);  //remove index from catalog
			System.out.println(catalog);
		}else if(selection.equals("B")){
			selection = "isbn";
			String isbn = getBookInfo(selection, console);	
			Book data = catalog.searchByISBN(isbn);
			int index = catalog.indexOf(data);
			System.out.println(index);
			catalog.remove(index);
			System.out.println(catalog);
		}
	}
	//searchBook lets user search by first, last name, title or isbn.
	//User selection is handled by switch case here, methods called
	//from below performed desired user action.
	public static void searchBook(String selection, BookLinkedList catalog, Scanner console) {
		switch(selection) {
		
		case "F":
			selection = "firstName";
			String firstName = getBookInfo(selection, console);
			System.out.println(firstName);
			Book data = catalog.searchByFirstName(firstName);
			System.out.println(data);
			break;
			
		case "L":
			selection = "lastName";
			String lastName = getBookInfo(selection, console);
			Book data2 = catalog.searchByLastName(lastName);
			System.out.println(data2);
			break;
			
		case "I":
			selection = "isbn";
			String isbn = getBookInfo(selection, console);
			Book data3 = catalog.searchByISBN(isbn);
			System.out.println(data3);
			break;
			
		case "T":
			selection = "title";
			String title = getBookInfo(selection, console);
			Book data4 = catalog.searchByTitle(title);
			System.out.println(data4);
			break;	
		case "M":
			break;
		default:
			System.out.println("Try again, not a valid choice.");
			System.out.println();
			break;	
		}
	}
	// gets user input for search, either ISBN or title.
	public static String getBookInfo(String bookID, Scanner input) {
		System.out.print("Enter " + bookID + ":");
		bookID = input.nextLine();
		return bookID;
	}
	//saveToCatalog allow user to save the added book into the txt file
	//When not saved the information is not stored when the program 
	//runs again. This option is presented to the user when Q-quit is selected.
	public static String saveToCatalog(String fileName, String fileName2, BookLinkedList catalog, Scanner save)
							throws FileNotFoundException {
		System.out.println("Would you like to save the catalog? ");
		String catalogSave = save.nextLine().toUpperCase();
		if(catalogSave.equals("Y")) {

			PrintStream outputFile = new PrintStream(new File(fileName2));
			BookListNode current = catalog.getFront();
			while (current != null) {
				outputFile.println(current.data.isbn + "\t" + current.data.firstName + "\t" + current.data.lastName + "\t" + current.data.title + "\t" + current.data.year + "\t" + current.data.price);
				current = current.next;
			} 
			outputFile.close();
		}
		return "Q";
	}
}
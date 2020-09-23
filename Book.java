/**
 * @author marcela gomez
 * CSC 143 - Project 1
 * Book Object with ISNB verification
 */
import java.util.Scanner;

//********************************************************************
public class Book {  //Object Book
	
	public String isbn;
	public String lastName;
	public String firstName;
	public int year;
	public String title;
	public double price;
	public String bookID;
	
	//Book Constructor insures all constraints set on variables are met
	//before constructing the Book Object. 
	public Book(String isbn, String lastName, String firstName, 
			 String title, int year, double price) {
		if(testISBN(isbn) && validateLastName(lastName) && validateFirstName(firstName) 
			 && validateTitle(title) && validateYear(year)  && validatePrice(price)) {
			this.isbn = isbn;
			this.lastName = lastName;
			this.firstName = firstName;
			this.title = title;
			this.year = year;
			this.price = price;
		}else {
			System.out.println("Invalid Entry.");
		}
	}
	
	// gets and returns isbn.
	public String getISBN() {
		return isbn;
	}
	// validates and sets isbn code.
	public void setISBN(String isbn) {
		testISBN(isbn);
		this.isbn = isbn;
	}
	// gets and returns last name.
	public String getLastName() {
		return lastName;
	}
	//validates and sets last name.
	public void setLastName(String lastName) {
		validateLastName(lastName);
		this.lastName = lastName;
	}
	//gets and returns first name
	public String getFirstName() {
		return firstName;
	}
	//validates and sets first name
	public void setFirstName(String firstName) {
		validateFirstName(firstName);
		this.firstName = firstName;
	}
	//gets and returns year
	public int getYear() {
		return year;
	}
	//validates and sets year of publication.
	public void setYear(int year) {
		validateYear(year);
		this.year = year;
	}
	//gets and returns book title
	public String getTitle() {
		return title;
	}
	//validates and sets book title.
	public void setTitle(String title) {
		validateTitle(title);
		this.title = title;
	}
	// gets and returns price
	public double getPrice() {
		return price;
	}
	//validates and sets book price
	public void setPrice(double price) {
		validatePrice(price);
		this.price = price;
	}
	public boolean equals(Book other) {
		return this.getISBN() == other.getISBN() && this.getTitle() == other.getTitle();
	}
	//returns printed book information
	public String toString() {
		return isbn + " " + firstName + " " + lastName + " " + title +
				" " + year + " " + price;
	}
	//Validation statements insure requirements are fulfilled before 
	//constructing the book object, these methods called above in constructor.
	public boolean validateLastName(String lastName) {
		return !lastName.equals("");
	}
	public boolean validateFirstName(String firstName) {
		return !firstName.equals(""); 
	}
	public boolean validateTitle(String title) {
		return !title.equals("");
	}
	private boolean validatePrice(double price) {
		return price >= 0;
	}
	private boolean validateYear(int year) {
		return year <= 2018;	
	}
	public static boolean testISBN(String isbn) {
		boolean valid = true;
		
		//ISNB can have 3 or 0 dashes to be valid.
		//Corresponding error massage returns condition is not met. 
		int dashes = dashCount(isbn);
		if (dashes != 0) {
			if (dashes > 3) {
				System.out.println("ISBN Error: Too many dashes.");
				return false; //"Too many dashes.";
			}
			if(dashes < 3) {
				System.out.println("ISBN Error: Not enough dashes.");
				return false; //"Not enough dashes.";
			}
		}
		
		//First or last character can not be a dash. 
		valid = firstLastDash(isbn);
			if (!valid) {
				System.out.println("ISBN can not start or end with a dash");
				return false; // "ISBN can not start or end with a dash";
			}
			
		//ISBN can't have two dashed in a row.	
		valid =	twoDashes(isbn);
			if (!valid) {
				System.out.println("ISBN Error: Sequential Dashes");
				return false; //"Sequential Dashes";
			}
		
		//This segment removes the dashes.
		//ISBN can't have more or less than 10 digits.
		// Two error messages return: "too few" or "too many" digits.
		String newIsbn = isbn.replace("-", "");
		if (newIsbn.length() > 10) {
			System.out.println("ISBN Error: Too many digits");
			return false; //"Too many digits";
		}
		if (newIsbn.length() < 10) {
			System.out.println("ISBN Error: Too few digits");
			return false; //"Too few digits";
		}
		
		//First nine digits must be 0-9, only the last char can be a letter.
		valid = badDigits(newIsbn);
		if(!valid){
			System.out.println("ISBN Error: bad digit");
			return false; // "bad digit";
		}
		
		//This verifies the check sum equals the last digit in the string.
		valid = checkSum(newIsbn);
		if(!valid) {
			System.out.println("ISBN Error: Wrong check sum.");
			return false;//"Wrong check sum.";
		}
		return true;  //"Valid ISBN.";
	}
	//Method tests number of dashes and returns a boolean.
	//Checked/verified correct response for 0, 3, other dashes.
	public static int dashCount(String ISBN){
		int count = 0;
		for (int i = 0; i < ISBN.length(); i++) {
			if(ISBN.charAt(i) == '-') {
				count++;
			}
		}
		return count;
	}
	//Method insures first and last character are no a dash
	//Checked/verified all combinations of possibilities. 
	public static boolean firstLastDash(String ISBN) {
		if (ISBN.charAt(0) == '-' || ISBN.charAt(ISBN.length() - 1) == '-' ) {
			return false;
		}
		else {
			return true;
		}
	}
	//Method insures that two dashes are not present.
	public static boolean twoDashes(String ISBN) {
		for(int i = 0; i <= ISBN.length() - 2; i++) {	
			if ((ISBN.charAt(i)) == '-' && (ISBN.charAt(i + 1)) == '-'){
				return false;
			}
		}
		return true;
	}
	//ISBN can have no letter aside from the last position.
	public static boolean badDigits(String newIsbn) {
		int end;
		if(newIsbn.charAt(9) != 'X') {
			end = 9;
		}
		else {
			end = 8;
		}
		for (int i = 0; i <= end; i++) {
			if (!(newIsbn.charAt(i) <= '9' && newIsbn.charAt(i) >='0')){
				return false; 
				}
		}
		return true;
	}
	
	//The check sum takes number's 1-9 and multiplies it by
	//its position and performs remainder division.  This
	//value must equal the last digit. This must equal the 
	//last character in position 10.
	public static boolean checkSum(String newIsbn) {
		//System.out.println(newIsbn);
		int sum = 0;
		int checkSum = 0;
		for(int i = 0; i < newIsbn.length() -1  ; i++) {
			sum = sum + ((int)newIsbn.charAt(i)-48) * (i + 1);
			//System.out.println(sum);
			checkSum = sum % 11;
		}
		if (newIsbn.charAt(9) != 'X') {
			if(checkSum != (int) newIsbn.charAt(9)-48) {
				return false;
			}
		} else if(checkSum != 10) {
			return false;
		}
		return true;
	}
}
	
		


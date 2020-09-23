/**
 * @author marcela gomez
 * CSC 143 - Project 4 BookLinkedList
 *
 */
import java.io.*;
import java.util.*;
public class BookLinkedList{ //Object BookLinkedList
	
	private BookListNode front;
	public int size;

	// Constructor creates an empty LinkedList
	public BookLinkedList() { //done
		front = null;
		size = 0; 
	}
	
	//Pre: size >= 0
	//returns size
	public int size() {  //done
		int size = 0;
		BookListNode current = front;
		while(current != null) {
			current = current.next;
			size++;
		}
		return size;
	}
	//Pre: size =0
	//Post: returns true if the list is empty.
	public boolean isEmpty() {  //empty
		return(size == 0);
	}
	//Pre index < size
	//Post: gets node at the specific index.
	public Book get(int index) {
		return nodeAt(index).data;
	}
	//Pre: index >= 0 and index < size
	//Post: returns node at before index as i < index but not equal to. 
	private BookListNode nodeAt(int index) {
		BookListNode current = front;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}
	//Pre: size >= 0
	//Post: adds BookListNode to the LinkedList.
	public void add(Book data) { //done
		if(this.isEmpty()) {
			front = new BookListNode(data);
			size = 1;
		} else {
			BookListNode temp = new BookListNode(data);
			BookListNode current = front;
			while(current.next != null) {
				current = current.next;
			}
			current.next = temp;
			size +=1;
		}
	}
	//Pre: index < size || index >= 0
	//Post: adds BookListNode to the LinkedList.
	public void add(int index, Book data) {  //done
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		size++;
		if(index == 0) {
			front = new BookListNode(data, front);
			//size ++;
		}else {
			BookListNode current = front;
			for(int i = 0; i < index -1; i++) {
				current = current.next;
			}
			current.next = new BookListNode(data, current.next);
		}
	}
	//Pre: index >= 0, index < size
	//Post returns index of BookListNode
	public int indexOf(Book data) {   
		int index = 0;
		BookListNode current = front; 
		while(current != null) {
			if (current.data.equals(data)) {
				return index;	
			}
			index++;
			current = current.next;
		}
		return -1;
	}
	//Pre: index >=0, index < size
	public void remove(int index) {  //done
		if(index == 0) {
			front = front.next;
		} else {
			BookListNode current = nodeAt(index -1);
			current.next = current.next.next;
		}
		size--;
	}
	//Post: returns printed BookLinkedList
	public String toString() {//OK working well
		if (size == 0) {
			return "[]";
		}
		String result = "[" + front.data;
		BookListNode current = front;
		while (current.next != null) {
			result += ", \n" + current.next.data;
			current = current.next;
		} 
		result += "]";
		return result;
	}
	//Pre: size >=0 
	//Post: Returns boolean true if book is in BLL and false if not found. 
	public boolean contains(Book data) {  //OK working well
		BookListNode current = front;
		while(current != null) {
			if(current.data == data) {//use .equals method for comparing
				return true;
			}
			current = current.next;
		}
		return false;
	}
	//Pre: size > 0
	//Post returns book, or error message if not found.
	public Book searchByISBN(String isbn) {
		BookListNode current = front;
		while(current != null) {
			if(current.data.isbn.equals(isbn)) {
				return current.data;
			}
			current = current.next;	
		}
		System.out.println("\tBook Not Found");
		return null;
	}
	//Pre: size > 0
	//Post returns book, or error message if not found.
	public Book searchByTitle(String title) {
		BookListNode current = front;
		while(current != null) {
			if(current.data.title.equals(title)) {
				return current.data;
			}
			current = current.next;					
		}
		System.out.println("\tBook Not Found");
		return null;
	}
	//Pre: size > 0
	//Post returns book, or error message if not found.
	public Book searchByLastName(String lastName) {
		BookListNode current = front;
		while(current != null) {
			if(current.data.lastName.equals(lastName)) {
				return current.data;
			}
			current = current.next;					
		}
		System.out.println("\tBook Not Found");
		return null;
	}
	//Pre: size > 0
	//Post returns book, or error message if not found.
	public Book searchByFirstName(String firstName) {
		BookListNode current = front;
		while(current != null) {
			if(current.data.firstName.equals(firstName)) {
				return current.data;
			}
			current = current.next;					
		}
		System.out.println("\tBook Not Found");
		return null;
	}
	//Post: retrieves front of BookLinkedList
	public BookListNode getFront() {
		return front;
	}
}
	
	
	
	
	

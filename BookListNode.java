/**
 * @author marcela gomez
 * CSC 143 Project 4 - BookListNode 
 */
public class BookListNode {
	//BookListNode object with constructor. 
	public Book data;
	public BookListNode next;
		
	public BookListNode(){
		this(null, null);
	}
	public BookListNode(Book data) {//default int 0, object null
		this(data, null);
	}
	public BookListNode(Book data, BookListNode next) {//default int 0, object null
		this.data = data; //this assigns to the field above.
		this.next = next;
	}
}
	
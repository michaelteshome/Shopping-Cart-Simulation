import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.Iterator;

//Simple linked list class based on AbstractList
//READ THE DOCUMENTATION for AbstractList for more information
//on how these methods should work and when exceptions should
//be thrown. This data structure does not allow adding null
//elements.

/***
 * @author: Michael Teshome
 * @version: 1.8.0_151
 * Instructor: Yutao Zhong
 * Course: CS 310
 */

public class SimpleList<T> extends AbstractList<T> {
	//Add more instance variables here...
	//private or protected only!

	//private variable for keeping track of current size of LinkedList
	private int currentItems = 0;

	//Constructor
	public SimpleList() {
		//O(1)
	}
	
	//returns the size
	public int size(){
		//O(1)
		return this.currentItems;
	}
	
	/**
	 * <p>Returns the value of head at a given indice location.</p>
	 * @param index
	 * @throws IndexOutOfBoundsException If index is greater than the current size or less than 0.
	 * @return The current head.value at the specified index and returns the value each time the function is invoked. 
	 */

	public T get(int index) {
		//O(n)
		DoubleNode<T>temp = head;
		int i = 0;
		if(temp == null){//check if temp(head) is null
			return null;
		}
		if(index < 0 || index > this.size()){//check if index is in range
			throw new IndexOutOfBoundsException();
		}
		else if(index == 0){//gets value of head
			return temp.value;
		}
		else{
			while(i < index){// loops through the linked list till temp equals the node at the index.
				temp = temp.next;
				i += 1;
			}
			return temp.value;
		}

	}
	
	/**
	 * <p>Sets the value of head at a given indice location and returns the old value.</p>
	 * @param index  
	 * @param value
	 * @throws IndexOutOfBoundsException If index is greater than the current size or less than 0.
	 * @return A local variable that stores the previous head.value at the specified index and returns the value each time the function is invoked.
	 */
	
	public T set(int index, T value) {
		//O(n)
		T oldValue;
		int i = 0;
		DoubleNode<T>temp, copy;
		temp = head;
		if(index < 0 || index > this.size()){//check if index is in range
			throw new IndexOutOfBoundsException();
		}
		if(value == null){
			throw new NullPointerException();
		}
		else{
			if(index == 0){//insertion at beginning of list
				oldValue = temp.value;
				temp.value = value;
				copy = temp;
				updateHead(copy);
			}
			else if(index == this.size() - 1 || index == this.size()){//insertion at end of list
				while(temp.next != null){
					temp = temp.next;
				}
				oldValue = temp.value;
				temp.value = value;
				copy = temp;
				updateHead(copy);
			}
			else{//insertion at middle of list
				while(i < index){
					i += 1;
					temp = temp.next;
				}
				oldValue = temp.value;
				temp.value = value;
				copy = temp;
				updateHead(copy);
			}
		}
		return oldValue;
	}
	
	/**
	 * <p>Adds a value at a given index.</p>
	 * @param index
	 * @param value
	 * @throws IndexOutOfBoundsException If the index is greater than the current size of less than 0.
	 * @throws NullPointerException If the value is null or not of the same generic type T.
	 */
	public void add(int index, T value) {
		//O(n)
		int i = 0;
		DoubleNode<T>newNode = new DoubleNode<T>(value);
		DoubleNode<T>temp = head;
		DoubleNode<T>copy;
		DoubleNode<T>tempNext;
		if(index < 0 || index > this.size()){
			throw new IndexOutOfBoundsException();
		}
		else if(value == null){
			throw new NullPointerException();
		}
		else if(temp == null){
			temp = new DoubleNode<T>(value);
			this.currentItems += 1;
			copy = temp;
			updateHead(copy);
		}
		else{
			if(index == 0){
				newNode.next = temp;
				temp.prev = newNode;
				newNode.prev = null;
				temp = newNode;
				copy = temp;
				this.currentItems += 1;
				updateHead(copy);
			}
			else if(index == this.size() || index == this.size() - 1){
				while(temp.next != null){
					temp = temp.next;
				}	
				temp.next = newNode;
				newNode.prev = temp;
				newNode.next = null;
				copy = temp;
				this.currentItems += 1;
				updateHead(copy);
			}
			else{
				while(temp.value != this.get(index - 1)){
					temp = temp.next;
				}
				tempNext = temp.next;
				temp.next = newNode;
				newNode.prev = temp;
				newNode.next = tempNext;
				tempNext.prev = newNode;
				copy = temp;
				this.currentItems += 1;
				updateHead(copy);
			}
		}

	}

	/**
	 * <p>Takes a parameter temp to traverse through the link list until the previous node is null.</p>
	 * <p>This function ensures that the head is always at the beginning of the list.</p>
	 * @param temp
	 */
	private void updateHead(DoubleNode<T> temp){
		DoubleNode<T>copy = temp;
		while(copy.prev != null){
			copy = copy.prev;
		}
		this.head = copy;
	}
	
	/**
	 * <p>Appends a value to the linked list.</p>
	 * @param value
	 * @return Returns true if the value was successfully added to list otherwise false.
	 */
	public boolean add(T value) {
		//O(1)
		DoubleNode<T> newNode, copy, temp;
		temp = head;
		newNode = new DoubleNode<T>(value);
		if(temp == null){
			temp = new DoubleNode<T>(value);
			copy = temp;
			this.currentItems += 1;
			updateHead(copy);
			return true;
		}
		else if(value == null){
			return false;
		}
		else
			this.add(this.size(), value);
			return true;
		

	}

	/**
	 * <p>Removes the value at a given index and returns the value removed.</p>
	 * @param index
	 * @return A local variable that stores the head.value before successfully removing the node from the list.
	 */
	public T remove(int index) {
		//O(n)
		int i = 0;
		DoubleNode<T>temp,copy, tempNext, tempPrev;
		temp = head;
		T deletedItem = null;
		if(index < 0 || index > this.size()){
			throw new IndexOutOfBoundsException();
		}
		if(temp == null){
			deletedItem = null; 
		}
		if(this.size() == 1){
			this.head = null;
			this.currentItems = 0;
		}
		else{
			if(index == 0 && this.size() > 1){
				deletedItem = temp.value;
				temp = temp.next;
				temp.prev = null;
				copy = temp;
				this.currentItems -= 1;
				updateHead(copy);
				temp = null;
			}
			else if(index == this.size() - 1){
				while(temp.next != null){
					temp = temp.next;
				}
				deletedItem = temp.value;
				tempPrev = temp.prev;
				tempPrev.next = null;
				copy = temp;
				this.currentItems -= 1;
				updateHead(copy);
			}
			else{
				while(temp.value != this.get(index)){
					temp = temp.next;
				}
				deletedItem = temp.value;
				tempNext = temp.next;
				tempPrev = temp.prev;
				tempNext.prev = tempPrev;
				tempPrev.next = tempNext;
				copy = temp;
				this.currentItems -= 1;
				updateHead(copy);
			}
		}
		return deletedItem;
	}

	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	
	public static void main(String[] args){
		SimpleList<Character> letters = new SimpleList<>();	
		for (int i=0; i<5; i++){
			letters.add((char)(97+i*2));
		}
		if (letters.size() == 5 && letters.get(0) == 'a'){
			System.out.println("Yay 1");
		}
		
		if (letters.set(1,'b') == 'c' && letters.get(1) == 'b'){
			System.out.println("Yay 2");
		}

		letters.add(2,'c');
		if (letters.size() == 6 && letters.get(2) == 'c' && letters.get(3)=='e'){
			System.out.println("Yay 3");
		}
		
		if (letters.remove(3)=='e' && letters.size() == 5 && letters.get(3)=='g'){
			System.out.println("Yay 4");
		}
	}
	
	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	//bad practice to have public inst. variables, but we want to test this...
	public DoubleNode<T> head = null;
	
	//provided doubly-linked list node class
	//bad practice to have public inst. variables,
	//in a public nested class, but we want to test this...
	public static class DoubleNode<T> {
		public T value;
		
		public DoubleNode<T> next;
		public DoubleNode<T> prev;
		
		public DoubleNode() { }
		public DoubleNode(T value) { this.value = value; }
	}
	
	//provided toString() method
	public String toString(){
		StringBuilder sBuilder = new StringBuilder("");
		for (T value : this){
			sBuilder.append(value);
			sBuilder.append(" ");
		}
		return sBuilder.toString();
	}
	
	//provided iterator, if your code is working, this should
	//work too...
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			DoubleNode<T> current = head;
			
			public T next() {
				if(!hasNext()) throw new NoSuchElementException();
				T val = current.value;
				current = current.next;
				return val;
			}
			
			public boolean hasNext() {
				return (current != null);
			}
		};
	}
}
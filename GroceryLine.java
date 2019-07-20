import java.util.NoSuchElementException;
import java.util.Queue;

//A data structure (based on a queue) to represent a grocery
//store line.

//Grocery lines can only contain people and can be compared 
//to eachother based on the number of items left to process
//in the line. The lines also have id numbers.

/***
 * @author: Michael Teshome
 * @version: 1.8.0_151
 * Instructor: Yutao Zhong
 * Course: CS 310
 */

public class GroceryLine extends SimpleQueue<Person> implements Comparable<GroceryLine> {
	//Represents a corresponding aisle in the Grocery Store
	private int id;
	//Keeps track of the current total amount of items in each aisle of the Grocery Store
	private int itemsInLine;

	/**
	 * <p>Creates a Grocery Line with a given ID.</p>
	 * @param id
	 */
	public GroceryLine(int id) {
		//O(1)
		this.id = id;
		this.itemsInLine = itemsInLine;
	}
	
	/**
	 * <p>Returns the ID of the Grocery Line</p>
	 * @return Returns the ID value.
	 */
	public int getId() {
		//O(1)
		return this.id;
	}
	
	/**
	 * <p>Counts the total number of items in a Grocery Line</p>
	 * @return Returns the value of the total items in the Grocery Line.
	 */
	public int itemsInLine() {
		this.itemsInLine = 0;
		//O(n), where n = the number of people in line
		for(int i = 0; i < super.size(); i++){
			this.itemsInLine += super.get(i).getNumItems();
		}
		return this.itemsInLine;
	}
	
	/**
	 * <p>Compares one Grocery Line to another Grocery Line based on the total number of items per line.</p>
	 * <p>If the two lines are tied by number of items, the function then compares by the ID of the two lines.</p>
	 * @param otherLine
	 * @return returns a integer value greater than 1 if the other Grocery Line is less than this Grocery Line otherwise returns a integer
	 * value less than 1.
	 */
	//Compare one grocery line to another based on
	//the number of items in the line and then, if
	//the two lines are tied, by their id.
	public int compareTo(GroceryLine otherLine) {
		//O(n+m), where n = the number of people in the
		//first line and m = the number of people in the
		//second line
		if(this.itemsInLine() == otherLine.itemsInLine() && this.getId() > otherLine.getId()){
			return 1;
		}
		else if(this.itemsInLine() == otherLine.itemsInLine() && this.getId() < otherLine.getId()){
			return -1;
		}
		else if(this.itemsInLine() == otherLine.itemsInLine() && this.getId() == otherLine.getId()){
			return 0;
		}
		else{
			if(this.itemsInLine() < otherLine.itemsInLine()){
				return -1;
			}
			else{
				return 1;
			}
		}

	}
	
	/**
	 * <p>Removes one items from the first person in the Grocery Line</p>
	 * <p>If the first person has no more items to process, then the first person is removed from the line.</p>
	 */
	public void processItem() {
		//O(1)
		if(super.get(0) == null){
			System.out.println();
		}
		else if(super.get(0).getNumItems() == 0){
			super.remove();
		}
		else{
			super.get(0).removeItem();
		}
	}
	
	/**
	 * <p>Converts the data in the Grocery Line into a summary of total amount of shoppers and items per line.</p>
	 * @return Returns a local variable that concatonates the data from the Grocery Line into a String format.
	 */
	//Converts the line to a string.
	public String toString() {
		//O(n), where n = the number of people in line
		String linesToString = "" + this.id + ": ";
		for(int i = 0; i < super.size(); i++){
			linesToString += "Person (" + super.get(i).getNumItems() + ") "; 
		}
		linesToString += "= " + super.size() + " shoppers(s) with " + this.itemsInLine() + " item(s) in line";
		return linesToString;
	}
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	
	public static void main(String[] args) {
		GroceryLine line = new GroceryLine(0);
		Person mason = new Person(10);
		Person george = new Person(20);
		
		line.offer(mason);
		line.offer(george);
		
		if (line.getId()==0 && line.itemsInLine() == 30){
			System.out.println("Yay 1");
		}
		
		line.processItem();		
		if (line.itemsInLine() == 29 && line.peek().getNumItems()==9){
			System.out.println("Yay 2");
		}

		GroceryLine line2 = new GroceryLine(1);
		Person washington = new Person(40);
		line2.offer(washington);
		
		if (line.compareTo(line2)<0){
			System.out.println("Yay 3");
		}
	}
}
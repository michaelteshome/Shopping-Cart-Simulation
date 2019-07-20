/***
 * @author: Michael Teshome
 * @version: 1.8.0_151
 * Instructor: Yutao Zhong
 * Course: CS 310
 */

//class representing a person and their cart 
public class Person {
	//Add more instance variables here...
	//private or protected only!
	
	// used for initializing number of items passed in by Person Constructor
	private int numOfItems;

	/**
	 * <p>Sets up a person with a given number of items.</p>
	 * @param numItems 
	 * @throws IlegalArgumentException If number of items is invalid or less than the value (1).
	 */
	public Person(int numItems) {
		//O(1)
		//throws an IllegalArgumentException if numItems is
		//invalid (the person has less than one item)
		if(Integer.valueOf(numItems) != numItems){
			throw new IllegalArgumentException();
		}
		this.numOfItems = numItems;
	}
	
	/**
	 * <p>Gets how many items the person still has.</p>
	 * @return Returns the current number of items.
	 */
	public int getNumItems() {
		//O(1)
		return this.numOfItems;
	}
	
	/**
	 * <p>Removes one item from this person (i.e "checks out" ).</p>
	 * @return Returns the current number of items after decrementing by a value of (1).
	 */
	public void removeItem() {
		//O(1)
		this.numOfItems -= 1;
	}
	
	/**
	 * <p>Indicates whether or not this person has any more items left to "check out".</p>
	 * @return Returns true if there are no more current items otherwise false.
	 */
	public boolean done() {
		//O(1)
		if(this.numOfItems == 0){
			return true;
		}
		else	
			return false;
	}
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	
	public static void main(String[] args){
		Person mason = new Person(2);
		if (mason.getNumItems() == 2 && !mason.done()){
			System.out.println("Yay 1");
		}
		
		mason.removeItem();
		boolean ok = (mason.getNumItems() == 1);
		mason.removeItem();
		if ( ok && mason.done()){
			System.out.println("Yay 2");
		}
	}
	
	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	//provided toString() method
	public String toString() {
		return "Person("+getNumItems()+")";
	}
}
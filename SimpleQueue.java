import java.util.Queue;
import java.util.NoSuchElementException;

//Class for a simple queue based on SimpleList
//The "front" of the queue should be the front of
//the list.

//READ THE DOCUMENTATION for the Queue interface for more information
//on how these methods should work and when exceptions should
//be thrown. This data structure does not allow adding null.

//DO NOT re-implement nodes in this class... remember how
//inheritance works in Java and make use of that!

/***
 * @author: Michael Teshome
 * @version: 1.8.0_151
 * Instructor: Yutao Zhong
 * Course: CS 310
 */

public class SimpleQueue<T> extends SimpleList<T> implements Queue<T> {
	/**
	 * <p>Appends a value to the linked list</p>
	 * @param item
	 * @throws NullPointrException Throws this exception if and only if the item is null or incompatible to the generic type.
	 * @return Returns true after successful insertion of the value.
	 */
	public boolean add(T item) {
		//O(1)
		boolean logic;
		if(item == null){
			logic = false;
			throw new NullPointerException();
		}
		else{
			super.add(item);	
			logic = true;
		}
		return logic;
	}
	
	/**
	 * Appends a value to the linked list and ensures that insertion of a value doesn't violate capacity restrictions
	 * @param item
	 * @throws NullPointerException Throws this exception if and only if the item is null or incompatible to the generic type.
	 * @return Returns true upons successful insertion of the value if the value is in the linked list otherwise returns false.
	 */
	public boolean offer(T item) {
		//O(1)
		boolean logic;
		if(item == null){
			throw new NullPointerException();
		}
		else{
			super.add(item);	
			logic = (super.get(super.size() - 1) == item);
		}
		return logic;
	}

	/**
	 * <p>Removes head and retrieves the head value prior to removal</p>
	 * @throws NoSuchElementException Throws this exception if the linked list is empty based on whether the head is null.
	 * @return A local variable that stores the original head value prior to removal. It is returned each time this function is called.
	 */
	public T remove() {
		//O(1)
		T oldItem;
		if(super.head == null){
			oldItem = null;
			throw new NoSuchElementException();
		}
		else{
			oldItem = super.get(0);
			super.remove(0);
		}
		return oldItem;
	}

	/**
	 * <p>Removes head and retrieves the head value prior to removal</p>
	 * @return Returns a local variable that stores the head value prior to removal if and only if head is not null otherwise returns null.
	 */
	public T poll() {
		//O(1)
		T oldItem = null;
		if(super.head == null){
			return null;
		}
		else{
			oldItem = this.remove();
		}
		
		return oldItem;
	}
	
	/**
	 * <p>Retrieves the head value of the linked list</p>
	 * @throws NoSuchElementException Throws this exception if and only if the head is null or the size is equivalent to the value (0).
	 * @return Returns the head value if and only if the head is not null otherwise throws an exception.
	 */
	public T element() {
		//O(1)
		if(super.head == null || super.size() == 0){
			throw new NoSuchElementException();
		}
		else
			return super.get(0);
	}
	
	/**
	 * <p>Retrieves the head value of the linked list</p>
	 * @return Returns the head value if and only if the head is not null otherwise returns null.
	 */
	public T peek() {
		//O(1)
		if(super.head == null || super.head.value == null){
			return null;
		}
		else 
			return this.element();
	}
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	
	public static void main(String[] args){
		SimpleQueue<Integer> nums = new SimpleQueue<>();
		nums.offer(2);
		nums.offer(3);
		nums.offer(5);
		
		if (nums.peek() == 2 && nums.size()==3){
			System.out.println("Yay 1");
		}

		if (nums.poll() == 2 && nums.poll() == 3 
			&& nums.poll() == 5 && nums.poll() == null){
			System.out.println("Yay 2");			
		}

	}
}
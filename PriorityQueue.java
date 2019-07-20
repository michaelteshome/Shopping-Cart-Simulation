import java.util.NoSuchElementException;
import java.util.Date; //for testing

/***
 * @author: Michael Teshome
 * @version: 1.8.0_151
 * Instructor: Yutao Zhong
 * Course: CS 310
 */

//priority queue where the minimum item has the highest priority
public class PriorityQueue<T extends Comparable<T>> extends SimpleQueue<T> {
	/**
	 * <p>Updates an item that's already in the queue.</p>
	 * @param item
	 */
	//updates an item that's already in the queue
	//NOTE: This should update the exact item in memory, 
	//not just any "equal" item (in other words, you 
	//should use == here and not .equals())
	public void update(T item) 	{
		//O(n)
		//throws NoSuchElementException if item is not
		//in the queue
		int replaceItemPos = 0;
		for(int i = 0; i < super.size(); i++){
			if(super.get(i) == item){
				super.set(i, item);
				replaceItemPos = i;
			}

		}

		if(this.get(replaceItemPos) != item){
			throw new NoSuchElementException();
		}

		this.sortPriorityQueue();		
	}
	
	//You may need to override some other methods from SimpleQueue!
	
	//Restriction 1: all methods from SimpleQueue should still work
	//(as in, if you add(), the value should be added, if you call 
	//size() it should return the correct value, etc.). However, 
	//remove/poll will remove the _minimum_ value from the queue;
	//element/peek will return the _minimum_ value from the queue.
	
	//Restriction 2: element() and peek() must still both be O(1)
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	
	//Represents the highest priority of the Queue.
	private T minimumValue;

	/**
	 * <p>Finds the highest priority of the Queue, to help maintain sortedness of the Queue.</p>
	 * @return Returns the specific index for which the highest priority of the queue is located.
	 */
	private int findMin(){
		T min = super.get(0);
		int minPos = 0;
		for(int i = 0; i < super.size(); i++){
			if(min.compareTo(super.get(i)) > 0){
				min = super.get(i);
				minPos = i;
			}
		}

		this.minimumValue = min;
		return minPos;
	}

	/**
	 * <p>Removes highest priority and retrieves the value prior to removal.</p>
	 * @throws NoSuchElementException Throws this exception if the linked list is empty based on whether the head is null and if the priority exists.
	 * @return A local variable that stores the original priority value prior to removal. It is returned each time this function is called.
	 */
	@Override
	public T remove(){
		int minPos = findMin();
		T oldItem = null;
		if(super.head == null || minimumValue == null){
			oldItem = null;
			throw new NoSuchElementException();
		}
		else{
			oldItem = super.get(minPos);
			super.remove(minPos);
		}	

		this.sortPriorityQueue();
		return oldItem;
	}

	/**
	 * <p>Removes highest priority and retrieves the value prior to removal</p>
	 * @return Returns a local variable that stores the priority value prior to removal if and only if head and priority is not null otherwise returns null.
	 */
	@Override
	public T poll(){
		T oldItem = null;
		if(super.head == null || minimumValue == null){
			return null;
		}
		else{
			oldItem = this.remove();
		}
		
		return oldItem;
	}

	/**
	 * <p>Retrieves the priority value of the linked list</p>
	 * @return Returns the priority value if and only if the priority is not null otherwise returns null.
	 */
	@Override
	public T element(){
		int minPos = findMin();
		if(this.minimumValue == null){
			return null;
		}
		else	
			return this.minimumValue;
	}

	/**
	 * <p>Retrieves the priority value of the linked list</p>
	 * @return Returns the priority value if and only if the priority is not null otherwise returns null.
	 */
	@Override
	public T peek(){
		if(super.get(findMin()) == null || this.minimumValue == null){
			return null;
		}
		else 
			return this.element();
	}

	/**
	 * <p>Appends a value to the linked list based on priority of the value passed in by the parameter.</p>
	 * @param item
	 * @throws NullPointrException Throws this exception if and only if the item is null or incompatible to the generic type.
	 * @return Returns true after successful insertion of the value.
	 */
	@Override
	public boolean add(T item){
		boolean logic;

		if(item == null){
			throw new NullPointerException();
		}
		else{
			super.add(item);	
			logic = true;
		}
		
		this.sortPriorityQueue();
		return logic;
	}

	/**
	 * <p>Sorts the Priority Queue based on which value has higher priority using the compareTo() method.</p>
	 */
	private void sortPriorityQueue(){
		int i = 0, j = 0;
		while(i < super.size()){
			for(j = i; j > 0; j--){
				if(super.get(j).compareTo(super.get(j - 1)) < 0){
					T replacement = super.get(j - 1);
					super.set((j - 1), super.get(j));
					super.set(j, replacement);
				}

			}

			i += 1;
		}
	}
    //********************************Testing for code is below this line**************************************************************************** */
	public static void main(String[] args){
		PriorityQueue<Date> values = new PriorityQueue<>();
		Date[] dates = new Date[5];
		for (int i=5; i>=1; i--){
			dates[i-1] = new Date(86400000*i);
			values.add(dates[i-1]);
		}
		
		for(Date d : values) {
			System.out.println(d);
		}
		
		dates[3].setTime(0);
		values.update(dates[3]);
		
		System.out.println();
		for(Date d : values) {
			System.out.println(d);
		}
	
		if(values.peek().equals(dates[3])) {
			System.out.println("\nYay 1");
		}
	}
}
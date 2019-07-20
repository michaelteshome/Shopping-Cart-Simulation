import java.util.Random;

class GroceryStore {
	/**
	 *  A random number generator.
	 */
	public static Random r;
	
	/**
	 *  Number of people who might be in a given
	 *  line at the beginning of the simulation.
	 */
	public static final int NUM_INIT_PEOPLE = 3;
	
	/**
	 *  Max number of initial items a person might have.
	 */
	public static final int NUM_INIT_ITEMS = 100;
	
	/**
	 *  How often (in steps) a person is added to the
	 *  simulation.
	 */
	public static final int NUM_STEPS_ADD_PERSON = 5;
	
	/**
	 *  How many simulation steps have occurred so far.
	 */
	public int numStepsRun = 0;
	
	/**
	 *  A priority queue of lines in the grocery store.
	 */
	public PriorityQueue<GroceryLine> lines = new PriorityQueue<>();
	
	/**
	 *  Get the number of items a given person has. The person
	 *  is identified as being the __th person in the __th line.
	 *  This method is only used with the simulator.
	 *  
	 *  @param lineNumber which line the person is in (starts with line 1)
	 *  @param placeInLine which position in line the person is (starts with position 1)
	 *  @return the number of items the person has
	 */
	public int getPersonItems(int lineNumber, int placeInLine) {
		Object[] linesArray = lines.toArray();
		
		//find the line the person is in (by id)
		for(int i = 1; i <= lines.size(); i++) {
			for(Object lineObject : linesArray) {
				GroceryLine line = (GroceryLine)lineObject;
				if(line.getId() == lineNumber) {
					//find the spot in line requested
					int spotInLine = 1;
					
					//if the line is shorter than the position asked for, give up
					if(line.size() < placeInLine)
						return -1;
					
					for(Person p : line) {
						//found the spot in line?
						if(spotInLine == placeInLine) {
							//get the number of items for the person and return
							return p.getNumItems();
						}
						spotInLine++;
					}
				}
			}
		}
		
		return -1;
	}
	
	/**
	 *  Sets up a grocery store with a given number of lines.
	 *  
	 *  @param numLines the number of lines the grocery store has
	 *  @param seed the seed for the random number generator
	 */
	public GroceryStore(int numLines, int seed) {
		r = new Random(seed);
		for(int i = 0; i < numLines; i++) {
			//make a new line
			GroceryLine line = new GroceryLine(i+1);
			
			//add a random number of people to the line (between 0 and 9)
			int numPeople = r.nextInt(NUM_INIT_PEOPLE);
			for(int j = 0; j < numPeople; j++) {
				//make a random person with between 0 and 100 items
				Person p = new Person(r.nextInt(NUM_INIT_ITEMS)+1);
				line.add(p);
			}
			
			//add lines to list of lines
			lines.add(line);
		}
	}
	
	/**
	 *  Runs some number of steps of the simulation.
	 *  
	 *  @param numSteps the number of steps to run
	 */
	public void simulate(int numSteps) {
		//get all the lines as an array
		Object[] linesArray = lines.toArray();
		
		//simulate each step (process one item from each person
		for(int i = 0; i < numSteps; i++) {
			for(Object line_o : linesArray) {
				GroceryLine line = (GroceryLine) line_o;
				line.processItem();
				lines.update(line);
			}
			numStepsRun++;
			
			//every ten steps
			if(numStepsRun % NUM_STEPS_ADD_PERSON == 0) {
				Person p = new Person(r.nextInt(NUM_INIT_ITEMS)+1);
				GroceryLine bestLine = lines.remove();
				bestLine.add(p);
				lines.add(bestLine);
			}
		}
	}
	
	/**
	 *  Print out an text representation of the grocery store.
	 */
	public void printStatus() {
		System.out.println("************ Grocery Store Step "+numStepsRun+" ************");
		System.out.println("Number of lines: " + lines.size());
		System.out.println();
		
		for(int i = 1; i <= lines.size(); i++) {
			for(GroceryLine line : lines) {
				if(line.getId() == i) {
					System.out.println((GroceryLine)line);
					System.out.println();
				}
			}
		}
		
		System.out.println("Best line: " + lines.element());
	}
	
	/**
	 *  Main method to run a text-based simulation of the store.
	 *  
	 *  @param args [0] = number of lines, [1] = seed for the random number generaor
	 */
	public static void main(String[] args) {
		GroceryStore g = new GroceryStore(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		for(int i = 0; i < 20; i++) {
			g.simulate(1);
			g.printStatus();
		}
	}
}
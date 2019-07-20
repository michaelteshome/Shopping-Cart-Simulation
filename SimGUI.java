import java.util.*;
import java.io.*;

//graphics packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  GUI
 *  
 *  @author Katherine (Raven) Russell
 */
class SimGUI {
	/**
	 *  Frame for the GUI
	 */
	private JFrame f;
	
	/**
	 *  Current simulation
	 */
	private GroceryStore store = null;
	
	/**
	 *  The panel containing the store diplay.
	 */
	private JPanel displayPanel = null;
	
	/**
	 *  The panel containing the step, reset, and play buttons.
	 */
	private JPanel buttonPanel = null;
	
	/**
	 *  Whether or not a simulation is currently playing with
	 *  the play button (i.e. automatically playing).
	 */
	private boolean playing = false;
	
	/**
	 *  How wide to make the buttons representing a person.
	 */
	private final int BUTTON_WIDTH = 40;
	
	/**
	 *  The seed to use for the random number generator
	 *  associated with the grocery store simulation.
	 */
	private final int seed;
	
	/**
	 *  How tall to make the buttons representing a person.
	 */
	private final int BUTTON_HEIGHT = 40;
	
	/**
	 *  How tall to make the buttons representing a person.
	 */
	private final int MAX_PERSON_DISPLAY = 10;
	
	/**
	 *  How tall to make the buttons representing a person.
	 */
	private final int NUM_LINES = 10;
	
	/**
	 *  Load up the GUI!
	 *  
	 *  @param seed seed for the random number generator in GroceryStore
	 */
	public SimGUI(int seed) {
		this.seed = seed;
		
		f = new JFrame("Grocery Store Simulation");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 540);
        f.getContentPane().setLayout(new FlowLayout());
		
		makeMenu();
		resetStore();
		
        f.setVisible(true);
	}
	
	/**
	 *  Makes the menu for the simulation.
	 */
	public void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Simulation");
		
		//exit option
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		menu.add(exit);
		
		menuBar.add(menu);
		f.setJMenuBar(menuBar);
	}
	
	/**
	 *  Makes the buttons for the store grid.
	 */
	public void makeStorePanel() {
		if(store == null) return;
		if(displayPanel != null) f.remove(displayPanel);
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(NUM_LINES, MAX_PERSON_DISPLAY));
		
		//add store grid
        for (int l = 0; l < NUM_LINES; l++)
        {
            for (int p = 0; p < MAX_PERSON_DISPLAY; p++)
            {
				if(p == 0) {
					ArrowButton b = new ArrowButton(l+1);
					b.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_WIDTH));
					b.setMargin(new Insets(0,0,0,0));
					b.setBorderPainted(false);
					//b.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
					b.setFocusPainted(false);
					b.setContentAreaFilled(false);
					displayPanel.add(b);
				}
				else {
					PersonButton b = new PersonButton(l+1, p);
					b.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_WIDTH));
					b.setMargin(new Insets(0,0,0,0));
					//b.setBorderPainted(false);
					b.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
					b.setFocusPainted(false);
					b.setContentAreaFilled(false);
					displayPanel.add(b);
				}
            }
        }
		
		f.add(displayPanel, 0);
		f.revalidate();
	}
	
	/**
	 *  Makes the panel containing the step, reset, and play buttons.
	 */
	public void makeBottomButtons() {
		if(store == null) return;
		if(buttonPanel != null) f.remove(buttonPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		//step button
		JButton step = new JButton("Step");
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				step();
			}
		});
		buttonPanel.add(step);
		
		//reset button
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				resetStore();
			}
		});
		buttonPanel.add(reset);
		
		//play button
		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//toggle playing and not playing
				playing = !playing;
				buttonPanel.getComponent(0).setEnabled(!playing);
				buttonPanel.getComponent(1).setEnabled(!playing);
				((JButton)buttonPanel.getComponent(2)).setText((playing ? "Stop" : "Play"));
				
				//if playing, kick off a timer to drop dots and step them
				if(playing) {
					new javax.swing.Timer(200, new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							//someone hit the stop button
							if(!playing) {
								((javax.swing.Timer)event.getSource()).stop();
								return;
							}
							else {
								step();
							}
						}
					}).start();
				}
			}
		});
		buttonPanel.add(play);
		
		f.add(buttonPanel, 1);
		f.revalidate();
	}
	
	/**
	 *  Calls the step button on the simulation and updates
	 *  the GUI to display the result.
	 *  
	 *  @return whether or not the simulation was able to step
	 */
	public boolean step() {
		store.simulate(1);
		for (int y = 0; y < NUM_LINES; y++)
        {
            for (int x = 0; x < MAX_PERSON_DISPLAY; x++)
            {
				Component c = displayPanel.getComponent((y*MAX_PERSON_DISPLAY) + x);
				if(x == 0)
					((ArrowButton)c).update();
				else
					((PersonButton)c).update();
			}
		}
		//store.printStatus();
		
		return true;
	}
	
	/**
	 *  Load a new simulation.
	 */
	public void resetStore() {
		store = new GroceryStore(NUM_LINES, this.seed);
		makeStorePanel();
		makeBottomButtons();
	}
	
	/**
	 *  A main method to run the simulation with GUI.
	 *  
	 *  @param args [0] = the seed for the store's random number generator
	 */
	public static void main(String[] args) {
		new SimGUI(Integer.parseInt(args[0]));
	}
	
	/**
	 *  Inner class representing an arrow (will display if
	 *  this line is the "smallest" based on the priority
	 *  queue.
	 */
	class ArrowButton extends JButton {
		/**
		 *  Current line.
		 */
		private int line;
		
		/**
		 *  Set the text to whatever the grid thinks it should be.
		 *  
		 *  @param line what line the arrow is in front of
		 */
		public ArrowButton(int line) {
			super("");
			this.line = line;
			update();
		}
		
		/**
		 *  Update the text to reflect the current state of the simulation
		 *  for this location.
		 */
		public void update() {
			int bestId = store.lines.element().getId();
			//System.out.println(numItems);
			if(bestId == this.line)
				setText("\u2192");
			else
				setText("");
		}
	}
	
	/**
	 *  Inner class representing a single person in line.
	 */
	class PersonButton extends JButton {
		/**
		 *  Place in line.
		 */
		private int place;
		
		/**
		 *  Current line.
		 */
		private int line;
		
		/**
		 *  Set the text to whatever the grid thinks it should be.
		 *  
		 *  @param line what line the person is in
		 *  @param place what spot in line the person is in
		 */
		public PersonButton(int line, int place) {
			super("");
			this.line = line;
			this.place = place;
			update();
		}
		
		/**
		 *  Update the text to reflect the current state of the simulation
		 *  for this location.
		 */
		public void update() {
			int numItems = store.getPersonItems(line, place);
			//System.out.println(numItems);
			if(numItems < 0)
				setText("");
			else
				setText("["+numItems+"]\u263A");
		}
	}
}
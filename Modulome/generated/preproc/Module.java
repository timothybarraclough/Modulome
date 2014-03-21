import netP5.NetAddress;

import com.rapplogic.xbee.api.XBeeAddress64;

import controlP5.ControlEvent;

import processing.core.PApplet;



public interface Module {
    /**
     * Draws this module.
     * @param g - applet to which it drawn.
     */
	public void draw(PApplet g);
	/**
	 * Method for processing the Serial input from XBee Module
	 * @param data - packet received
	 */
	public void processdata(int[] data);
	/**
	 * Output what is stored in variables
	 */
	public void output();
	
	
	/**
	 * Gets the XBee Address of the module
	 * @return the address of the module
	 */
	public int getaddress();
	/**
	 * Sets the Xbee Adress of the module
	 * @param addr - takes the xbee address to be set
	 */
	public void setaddress(int addr);
	
	public void setnetaddress(NetAddress addr);
	/**
	 * Gets the Colour for the module
	 * @return the colour as a 32bit int
	 */
	public int getColour();
	/**
	 * Sets the Colour for the module
	 * @param colour - the colour to be set
	 */
	public void setColour(int colour);
	/**
	 * Sets the Source for LED Feedback
	 * @param source - Midi, OSC or Module
	 */
	public void setfeedbacksource(int source);
	/**
	 * Returns the current source for the LED Feedback
	 * @return the source
	 */
	public int getfeedbacksource();
	/**
	 * Gets the ID for a module depending on the order they were added
	 * @return The ID number
	 */
	public int getID();
	/**
	 * Sets the ID for a module depending on the order it was added
	 * @ret
	 */
	
public void setID(int theID);

	/**
	 * Uses ID number to set the xPosition of the module frame
	 * @return the x position in pixels
	 */
	
	public int getXposition();
	
	/**
	 * Uses ID number to set the xPosition of the module frame
	 * @return the y position in pixels
	 */
	
	public int getYposition();
	/**
	 * Will Display the settings for the module and initiate fade
	 */
	public void displaySettings();
	/**
	 * Will close the settings window and do the fade
	 */
	public void hideSettings();
	/**
	 * Call this when the module starts to initiate a nice fade in
	 */
	public void start();
	
	/**
	 * Get the size of the module (should be a global one)
	 * @return the size of the module
	 */
	public int getmodsize();
	/**
	 * Sets the module size. This is only really important for the initiation method but can also be handy for dynamic resizing.
	 * @param thesize - The size to set the module too
	 */
	public void setmodsize(int thesize);
	/**
	 * All this really does is alternates between hidesettings and displaysettings, depending on what each one is currently doing.
	 */
	public void settings();
	
	/**
	 * Delete the module --- This will be handy for closing OSC ports or freeing up Midi junk.
	 * 
	 */
	public void exit();
	/**
	 * Method for passing mouse Events to module
	 * @param mouseButton - The Mouse button
	 * @param mouseX - The X position (Absolute)
	 * @param mouseY - The Y position
	 */
	public void mousePressed(int mouseButton, int mouseX, int mouseY);

	

/**
 * Method for passing mouse Events to module
 * @param mouseButton - The Mouse button
 * @param mouseX - The X position (Absolute)
 * @param mouseY - The Y position
 */
public void mouseMoved(int mouseButton, int mouseX, int mouseY);
public void modulemoved();
public void remove();
public void controlEvent(ControlEvent theevent);

public void setoscprefix(String theprefix);

/**
 * Method for checking if OSC is on
 * 
 */
public boolean isoscon();
}

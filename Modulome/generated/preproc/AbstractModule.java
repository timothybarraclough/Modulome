import java.util.Arrays;

import netP5.NetAddress;

import processing.core.PApplet;

import com.rapplogic.xbee.api.XBeeAddress64;

import controlP5.ControlEvent;


public abstract class AbstractModule implements Module {
	private int address;
	private int Colour;
	private int ID;
	private int xpos;
	private int ypos;
	private int fade = 255;
	private int modsize;
	private int fadetarget;
	private boolean isOscOn = true;
	private boolean SerialOsc = false;
	protected String pre = "/mlr/grid/key";
	protected NetAddress myRemoteLocation;
	
	abstract public void modulemoved();
	
	@Override
	abstract public void draw(PApplet g);

	@Override
	abstract public void processdata(int[] data);

	@Override
	public int getaddress() {
		// TODO Auto-generated method stub
		return address;
	}

	@Override
	public void setaddress(int addr) {
		//address = 0;
		address = addr;

	}
	public void setnetaddress(NetAddress addr) {
		//address = 0;
		myRemoteLocation = addr;

	}
	
	@Override
	public int getColour() {
		// TODO Auto-generated method stub
		
		return Colour;
	}

	@Override
	public void setColour(int colour) {
		int col = colour;
		Colour = col;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setfeedbacksource(int source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getfeedbacksource() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getID(){
		
		
		return ID;
	}

	@Override	
public void setID(int theID){
		ID = theID;
	}
	@Override
	public int getXposition(){
		
		int pos = ((ID%4) * modsize);
		
		return pos;
		
	}
	@Override
	public int getYposition(){
		int pos = (((int)(ID/4)) * modsize);
		return pos;
	}
	@Override
	public void displaySettings(){
		fadetarget = 255;
	}
	@Override	
	public void hideSettings(){
		fadetarget = 0;
	}
	@Override	
	public void start(){
		
	
	}
	
	@Override	
	public int getmodsize(){
	return modsize;	
	}
	@Override	
	public void setmodsize(int thesize){
		modsize = thesize;
	}
	

public void setfade(int thefade){
	
	fade = thefade;
}

public int getfade(){
	if ((fadetarget > fade)){
		fade = fade + 8;
	}
	else if ((fadetarget < fade)){
		fade = fade - 8;
	}
	
	if (fade < 0) fade = 0;
	else if (fade > 255) fade = 255;
	
	return fade;
			
	}


public void settings(){
	if (fadetarget <= 127){
		displaySettings();
	}
	else if (fadetarget > 127){
		hideSettings();
	}
}
@Override
public void exit(){
	
}

@Override
public void mousePressed(int mouseButton, int theX, int theY){
	
}
@Override
public void mouseMoved(int mouseButton, int theX, int theY){
	
}

public void controlEvent(ControlEvent theevent) {
	// TODO Auto-generated method stub
	
}

public void setoscprefix(String theprefix){
	
	pre = theprefix;
}

@Override
public boolean isoscon(){
	return isOscOn;
	
}


}
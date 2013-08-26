import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.RadioButton;
import processing.core.PApplet;
import processing.core.PConstants;


public class EncoderModule extends AbstractModule{

	
	private ControlP5 cp5;
	private Group controls;
	private Group midisettings;
	private Group oscsettings;
	private Group feedbacksettings;
	private int modsize;
	private Modulome that;
	private int[] encoderlights = new int[32];
	private int thedragstate;
	
	public EncoderModule(int ID, int Colour, int modsize2, int addr, Modulome tha, ControlP5 thep5){
		
	setID(ID);
	setColour(Colour);
	setmodsize(modsize2);
	hideSettings();
	setaddress(addr);
	for (int i = 0; i < 32; i++)
	encoderlights[i] = 1;
	 that = tha;
	 cp5 = thep5;
	 modsize = modsize2;
	 
	 
		controls =  cp5.addGroup("Module "+addr+ " Settings")
				.setId(addr*100)
				.setPosition(getXposition()+(modsize/10)+1,getYposition()+(modsize/10)+1)
				.hideBar()
				.hide()
				.setBackgroundHeight(4*modsize/5)
				.setWidth(4*modsize/5)
				.setBackgroundColor(that.color(255,50))
				;

		 RadioButton rb = cp5.addRadioButton("modeselect"+addr)
				.setId(addr*100 + 1)
				 .moveTo(controls)
				.setItemsPerRow(3)
				.addItem("Feedback " + addr,0)
				.addItem("MIDI " + addr,1)
				.addItem("OSC " + addr,2)
				.setSpacingColumn(0)
				.setLabelPadding(-60, 30)
				.setPosition(0,0)
				//.hideLabels()
				.setLabel("Feedback")
				.setColorBackground(Colour)
				.setColorForeground(Colour + (0x00222222))
				.setColorActive(Colour + (0x00333333))
				//.set
				.setSize(4*modsize/15+1,modsize/8);
		

		midisettings =  cp5.addGroup("Module "+addr+ " MIDI")
				.setId(addr*100 + 2)				
				.setPosition(0,modsize/4)
				.hideBar()
				.moveTo(controls)
				//.hide()
				.setBackgroundHeight(modsize/2)
				.setWidth(4*modsize/5)
				//.setBackgroundColor(that.color(30,140,255,150))
				;
		oscsettings =  cp5.addGroup("Module "+addr+ " OSC")
				.setId(addr*100 + 3)				
				.setPosition(0,modsize/4)
				.hideBar()
				.moveTo(controls)
				//.hide()
				.setBackgroundHeight(modsize/2)
				.setWidth(4*modsize/5)
				//.setBackgroundColor(that.color(130,30,140,150))
				;
		feedbacksettings =  cp5.addGroup("Module "+addr+ " Feedback")
				.setId(addr*100 + 4)
				.setPosition(0,modsize/4)
				.hideBar()
				.moveTo(controls)
				//.hide()
				.setBackgroundHeight(modsize/2)
				.setWidth(4*modsize/5)
				//.setBackgroundColor(that.color(255,140,30,150))
				;

	}

	@Override
	public void draw(PApplet g) {
		
		
		int x = getXposition();
		int y = getYposition();
		
		if (getfade() < 255){
			if (controls.isVisible() == true) controls.hide();
		
		g.stroke(150);
		
		
		g.strokeWeight(2);
		g.fill(getColour());
		g.ellipse(1+x+(getmodsize()/2),
				1+y+(getmodsize()/2),
				getmodsize()*9/10,
				getmodsize()*9/10);
		g.fill(getColour() + 0x00222222);
		
		g.noStroke();		
		for(int i = 0; i < 32; i++){
			if(encoderlights[i] == 1)
		g.arc(1+getmodsize()/2+x, 
				1+getmodsize()/2+y, 
				getmodsize()*17/20, 
				getmodsize()*17/20, 
				((i*2)*g.PI/32) - (1/360), 
				(((i+1))*2*g.PI/32) + (1/360), 
				PConstants.PIE);
		}
		g.fill(getColour());
		g.ellipse(1+getmodsize()/2+x, 1+getmodsize()/2+y, getmodsize()*22/30, getmodsize()*22/30);
		g.stroke(2);
		//g.ellipse(x+150,y+150,250,250);
		//g.strokeWeight(2);
		//g.fill(70,70,170);
		g.noStroke();
		g.fill(getColour() + 0x00111111);
		g.ellipse(1+x+(getmodsize()/2),1+y+(getmodsize()/2), getmodsize()*7/10 , getmodsize()*7/10 );

		//g.stroke(getColour() + 0x77000000);
		//g.noStroke();
		g.fill(getColour());
		//g.strokeWeight(2);
		//g.stroke(150);
		g.ellipse(1+(getmodsize()/2)+x+(getmodsize()/4)*g.sin((float)(g.PI*2*0.1)),  //The * 1 is where the variable from the encoder goes
				(1+getmodsize()/2)+y+(getmodsize()/4)*g.cos((float)(g.PI*2*0.1)),
				getmodsize()/6, getmodsize()/6);
		

		
		}
		if (getfade() != 0){
			
		}
		if (getfade() == 255){

			controls.show();
		}
		g.fill(0,getfade());
		g.strokeWeight(2);
		g.stroke(150);
		g.rect(x+2,y+2,getmodsize()-4,getmodsize()-4);
		
		
	}

	@Override
	public void processdata(int[] data) {
		// TODO Auto-generated method stub

	}
	public void mousePressed(int thebutton, int thex, int they){
		
		thex = thex - getmodsize()/2 + 2;
		they = they - getmodsize()/2 + 2;
		
		
		if (thebutton == 37){
			if (getfade() == 0){
			if(Math.hypot(Math.abs(thex), Math.abs(they)) < getmodsize()*17/20){
				
				int i = (int) ((((Math.atan2(they,thex)) + Math.PI)/(Math.PI/16)) + 16)%32;
				encoderlights[i] = Math.abs(encoderlights[i] - 1);
				thedragstate = encoderlights[i];
				
			}
		
			}
			else {
			//	that.moduleremover(getID());
		
			}
		
		
		
		}
		else if ((thebutton == 38)&&(getfade()==255)){
			controls.remove();
			that.moduleremover(getID());

		}
		
	}
public void mouseMoved(int thebutton, int thex, int they){
	
		thex = thex - getmodsize()/2 + 2;
		they = they - getmodsize()/2 + 2;
		
		
		if (thebutton == 37){
		
			if(Math.hypot(Math.abs(thex), Math.abs(they)) < getmodsize()*17/20){
				
				int i = (int) ((((Math.atan2(they,thex)) + Math.PI)/(Math.PI/16)) + 16)%32;
				encoderlights[i] = thedragstate;
				
			}
		
		
		
		
		}
	}

@Override
public void modulemoved() {
	controls.setId(getID());
	controls.setTitle("Module "+getaddress()+ " Settings");

	controls.setPosition(getXposition()+(modsize/10)+1,getYposition()+(modsize/10)+1);	
}

@Override
public void remove() {
controls.remove();	

}
@Override
public void controlEvent(ControlEvent theevent){
	that.println(theevent.getId() - (getaddress()*100));		
}

@Override
public void output() {
	// TODO Auto-generated method stub
	
}

}

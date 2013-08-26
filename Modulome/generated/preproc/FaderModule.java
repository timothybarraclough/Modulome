import java.util.Arrays;

import processing.core.PApplet;
import rwmidi.MidiOutput;

import com.rapplogic.xbee.api.XBeeAddress64;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.Group;
import controlP5.RadioButton;
import controlP5.Toggle;


public class FaderModule extends AbstractModule {

	private int[] sliders = new int[3];
	private ControlP5 cp5;
	private Group controls;
	private Group midisettings;
	private Group oscsettings;
	private Group feedbacksettings;
	private int modsize;
	public Modulome that;
	
	
	private boolean isMidiOn;
	private int fader1midi, fader2midi, fader3midi, midichannel;
	private MidiOutput midiout;

	public FaderModule(int ID, int Colour, int modsize2, int addr, Modulome tha, ControlP5 cp52, MidiOutput mout){

		setID(ID);
		setColour(Colour);
		modsize = modsize2;
		setmodsize(modsize);
		hideSettings();
		setaddress(addr);
		cp5 = cp52;
		that = tha;
		midiout = mout;
		
		//CONTROL GROUP // ID 0
		controls =  cp5.addGroup("Module "+addr+ " Settings")
				.setId(addr*100)
				.setPosition(getXposition()+(modsize/10)+1,getYposition()+(modsize/10)+1)
				.hideBar()
				.hide()
				.setBackgroundHeight(4*modsize/5)
				.setWidth(4*modsize/5)
				.setBackgroundColor(that.color(255,50))
				;
		//MODE SELECT // ID 1
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

		//MIDI SETTINGS // ID 2
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
		//OSC Settings ID 3
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
		// FEEDBACK SETTINGS ID 4
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
		
		
		//FADER MODULE SPECIFIC SETTINGS
		
		//MIDI SETTINGS
		
		// MIDI ON // ID 5
		Toggle midion = cp5.addToggle("midion" + addr)
				.setId(addr*100 + 5)
				.setPosition((modsize/20) , ((modsize/20) * -1) - 2)
				.setLabel("Midi On")
				.setHeight(3*modsize/10)
				.setWidth(3*modsize/10)
				.setColorBackground(Colour)
				.setColorForeground(Colour + (0x00222222))
				.setColorActive(Colour + (0x00333333))
				.moveTo(midisettings);
	
	
	//What is the MIDI CHANNEL?  //   ID 7
			DropdownList midichan = cp5.addDropdownList("midichan"+addr, (modsize/20), 5*modsize/10, (modsize/2) - (modsize/5), modsize/5)
					.setId(addr*100 + 7)
					.setBarHeight(modsize/10)
					.setLabel("Midi Channel")
					.actAsPulldownMenu(true);
			midichan.captionLabel();
			midichan.setItemHeight(modsize/10)
			.setColorBackground(Colour)
			.setColorForeground(Colour + (0x00222222))
			.setColorActive(Colour + (0x00333333))
			.moveTo(midisettings);
			
			for (int i = 0; i < 16; i++){
				
				midichan.addItem(""+(i+1), i);
			}

			
			//What is the Slider 1 CC ?
			//First Value // ID 8

			DropdownList firstvalue = cp5.addDropdownList("firstvalue"+addr, (8*modsize/20), 0, (3*modsize/10), 2*modsize/10)
					.setBarHeight(modsize/20)
					.setId(addr*100 + 8)
					.setLabel("First Value")
					.actAsPulldownMenu(true)
					.setItemHeight(modsize/20)
					.setColorBackground(Colour)
					.setColorForeground(Colour + (0x00222222))
					.setColorActive(Colour + (0x00333333))
					.moveTo(midisettings);

			for (int i = 0; i < 128; i++){
				firstvalue.addItem(""+ (i+1), i);

			}
			
			// Second FADER //ID 9
			DropdownList secondvalue = cp5.addDropdownList("secondvalue"+addr, (8*modsize/20), 9*modsize/40, (3*modsize/10), 2*modsize/10)
					.setBarHeight(modsize/20)
					.setId(addr*100 + 9)
					.setLabel("Second Value")
					.actAsPulldownMenu(true)
					.setItemHeight(modsize/20)
					.setColorBackground(Colour)
					.setColorForeground(Colour + (0x00222222))
					.setColorActive(Colour + (0x00333333))
					.moveTo(midisettings);

			for (int i = 0; i < 128; i++){
				secondvalue.addItem(""+ (i+1), i);

			}
			//THIRD FARDER //ID 10
			DropdownList thirdvalue = cp5.addDropdownList("thirdvalue"+addr, (8*modsize/20), 9*modsize/20, (modsize/2) - (modsize/5), modsize/5)
					.setBarHeight(modsize/20)
					.setId(addr*100 + 10)
					.setLabel("Third Value")
					.actAsPulldownMenu(true)
					.setItemHeight(modsize/20)
					.setColorBackground(Colour)
					.setColorForeground(Colour + (0x00222222))
					.setColorActive(Colour + (0x00333333))
					.moveTo(midisettings);

			for (int i = 0; i < 128; i++){
				thirdvalue.addItem(""+ (i+1), i);

			}
			isMidiOn = true;
			
			midichannel = getID();
			
			fader1midi = 0;
			fader2midi = 1;
			fader3midi = 2;
		//	sliders[0] = sliders[1] = sliders[2] = 0;
			
			
			midisettings.hide();
			oscsettings.hide();
	}

	@Override
	public void draw(PApplet g) {
		int x = getXposition();
		int y = getYposition();


		if (getfade() < 255){
			if (controls.isVisible() == true) controls.hide();


			g.stroke(150);
			g.strokeWeight(2);

			//g.noStroke();
			for (int i = 0; i < 3; i++) {
				g.fill (getColour());
				g.rect(1+x+(getmodsize()/20) + ((getmodsize()/3 - getmodsize()/60)*i), getmodsize()-(1+y+(getmodsize()/20)), (getmodsize() - getmodsize()/5)/3, (sliders[i] / -127.0f) * getmodsize()*9/10);
			}
		}
		if (getfade() == 255){

			controls.show();
		}
		g.fill(0,getfade());
		g.rect(x+2,y+2,getmodsize() - 4,getmodsize() - 4);


	}

	@Override
	public void processdata(int[] data) {
		if (data[7] != sliders[0]) {
			sliders[0] = data[7];
			 if (isMidiOn) midiout.sendController(midichannel, fader1midi, sliders[0]);
		}

		if (data[8] != sliders[1]) {
			sliders[1] = data[8];
			 if (isMidiOn) midiout.sendController(midichannel, fader2midi, sliders[1]);

		}

		if (data[9] != sliders[2]) {
			sliders[2] = data[9];
			 if (isMidiOn) midiout.sendController(midichannel, fader3midi, sliders[2]);

		}


	}

	public void mousePressed(int thebutton, int thex, int they){


		if (thebutton == 37){
			if (getfade() == 0){

			}


			else {


			}
		}
		else if ((thebutton == 38)&&(getfade()==255)){
			controls.remove();
			that.moduleremover(getID());



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
	public void controlEvent(ControlEvent theevent) {
		int thecontrol = (theevent.getId() - (getaddress()*100));
that.println(thecontrol);
		if (thecontrol == 1){
			if(theevent.getValue() == 0){
				midisettings.hide();
				oscsettings.hide();
				feedbacksettings.show();
				that.println("feedback");
			}
			if(theevent.getValue() == 1){
				midisettings.show();
				oscsettings.hide();
				feedbacksettings.hide();
				that.println("Midi");
			}
			if(theevent.getValue() == 2){
				midisettings.hide();
				oscsettings.show();
				feedbacksettings.hide();
				that.println("osc");
			}
		}
		else if(thecontrol == 5){

			isMidiOn = (theevent.getValue() == 1);
		}
		else if(thecontrol == 7){

			midichannel = (int) (theevent.getValue());
		}
		
		else if(thecontrol == 8){

			fader1midi = (int) (theevent.getValue());
		}
		
		else if(thecontrol == 9){

			fader2midi = (int) (theevent.getValue());
		}
		
		else if(thecontrol == 10){

			fader3midi = (int) (theevent.getValue());
		}
	}

	@Override
	public void output() {
		// TODO Auto-generated method stub
		
	}
}


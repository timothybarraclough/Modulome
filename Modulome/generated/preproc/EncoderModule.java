import netP5.NetAddress;
import oscP5.OscP5;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.RadioButton;
import controlP5.Toggle;
import processing.core.PApplet;
import processing.core.PConstants;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;


public class EncoderModule extends AbstractModule{


	private ControlP5 cp5;
	private Group controls;
	private Group midisettings;
	private Group oscsettings;
	private Group feedbacksettings;
	private int modsize;
	private Modulome that;
	private int encoderPos;
	private int buttonState;
	private int[] encoderlights = new int[32];
	private int thedragstate;

	private boolean isMidiOn = true;
	private int firstNote, midichannel;
	private MidiOutput midiout;
	private MidiInput midiin;
	private int ButtonMode;
	private int EncoderMode;
	private int NOTE = 4;
	private int WRAP = 1;
	private int LIMIT = 2;
	private int PANPOT = 3;

	private boolean isOscOn;
	private boolean SerialOsc;

	public EncoderModule(int ID, int Colour, int modsize2, int addr, Modulome tha, ControlP5 thep5,MidiOutput mout, MidiInput min, OscP5 oscar, NetAddress mrl){

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
		midiin = min;
		midiout = mout;
		ButtonMode = LIMIT;
		EncoderMode = WRAP;


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
				.setLabelPadding((modsize/4)*-1, 30)
				.setPosition(0,0)
				//.hideLabels()
				.setLabel("Feedback")
				.setColorBackground(Colour)
				.setColorForeground(Colour + (0x00222222))
				.setColorActive(Colour + (0x00333333))
				//.set
				.setSize(4*modsize/15+1,modsize/8);

		rb.getItem(0).setLabel("FeedBack");
		rb.getItem(1).setLabel("MIDI");
		rb.getItem(2).setLabel("OSC");



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

		Toggle midion = cp5.addToggle("midion" + addr)
				.setId(addr*100 + 5)
				.setPosition((modsize/20) , ((modsize/20) * - 1) - 2)
				.setLabel("Midi On")
				.setHeight(3*modsize/10)
				.setWidth(3*modsize/10)
				.setColorBackground(Colour)
				.setColorForeground(Colour + (0x00222222))
				.setColorActive(Colour + (0x00333333))
				.moveTo(midisettings);

		//
		RadioButton MidiEncMode = cp5.addRadioButton("Unpressed Mode"+addr)
				.setId(addr * 100 + 51)
				.moveTo(midisettings)
				//.setItemWidth(modsize/4);
				//feedback.setItemHeight(modsize/6)
				.setItemsPerRow(3)
				.setSize((modsize/5)+1, modsize/10);
		MidiEncMode.addItem("Wrap" + addr,0)
		.addItem("Fader" + addr,1)
		.addItem("Pan Pot" + addr,2)
		.setSpacingColumn(modsize/20)
		.setPosition(modsize/20,2*modsize/5)
		//.setCaptionLabel()
		//.hideLabels()
		.setLabel("Unpressed Mode")
		.setColorBackground(Colour)
		.setColorForeground(Colour + (0x00222222))
		.setColorActive(Colour + (0x00333333))
		.setLabelPadding((modsize/5)*-1, 30);

		MidiEncMode.getItem(0).setLabel("Wrap");
		MidiEncMode.getItem(1).setLabel("Fader");
		MidiEncMode.getItem(2).setLabel("PanPot");

		RadioButton MidiButMode = cp5.addRadioButton("Pressed Mode"+addr)
				.setId(addr * 100 + 52)
				.moveTo(midisettings)
				//.setItemWidth(modsize/4);
				//feedback.setItemHeight(modsize/6)
				.setItemsPerRow(2)
				.setSize((modsize/7), modsize/10);
		MidiButMode.addItem("ButWrap" + addr,0)
		.addItem("ButFader" + addr,1)
		.addItem("ButPan Pot" + addr,2)
		.addItem("ButNote" + addr,3)
		.setSpacingColumn(modsize/20)
		.setSpacingRow(modsize/20)
		.setPosition(9*modsize/20,((modsize/20) * - 1) - 2)
		//.setCaptionLabel()
		//.hideLabels()

		.setLabel("Pressed Mode")

		.setColorBackground(Colour)
		.setColorForeground(Colour + (0x00222222))
		.setColorActive(Colour + (0x00333333))
		.setLabelPadding((modsize/8)*-1, 30);
		MidiButMode.captionLabel();
		MidiButMode.getItem(0).setLabel("Wrap");
		MidiButMode.getItem(1).setLabel("Fader");
		MidiButMode.getItem(2).setLabel("Pan");
		MidiButMode.getItem(3).setLabel("Note");

		midichannel = getID();
	
	
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
			g.ellipse(1+(getmodsize()/2)+x+(getmodsize()/4)*g.sin((float)(g.PI*2*encoderPos/-127.0)),  //The * 1 is where the variable from the encoder goes
					(1+getmodsize()/2)+y+(getmodsize()/4)*g.cos((float)(g.PI*2*encoderPos/-127.0)),
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
		if (encoderPos != data[7])
		{
			encoderPos = data[7];
			outputEnc();
		}
		if (buttonState != data[8])
		{
			buttonState = data[8];
			outputBut();
		}

	}

	private void outputBut() {
		if (isMidiOn){
			if (ButtonMode == NOTE){
				midiout.sendNoteOn(midichannel, 40, buttonState);
			}
			else if (ButtonMode != NOTE){
				midiout.sendController(midichannel, 40, buttonState);
			}
		}

		if (isOscOn){
			//TODO add in OSC JUNK = >	//OSC/arc/press buttonState/127 ? ?

		}
	}

	private void outputEnc() {
		if (isMidiOn){
			midiout.sendController(midichannel, 41, encoderPos);
		}

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
	public void output() {


	}

	public void controlEvent(ControlEvent theevent) {
		int thecontrol = (theevent.getId() - (getaddress()*100));

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

		else if(thecontrol == 51){

			if((theevent.getValue()) == 0){
				EncoderMode = WRAP;
			}
			else if((theevent.getValue()) == 1){
				EncoderMode = LIMIT;
			}
			else if((theevent.getValue()) == 2){
				EncoderMode = PANPOT;
			}
			toggleEncoderMode();

		}
		
		else if(thecontrol == 52){

			if((theevent.getValue()) == 0){
				ButtonMode = WRAP;
			}
			else if((theevent.getValue()) == 1){
				ButtonMode = LIMIT;
			}
			else if((theevent.getValue()) == 2){
				ButtonMode = PANPOT;
			}
			else if((theevent.getValue()) == 3){
				ButtonMode = NOTE;
			}
			toggleEncoderMode();

		}
	}

	private void toggleEncoderMode() {
		int[] payload = {240,EncoderMode,ButtonMode};
		that.xbeeoutput(getaddress(),payload);		
	}
}

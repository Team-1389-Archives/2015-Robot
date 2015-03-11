package com.team1389;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalSwitch implements Cloneable {
	private DigitalInput input;
	private boolean on;

	DigitalSwitch(int port){
		input = new DigitalInput(port);
	}

	public void tick(){
		on = input.get();
	}

	public boolean isOn(){
		return on;
	}
	protected DigitalSwitch clone() throws CloneNotSupportedException {
		return (DigitalSwitch) super.clone();
	}

}

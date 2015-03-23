package com.team1389;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class XBoxController implements Cloneable{
	private Joystick stick;
	private boolean buttonX; // XBox Controller X Button number for getRawButton= or getRawAxis=
	private boolean buttonA; // XBox Controller A Button number
	private boolean buttonB; // XBox Controller B Button number
	private boolean buttonY; // XBox Controller Y Button number
	private boolean bumperL; // XBox Controller  Left Bumper number
	private boolean bumperR; // XBox Controller Right Bumper number
	public boolean BPressed;
	public boolean APressed;
	private double leftY; // XBox Controller  Left Y Axis number
	private double leftX; // XBox Controller  Left X Axis number
	private boolean leftTrigger; // XBox Controller  Left Trigger Axis number
	private boolean rightTrigger; // XBox Controller  Right Trigger Axis number
	private double rightY; // XBox Controller Right Y Axis number
	private double rightX; // XBox Controller Right X Axis number
	public XBoxController(int port){
		stick = new Joystick(port);
	}
	public void tick(){
		BPressed=false;
		APressed=false;
		if(isPressed(buttonB,stick.getRawButton(Constants.ButtonB)))BPressed=true;
		if(isPressed(buttonA,stick.getRawButton(Constants.ButtonA)))APressed=true;
		leftX = stick.getRawAxis(Constants.LeftX);
		leftY = stick.getRawAxis(Constants.LeftY);
		rightX = stick.getRawAxis(Constants.RightX);
		rightY = stick.getRawAxis(Constants.RightY);
		rightTrigger = stick.getRawButton(Constants.RightTrigger);
		leftTrigger = stick.getRawButton(Constants.LeftTrigger);
		buttonX = stick.getRawButton(Constants.LeftTrigger);
		buttonA = stick.getRawButton(Constants.ButtonA); 
		buttonB = stick.getRawButton(Constants.ButtonB);
		buttonY = stick.getRawButton(Constants.ButtonY);
		bumperL = stick.getRawButton(Constants.BumperL); 
		bumperR = stick.getRawButton(Constants.BumperR);
	}
	public Joystick getStick() {
		return stick;
	} 
	public boolean isButtonX() {
		return buttonX;
	}
	public boolean isButtonA() {
		return buttonA;
	}
	public boolean isButtonB() {
		return buttonB;
	}
	public boolean isButtonY() {
		return buttonY;
	}
	public boolean isBumperL() {
		return bumperL;
	}
	public boolean isBumperR() {
		return bumperR;
	}
	public double getLeftY() {
		return leftY;
	}
	public double getLeftX() {  
		return leftX;
	}
	public boolean isLeftTrigger() {
		return leftTrigger;
	}
	public boolean isRightTrigger() {
		return rightTrigger;
	}
	public double getRightY() {
		return rightY;
	}
	public double getRightX() {
		return rightX;
	}
	public boolean isBPressed(){
		return BPressed;
	}
	public boolean isAPressed(){
		return BPressed;
	}
	public boolean isPressed(boolean oldState, boolean currentState){
		return oldState&&(!currentState);
	}
	
}

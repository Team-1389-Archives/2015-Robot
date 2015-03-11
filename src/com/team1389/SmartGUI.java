package com.team1389;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartGUI extends Component{

	private ArrayList <Component> comps = Robot.components;
	TestBotDriveControl control = (TestBotDriveControl)comps.get(Robot.DRIVE);
	//PosTrack pos = (PosTrack)comps.get(Robot.POS);
	Component ele = comps.get(Robot.ELEVATOR);
	
	public SmartGUI() {
		
	}
	
	@Override
	public void teleopConfig() {
	} 
	
	@Override
	public void teleopTick() {
		//From PosTrack Class
		/*
		SmartDashboard.putNumber("distance", pos.distance);
		SmartDashboard.putNumber("velX", pos.velX);
		SmartDashboard.putNumber("velY", pos.velY);
		SmartDashboard.putNumber("X Displacment", pos.posX);
		SmartDashboard.putNumber("Y Displacment", pos.posY);
		SmartDashboard.putNumber("accelX", pos.aX);
		SmartDashboard.putNumber("accelY", pos.aY);
		SmartDashboard.putNumber("dt", pos.dt);
		SmartDashboard.putNumber("t1", pos.t1);
		*/
		//from DriveControl Class
		SmartDashboard.putNumber("Power", (double)((int)(100*((control.actualLeft + control.actualRight) / 2)))/100);
//		SmartDashboard.putString("RampUp", control.rampUp);
		SmartDashboard.putNumber("state", control.rampUpState);
		//Elevator control class
		SmartDashboard.putBoolean("IR One value", Robot.state.infared[0].get());
		
	}
	
	@Override
	public void autonConfig() {
	
	}
	
	@Override
	public void autonTick() {
	
	}
	
	
	
}

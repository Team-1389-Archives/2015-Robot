package com.team1389;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ElevatorControl extends GenericElevator{
	
	SpeedController elevator= new Victor(Constants.ELEVATOR_PWM);
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	int goToPosition = 0;
	boolean going;
	PosTrack pos = (PosTrack)Robot.components.get(Robot.POS);
	
	public void teleopConfig(){
	}
	@Override
	public void teleopTick(){
		if(Robot.state.manip.getLeftY()!=0){
			move(Robot.state.manip.getLeftY());
		}
		boolean isA = Robot.state.manip.isAPressed();
		boolean isB = Robot.state.manip.isBPressed();
		if (isA && goToPosition != Constants.ELEVATOR_MAX_HEIGHT){
			goToPosition += 1;
		}
		if (isB && goToPosition != 0){
			goToPosition -= 1;
		}
		SmartDashboard.putNumber("pos",goToPosition);
		DigitalInput[] sensors= Robot.state.infared;
		if(!going)goTo(goToPosition);
		SmartDashboard.putBoolean("IR One value", Robot.state.infared[0].get());
	}
	
	public void autonTick(){
	}

	
	//Our IR sensors output low when an object is within 2cm - 10cm of an object (e. g. !IRa.get() equates to true when there is an object in front of sensor one) 
	//Sense stores the boolean returns of each IR sensor, updating each tick. Last sense is the same as sense,
	//but values only update when a IR sensor is passed
	/**
 	* 
 	* @param loc level to go to
 	* @param sensors array of infared sensors
	*/
	public void goTo(int loc){
		pos.teleopTick();
		DigitalInput[] sensors=Robot.state.infared;
		int lastSensor=0;
		for(int d=0;d<sensors.length;d++){
			if(!sensors[d].get())lastSensor=d;
		}
		elevator.set(whereToGo(loc, lastSensor) * Constants.ELEVATOR_SPEED_MOD);
		if(going&&Robot.isAuton){
			goTo(loc);
		}
	}
	public void move(double direction){
		
		going=false;
		DigitalInput[] sensors=Robot.state.infared;
		int dir=0;
		if (direction==1&&!sensors[4].get())
		{
			dir=1;
		}
		else if (direction==-1 && !sensors[0].get())
		{
			dir=-1;
		}
		elevator.set(dir*Constants.ELEVATOR_SPEED_MOD);
	}

	/** 
	* When input is given to bring the elevator to a specified level, this function provides the direction necessary to do so.
	* @param senseID represents the desired elevator level (Integers 0 - 4)
	* @param saves last triggered infared sensor(last known location of elevator)
	* @return +/- direction to reach desired level
	*/
	public int whereToGo(int senseID, int lastSensor)
	{
		going=true;
		if (lastSensor > senseID){ //above the requested spot
			return 1;
		}
		else if (lastSensor < senseID){ //below the requested spot
			return -1;
		}
		else{
			going=false;
			return 0;
		}
	}	
	
	
}

package com.team1389;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrapElevator extends GenericElevator{
	Victor lift;
	public CrapElevator(){
		//lift = new Victor(Constants.ELEVATOR_PWM);
	}
	public void teleopConfig(){
		lift=new Victor(Constants.ELEVATOR_PWM);
	}
	public void teleopTick(){
		lift.set(Robot.state.manip.getRightY()*Constants.ELEVATOR_SPEED_MOD);
	}
	@Override
	public void goTo(int position) {
		throw new RuntimeException("this is a crap elevator, not able to move to a specific location");
	}
	
}

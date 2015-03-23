package com.team1389;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Knocker extends GenericKnockerArm {
	private SpeedController motor;
	public Knocker() {
		motor = new Victor(Constants.KNOCKER_PORT);
	}
	
	public void goOut(){
		motor.set(Constants.OUT_KNOCKER_SPEED);
		Robot.autonTickForSeconds(Constants.KNOCKER_DELAY);
		motor.set(0);
	}
	
	public void goIn(){
		motor.set(-Constants.IN_KNOCKER_SPEED);
		Robot.autonTickForSeconds(Constants.KNOCKER_DELAY);
		motor.set(0);
	}
	
	@Override
	public void teleopTick() {
		motor.set(Robot.state.drive.getRightX() * Constants.OUT_KNOCKER_SPEED);
	}
}

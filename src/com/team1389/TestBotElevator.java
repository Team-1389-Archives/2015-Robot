package com.team1389;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestBotElevator extends GenericElevator {
	
	SpeedController motor;
	
	private int position;
	private int wantedPosition;
	private long timeRequested;
	public TestBotElevator() {
		motor = new Victor(Constants.KNOCKER_PORT);
	
		position = 0;
		updatePositionMessage();
	}
	
	@Override
	public void teleopConfig() {
		motor.set(0);
		super.teleopConfig();
	}

	private void updatePositionMessage() {
		SmartDashboard.putNumber("Theoretical Elevator Position:", position);
	}
		
	@Override
	public void goTo(int position) {
		this.wantedPosition = position;
		timeRequested = System.currentTimeMillis();
	}
	
	@Override
	public void autonConfig() {
		position = 0;
	}
	
	@Override
	public void autonTick() {
		long now = System.currentTimeMillis();
		if (now >= timeRequested + 1000){
			position = wantedPosition;
		}
		if(!thereYet()){
			if (wantedPosition == 0){
				motor.set(-.2);
			} else if (wantedPosition == 1){
				motor.set(.2);
			} else {
				throw new RuntimeException("this should never happen");
			}
		}
		updatePositionMessage();
	}
	
	@Override
	public boolean thereYet() {
		boolean done = wantedPosition == position;
		return done;
	}


}

package com.team1389;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TheoreticalElevator extends GenericElevator {
	
	private int position;
	private int wantedPosition;
	private long timeRequested;
	public TheoreticalElevator() {
	
		position = 0;
		updatePositionMessage();
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
		if (now >= timeRequested + 2){
			position = wantedPosition;
		}
		updatePositionMessage();
	}
	
	@Override
	public boolean thereYet() {
		return wantedPosition == position;
	}

}

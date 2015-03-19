package com.team1389;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TheoreticalElevator extends GenericElevator {
	
	private int position;
	public TheoreticalElevator() {
		position = 0;
		updatePositionMessage();
	}

	private void updatePositionMessage() {
		SmartDashboard.putNumber("Theoretical Elevator Position:", position);
	}
		
	@Override
	public void goTo(int position) {
		this.position = position;
		updatePositionMessage();
	}

}

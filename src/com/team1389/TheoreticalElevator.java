package com.team1389;

import edu.wpi.first.wpilibj.Timer;
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
		Robot.autonTickForSeconds(.5);
		this.position = position;
		updatePositionMessage();
	}

}

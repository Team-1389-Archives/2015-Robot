package com.team1389;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TheoreticalKnockerArm extends GenericKnockerArm {
	
	private boolean position;
	public TheoreticalKnockerArm() {
		position = false;
		updatePositionMessage();
	}

	private void updatePositionMessage() {
		SmartDashboard.putBoolean("Theoretical Knocker Position:", position);
	}
		
	@Override
	public void goOut() {
		Robot.autonTickForSeconds(.5);
		this.position = true;
		updatePositionMessage();
	}
	
	@Override
	public void goIn() {
		Robot.autonTickForSeconds(.5);
		this.position = false;
		updatePositionMessage();
	}
}

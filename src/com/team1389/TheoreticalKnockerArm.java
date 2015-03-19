package com.team1389;

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
	public void set(boolean isExtended) {
		this.position = isExtended;
		updatePositionMessage();
	}

}

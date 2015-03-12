package com.team1389.auton;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceTracker{
	
	Encoder encoder;
	double endDistance;
	
	public DistanceTracker(Encoder encoder) {
		this.encoder = encoder;
	}
	
	public void start(double distanceToGo){
		endDistance = encoder.getDistance() + distanceToGo;
	}
	
	public boolean isFinished(){
		double distanceLeft = endDistance - encoder.getDistance();
		SmartDashboard.putNumber("distance left", distanceLeft);
		return distanceLeft <= 0;
	}
	
		
}

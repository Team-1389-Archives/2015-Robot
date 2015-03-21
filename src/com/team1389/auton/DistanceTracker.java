package com.team1389.auton;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceTracker{
	
	Encoder encoder;
	double startDistance;
	double distanceToGo;
	
	public DistanceTracker(Encoder encoder) {
		this.encoder = encoder;
	}
	
	public void start(double distanceToGo){
		startDistance = encoder.getDistance();
		this.distanceToGo = Math.abs(distanceToGo);
	}
	
	public boolean isFinished(){
		double difference = startDistance - encoder.getDistance();
		SmartDashboard.putNumber("distance difference", difference);
		return Math.abs(difference) >= distanceToGo;
	}
	
		
}

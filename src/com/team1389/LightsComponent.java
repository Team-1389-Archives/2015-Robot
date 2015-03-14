package com.team1389;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LightsComponent extends Component {
	private SpeedController light;

	private double nextToggle;
	private boolean isOn;

	private boolean lightsGoing;

	public LightsComponent() {
		light = new Victor(2);
	}

	@Override
	public void teleopConfig() {
		nextToggle = Timer.getFPGATimestamp();
		isOn = false;
		lightsGoing = false;
	}

	private void checkStrobe(){
		double now = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("time", now);
		if (now >= nextToggle){
			nextToggle = now + .1;
			toggle();
		}
	}

	private void toggle(){
		if (isOn){
			light.set(1);
			SmartDashboard.putBoolean("lights", true);
			isOn = false;
		} else {
			light.set(0);
			SmartDashboard.putBoolean("lights", false);
			isOn = true;
		}
	}

	@Override
	public void teleopTick() {
		if (Robot.state.drive.isAPressed()){
			lightsGoing = !lightsGoing;
			if (lightsGoing = false){
				light.set(0);
			}
		}
		if (lightsGoing){
			checkStrobe();
		}
	}
}

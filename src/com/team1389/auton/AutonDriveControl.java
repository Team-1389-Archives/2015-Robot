package com.team1389.auton;

import com.team1389.Component;
import com.team1389.GenericDriver;
import com.team1389.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonDriveControl extends Component {
	
	private DriveStraight straight;
	
	public AutonDriveControl() {
		straight = new DriveStraight(Robot.state.imu, .4);
	}
	
	@Override
	public void autonConfig() {
		Robot.driveControl.setRampMode(GenericDriver.FULL_USER);
		SmartDashboard.putString("i am", "staring auton");
		straight.setSetpoint(Robot.state.imu.getYaw());
		straight.enable();
	}

	@Override
	public void autonTick() {
	}
	
	@Override
	public void onDisable() {
		SmartDashboard.putString("i am", "diabled");
		straight.disable();
	}
}

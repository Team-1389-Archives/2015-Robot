package com.team1389;

import com.team1389.auton.DriveStraight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class GenericDriver extends Component {
	public static final int STRICT_COMPUTER=0;
	public static final int FULL_USER=1;
	public static final int COMPUTER_ASSISTED=2;

	public abstract void setRampMode(int mode);
	public abstract void drive(double direction, double magnitude);
	public double pos;
	
	private DriveStraight straight;
	
	public GenericDriver() {
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
		
		pos = getAbsoluteDistance(Robot.state.imu.getDisplacementX(), Robot.state.imu.getDisplacementY(), 0, 0);
		SmartDashboard.putNumber("Displacment", pos);
		SmartDashboard.putNumber("DisplacmentX", Robot.state.imu.getDisplacementX());
		SmartDashboard.putNumber("DisplacmentY", Robot.state.imu.getDisplacementY());
		
		//if (pos > 100)
			//straight.disable();
	}
	
	@Override
	public void onAutonDisable() {
		SmartDashboard.putString("i am", "diabled");
		straight.disable();
	}
	
	double getAbsoluteDistance(double xOne, double yOne, double xTwo, double yTwo)
	{
		return Math.sqrt(Math.pow(xOne - xTwo, 2) + Math.pow(yOne - yTwo, 2));
	}

}
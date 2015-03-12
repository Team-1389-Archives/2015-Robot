package com.team1389;

import com.team1389.auton.DistanceTracker;
import com.team1389.auton.DriveStraight;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class GenericDriver extends Component {
	public static final int STRICT_COMPUTER=0;
	public static final int FULL_USER=1;
	public static final int COMPUTER_ASSISTED=2;

	public abstract void setRampMode(int mode);
	public abstract void drive(double direction, double magnitude);
	public double pos;

	private DriveStraight straight;
	private DistanceTracker tracker;
	private boolean stillDriving;

	public GenericDriver() {
		straight = new DriveStraight(Robot.state.imu, .4);
		tracker = new DistanceTracker(Robot.state.encoder1);
	}

	@Override
	public void autonConfig() {
		setRampMode(GenericDriver.FULL_USER);
		straight.setSetpoint(Robot.state.imu.getYaw());
	}

	@Override
	public void autonTick() {
	}
	
	public void goStaightDistance(double feet){
		tracker.start(feet);
		straight.setSetpoint(Robot.state.imu.getYaw());
		straight.enable();
		while(!tracker.isFinished() && Robot.isRobotEnabled()){
			SmartDashboard.putBoolean("wating for done with motion", true);
			Timer.delay(.1);
		}
		SmartDashboard.putBoolean("wating for done with motion", false);
		straight.disable();
		drive(0,0);
	}

	@Override
	public void onAutonDisable() {
		straight.disable();
		drive(0,0);
	}

	double getAbsoluteDistance(double xOne, double yOne, double xTwo, double yTwo)
	{
		return Math.sqrt(Math.pow(xOne - xTwo, 2) + Math.pow(yOne - yTwo, 2));
	}

}
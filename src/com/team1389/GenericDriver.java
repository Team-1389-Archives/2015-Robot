package com.team1389;

import com.team1389.auton.DistanceTracker;
import com.team1389.auton.DriveStraight;
import com.team1389.auton.TurnPID;

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
	private TurnPID turn;
	private boolean stillDriving;

	public GenericDriver() {
		straight = new DriveStraight(Robot.state.imu, .4);
		tracker = new DistanceTracker(Robot.state.encoder1);
		turn = new TurnPID(Robot.state.imu);
		
		SmartDashboard.putNumber("P", .2);
		SmartDashboard.putNumber("I", 0);
		SmartDashboard.putNumber("D", 0);
		SmartDashboard.putNumber("MaxSpeed", .4);
	}

	@Override
	public void autonConfig() {
		setRampMode(GenericDriver.FULL_USER);
		straight.setSetpoint(Robot.state.imu.getYaw());
		
		double p = SmartDashboard.getNumber("P");
		double i = SmartDashboard.getNumber("I");
		double d = SmartDashboard.getNumber("D");
		double maxSpeed = SmartDashboard.getNumber("MaxSpeed");
		
		turn.getPIDController().setPID(p, i, d);
		turn.getPIDController().setOutputRange(-maxSpeed, maxSpeed);

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
			Timer.delay(.05);
		}
		SmartDashboard.putBoolean("wating for done with motion", false);
		straight.disable();
		drive(0,0);
	}
	
	public void turnAngle(double angle){
		double finishAngle = Robot.state.imu.getYaw() + angle;
		double revisedAngle = 0.0;
		if (finishAngle > 180){
			revisedAngle = finishAngle - 360;
		} else if (finishAngle < -180) {
			revisedAngle = finishAngle + 360;
		} else {
			revisedAngle = finishAngle;
		}
		turn.setSetpoint(revisedAngle);
		turn.enable();
		while(!turn.onTarget() && Robot.isRobotEnabled()){
			Timer.delay(.05);
		}
		turn.disable();
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
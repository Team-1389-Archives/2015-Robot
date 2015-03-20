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

	private double theoreticalAngle;

	public GenericDriver() {
		straight = new DriveStraight(Robot.state.imu, .4);
		tracker = new DistanceTracker(Robot.state.encoder1);
		turn = new TurnPID(Robot.state.imu);


		SmartDashboard.putNumber("100P", SmartDashboard.getNumber("100P", 0));
		SmartDashboard.putNumber("100I", SmartDashboard.getNumber("100I", 0));
		SmartDashboard.putNumber("100D", SmartDashboard.getNumber("100D", 0));
		SmartDashboard.putNumber("MaxSpeed", SmartDashboard.getNumber("MaxSpeed", 0));
		SmartDashboard.putNumber("%tolerance", SmartDashboard.getNumber("%tolerance", 0));
	}

	@Override
	public void autonConfig() {
		setRampMode(GenericDriver.FULL_USER);
		straight.setSetpoint(Robot.state.imu.getYaw());

		SmartDashboard.putNumber("100P", Constants.STRAIGHT_P * 100);
		SmartDashboard.putNumber("100I", Constants.STRAIGHT_I * 100);
		SmartDashboard.putNumber("100D", Constants.STRAIGHT_D * 100);
		
		double p = SmartDashboard.getNumber("100P") / 100;
		double i = SmartDashboard.getNumber("100I") / 100;
		double d = SmartDashboard.getNumber("100D") / 100;
		double maxSpeed = SmartDashboard.getNumber("MaxSpeed");

//		turn.getPIDController().setPID(p, i, d);
//		turn.getPIDController().setPercentTolerance(SmartDashboard.getNumber("%tolerance"));
//		turn.getPIDController().setOutputRange(-maxSpeed, maxSpeed);
		
		straight.getPIDController().setPID(p, i, d);
//		straight.getPIDController().setPercentTolerance(SmartDashboard.getNumber("%tolerance"));
//		straight.getPIDController().setOutputRange(-maxSpeed, maxSpeed);
		
		theoreticalAngle = Robot.state.imu.getYaw();
	}

	@Override
	public void autonTick() {

	}

	public void goStaightDistance(double feet){
		tracker.start(feet);
		float yaw = Robot.state.imu.getYaw();
		SmartDashboard.putNumber("startYaw", yaw);
		SmartDashboard.putNumber("theoreticalAngle", theoreticalAngle);
		straight.enable();
		while(!tracker.isFinished() && Robot.isRobotAutonEnabled()){
			SmartDashboard.putBoolean("wating for done with motion", true);
			Timer.delay(.05);
		}
		SmartDashboard.putBoolean("wating for done with motion", false);
		straight.disable();
		drive(0,0);
	}

	public void turnAngle(double angle){
		theoreticalAngle += angle;
		if (theoreticalAngle > 180){
			theoreticalAngle -= 360;
		} else if (theoreticalAngle < -180) {
			theoreticalAngle += 360;
		}

		turn.setSetpoint(theoreticalAngle);
		turn.enable();
		while(!turn.onTarget() && Robot.isRobotAutonEnabled()){
			Timer.delay(.05);
		}
		turn.disable();
		drive(0,0);
	}

//	public void turnAngle(double angle){
//		theoreticalAngle += angle;
//		if (theoreticalAngle > 180){
//			theoreticalAngle -= 360;
//		} else if (theoreticalAngle < -180) {
//			theoreticalAngle += 360;
//		}
//
//		double rawSpeed = SmartDashboard.getDouble("MaxSpeed");
//		double speed;
//
//		boolean goAngleUp = angle > 0;
//		if (goAngleUp){
//			speed = rawSpeed;
//
//		} else {
//			speed = -rawSpeed;
//		}
//
//		drive(speed,0);
//		float yaw = Robot.state.imu.getYaw();
//		while( thereYet(theoreticalAngle, yaw, goAngleUp) && Robot.isRobotAutonEnabled()){
//			SmartDashboard.putNumber("angle left", angleDifference(theoreticalAngle, yaw));
//			Timer.delay(.05);
//			yaw = Robot.state.imu.getYaw();
//		}
//		drive(0,0);
//	}

	private double angleDifference(double a, double b){
		double adjustedA;
		if (Math.abs(a-b) > 180){
			if (a < b){
				adjustedA = a + 360;
			} else {
				adjustedA = a  -360;
			}
		} else {
			adjustedA = a;
		}
		return adjustedA - b;
	}

	private boolean thereYet(double target, double now, boolean goingUp){
		if (goingUp){
			return angleDifference(target, now) <= 0;
		} else {
			return angleDifference(now, target) <= 0;
		}
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
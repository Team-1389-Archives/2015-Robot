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
		
		double p = SmartDashboard.getNumber("100P") / 100;
		double i = SmartDashboard.getNumber("100I") / 100;
		double d = SmartDashboard.getNumber("100D") / 100;
		double maxSpeed = SmartDashboard.getNumber("MaxSpeed");
		
		turn.getPIDController().setPID(p, i, d);
		turn.getPIDController().setPercentTolerance(SmartDashboard.getNumber("%tolerance"));
		turn.getPIDController().setOutputRange(-maxSpeed, maxSpeed);

		theoreticalAngle = Robot.state.imu.getYaw();
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
		theoreticalAngle += angle;
		if (theoreticalAngle > 180){
			theoreticalAngle -= 360;
		} else if (theoreticalAngle < -180) {
			theoreticalAngle += 360;
		}
		
		turn.setSetpoint(theoreticalAngle);
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
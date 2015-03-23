package com.team1389.auton;

import com.kauailabs.nav6.frc.IMU;
import com.team1389.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnPID extends PIDSubsystem{
	final double leftModifyer = -1;
	final double rightModifyer = 1;

	private double startAngle;
	private double setPoint;

	private double lastOutput;

	private IMU imu;
	public TurnPID(IMU imu){
		super(.5, 0, 0);
		this.imu=imu;
		setInputRange(-180, 180);
		setOutputRange(-.5, .5);
		getPIDController().setContinuous();
		getPIDController().setPercentTolerance(3);

		lastOutput = 1;
	}

	@Override
	protected double returnPIDInput(){
		float pidOutput = imu.getYaw();
		return pidOutput;
	}

	@Override
	protected void usePIDOutput(double output) {

		lastOutput = output;

		Robot.driveControl.drive(output, 0);
	}

	@Override
	protected void initDefaultCommand() {
	}

	@Override
	public void setSetpoint(double setpoint) {
		startAngle = imu.getYaw();
		this.setPoint = setpoint;
		super.setSetpoint(setpoint);
	}

	@Override
	public void disable() {
		super.disable();
	}

//	@Override
//	public boolean onTarget() {
//		if (Math.abs(lastOutput) > .2){
//			return false;
//		} else {
//			return super.onTarget();
//		}
//	}
}

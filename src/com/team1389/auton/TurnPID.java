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
	
	private IMU imu;
	public TurnPID(IMU imu){
		super(.03, 0.1, 0);
		this.imu=imu;
		setInputRange(-180, 180);
		setOutputRange(-.5, .5);
		getPIDController().setContinuous();
		getPIDController().setPercentTolerance(2);
	}

	@Override
	protected double returnPIDInput(){
		float pidOutput = imu.getYaw();
		SmartDashboard.putNumber("startAngle" , startAngle);
		SmartDashboard.putNumber("endAngle", setPoint);
		SmartDashboard.putNumber("difference", setPoint - startAngle);
		return pidOutput;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("output", output);
		
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
}

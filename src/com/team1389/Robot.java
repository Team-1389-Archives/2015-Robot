package com.team1389;

import java.util.ArrayList;

import com.team1389.auton.Autonomous;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * robotRIO code 2015 for FRC team 1389 now in Java!
 * This year we are attempting to organize the code through the object oriented capabilities java has. The code is organized as follows:
 * All constants (i.e port numbers and joystick input values) are final members of the Constants class.
 * All static Object used (Motor controllers, sensors, joysticks, etc.) are declared/initialized by the Motors_Sensors object
 * All individual robot components (i.e. drive train, lift system, etc.) are separate objects.
 * For both the autonomous and teleoperated phases of the match, the each component as a config method and tick method.
 * Config is called at the beginning of the phase, which tick is called every tick during the phase. 
 * @author Paul LoBuglio
 */


//Component @ in0 = DriverControl
//Component @ in1 = ElevatorControl
//Component @ in2 = PosTrack
public class Robot extends SampleRobot {

	//instance variables
	static Robot me;
	static boolean isAuton;
	static ArrayList<Component> components;
	public static InputState state;
	final static int DRIVE=1,ELEVATOR=0,POS=2;

	public static GenericDriver driveControl;
	public static GenericElevator elevatorControl;
	public static GenericKnockerArm knockerControl;
	

	public CameraServer server;

	/**
	 * Instantiates all static motors and sensors. 
	 * Instantiates all component objects
	 */
	public Robot()
	{
		me = this;
		state= new InputState();
		components = new ArrayList<Component>();


		//server = CameraServer.getInstance();
		//server.setQuality(50);
		//the camera name (ex "cam0") can be found through the roborio web interface
		//server.startAutomaticCapture("cam0");

		if (Constants.isTestBot){
			setupTestbotComponents();
		} else {
			setupFinalBotComponents();
		}
		
		commonSetup();
		
		dashboardSetup();

	}
	
	private void dashboardSetup(){
		initDashNum("AutonMode", 4);
	}
	
	private void initDashNum(String key, double defaultVal){
		SmartDashboard.putNumber(key, SmartDashboard.getNumber(key, defaultVal));
	}
	
	private void commonSetup(){
		knockerControl = new Knocker();
		components.add(driveControl);
		components.add(elevatorControl);
		components.add(knockerControl);
		
		
	}

	private void setupFinalBotComponents(){
		driveControl = new FinalRobotDriveControl();
		elevatorControl = new ElevatorControl();
		
	}

	private void setupTestbotComponents(){
		driveControl = new TestBotDriveControl();
		elevatorControl = new TheoreticalElevator();
//		knockerControl = new TheoreticalKnockerArm();
	}


	/**
	 * Teleoperated configuration
	 * Update each component each iteration through the ".teleopTick()" method
	 */
	public void operatorControl()
	{
		isAuton=false;
		for (Component c: components){

			c.teleopConfig();
		}
		while (isOperatorControl() && isEnabled())
		{

			state.tick();	

			if (state.drive.isButtonY()){
				state.imu.resetDisplacement();
			}


			SmartDashboard.putNumber("X Displacment", state.imu.getDisplacementX());
			SmartDashboard.putNumber("Y Displacment", state.imu.getDisplacementY());
			SmartDashboard.putNumber("Compass X", state.imu.getCalibratedMagnetometerX());
			SmartDashboard.putNumber("Compass Y", state.imu.getCalibratedMagnetometerY());
			SmartDashboard.putNumber("Compass Z", state.imu.getCalibratedMagnetometerZ());
			SmartDashboard.putNumber("IMU YAW", state.imu.getYaw());
			SmartDashboard.putNumber("x velocity", state.imu.getVelocityX());
			SmartDashboard.putNumber("y velocity", state.imu.getVelocityY());
			SmartDashboard.putNumber("IMU acceleration x", -state.imu.getWorldLinearAccelX());
			SmartDashboard.putNumber("IMU acceleration y", state.imu.getWorldLinearAccelY());
			SmartDashboard.putNumber("IMU acceleration z", -state.imu.getWorldLinearAccelZ());
			SmartDashboard.putNumber("Rio acceleration x", state.accel.getX());
			SmartDashboard.putNumber("Rio acceleration y", state.accel.getY());
			SmartDashboard.putNumber("Rio acceleration z", state.accel.getZ());
			for (Component c: components){
				c.teleopTick();
			}
		}

	}

	@Override
	public void autonomous(){

		isAuton=true;
		
		Autonomous.autonInit();

		for (Component c: components){

			c.autonConfig();
		}
		/*
		while (isAutonomous() && isEnabled())
		{
			state.tick();

			for (Component c: components){
				c.autonTick();
			}
		}

		for (Component c : components){
			c.onAutonDisable();
		}*/
		
		Autonomous auto = new Autonomous((int)SmartDashboard.getNumber("AutonMode"), components);
	}

	/**bot into auton
	 * go forward into autonomous zone
	 */

	/**
	 * Autonomous configuration
	 * Update each component through the ".autonTick()" method
	 */
	public static Component getComponent(int index){
		return components.get(index);
	}

	@Override
	protected void disabled() {
		isAuton = false;
	}
	public static boolean isRobotAutonEnabled(){
		return me.isEnabled() && me.isAutonomous();
	}

}

package com.team1389.auton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.team1389.Component;
import com.team1389.Constants;
import com.team1389.ElevatorControl;
import com.team1389.GenericDriver;
import com.team1389.PosTrack;
import com.team1389.Robot;
import com.team1389.TestBotDriveControl;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	private final double AUTON_SPEED_MOD = 1;

	//These constants hold relevant distances we need to travel in inches

	private GenericDriver drive;

	public static String getAutonName(int number){
		String autonName = null;
		switch (number) {
		case 1:
			autonName = "driveToAutonZone";
			break;
		case 2:
			autonName = "pushToteToAutoZone";
			break;
		case 3:
			autonName = "pickupToteAndToAutoZone";
			break;
		case 4:
			autonName = "threeTotes:";
			break;
		case 5:
			autonName = "doNothing";
			break;
		default:
			autonName = "INVALID AUTON NUMBER";
		}
		
		return autonName;
	}
	
	public Autonomous(int methodNum,ArrayList<Component> components)
	{
		drive = Robot.driveControl;

		String autonName = null;

		switch (methodNum) {

		case 1:
			driveToAutonZone();
			break;
		case 2:
			pushToteToAutoZone();
			break;
		case 3:
			pickupToteAndToAutoZone();
			break;
		case 4:
			threeTotes();
			break;
		case 5:
			doNothing();
			break;
		default://if you add on to this, add name to getAutonName method
			break;
		}
	}


	public static void autonInit(){
		SmartDashboard.putBoolean("calibrating",true);
		while(Robot.state.imu.isCalibrating() && Robot.isRobotAutonEnabled());
		SmartDashboard.putBoolean("calibrating",false);
		Robot.state.imu.resetDisplacement();
		Robot.state.imu.zeroYaw();
		//Timer.delay(5);
	}
	
	//auton 1
	private void driveToAutonZone(){
		drive.goStaightDistance(Constants.DISTANCE_TO_LANDMARK);
	}
	
	//auton2
	private void pushToteToAutoZone(){
		drive.goStaightDistance(Constants.DISTANCE_TO_LANDMARK + Constants.STAGING_TO_AUTO_ZONE);
	}
	
	//auton 3
	private void pickupToteAndToAutoZone(){
		Robot.elevatorControl.goToAndWait(1);
		drive.goStaightDistance(Constants.TOTE_LENGTH);
		drive.turnAngle(90);
		drive.goStaightDistance(Constants.DISTANCE_TO_LANDMARK + Constants.STAGING_TO_AUTO_ZONE);
	}
	

	//auton 4
	private void threeTotes(){
		doToteContainerSet();

		doToteContainerSet();
		
		pickupTote();
		
		//grab tote
		Robot.knockerControl.goIn();
		//hold on through turn
		Robot.knockerControl.setPowerDirectly(Constants.KNOCKER_HOLDING_POWER);

		//turn to auto zone
		drive.turnAngle(90);

		//go into auto zone
		drive.goStaightDistance(Constants.DISTANCE_TO_LANDMARK);

		//put down totes
		Robot.elevatorControl.goTo(0);

		//release totes
		Robot.knockerControl.goOut();

		//back off totes
		drive.goStaightDistance(-(Constants.TOTE_LENGTH + Constants.EXTRA_BACK_DISTANCE));
	}


	private void doToteContainerSet() {
		//start with elevator up
		Robot.elevatorControl.goToAndWait(1);
		
		pickupTote();

		//knock container 1
		knockContainer();

		//go to second element set
		moveToNextElementSet();
	}


	private void pickupTote() {
		//drive into tote
		drive.goStaightDistance(Constants.TOTE_LENGTH);
		
		//pick up first tote
		Robot.elevatorControl.goToAndWait(0);
		
		//move into first tote
		drive.goStaightDistance(Constants.TOTE_LENGTH);

		//start elevator down on tote
		Robot.elevatorControl.goTo(1);
	}
	private void moveToNextElementSet() {
		drive.goStaightDistance(Constants.AUTON_ELEMENT_GAP + Constants.CONTAINER_DIAMETER);
	}
	private void knockContainer() {
		Robot.knockerControl.goIn();
		Robot.knockerControl.goOut();
	}
	
	private void doNothing(){

	}
	
	
	//tests autons
	
	
	private void autonOne(){
		drive.goStaightDistance(SmartDashboard.getNumber("autonDistance"));
		drive.turnAngle(90);
	}

	private void squareDance(){
		while(Robot.isRobotAutonEnabled()){
			drive.goStaightDistance(2);
			Robot.autonTickForSeconds(1);
			drive.turnAngle(90);
			Robot.autonTickForSeconds(1);
		}
	}

	private void testAuton(){
		while(Robot.isRobotAutonEnabled()){
			Robot.elevatorControl.goToAndWait(1);
			Robot.elevatorControl.goToAndWait(0);
		}
	}

	private void zigZag(){
		drive.turnAngle(-45);
		while(Robot.isRobotAutonEnabled()){
			drive.turnAngle(90);
			Robot.autonTickForSeconds(1);
			drive.goStaightDistance(2);
			Robot.autonTickForSeconds(1);
			drive.turnAngle(-90);
			Robot.autonTickForSeconds(1);
			drive.goStaightDistance(2);
			Robot.autonTickForSeconds(1);
		}

	}

	private void t20Feet(){
		drive.goStaightDistance(20);
	}

	private void testForwardBackward(){
		while(Robot.isRobotAutonEnabled()){
			drive.goStaightDistance(2);
			drive.goStaightDistance(-2);
		}
	}
}

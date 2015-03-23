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

	private final double 
	MULTIPLIER=					0.0254,					//inches->meters conversion
	TAPE_TO_LANDMARK = 			107		*MULTIPLIER, 	//Distance from in front of AutoTotes -> AutoZone.
	STAGING_ZONE_WIDTH=			48		*MULTIPLIER,	//length down the field of yellow crate zone
	STAGING_ZONE_LENGTH=		23		*MULTIPLIER,	//width of yellow crate zone
	BETW_AUTO_TOTES = 			33		*MULTIPLIER, 	//Distance to travel in between auto totes when picking up all totes
	TAPE_TO_DRIVER = 			76		*MULTIPLIER, 	//Distance to from down-field edge of staging zone to driver station
	TOTE_WIDTH = 				26.9	*MULTIPLIER, 	//width of a tote
	LANDFILL_TO_SCORING = 		51.2	*MULTIPLIER, 	//distance from two totes in landfill to white scoring platform
	LANDFILL_TO_AUTON = 		54.5	*MULTIPLIER, 	//distance from white scoring platform to middle of auton zone
	AUTONTOTE_TO_LANDFILLTOTE = 12		*MULTIPLIER, 	//offset from auton tote to landfill totes
	OVERHANG = 					13.5	*MULTIPLIER; 	//distance past chassis of lift arm	

	private GenericDriver drive;

	public Autonomous(int methodNum,ArrayList<Component> components)
	{
		drive = Robot.driveControl;

		String autonName = null;

		switch (methodNum) {

		case 1:
			autonOne();
			autonName = "autonOne";
			break;
		case 2:
			squareDance();
			autonName = "squareDance";
			break;
		case 3:
			testAuton();
			autonName = "testAuton";
			break;
		case 4:
			dontDoShit();
			autonName = "dontDoShit:";
			break;
		case 5:
			zigZag();
			autonName = "zigZag";
			break;
		case 6:
			t20Feet();
			autonName = "t20Feet";
			break;
		case 7:
			threeTotes();
			autonName = "threeTotes";
			break;
		case 8:
			testForwardBackward();
			autonName = "testForwardBackward";
			break;
		default:
			break;
		}
		
		SmartDashboard.putString("ActiveAuton", autonName);
	}


	public static void autonInit(){
		SmartDashboard.putBoolean("calibrating",true);
		while(Robot.state.imu.isCalibrating() && Robot.isRobotAutonEnabled());
		SmartDashboard.putBoolean("calibrating",false);
		Robot.state.imu.resetDisplacement();
		Robot.state.imu.zeroYaw();
		//Timer.delay(5);
	}


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
			//			drive.turnAngle(-180);
			//			Timer.delay(1);
			drive.turnAngle(90);
			Robot.autonTickForSeconds(1);
		}
	}

	private void dontDoShit(){

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

	private void threeTotes(){
		//start with elevator up
		Robot.elevatorControl.goTo(1);

		//pick up first tote
		pickupTote();

		//knock container 1
		knockContainer();

		//go to second element set
		moveToNextElementSet();

		//pickup second tote
		pickupTote();

		//knock second container
		knockContainer();

		//go to third element set
		moveToNextElementSet();

		//pickup third tote
		pickupTote();

		//grab tote
		Robot.knockerControl.goIn();

		//turn to auto zone
		drive.turnAngle(90);

		//go into auto zone
		drive.goStaightDistance(Constants.DISTANCE_TO_LANDMARK);

		//put down totes
		Robot.elevatorControl.goTo(0);

		//release totes
		Robot.knockerControl.goIn();

		//back off totes
		drive.goStaightDistance(-(Constants.TOTE_LENGTH + Constants.EXTRA_BACK_DISTANCE));
	}
	private void moveToNextElementSet() {
		drive.goStaightDistance(Constants.AUTON_ELEMENT_GAP + Constants.CONTAINER_DIAMETER);
	}
	private void pickupTote() {
		Robot.elevatorControl.goTo(0);
		drive.goStaightDistance(Constants.TOTE_LENGTH);
		Robot.elevatorControl.goTo(1);
	}
	private void knockContainer() {
		Robot.knockerControl.goIn();
		Robot.knockerControl.goOut();
	}
}

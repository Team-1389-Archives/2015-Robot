package com.team1389.auton;

import java.sql.Time;
import java.util.ArrayList;

import com.team1389.Component;
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

		
		
		switch(methodNum)
		{
		case 1: autonOne(); break;
		case 2: squareDance(); break;
		default: break;
		}
	}
	
	
	private void autonOne(){
		drive.goStaightDistance(SmartDashboard.getNumber("autonDistance"));
		drive.turnAngle(90);
	}

	private void squareDance(){
		while(Robot.isRobotEnabled()){
			drive.goStaightDistance(2);
			Timer.delay(1);
			drive.turnAngle(90);
			Timer.delay(1);
		}
	}
}

package org.usfirst.frc.team1389.robot;

import java.util.ArrayList;

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
	
	private DriveControl drive;
	private ElevatorControl elevator;
	private PosTrack pos;

	public Autonomous(int methodNum,ArrayList<Component> components)
	{
		pos=(PosTrack)components.get(Robot.POS);
		drive=(DriveControl)components.get(Robot.DRIVE);
		elevator=(ElevatorControl)components.get(Robot.ELEVATOR);

		switch(methodNum)
		{
		case 1: autonOne(); break;
		case 2: autonTwo(); break;
		case 3: autonThree(); break;
		case 4: autonFour(); break;
		case 5: autonFive(); break;
		case 6: autonSix(); break;
		case 7: autonSeven(); break;
		default: break;
		}
	}
	
	/**
	 * drive the bot into the autonomous zone
	 */
	public void autonOne()
	{
		drive.move(TAPE_TO_LANDMARK,AUTON_SPEED_MOD);
	}

	/**
	 * push a single auton crate into the autonomous zone
	 */
	public void autonTwo()
	{
		double crateCarryDistance=(TAPE_TO_LANDMARK+STAGING_ZONE_LENGTH);
		drive.move(crateCarryDistance,AUTON_SPEED_MOD);
	}
	
	/**
	 *collect a container and an auton tote and carry them into the auton zone 
	 */
	public void autonThree()
	{
		double distance1=TAPE_TO_DRIVER-13.5;
		double distance2=distance1-12;
		drive.move(distance1,AUTON_SPEED_MOD);
		elevator.goTo(4);
		
		drive.move(12*MULTIPLIER,-AUTON_SPEED_MOD);
		drive.turn(-90);
		drive.move(TOTE_WIDTH/3, AUTON_SPEED_MOD);
		elevator.goTo(0);
		drive.turn(-90);
		drive.move(distance2, AUTON_SPEED_MOD);
		drive.move(TAPE_TO_LANDMARK, AUTON_SPEED_MOD);		
	}
	
	/**
	 * drag 2 grey totes from the landfill into scoring zone and continue into the auton zone
	 */
	public void autonFour()
	{
		drive.move(LANDFILL_TO_SCORING, -AUTON_SPEED_MOD);
		elevator.goTo(2);
		drive.move(LANDFILL_TO_AUTON, -AUTON_SPEED_MOD);
	}
	
	/**
	 * push a yellow tote into auton zone, continue to do auton four
	 */
	public void autonFive()
	{
		double distance1=TAPE_TO_LANDMARK+STAGING_ZONE_LENGTH;
		double distance2=LANDFILL_TO_AUTON+12*MULTIPLIER;
		drive.move(distance1, AUTON_SPEED_MOD*(2/3));
		drive.move(12*MULTIPLIER, -AUTON_SPEED_MOD);
		drive.turn(-90);
		drive.move(AUTONTOTE_TO_LANDFILLTOTE,AUTON_SPEED_MOD);
		drive.turn(90);
		elevator.goTo(2);
		drive.move(distance2,AUTON_SPEED_MOD);
		autonFour();
	}
	
	/**
	 * zigzag, picking up a stack of all 3 yellow totes and bringing them into auton zone
	 */
	public void autonSix()
	{
		double angle=35;
		double distance1=.5*BETW_AUTO_TOTES*Math.cos(angle);
		double distance2=OVERHANG;
		for(int x=1;x<=2;x++){
		drive.move(OVERHANG, AUTON_SPEED_MOD);
		elevator.goTo(0);
		
		elevator.goTo(1);
		drive.turn(angle);
		drive.move(distance1, AUTON_SPEED_MOD);
		drive.turn(angle-90);
		drive.move(distance1, AUTON_SPEED_MOD);
		drive.turn(-angle);
		}
		drive.move(OVERHANG, AUTON_SPEED_MOD);
		elevator.goTo(0);
		elevator.goTo(1);
		drive.turn(-90);
		drive.move(TAPE_TO_LANDMARK, AUTON_SPEED_MOD);

	}
	
	/**
	 * carry 2 trash cans into auton zone
	 */
	public void autonSeven()
	{
		double distance1=TAPE_TO_DRIVER-OVERHANG;
		double distance2=distance1-12+TAPE_TO_LANDMARK;
		drive.move(distance1,AUTON_SPEED_MOD);
		elevator.goTo(5);
		
		drive.move(12*MULTIPLIER,-AUTON_SPEED_MOD);
		drive.turn(90);
		drive.move(BETW_AUTO_TOTES, AUTON_SPEED_MOD);
		drive.turn(90);
		drive.move(distance2, AUTON_SPEED_MOD);
	}
	
	/**
	 * auton six, except knocking trash cans out of the way instead of avoiding them
	 */
	public void autonEight()
	{
		elevator.goTo(1);
		
		drive.move(BETW_AUTO_TOTES, AUTON_SPEED_MOD);
		elevator.goTo(0);
		
		elevator.goTo(1);
		
		drive.move(BETW_AUTO_TOTES * MULTIPLIER, AUTON_SPEED_MOD);
		elevator.goTo(0);
		
		elevator.goTo(1);
		
		drive.turn(-90);
		drive.move(TAPE_TO_LANDMARK, AUTON_SPEED_MOD);

	}
}

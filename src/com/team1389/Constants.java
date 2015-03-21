
package com.team1389;


/** KNOWN CONTROLS
 * ---------------------------------------DRIVE STICK -----------------------------------------------------
 * Left Analog X: Turn drive train
 * Left Analog Y: Speed of drive train
 * 
 * 
 * 
 * 
 * ---------------------------------------MANIP STICK -----------------------------------------------------
 * Left Analog Y: Move elevator up and down (within bounds of IR sensor 1 of 5 and IR sensor 5 of 5)
 * A Button: Move elevator to IR sensor 2 of 5
 * X Button: Move elevator to IR sensor 3 of 5
 * B Button: Move elevator to IR sensor 4 of 5 
 * 
 * 
 * @author Paul LoBuglio
 */

public class Constants {
	//which bot
	public static final boolean isTestBot = true;	
	
	//Joystick input values
	public static final int ButtonX 			  = 3; // XBox Controller X Button number for getRawButton= or getRawAxis=
	public static final int ButtonA			  = 1; // XBox Controller A Button number
	public static final int ButtonB			  = 2; // XBox Controller B Button number
	public static final int ButtonY			  = 4; // XBox Controller Y Button number
	public static final int BumperL			  = 5; // XBox Controller  Left Bumper number
	public static final int BumperR			  = 6; // XBox Controller Right Bumper number
	
	public static final int LeftY				  = 1; // XBox Controller  Left Y Axis number
	public static final int LeftX				  = 0; // XBox Controller  Left X Axis number
	public static final int LeftTrigger		  = 3; // XBox Controller  Left Trigger Axis number
	public static final int RightTrigger	      = 4; // XBox Controller  Right Trigger Axis number
	public static final int RightY			 	  = 5; // XBox Controller Right Y Axis number
	public static final int RightX				  = 4; // XBox Controller Right X Axis number

	//Motor PWM ports
	public static final int RB_PWM_DRIVE_TEST_BOT         = 4;
	public static final int RF_PWM_DRIVE_TEST_BOT         = 3;
	public static final int LF_PWM_DRIVE_TEST_BOT         = 0;
	public static final int LB_PWM_DRIVE_TEST_BOT         = 1;
	public static final int ELEVATOR_PWM         = 2;
	public static final int KNOCKER_PORT = testFinalSwitch(2, -1);
	
	public static final int RIGHT_PWM_DRIVE_FINAL_BOT          = 0;
	public static final int LEFT_PWM_DRIVE_FINAL_BOT           = 1;
	
	static final int ELEVATOR_ONE_PWM         = 2;
	static final int ELEVATOR_TWO_PWM         = 3;
	
	//Joystick USB ports
	public static final int DRIVE_JOY            = 0;
	public static final int MANIP_JOY            = 1;
	
	//IR Sensor Digital Ports
	public static final int INFRARED_ONE         = 3;
	public static final int INFRARED_TWO         = 4;
	public static final int INFRARED_THREE       = 5;
	public static final int INFRARED_FOUR        = 6;
	public static final int INFRARED_FIVE        = 7;
	public static final int CONTACT_SENSE        = 2;
	
	//Encoder Digital Ports
	public static final int ENCODER_1A           = 8;
	public static final int ENCODER_1B           = 9;
	public static final int ENCODER_2A           = 10;
	public static final int ENCODER_2B           = 11;

	///PID VALUES
	public static final double STRAIGHT_P = .05; //these work
	public static final double STRAIGHT_I = .002;
	public static final double STRAIGHT_D = 0;
	
	//Values
	public static final float LIMITER            = (float) 1.42; //Approximately sqrt(2)
	public static final float INCHES_PER_ROT     = (float) 12.5663706144;
	public static final float ELEVATOR_SPEED_MOD = 1;
	public static final int ELEVATOR_MAX_HEIGHT = 4;
	public static final double MAX_ACCELERATION   = 0.002;
	public static final double PERCENT_POWER_CHANGE = 0.025;
	
	//field dimentions in feet
	public static final double TOTE_LENGTH = 27 / 12;
	public static final double CONTAINER_DIAMETER = 22 / 12;
	public static final double AUTON_ELEMENT_GAP = 2 + 9/12; //gap between tote+container pairs in auton setup
	public static final double DISTANCE_TO_LANDMARK = 8 + 11/12; //distance needed to travel from auton elements to landmark
	public static final double EXTRA_BACK_DISTANCE = 1; //distance to be sure we are clear of tote
	
	//motor speeds
	public static final double DRIVE_SPEED = testFinalSwitch(.5, .4);
	public static final double KNOCKER_SPEED = .1;
	
	//timing
	public static final double KNOCKER_DELAY = .3;
	
	/**
	 * lets options automatically switch when isTestBot is changed to true or false
	 * @param testOption
	 * @param finalOption
	 * @return
	 */
	static <T> T testFinalSwitch(T testOption, T finalOption){
		if (isTestBot){
			return testOption;
		} else {
			return finalOption;
		}
	}
}
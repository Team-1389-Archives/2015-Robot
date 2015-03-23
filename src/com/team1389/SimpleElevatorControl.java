package com.team1389;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class SimpleElevatorControl extends GenericElevator { //WARNING: UNTESTED

	SpeedController liftOne;
	SpeedController liftTwo;

	enum Positon{
		DOWN,
		UP
	}

	private Positon position;
	private Positon wantedPosition;

	public SimpleElevatorControl() {
		liftOne=new Victor(Constants.ELEVATOR_ONE_PWM);
		liftTwo=new Victor(Constants.ELEVATOR_TWO_PWM);
	}

	@Override
	public void goTo(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean thereYet() {
		return position == wantedPosition;
	}

	@Override
	public void teleopConfig() {
		setMotors(0);
		super.teleopConfig();
	}
	
	@Override
		public void teleopTick() {
			setMotors(Robot.state.manip.getRightY() * Constants.ELEVATOR_SPEED_MOD);
			super.teleopTick();
		}

	@Override
	public void autonConfig() {
		position = Positon.DOWN;
		super.autonConfig();
	}

	@Override
	public void autonTick() {
		if (Robot.state.bottomInfared.get()){
			position = Positon.DOWN;
		} else {
			position = Positon.UP;
		}

		if (!thereYet()){
			if(wantedPosition == Positon.UP){

			}
		}
		super.autonTick();
	}

	private void setMotors(double speed){
		liftOne.set(speed * Constants.ELEVATOR_SPEED_MOD * -1);
		liftTwo.set(speed * Constants.ELEVATOR_SPEED_MOD);
	}
}

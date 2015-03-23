package com.team1389;

public abstract class GenericElevator extends Component{
	public abstract void goTo(int position);
	public abstract boolean thereYet();
	
	public void goToAndWait(int position){
		goTo(position);
		while(!thereYet() && Robot.isRobotAutonEnabled()){
			Robot.doAutonTick();
		}
	}
}

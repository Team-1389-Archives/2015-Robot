package org.usfirst.frc.team1389.robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrapElevator extends Component{
	Victor lift;
	public CrapElevator(){
		//lift = new Victor(Constants.ELEVATOR_PWM);
	}
	public void teleopConfig(){
		lift=new Victor(Constants.ELEVATOR_PWM);
	}
	public void teleopTick(){
		lift.set(Robot.state.manip.getRightY()*Constants.ELEVATOR_SPEED_MOD);
		SmartDashboard.putNumber("righty", Robot.state.manip.getRightY());
	}
	
}

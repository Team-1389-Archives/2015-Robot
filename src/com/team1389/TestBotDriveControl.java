package com.team1389;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestBotDriveControl extends GenericDriver{
	Victor RFDrive;
	Victor RBDrive;
	Victor LFDrive;
	Victor LBDrive;
	double leftCoef;
	double rightCoef;
	int rampUpState;
	PosTrack pos;
	int moveCount = 0;
	int turnCount = 0;
	final boolean encoderVerified=false;

	double actualLeft = 0, actualRight = 0;
	
	public TestBotDriveControl(PosTrack pos) {
		this();
		this.pos=pos;
	}
	public TestBotDriveControl(){
		rampUpState=COMPUTER_ASSISTED;
		leftCoef=1;
		rightCoef=1;
		RFDrive = new Victor(Constants.RF_PWM_DRIVE_TEST_BOT);
		RBDrive = new Victor(Constants.RB_PWM_DRIVE_TEST_BOT);
		LFDrive = new Victor(Constants.LF_PWM_DRIVE_TEST_BOT);
		LBDrive = new Victor(Constants.LB_PWM_DRIVE_TEST_BOT);
	}

	public void drive(double x,double y){
		SmartDashboard.putNumber("rampup mode", rampUpState);
		double leftPower=(y + x) / Constants.LIMITER;
		double rightPower=(y - x) / Constants.LIMITER * -1;

		if(rampUpState==COMPUTER_ASSISTED){
			actualLeft=AssistedPowerControl(leftPower,actualLeft);
			actualRight=AssistedPowerControl(rightPower,actualRight);
		}
		else if(rampUpState==STRICT_COMPUTER){
			actualLeft=PowerControl(leftPower,actualLeft);
			actualRight=PowerControl(rightPower,actualRight);
		}
		else{
			actualLeft=leftPower;
			actualRight=rightPower;
		}

		LFDrive.set(actualLeft);
		LBDrive.set(actualLeft);
		RFDrive.set(actualRight);
		RBDrive.set(actualRight);

		if(encoderVerified){
			VerifyVelocity(leftPower, rightPower, Robot.state.encoder1, Robot.state.encoder2);
			leftPower*=leftCoef;
			rightPower*=rightCoef;
		}

	}

	/**
	 * used in computer assisted ramp up state, uses a proportional power increase/decrease curve to allow user override
	 * @param Power side-specific input from controller
	 * @param actualPower current power on the same side
	 * @return new actual power based on requested Power
	 */
	private double AssistedPowerControl(double Power, double actualPower){
		double proportionalChange = Constants.PERCENT_POWER_CHANGE * Math.abs(Power - actualPower);
		if (Power > actualPower + proportionalChange){
			actualPower += proportionalChange;
		} else if (Power < actualPower - proportionalChange){
			actualPower-= proportionalChange;
		} else {
			actualPower = Power;
		}
		return actualPower;
	}

	/**
	 * used in computer restricted ramp up state, power increase/decrease per tick is limited to a fixed value
	 * @param Power side-specific input from controller
	 * @param actualPower current power on the same side
	 * @return new actual power based on requested Power
	 */
	private double PowerControl(double Power, double actualPower){
		//TODO
		double proportionalChange = Constants.MAX_ACCELERATION;
		if (Power > actualPower + proportionalChange){
			actualPower += proportionalChange;
		} else if (Power < actualPower - proportionalChange){
			actualPower-= proportionalChange;
		} else {
			actualPower = Power;
		}
		return actualPower;
	}
	
	/**
	 * uses encoders to verify that the robot is going straight
	 * @param leftPow left input power
	 * @param rightPow right input power
	 * @param encoder1 left encoder (output power)
	 * @param encoder2 right encoder (output power)
	 */
	private void VerifyVelocity(double leftPow, double rightPow,
			Encoder encoder1, Encoder encoder2) {
		final double multiplier=1;
		double error=(leftPow/rightPow)-(encoder1.getRate()/encoder2.getRate());
		if(error>0){//turning desired direction too fast
			leftCoef*=Math.abs(error)*multiplier;
			rightCoef/=Math.abs(error)*multiplier;
		}
		else if(error<0){//turning desired direction too slow
			leftCoef/=Math.abs(error)*multiplier;
			rightCoef*=Math.abs(error)*multiplier;
		}
	}
	
	public float fullPow(float y)
	{
		if (1 - Robot.state.drive.getLeftY() < .15)
			return 1;
		if (-1 - Robot.state.drive.getLeftY() < -.15)
			return -1;
		return y;
	}
	

	@Override
	public void teleopConfig(){
		super.teleopConfig();
	}

	/**
	 * Teleoperated control for the drive train
	 */
	@Override
	public void teleopTick()
	{
		String rampUp = null;
		switch(rampUpState){
			case STRICT_COMPUTER:rampUp="Strict";
			break;
			case FULL_USER:rampUp="User";
			break;
			case COMPUTER_ASSISTED:rampUp="Assisted";
			break;
			default:rampUp="null";
		}
		if(Robot.state.drive.isBPressed()){
			rampUpState++;
			rampUpState%=3;
		}
		float y = (float) Robot.state.drive.getLeftY() * -1;
		//y = fullPow(y);
		drive(Robot.state.drive.getLeftX(), y); 
	}
	
	/**
	* autonomous drive system
	 * @param distance the distance to drive
	 * @param speed min:-1 max:1 - numbers cause robot to go backward
	 * simulates xbox control stick
	 */

	public double move(double distance, double speed){
		
		pos.teleopTick();
		if (moveCount == 0)
		{
			pos.resetDistance();
		if(pos.distance>=distance)
			{
			moveCount++;
			return distance;
			}
		else if (Robot.state.contactSensor.get()){
			drive(speed,0);
			moveCount++;
			return move(distance,speed);
		}
		}
		return 0;
	}
	public double turn(double angle){
		pos.teleopTick();
		if(pos.angle>=angle)return angle;
		else{
			drive(0,Math.abs(angle)/angle);
			return turn(angle);
		}
	}
	
	/**
	 * Drive train Autonomous setup
	 */
	@Override
	public void autonConfig(){
		super.autonConfig();
	}

	/**
	* Instructions for drive train at each autonomous tick. 
	*/
	@Override
	public void autonTick(){
		super.autonTick();
	}
	@Override	
	public void test(){

	}
	@Override
	public void setRampMode(int mode) {
		rampUpState = mode;
	}
}

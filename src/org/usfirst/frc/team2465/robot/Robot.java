/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team2465.robot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends SampleRobot {

	SerialPort serial_port;
	// IMU imu; // This class can be used w/nav6 and navX MXP.
	// IMUAdvanced imu; // This class can be used w/nav6 and navX MXP.
	AHRS imu; // This class can only be used w/the navX MXP.
	boolean first_iteration;
	Victor RF, RB, LF, LB;
	Joystick joy;
	Timer time;
	double angle;// degrees
	double heading;
	double dt = 0;
	double t1 = 0;
	double aX, aY, velX, velY, posX, posY, distance;
	int holdCount = 0;
	float initAng = 0;
	float rMod = 0;
	float lMod = 0;
	float xi, yi;
	float rSpeed = 0;
	float lSpeed = 0;
	float rMax = -.2f;
	int turn = 0;
	float lMax = .2f;
	float newCount = 0;
	float anotherAng = 0;
	float lSpeedMod = 0;
	ServerSocket server;
	Socket client;
	DataOutputStream dos;
	
	private final float coefficent = .25f;

	public Robot() {

		joy = new Joystick(0);
		RF = new Victor(3);
		RB = new Victor(4);
		LF = new Victor(0);
		boolean moveRobo;
		LB = new Victor(1);
		time = new Timer();

		try {

			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);

			byte update_rate_hz = 50;
			// imu = new IMU(serial_port,update_rate_hz);
			// imu = new IMUAdvanced(serial_port,update_rate_hz);
			imu = new AHRS(serial_port, update_rate_hz);
		} catch (Exception ex) {

		}
		if (imu != null) {
			LiveWindow.addSensor("IMU", "Gyro", imu);
		}
		first_iteration = true;
		try {
			server = new ServerSocket(4444);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Thread vfc = new Thread(new Runnable() {
			public void run() {
				//	while (!client.isConnected()) {
				try {
					client = server.accept();
					dos = new DataOutputStream(client.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//}
		});
		vfc.start();
	}

	/**
	 * This function is called once each time the robot enters autonomous mode.
	 */
	public void autonomous() {

		RF.set(.5);
		RB.set(.5);
		LF.set(-.5);
		LB.set(-.5);

	}

	/**
	 * This function is called once each time the robot enters operator control.
	 */
	public void operatorControl() {

		while (isOperatorControl() && isEnabled()) {


			if (dos!= null)
			{
				try{

					dos.writeFloat(imu.getDisplacementX());
					dos.writeFloat(imu.getDisplacementY());
					dos.writeFloat(imu.getYaw());
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			// When calibration has completed, zero the yaw
			// Calibration is complete approaximately 20 seconds
			// after the robot is powered on.  During calibration,
			// the robot should be still



			float x = (float) joy.getRawAxis(1) * -1;
			float y = (float) joy.getRawAxis(0);
			
			
			
			if(joy.getRawButton(3))
			{
				SmartDashboard.putString("mode", "turn");
				float diff = initAng - imu.getYaw(); 
				
				int turn = 0;
				if (diff < -10)
					turn = -1;
				if (diff > 10)
					turn = 1;
				
				if (Math.abs(diff) > 180){
					turn *= -1;
				}
				
				SmartDashboard.putNumber("turn", turn);
				
				RF.set(.5 * turn);
				RB.set(.5 * turn);
				LF.set(.5 * turn);
				LB.set(.5 * turn);
				
			}
			
			


			if (!deadController(x, y))
			{
				RF.set(y - x / Math.sqrt(2));
				RB.set(y - x / Math.sqrt(2));
				LF.set(y + x / Math.sqrt(2));
				LB.set(y + x / Math.sqrt(2));
			}
			boolean is_calibrating = imu.isCalibrating();
			if ( first_iteration && !is_calibrating ) {
				Timer.delay( 0.3 );
				imu.zeroYaw();
				first_iteration = false;
			}
			

			SmartDashboard.putNumber("initAng", initAng);
			float diff = initAng - imu.getYaw();
			float distance = (float) Math.sqrt(Math.pow(imu.getDisplacementX() - xi, 2) + Math.pow(imu.getDisplacementY() - yi, 2));
			SmartDashboard.putNumber("DIFF YO", diff);
			SmartDashboard.putNumber("Left Speed mod", lSpeed);
			SmartDashboard.putNumber("Distance", distance);
			SmartDashboard.putNumber("left or right", turn);
			SmartDashboard.putNumber("rMax", rMax);
			SmartDashboard.putNumber("lMax", lMax);
			
			

			if (joy.getRawButton(1))
			{
				SmartDashboard.putString("mode", "forward");


				if (holdCount == 0)
				{
					//initAng = imu.getYaw();
					xi = imu.getDisplacementX();
					yi = imu.getDisplacementY();
				}

					if (diff < -5)
					{
						rMax = lSpeed;
						turn = 1;
					}
					
					if (diff > 5)
					{
						lMax = lSpeed;
						turn = -1;
					}
					
					lSpeed = findMod(lMax, rMax);
					
					
				RF.set(-.5);
				RB.set(-.5);
				LF.set(.5 + lSpeed);
				LB.set(.5 + lSpeed);
				

				holdCount++;

			}

			if (!joy.getRawButton(3) && deadController(x, y))
			{
				holdCount = 0;
				lSpeed = 0;
				RF.set(0);
				RB.set(0);
				LF.set(0);
				LB.set(0);

			}



				if (joy.getRawButton(2))
				{
					initAng = imu.getYaw();
				}
				
				
			SmartDashboard.putBoolean(  "IMU_Connected",        imu.isConnected());
			SmartDashboard.putBoolean(  "IMU_IsCalibrating",    imu.isCalibrating());
			SmartDashboard.putNumber(   "IMU_Yaw",              imu.getYaw());
			SmartDashboard.putNumber(   "IMU_Pitch",            imu.getPitch());
			SmartDashboard.putNumber(   "IMU_Roll",             imu.getRoll());
			SmartDashboard.putNumber(   "IMU_CompassHeading",   imu.getCompassHeading());
			SmartDashboard.putNumber(   "IMU_Update_Count",     imu.getUpdateCount());
			SmartDashboard.putNumber(   "IMU_Byte_Count",       imu.getByteCount());
			// If you are using the IMUAdvanced class, you can also access the following
			// additional functions, at the expense of some extra processing
			// that occurs on the CRio processor
			SmartDashboard.putNumber(   "IMU_Accel_X",          imu.getWorldLinearAccelX());
			SmartDashboard.putNumber(   "IMU_Accel_Y",          imu.getWorldLinearAccelY());
			SmartDashboard.putBoolean(  "IMU_IsMoving",         imu.isMoving());
			SmartDashboard.putNumber(   "IMU_Temp_C",           imu.getTempC());

			SmartDashboard.putNumber(   "Velocity_X",       	imu.getVelocityX());
			SmartDashboard.putNumber(   "Velocity_Y",       	imu.getVelocityY());
			SmartDashboard.putNumber(   "Displacement_X",       imu.getDisplacementX());
			SmartDashboard.putNumber(   "Displacement_Y",       imu.getDisplacementY());

		}
	}
	
	public float findMod (float lEx, float rEx)
	{
		return (lEx + rEx) / 2;
	}

	public boolean deadController(float x, float y)
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < .2;
	}
	/**
	 * This function is called once each time the robot enters test mode.
	 */
	public void test() {

	}
}

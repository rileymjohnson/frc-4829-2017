package org.usfirst.frc.team4829.robot;

import com.ctre.CANTalon;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	
	CANTalon ldrive = new CANTalon(4);
	CANTalon rdrive = new CANTalon(3);
	// CANTalon cdrive = new CANTalon(0);
	
	RobotDrive myRobot = new RobotDrive(ldrive, rdrive);
	
	Joystick wheel_stick = new Joystick(0);
	//Joystick climb_stick = new Joystick(1);
	Timer timer = new Timer();
	UsbCamera f_camera = new UsbCamera("f_camera", 0);
	UsbCamera b_camera = new UsbCamera("b_camera", 1);
	boolean isFrontCamera = true;
	boolean switchedLastLoop = false;
	JoystickButton cameraToggle = new JoystickButton(wheel_stick, 2);
	int width = 40;
	int height = 30;
	int fps = 4;
	
	//constants
	float autonomousTime = 1.4f;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		ldrive.setSafetyEnabled(false)
		drive.setSafetyEnabled(false)
		
		if(isFrontCamera)
		{
			f_camera = CameraServer.getInstance().startAutomaticCapture(0);
			f_camera.setResolution(width, height);
			f_camera.setFPS(fps);
		}
		else
		{
			b_camera = CameraServer.getInstance().startAutomaticCapture(1);
			b_camera.setResolution(width, height);
			b_camera.setFPS(fps);
		}
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		
		timer.reset();
		timer.start();
	}
	
	

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (timer.get() < autonomousTime) {
			myRobot.drive(-0.5, 0.0); // drive forwards half speed
		} else {
			myRobot.drive(0.0, 0.0); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	
	
	
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		ldrive.set(wheel_stick.getX() + wheel_stick.getY());
		rdrive.set(wheel_stick.getX() - wheel_stick.getY());
		
//		if(cameraToggle.get() && !switchedLastLoop)
//		{
//			isFrontCamera = !isFrontCamera;
//		}
//		switchedLastLoop = cameraToggle.get();
//		
//		if(isFrontCamera)
//		{
//			b_camera.setFPS(-1);
//			f_camera = CameraServer.getInstance().startAutomaticCapture(0);
//			f_camera.setResolution(width, height);
//			f_camera.setFPS(fps);
//		}
//		else
//		{
//			f_camera.setFPS(-1);
//			b_camera = CameraServer.getInstance().startAutomaticCapture(1);
//			b_camera.setResolution(width, height);
//			b_camera.setFPS(fps);
//		}
		
		//double axis = climb_stick.getY();
		//cdrive.set(axis*0.4);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}


}

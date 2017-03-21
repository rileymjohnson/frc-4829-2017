package org.usfirst.frc.team4829.robot;

import com.ctre.CANTalon;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.lang.Math;

public class Robot extends IterativeRobot {
	
	CANTalon ldrive = new CANTalon(3);
	CANTalon rdrive = new CANTalon(2);
	CANTalon cdrive = new CANTalon(0);
	
	RobotDrive myRobot = new RobotDrive(ldrive, rdrive);
	
	Joystick wheel_stick = new Joystick(0);
	Joystick climb_stick = new Joystick(1);
	Timer timer = new Timer();
	int width = 400;
	int height = 300;
	int fps = 30;
	
	//constants
	float autonomousTime = 1.4f;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		UsbCamera f_camera = CameraServer.getInstance().startAutomaticCapture(0);
		f_camera.setResolution(width, height);
		f_camera.setFPS(fps);
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
			myRobot.drive(0.5, 0.0); // drive forwards half speed
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
		ldrive.set(wheel_stick.getX() - wheel_stick.getY());
		rdrive.set(wheel_stick.getX() + wheel_stick.getY());	
		
		double axis = climb_stick.getY();
		cdrive.set(-Math.abs(axis));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}


}

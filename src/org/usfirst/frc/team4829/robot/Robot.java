package org.usfirst.frc.team4829.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	
	CANTalon ldrive = new CANTalon(3);
	CANTalon rdrive = new CANTalon(2);
	CANTalon cdrive = new CANTalon(0);
	
	RobotDrive myRobot = new RobotDrive(ldrive, rdrive);
	
	Joystick wheel_stick = new Joystick(0);
	Joystick climb_stick = new Joystick(1);
	Timer timer = new Timer();
	UsbCamera f_camera = new UsbCamera("f_camera", 0);
	boolean isFrontCamera = true;
	boolean switchedLastLoop = false;
	
	//JoystickButton cameraToggle = new JoystickButton(wheel_stick, 2);
	int width = 400;
	int height = 300;
	int fps = 30;
	Encoder leftE = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder rightE = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	// 4 is digital input channel for th LED
	
	//constants
	float autonomousTime = 10f;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//ldrive.setSafetyEnabled(false);
		//drive.setSafetyEnabled(false);
		
		leftE.setMaxPeriod(.1);
		leftE.setMinRate(10);
		leftE.setDistancePerPulse(5);
		leftE.setReverseDirection(true);
		leftE.setSamplesToAverage(7);
	
		rightE.setMaxPeriod(.1);
		rightE.setMinRate(10);
		rightE.setDistancePerPulse(5);
		rightE.setReverseDirection(true);
		rightE.setSamplesToAverage(7);
		
		ldrive.changeControlMode(TalonControlMode.Position);
		ldrive.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		ldrive.setPID(.5, .001, 0);
		ldrive.enableControl();
		
		rdrive.changeControlMode(TalonControlMode.Position);
		rdrive.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rdrive.setPID(.5, .001, 0);
		rdrive.enableControl();
		
		
		f_camera = CameraServer.getInstance().startAutomaticCapture(0);
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
	//	if (timer.get() < autonomousTime) {
			//myRobot.drive(0.5, 0.0); // drive forwards half speed
			ldrive.set(20);
			rdrive.set(20);
		//} else {
			myRobot.drive(0.0, 0.0); // stop robot
	//	}
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
		
		
		f_camera = CameraServer.getInstance().startAutomaticCapture(0);
		f_camera.setResolution(width, height);
		f_camera.setFPS(fps);
	
		
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
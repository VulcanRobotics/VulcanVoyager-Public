/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc1218.VulcanVoyager;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * FALIURE
 */

public class RobotMap {

	//Elevator
	public static int elevTalonID = 3;
	public static int elevSlaveTalonID = 2;
	public static double[] elevTalonPIDF = {9.5, 0, 55, 45};
	public static int elevatorUpLmt = 0;
	public static int elevatorDnLmt = 1000;

	//DriveTrain
	public static int speedControllerL1ID = 21;
	public static int speedControllerL2ID = 20;
	public static int speedControllerL3ID = 19;
	public static int speedControllerR1ID = 24;
	public static int speedControllerR2ID = 23;
	public static int speedControllerR3ID = 22;

	public static double maxPower = 0.35;
	public static double jsDeadband = 0.2;

	//Climb
	public static int scorpTalonID = 0;
	public static int pogoTalonID = 1;
	public static double[] pogoTalonPIDF = {5.5, 0, 20, 25};
	public static double pogoUpPower = -0.3;
	public static double pogoCurrentLmt = 0.5;
	public static double scorpCurrentLmt = 0.5;
	public static double pogoInitPower = 0.2;
	public static double scorpInitPower = 0.2;
	public static double scorpRunPower = 0.3;

	//Claw 
	public static int[] cargoSolenoid = {1, 2};
	public static int[] panelSolenoid = {1, 1};
	public static int[] clawSolenoid = {1, 0};
	public static int cargoTalonID = 4;
	public static int compressorID = 0;
	public static int nullcompressorID = 1;

	// public static void loadProperties() {
	// 	PropertiesManager pm = new PropertiesManager("/home/lvuser/robot.properties");
	// 	// System.out.println("RobotMap: loading properties");
	// 	pm.load();		

	// }
	
	
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}

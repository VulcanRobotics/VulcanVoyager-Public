
package org.usfirst.frc1218.VulcanVoyager.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc1218.VulcanVoyager.Robot;
import org.usfirst.frc1218.VulcanVoyager.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 *
 */
public class DriveTrain extends Subsystem {

    public AHRS navX;
    
    private CANSparkMax speedControllerL1;
    private CANSparkMax speedControllerL2;
    private CANSparkMax speedControllerL3;
    private SpeedControllerGroup speedControllerGroupLeft;
    private CANSparkMax speedControllerR1;
    private CANSparkMax speedControllerR2;
    private CANSparkMax speedControllerR3;
    private SpeedControllerGroup speedControllerGroupRight;
    public DifferentialDrive differentialDrive;

    public Spark LEDController;

    public Ultrasonic ultraSonicSensor;

    public DriveTrain() {

        speedControllerL1 = new CANSparkMax(21, MotorType.kBrushless);
        speedControllerL2 = new CANSparkMax(20, MotorType.kBrushless);
        speedControllerL3 = new CANSparkMax(19, MotorType.kBrushless);
        speedControllerR1 = new CANSparkMax(24, MotorType.kBrushless);
        speedControllerR2 = new CANSparkMax(23, MotorType.kBrushless);
        speedControllerR3 = new CANSparkMax(22, MotorType.kBrushless);
        
        
        speedControllerL1.setInverted(false);
        speedControllerL2.setInverted(false);
        speedControllerL3.setInverted(false);
        speedControllerR1.setInverted(false);
        speedControllerR2.setInverted(false);        
        speedControllerR3.setInverted(false);


        speedControllerGroupLeft = new SpeedControllerGroup(speedControllerL1, speedControllerL2, speedControllerL3);
        speedControllerGroupRight = new SpeedControllerGroup(speedControllerR1, speedControllerR2, speedControllerR3);
        
        
        differentialDrive = new DifferentialDrive(speedControllerGroupLeft, speedControllerGroupRight);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        try {
            navX = new AHRS(SPI.Port.kMXP); 
        }catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

        LEDController = new Spark(0);

        ultraSonicSensor = new Ultrasonic(0, 1);
        ultraSonicSensor.setAutomaticMode(true);

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
        // setDefaultCommand(new AutoDock());
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void joystickDrive(Joystick stick){
        double maxPower;
        if (Robot.oi.driverJoystick.getRawButton(3)){
            maxPower = 1;
        }else{
            // maxPower = 0.75; //0.5
            maxPower = 0.5;
        }
        double jsDeadBand = 0.2;
        double y_coord;
        double x_coord;

        y_coord = maxPower * Math.pow((-stick.getY()), 3);
        x_coord = maxPower * Math.pow(stick.getX(), 3);
       
        double quickTurnFactor = 0.3; //0.18

        if (Math.abs(stick.getZ()) >= 0.25){
            differentialDrive.curvatureDrive(y_coord, (quickTurnFactor*(Math.pow(stick.getZ(),1))), true);
            // differentialDrive.curvatureDrive(y_coord, (0.5*(Math.pow(stick.getZ(),1))), true);
        }else{
            if (y_coord > 0){
                differentialDrive.curvatureDrive(y_coord, x_coord, false);
            }else{
                differentialDrive.curvatureDrive(y_coord, -x_coord, false);

            }
        }
    }

    public void driveStraight(double speed){
        differentialDrive.tankDrive(-speed, -speed);
    }

    public boolean VisionHasTarget() {
        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        // System.out.println(ta);
        if (tv < 1){
            return false;
        }else{
            return true;
        }
    }

    
}


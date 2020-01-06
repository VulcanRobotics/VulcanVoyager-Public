package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class DriveWithJoystick extends Command {

    public DriveWithJoystick() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        // Robot.driveTrain.LEDController.set(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.driveTrain.joystickDrive(Robot.oi.getDriverJoystick());
        if (Robot.driveTrain.VisionHasTarget()){
            Robot.driveTrain.LEDController.set(0.01);
        }else{
            Robot.driveTrain.LEDController.set(0.0);
        }

        SmartDashboard.putString("DB/String 9", "SONIC:"+Robot.driveTrain.ultraSonicSensor.getRangeInches());
        if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) < 1){
            SmartDashboard.putString("DB/String 8", "No Visual Target");
        }else{
            SmartDashboard.putString("DB/String 8", "Visual Target");

        }
        // SmartDashboard.putString("DB/String 5", "PogoPos:" + Robot.climb.pogoPot.get());
        System.out.println(Robot.climb.pogoPot.get());
        // System.out.println("NAVX:    "+ Robot.driveTrain.navX.getRoll());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.driveStraight(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}

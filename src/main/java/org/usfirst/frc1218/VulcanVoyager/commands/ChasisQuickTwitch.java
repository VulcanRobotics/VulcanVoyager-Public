package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class ChasisQuickTwitch extends Command {
    private int direc;
    private double currentTime;
    private DriverStation ds;
    private boolean hasRun;

    public ChasisQuickTwitch(int direction) {
        requires(Robot.driveTrain);
        direc = direction;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        ds = DriverStation.getInstance();
        System.out.println("TURNING!!");
        if (direc == 1){
            Robot.driveTrain.differentialDrive.curvatureDrive(0, 0.1, true);
        }else if (direc == -1){
            Robot.driveTrain.differentialDrive.curvatureDrive(0, -0.1, true);
        }
        hasRun = false;
        currentTime = ds.getMatchTime();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (direc == 1){
            Robot.driveTrain.differentialDrive.curvatureDrive(0, 0.1, true);
        }else if (direc == -1){
            Robot.driveTrain.differentialDrive.curvatureDrive(0, -0.1, true);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // return hasRun;
        if(currentTime <= 0.2){
            return true;
        }
        if (ds.getMatchTime() <= currentTime - 0.2){
            return true;
        }else{
            return false;
        }

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.differentialDrive.curvatureDrive(0, 0, true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}

package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class ChangeCameraMode extends Command {

    private int lastCamMode;
    private int camMode;

    public ChangeCameraMode() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        lastCamMode = (int)NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getDouble(2);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (lastCamMode == 1){
            camMode = 0;
        }else if (lastCamMode == 0){
            camMode = 1;
        }

        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(camMode);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
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

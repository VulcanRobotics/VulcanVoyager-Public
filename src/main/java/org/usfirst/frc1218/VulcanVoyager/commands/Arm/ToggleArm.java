
package org.usfirst.frc1218.VulcanVoyager.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;

import com.sun.javadoc.RootDoc;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *vulcan robotics is so cool i hope that you all have a good day and are safe 
 */
public class ToggleArm extends Command {

    protected boolean armStatus;

    public ToggleArm() {
        requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        armStatus =  Robot.claw.getArmStat();
        if (armStatus == false) {
            Robot.claw.armOut();
        } else {
            Robot.claw.armIn();
        }
        // Robot.claw.armIn();
        // Robot.claw.armOut();
        // System.out.println(armStatus);
    }

    // Called repeatedly when this Command is sche duled to run
    @Override
    protected void execute() {
       
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}


package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *vulcan robotics is so cool i hope that you all have a good day and are safe 
 */
public class UpPogo extends Command {

    protected boolean scorpionEngaged;
    protected boolean pogoEngaged;
    protected double zeroPitch;
    
    public UpPogo() {
        requires(Robot.climb);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // Robot.claw.armIn();
        Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0.4);

        // Robot.climb.setMotorPower(Robot.climb.scorpionTalon, 0.1);

    }

    // Called repeatedly when this Command is sche duled to run
    @Override
    protected void execute() {
       
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
        // return Robot.elevator.onTarget(target);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0);
        Robot.climb.setMotorPower(Robot.climb.scorpionTalon, 0);


    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0);
        Robot.climb.setMotorPower(Robot.climb.scorpionTalon, 0);


    }
}

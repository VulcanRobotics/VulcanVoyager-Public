
package org.usfirst.frc1218.VulcanVoyager.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *vulcan robotics is so cool i hope that you all have a good day and are safe 
 */
public class ActuateIntakeWheels extends Command {

    double power;

    
    public ActuateIntakeWheels(double power) {
        this.power = power;
        requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.claw.setIntakePower(power);


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
        Robot.claw.setIntakePower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}

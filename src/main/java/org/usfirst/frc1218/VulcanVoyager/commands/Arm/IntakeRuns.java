
package org.usfirst.frc1218.VulcanVoyager.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *vulcan robotics is so cool i hope that you all have a good day and are safe 
 */
public class IntakeRuns extends Command {

    public IntakeRuns() {
        requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is sche duled to run
    @Override
    protected void execute() {
        // Robot.claw.cargoTalon.set(ControlMode.PercentOutput, 0);
        // if (Robot.claw.clawSolenoid.get()){
        if (Robot.claw.cargoSolenoid.get()){
            Robot.claw.cargoTalon.set(ControlMode.PercentOutput, -0.15);
        }else{
            Robot.claw.cargoTalon.set(ControlMode.PercentOutput, 0);
        }
       
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

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}

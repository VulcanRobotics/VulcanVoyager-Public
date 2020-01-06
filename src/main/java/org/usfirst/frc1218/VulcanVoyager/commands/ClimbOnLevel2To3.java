
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *vulcan robotics is so cool i hope that you all have a good day and are safe 
 */
public class ClimbOnLevel2To3 extends Command {

    protected boolean scorpionEngaged;
    protected boolean pogoEngaged;
    protected double zeroPitch;
    protected double scorpInitCurrent;
    protected double pogoInitCurrent;
    
    
    public ClimbOnLevel2To3() {
        requires(Robot.climb);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.claw.armIn();
        Robot.climb.setMotorPower(Robot.climb.scorpionTalon, -0.675);
        Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0.75);
        // Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0.4);
        // scorpInitCurrent = Robot.climb.getCurrent(Robot.climb.scorpionTalon);
        // pogoInitCurrent = Robot.climb.getCurrent(Robot.climb.pogoTalon);
        // zeroPitch = Robot.driveTrain.navX.getPitch();
        // System.out.println(zeroPitch);
        scorpionEngaged = false;
        pogoEngaged = true;
    }

    // Called repeatedly when this Command is sche duled to run
    @Override
    protected void execute() {

        if (Robot.climb.pogoPot.get() <= 10){
            Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0);
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
        Robot.climb.setMotorPower(Robot.climb.scorpionTalon, 0);
        Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.climb.setMotorPower(Robot.climb.scorpionTalon, 0);
        Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0);

    }
}

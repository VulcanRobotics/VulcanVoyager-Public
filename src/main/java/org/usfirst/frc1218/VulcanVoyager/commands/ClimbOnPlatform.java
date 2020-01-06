
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class ClimbOnPlatform extends Command {

    protected boolean scorpionEngaged;
    protected boolean pogoEngaged;
    protected double zeroPitch;
    protected double scorpInitCurrent;
    protected double pogoInitCurrent;
    
    
    public ClimbOnPlatform() {
        requires(Robot.climb);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.claw.armIn();
        Robot.climb.setMotorPower(Robot.climb.scorpionTalon, -0.8);
        zeroPitch = Robot.driveTrain.navX.getPitch();
        scorpionEngaged = false;
        pogoEngaged = true;
    }

    // Called repeatedly when this Command is sche duled to run
    @Override
    protected void execute() {
        if (Robot.climb.getCurrent(Robot.climb.scorpionTalon) >= 2 && scorpionEngaged == false){
            Robot.climb.setMotorPower(Robot.climb.scorpionTalon, 0);
            // System.out.println("*********SCORP ENGAGED************");
            scorpionEngaged = true;
        }        
       
        if (pogoEngaged && scorpionEngaged){
            Robot.climb.setMotorPower(Robot.climb.scorpionTalon, -0.675);
            Robot.climb.setMotorPower(Robot.climb.pogoTalon, 1);
            // Robot.climb.pogoMoveTo(zeroPitch);

            if (Robot.climb.pogoReachedCapacity()){
                Robot.climb.setMotorPower(Robot.climb.pogoTalon, 0.1);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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


package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class MotionMagicMoveTo extends Command {
    
    private int target;
    private double currentPos;

    public MotionMagicMoveTo(int setpoint) {
        requires(Robot.elevator);
        setInterruptible(true);
        target = setpoint;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Motion Magicing!!!!!");
        Robot.elevator.moveTo(target);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //System.out.println(Robot.elevator.elevTalon.getSelectedSensorPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // return ;
        // System.out.print(Robot.elevator.onTarget(target)); 
        // System.out.println(Robot.elevator.getCurrentPosition());

        if ((Math.abs(Robot.oi.operatorJoystick.getY()) >= 0.05) || Math.abs(currentPos - target) <= 1){
            return true;
        }else{
            return false;
        }

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.elevator.setElevatorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}

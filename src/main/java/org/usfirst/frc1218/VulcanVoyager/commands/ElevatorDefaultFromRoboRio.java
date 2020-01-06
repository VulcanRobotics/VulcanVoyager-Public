
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class ElevatorDefaultFromRoboRio extends Command {

    private double elevZero;
    private double elevTop;
    private double elevPos;
    private double power;
    private double approachingPower = 0.5;

    public ElevatorDefaultFromRoboRio() {
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        elevZero = 25;
        elevTop = 685;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // System.out.println(Robot.elevator.elevPotRio.get());
        power = -(Robot.oi.operatorJoystick.getY());
        elevPos = 1000 - Robot.elevator.elevPotRio.get();
        System.out.println(power);

        if (elevPos >= elevTop && power < 0){
            power = Math.max(power, 0);
        }else if (elevPos <= elevZero && power > 0){
            power = Math.min(power, 0);
        }

        if (elevTop - elevPos <= 120 && power < 0){
            power = Math.max(power, -approachingPower);
        }else if (elevPos - elevZero <= 120 && power > 0){
            power = Math.min(power, approachingPower);
        }

        SmartDashboard.putString("DB/String 0", "ZERO:"+elevZero);
        SmartDashboard.putString("DB/String 1", "TOP:"+elevTop);
        SmartDashboard.putString("DB/String 2", "POS:"+elevPos);
        SmartDashboard.putString("DB/String 3", "POWER:"+power);

        Robot.elevator.setElevatorPower(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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
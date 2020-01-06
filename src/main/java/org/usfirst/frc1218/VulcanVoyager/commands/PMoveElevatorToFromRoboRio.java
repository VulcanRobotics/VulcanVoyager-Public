
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.sun.javadoc.Tag;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class PMoveElevatorToFromRoboRio extends Command {

    private double targetInch;
    private double targetAbsoluteSensorUnit;
    private String gp;
    private int lv;

    public final double elevatorTravelInch = 72;
    public final double floorOffset = 6;

    private double elevPos;
    private double elevError;

    private double elevKp = 0.02;

    public PMoveElevatorToFromRoboRio(String gamepiece, int level) {
        requires(Robot.elevator);
        gp = gamepiece;
        lv = level;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        if (gp == "Cargo"){
            switch(lv){
                case 1:
                    targetInch = 27.5;
                    break;
                case 2:
                    targetInch = 55.5;
                    break;
                case 3:
                    targetInch = 83.5;
                    break;
                case 4:
                    targetInch = 7.8;
            }            
        }else if (gp == "Hatch"){
            switch(lv){
                case 1:
                    targetInch = 19;
                    break;
                case 2:
                    targetInch = 47;
                    break;
                case 3:
                    targetInch = 75;
                    break;
            }      
        }

        targetAbsoluteSensorUnit = 15 + (targetInch - 7) * ((685 - 15)/72);
        if (targetAbsoluteSensorUnit > 685){
            targetAbsoluteSensorUnit = 685;
        }else if(targetAbsoluteSensorUnit < 25){
            targetAbsoluteSensorUnit = 25;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        SmartDashboard.putString("DB/String 5", "target:"+targetAbsoluteSensorUnit);

        elevPos = 1000 - Robot.elevator.elevPotRio.get();    
        elevError = targetAbsoluteSensorUnit - elevPos;
        double output = elevKp * elevError;

        if (output > 0.8){
            output = 0.8;
        }else if (output < -0.8){
            output = -0.8;
        }

        Robot.elevator.setElevatorPower(-output);

    }
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // return false;
        return (Math.abs(Robot.oi.operatorJoystick.getY()) >= 0.05) || Math.abs(elevError) <= 2;
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
        end();
    }
}
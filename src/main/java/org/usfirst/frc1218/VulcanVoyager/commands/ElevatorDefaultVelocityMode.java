
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *faliure
 */
public class ElevatorDefaultVelocityMode extends Command {

    protected static final double deadband = 0;
    protected ControlMode controlMode = ControlMode.PercentOutput;
    protected ControlMode lastControlMode = ControlMode.PercentOutput;
    protected int setpoint = Robot.elevator.elevatorDnLimit;
    protected double[] smartDashboardData = {1,1};
    
    public ElevatorDefaultVelocityMode() {
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setpoint = Robot.elevator.getCurrentPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        

        double targetSpeed = Robot.oi.operatorJoystick.getY() * 1000;
        Robot.elevator.elevTalon.set(ControlMode.Velocity, targetSpeed);


        if (Math.abs(Robot.oi.operatorJoystick.getY()) >= deadband){
            controlMode = ControlMode.PercentOutput;
    	}else{
            controlMode = ControlMode.MotionMagic;
        }
        
        if(controlMode == ControlMode.MotionMagic && lastControlMode == ControlMode.PercentOutput) {
            setpoint = Robot.elevator.getCurrentPosition();
            if(setpoint < Robot.elevator.elevatorUpLimit) {
                setpoint = Robot.elevator.elevatorUpLimit;
            }else if(setpoint > Robot.elevator.elevatorDnLimit) {
                setpoint = Robot.elevator.elevatorDnLimit;
            }
            // System.out.println("setting to " + setpoint);
            Robot.elevator.moveTo(setpoint);
        
        }
        
        lastControlMode = controlMode;

        if(controlMode == ControlMode.PercentOutput) {
            double power = -(Robot.oi.operatorJoystick.getY());

            if ((Math.abs(Robot.elevator.getCurrentPosition() - Robot.elevator.elevatorDnLimit) <= 15 && power > 0)|| (Math.abs(Robot.elevator.getCurrentPosition() - Robot.elevator.elevatorUpLimit) <= 15 && power < 0)){
                if (power > 0.35){
                    power = 0.35;
                }else if (power < -0.35){
                    power = -0.35;
                }
            }

            Robot.elevator.setElevatorPower(power);
            // System.out.println("POWER at   "+ power);
            SmartDashboard.putNumber("Power at", power);


        }else if (controlMode == ControlMode.MotionMagic){
            // System.out.println("SET to " + setpoint);
            SmartDashboard.putNumber("Set to", setpoint);
            // SmartDashboard.putNumberArray("Array", value);
            Robot.elevator.moveTo(setpoint);

        }

        // SmartDashboard.putNumber("pogo", Robot.climb.pogoTalon.getOutputCurrent());
        // SmartDashboard.putNumber("scorp", Robot.climb.scorpionTalon.getOutputCurrent());
        

        // System.out.println("MASTER:   " + Robot.elevator.elevTalon.getMotorOutputPercent());
        // System.out.println("SLAVE:   " + Robot.elevator.elevSlaveTalon.getMotorOutputPercent());
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


package org.usfirst.frc1218.VulcanVoyager.subsystems;

import org.usfirst.frc1218.VulcanVoyager.Robot;
import org.usfirst.frc1218.VulcanVoyager.commands.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;

/**
 *
 */
public class Elevator extends Subsystem {

    public WPI_TalonSRX elevTalon;
    public WPI_TalonSRX elevSlaveTalon;

    //beta
    // public final int elevatorUpLimit = 250;
    // public final int elevatorDnLimit = 941;
    // public final double elevatorTravelInch = 81;
    // public final double floorOffset = 7;
    // public final double armUpLimitInch = 83.5;
    // public final double armDnLimitInch = 7;

    //alpha
    public final int elevatorUpLimit = -100000;//800; //808;
    public final int elevatorDnLimit = 100000 ;//905; //888   
    public final double elevatorTravelInch = 72;
    public final double floorOffset = 6;

    //constants 
    public final int elevatorTravelUnits = Math.abs(elevatorUpLimit - elevatorDnLimit);
    public final double clicksPerInch = elevatorTravelUnits / elevatorTravelInch;
    
    public final double cargoLv1Inch = 27.5;
    public final double cargoLv2Inch = 55.5;
    public final double cargoLv3Inch = 83.5;


    public final double hatchLv1Inch = 19;
    public final double hatchLv2Inch = 47;
    public final double hatchLv3Inch = 75;

    public AnalogPotentiometer elevPotRio;


    // Initialize your subsystem here
    public Elevator() {
       
        //Talon Motor settings 
        elevTalon = new WPI_TalonSRX(3);
        //elevTalon.configFactoryDefault();
        elevSlaveTalon = new WPI_TalonSRX(2);
        //elevSlaveTalon.configFactoryDefault();

        //RoboRio Pot @ MAR Champs
        elevPotRio = new AnalogPotentiometer(1,1024,0);


        // elevPot = new AnalogPotentiometer(1,1024,0);
        //elevTalon.setInverted(InvertType.InvertMotorOutput);
        elevTalon.setNeutralMode(NeutralMode.Brake);
        elevSlaveTalon.setNeutralMode(NeutralMode.Brake);
        elevSlaveTalon.setInverted(InvertType.OpposeMaster);

        //set slave to slave
        elevSlaveTalon.set(ControlMode.Follower, elevTalon.getDeviceID());


        //Power draw
        elevTalon.configVoltageCompSaturation(12,0);
        elevTalon.enableVoltageCompensation(true);
        elevTalon.configContinuousCurrentLimit(40,0);
        elevTalon.configPeakCurrentLimit(40,0);
        elevTalon.configPeakCurrentDuration(10,0);
        elevTalon.configOpenloopRamp(0.2,0); //seconds from neutral to full throttle

        elevSlaveTalon.configVoltageCompSaturation(12,0);
        elevSlaveTalon.enableVoltageCompensation(true);
        //elevSlaveTalon.configContinuousCurrentLimit(40,0);
        //elevSlaveTalon.configPeakCurrentLimit(40,0);
        elevSlaveTalon.configPeakCurrentDuration(10,0);
        elevSlaveTalon.configOpenloopRamp(0.2,0); //seconds from neutral to full throttle
        
        //PID cosntants settings
        elevTalon.config_kP(0, 75, 0);
        elevTalon.config_kI(0, 0, 0);
        elevTalon.config_kD(0, 0, 0);
        elevTalon.config_kF(0, 24, 0);

        //Max min outputs
        elevTalon.configNominalOutputForward(0,0);
        elevTalon.configNominalOutputReverse(0,0);
        elevTalon.configPeakOutputForward(1,0);
        elevTalon.configPeakOutputReverse(-1,0);
        elevTalon.configAllowableClosedloopError(0, 5, 0);

       elevTalon.configMotionCruiseVelocity(30,0);
        elevTalon.configMotionAcceleration(60,0);

        //set soft limits 
        elevTalon.configForwardSoftLimitEnable(true);
        elevTalon.configForwardSoftLimitThreshold(elevatorDnLimit);
        elevTalon.configReverseSoftLimitEnable(true);
        elevTalon.configReverseSoftLimitThreshold(elevatorUpLimit);
        
        // elevTalon.overrideLimitSwitchesEnable(false);
        elevTalon.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        elevTalon.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);


        elevTalon.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 10, 0);
        elevTalon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 10, 0);

        //Config input potentiometer 
        elevTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog,0,0);
        // elevTalon.configSelectedFeedbackSensor(FeedbackDevice.values(),0,0);
        // elevTalon.setSensorPhase(false);

    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new ElevatorDefault());
        setDefaultCommand(new ElevatorDefaultFromRoboRio());


        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        //dominique was here :)  and corinne :)  
    }

    public void setElevatorPower(double power){
        elevTalon.set(ControlMode.PercentOutput, power);
    }

    public int getCurrentPosition(){
        return elevTalon.getSelectedSensorPosition(0);
        // return (int) elevPot.get();
    }
    
    public double getTargetPosition() {
		return elevTalon.getClosedLoopTarget(0);
    } 
    
    public void moveTo(int position) {
        elevTalon.set(ControlMode.MotionMagic, position);
        // System.out.println("Moving!");        
	}
    
    public void setMotionMagicSpeeds(int cruise) {
		elevTalon.configMotionCruiseVelocity(cruise, 0);
		elevTalon.configMotionAcceleration(cruise*2, 0);
    }
    
    public boolean onTarget(int target){
        if (Math.abs(Robot.elevator.getCurrentPosition() - target) < 1){
            return true;
        }else{
            return false;
        }
    }

    public double getPercentPower(WPI_TalonSRX motor){
        return motor.getMotorOutputPercent();
    }

    public double levelToClicks(double level){
        return elevatorDnLimit - Math.abs((level-floorOffset)*clicksPerInch);
    }

    public double inchesMapped(double dnLmUnit, double upLmUnit, double dnLmInch, double upLmInch, double numToConvert){
        double unitsTraveled = Math.abs(upLmUnit - dnLmUnit);
        double inchesTraveled = Math.abs(upLmInch - dnLmInch);
        double convertionFactor = unitsTraveled / inchesTraveled;
        double output = dnLmUnit - ((numToConvert - dnLmInch) * convertionFactor);
        if(output > dnLmUnit){
            output = dnLmUnit;
        }else if (output < upLmUnit){
            output = upLmUnit;
        }
        return output;
    }
}

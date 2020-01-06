package org.usfirst.frc1218.VulcanVoyager.subsystems;

import org.usfirst.frc1218.VulcanVoyager.Robot;
import org.usfirst.frc1218.VulcanVoyager.commands.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
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
public class Climb extends Subsystem {
    
    public WPI_TalonSRX pogoTalon;
    public WPI_TalonSRX scorpionTalon;

    public DigitalInput scorpionLimit;
    public AnalogInput ai;
    public AnalogPotentiometer pogoPot;



    public Climb() {
        scorpionTalon = new WPI_TalonSRX(6);
        pogoTalon = new WPI_TalonSRX(1);
        ai = new AnalogInput(0);
        pogoPot = new AnalogPotentiometer(ai,100,0);
        //87 full engage

        scorpionTalon.setInverted(false);
        scorpionTalon.setNeutralMode(NeutralMode.Brake);
        pogoTalon.setInverted(false);
        pogoTalon.setNeutralMode(NeutralMode.Brake);

         //Power draw
        scorpionTalon.configVoltageCompSaturation(12,0);
        scorpionTalon.enableVoltageCompensation(true);
        scorpionTalon.configContinuousCurrentLimit(30,0);
        scorpionTalon.configPeakCurrentLimit(30,0);
        scorpionTalon.configPeakCurrentDuration(15,0);
        scorpionTalon.configOpenloopRamp(0.1,0); //seconds from neutral to full throttle

        pogoTalon.configVoltageCompSaturation(12,0);
        pogoTalon.enableVoltageCompensation(true);
        pogoTalon.configContinuousCurrentLimit(30,0);
        pogoTalon.configPeakCurrentLimit(30,0);
        pogoTalon.configPeakCurrentDuration(15,0);
        pogoTalon.configOpenloopRamp(0.1,0); //seconds from neutral to full throttle

          //PID cosntants settings
        pogoTalon.config_kP(0, 40, 0);
        pogoTalon.config_kI(0, 0, 0);
        pogoTalon.config_kD(0, 35, 0);
        pogoTalon.config_kF(0, 40, 0);

        //Max min outputs
        pogoTalon.configNominalOutputForward(0,0);
        pogoTalon.configNominalOutputReverse(0,0);
        pogoTalon.configPeakOutputForward(1,0);
        pogoTalon.configPeakOutputReverse(-1,0);
        pogoTalon.configAllowableClosedloopError(3, 0, 0);

        pogoTalon.configMotionCruiseVelocity(30,0);
        pogoTalon.configMotionAcceleration(60,0);

        // pogoTalon.configSelectedFeedbackSensor(FeedbackDevice.valueOf(Math.abs(Robot.driveTrain.navX.getRoll())),0,0);
        // pogoTalon.configSelectedFeedbackSensor(FeedbackDevice.valueOf(Robot.driveTrain.navX.getRoll()),0,0);
        pogoTalon.configSelectedFeedbackSensor(FeedbackDevice.valueOf(Robot.driveTrain.navX.getPitch()),0,0);
        
        pogoTalon.setSensorPhase(false);

        scorpionTalon.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        // scorpionTalon.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        // scorpionTalon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        scorpionTalon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

        // scorpionTalon.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);

        pogoTalon.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        pogoTalon.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);

        

    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        // setDefaultCommand(new ClimbOnPlatform());   
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void setMotorPower(WPI_TalonSRX Motor, double power){
        Motor.set(ControlMode.PercentOutput, power);
    }

    public double getCurrent(WPI_TalonSRX Motor) {
        return Motor.getOutputCurrent();
    }

    public double getPower(WPI_TalonSRX Motor){
        return Motor.getMotorOutputPercent();
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void pogoMoveTo(double zeroPitch) {
        pogoTalon.set(ControlMode.MotionMagic, zeroPitch);
        // System.out.println("Moving!");
        // System.out.println("POWER:" + Robot.climb.pogoTalon.getMotorOutputPercent());        
    }
    
    public boolean pogoReachedCapacity(){
        //check for pogo pot
        //up 16, down 87 - beta 3/5/2019
        //NEW VAL from MAR Champs
        //up52.6, down90
        return !(pogoPot.get() <= 90);

    }

    
}


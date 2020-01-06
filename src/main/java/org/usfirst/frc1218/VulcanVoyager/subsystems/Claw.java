package org.usfirst.frc1218.VulcanVoyager.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc1218.VulcanVoyager.commands.Arm.IntakeRuns;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {

    public Solenoid cargoSolenoid;
    public Solenoid panelSolenoid;
    public Solenoid clawSolenoid;
    // public Solenoid fakeSolenoid;
    public WPI_TalonSRX cargoTalon;
    public WPI_TalonSRX cargoTalonSlave;

    public Compressor compressor;
    public Compressor nullCompressor;


    public Claw() { 
        cargoSolenoid = new Solenoid(1, 2); //not sure if it's 3 or 1
        panelSolenoid = new Solenoid(1,1);
        clawSolenoid = new Solenoid(1,0);
        cargoTalon = new WPI_TalonSRX(4);
        cargoTalonSlave = new WPI_TalonSRX(5);
        // fakeSolenoid = new Solenoid(0, 0);

        cargoTalonSlave.set(ControlMode.Follower, cargoTalon.getDeviceID());
        cargoTalonSlave.setInverted(InvertType.OpposeMaster);

        compressor = new Compressor(0);
        compressor.setClosedLoopControl(false);
        nullCompressor = new Compressor(1);
        nullCompressor.setClosedLoopControl(true);
        // nullCompressor.stop();

        cargoTalon.setNeutralMode(NeutralMode.Brake);
        cargoTalonSlave.setNeutralMode(NeutralMode.Brake);

    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        // setDefaultCommand(new ClimbOnPlatform());
        setDefaultCommand(new IntakeRuns());  
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void openCargo() {
        cargoSolenoid.set(false);
    }

    
    public void closeCargo() {
        cargoSolenoid.set(true);
        // cargoTalon.set(ControlMode.PercentOutput, 0.5);
    }

    
    public void closePanel() {
        panelSolenoid.set(true);
    }


    public void openPanel() {
        panelSolenoid.set(false);
    }

    public void armOut() {
        clawSolenoid.set(true);
    }
    
    public void armIn() {
        clawSolenoid.set(false);
    }

    public boolean getArmStat() {
        return clawSolenoid.get();
    }
    
    public void setIntakePower(double power){
        cargoTalon.set(power);

    }






}


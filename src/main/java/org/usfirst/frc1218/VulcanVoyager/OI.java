
package org.usfirst.frc1218.VulcanVoyager;

import org.usfirst.frc1218.VulcanVoyager.commands.*;
import org.usfirst.frc1218.VulcanVoyager.commands.Arm.*;
import org.usfirst.frc1218.VulcanVoyager.commands.Arm.ClosePanel;
import org.usfirst.frc1218.VulcanVoyager.subsystems.Elevator;
import org.usfirst.frc1218.VulcanVoyager.subsystems.Climb;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    public Joystick driverJoystick;
    public Joystick operatorJoystick;

    //operator js buttons
    public Button o_cargoIn; //1
    public Button o_shootCargo; //2
    public Button o_clawOpen;//3
    public Button o_hatch; //4
    public Button o_armToggleBtn; //6

    public Button o_hatchLOW; //11
    public Button o_hatchMID; //9
    public Button o_hatchHIGH; //7    
    
    public Button o_cargoLOW; //12
    public Button o_cargoMID; //10
    public Button o_cargoHIGH; //8
    
    //driver js buttons
    public Button d_cargoIn; //1
    public Button d_shootCargo; //2
    public Button d_clawOpen; //3
    public Button o_scorpBack; //5
    public Button d_climb1; //9
    public Button d_climb2; //10
    public Button d_pogoDn; //7

    public Button scorpion; //12
    public Button pogoBack; //8

    public Button d_dock; //11
    public Button d_camera; //12

    public Button d_climb;

    public Button d_shift;

    public Button d_climb2to3;

    public POVButton d_leftTwitch;
    public POVButton d_rightTwitch;
    


    public OI() {
        driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(1);

        //operator buttons
        o_cargoIn = new JoystickButton(operatorJoystick, 1);
        o_shootCargo = new JoystickButton(operatorJoystick, 2);
        o_clawOpen = new JoystickButton(operatorJoystick, 3); //not right
        o_hatch = new JoystickButton(operatorJoystick, 4);
        o_armToggleBtn = new JoystickButton(operatorJoystick, 6);

        o_hatchLOW = new JoystickButton(operatorJoystick, 11);
        o_hatchMID = new JoystickButton(operatorJoystick, 9);
        o_hatchHIGH = new JoystickButton(operatorJoystick, 7);        
        o_cargoLOW = new JoystickButton(operatorJoystick, 12);
        o_cargoMID = new JoystickButton(operatorJoystick, 10);
        o_cargoHIGH = new JoystickButton(operatorJoystick, 8);
        
        //driver buttons
        d_cargoIn = new JoystickButton(driverJoystick, 1);
        d_shootCargo = new JoystickButton(driverJoystick, 2);
        // d_clawOpen = new JoystickButton(driverJoystick, 3);
        d_climb1 = new JoystickButton(driverJoystick, 9);
        d_climb2 = new JoystickButton(driverJoystick, 10);
        d_climb2to3 = new JoystickButton(driverJoystick, 6);
        d_pogoDn = new JoystickButton(driverJoystick, 7);
        o_scorpBack = new JoystickButton(operatorJoystick, 5);

        d_shift = new JoystickButton(driverJoystick, 3);


        // d_climb = new ClimbButton();

        d_dock = new JoystickButton(driverJoystick, 11);
        d_camera = new JoystickButton(driverJoystick, 12);

        d_leftTwitch = new POVButton(driverJoystick, 270);
        d_rightTwitch = new POVButton(driverJoystick, 90);


        pogoBack = new JoystickButton(driverJoystick, 8);
        // scorpion = new JoystickButton(driverJoystick, 12);


        //command bindings
        o_hatch.whenPressed(new OpenCargo());
        o_hatch.whileHeld(new ClosePanel());
        o_cargoIn.whenPressed(new CloseCargo());
        o_cargoIn.whileHeld(new ActuateIntakeWheels(-0.6));
        o_clawOpen.whenPressed(new OpenCargo());
        o_armToggleBtn.whenPressed(new ToggleArm());
        o_shootCargo.whileHeld(new ActuateIntakeWheels(0.6));


        // o_hatchLOW.whenPressed(new MoveElevatorTo(Robot.elevator));
        // o_hatchLOW.whenPressed(new MoveElevatorTo((int) Robot.elevator.inchesMapped(900, 800, 19, 83.5, Robot.elevator.hatchLv1Inch)));
        // o_hatchMID.whenPressed(new MoveElevatorTo((int) Robot.elevator.inchesMapped(900, 800, 19, 83.5, Robot.elevator.hatchLv2Inch)));
        // o_hatchHIGH.whenPressed(new MoveElevatorTo((int) Robot.elevator.inchesMapped(900, 800, 19, 83.5, Robot.elevator.hatchLv3Inch)));
        // o_cargoLOW.whenPressed(new MoveElevatorTo((int) Robot.elevator.inchesMapped(900, 800, 19, 83.5, Robot.elevator.cargoLv1Inch)));
        // o_cargoMID.whenPressed(new MoveElevatorTo((int) Robot.elevator.inchesMapped(900, 800, 19, 83.5, Robot.elevator.cargoLv2Inch)));
        // o_cargoHIGH.whenPressed(new MoveElevatorTo((int) Robot.elevator.inchesMapped(900, 800, 19, 83.5, Robot.elevator.cargoLv3Inch)));
       
        o_hatchLOW.whenPressed(new PMoveElevatorToFromRoboRio("Hatch", 1));
        o_hatchMID.whenPressed(new PMoveElevatorToFromRoboRio("Hatch", 2));
        o_hatchHIGH.whenPressed(new PMoveElevatorToFromRoboRio("Hatch", 3));
        // o_cargoLOW.whenPressed(new PMoveElevatorToFromRoboRio("Cargo", 1));
        o_cargoLOW.whenPressed(new PMoveElevatorToFromRoboRio("Cargo", 4));
        o_cargoMID.whenPressed(new PMoveElevatorToFromRoboRio("Cargo", 2));
        o_cargoHIGH.whenPressed(new PMoveElevatorToFromRoboRio("Cargo", 3));

        d_cargoIn.whenPressed(new CloseCargo());
        d_cargoIn.whileHeld(new ActuateIntakeWheels(-0.6));
        // d_clawOpen.whenPressed(new OpenCargo());
        d_shootCargo.whileHeld(new ActuateIntakeWheels(0.6));
        
        d_climb1.whileHeld(new ClimbOnPlatform());
        d_climb2.whileHeld(new ClimbOnLevel2());
        d_climb2to3.whileHeld(new ClimbOnLevel2To3());

        d_pogoDn.whileHeld(new UpPogo());
        o_scorpBack.whileHeld(new ScorpBack());

        d_dock.whileHeld(new AutoDock());
        d_camera.whenPressed(new ChangeCameraMode());

        pogoBack.whileHeld(new LowerPogo());

        d_leftTwitch.whenPressed(new ChasisQuickTwitch(-1));
        d_rightTwitch.whenPressed(new ChasisQuickTwitch(1));
        
        
        // scorpion.whileHeld(new ClimbOnPlatform());

        // if (driverJoystick.getRawButton(9) && driverJoystick.getRawButton(10)){
        //     // System.out.println("************climb*************");
        //     d_climb1.whileHeld(new ClimbOnPlatform());
        // }

        // d_climb1.whileHeld(new ClimbOnPlatform());

        
    }

    public Joystick getDriverJoystick() {
        return driverJoystick;
    }   
    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }

    public boolean climbPressed(){
        if (driverJoystick.getRawButton(9)&& driverJoystick.getRawButton(10)){
            // System.out.println("************climb*************");
            return true;
        }else{
            return false;
        }
    }

}


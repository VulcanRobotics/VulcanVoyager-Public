
package org.usfirst.frc1218.VulcanVoyager;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc1218.VulcanVoyager.commands.*;
import org.usfirst.frc1218.VulcanVoyager.subsystems.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    public static DriveTrain driveTrain;
    public static Elevator elevator;
    public static Climb climb;
    public static Claw claw;

    int elSpeed = 0;

    // public File f;
    // public BufferedWriter bufferWriter;
    // public FileWriter fileWriter;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        driveTrain = new DriveTrain();
        elevator = new Elevator();
        climb = new Climb();
        claw = new Claw();

        // file = new File("")

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Auto mode", chooser);

        // CameraServer.getInstance().startAutomaticCapture();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        periodicTasks();
    }

    @Override
    public void autonomousInit() {
        // Robot.elevator.elevatorDnLimit = Robot.elevator.getCurrentPosition();
        // Robot.elevator.elevatorUpLimit = Robot.elevator.elevatorDnLimit + 105;
        // autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        // if (autonomousCommand != null) autonomousCommand.start();
        // Robot.claw.armOut();
        teleopInit();

    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        periodicTasks();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();

        Robot.claw.armOut();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        periodicTasks();

        // if (Robot.oi.driverJoystick.getRawButton(10) && Robot.oi.driverJoystick.getRawButton(9)){
        //     new ClimbOnPlatform().start();
        // }else{
        //     new ClimbOnPlatform().cancel();
        // }
    }

    void periodicTasks(){
        // System.out.println(Robot.elevator.elevTalon.getSelectedSensorPosition());
    }
}

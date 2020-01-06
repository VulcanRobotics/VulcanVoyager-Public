
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc1218.VulcanVoyager.Robot;

/**
 *
 */
public class AutoDock extends Command {
    private boolean m_LimelightHasValidTarget;
    private double m_LimelightDriveCommand;
    private double m_LimelightSteerCommand;
    
    public AutoDock(){
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_LimelightHasValidTarget = false;
        m_LimelightDriveCommand = 0.1;
        m_LimelightSteerCommand = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Robot.driveTrain.differentialDrive.feedWatchdog();
        Update_Limelight_Tracking();
        // if (m_LimelightHasValidTarget){
        //     Robot.driveTrain.differentialDrive.curvatureDrive(m_LimelightDriveCommand,m_LimelightSteerCommand, false);
        //   }
        if (m_LimelightSteerCommand != 0){
            Robot.driveTrain.differentialDrive.curvatureDrive(m_LimelightDriveCommand,m_LimelightSteerCommand, false);

        }else if (m_LimelightSteerCommand == 0){
            Robot.driveTrain.differentialDrive.curvatureDrive(m_LimelightDriveCommand, 0.18*Robot.oi.driverJoystick.getZ(), true);
        } 
        //   else{
        //     Robot.driveTrain.differentialDrive.curvatureDrive(0.1, 0, false);
        //   }
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
        end();
    }
        /**
    * This function implements a simple method of generating driving and steering commands
    * based on the tracking data from a limelight camera.
    */
    public void Update_Limelight_Tracking(){
        // System.out.println("Updating Tracking...");
        final double STEER_K = 0.025;                    // how hard to turn toward the target
        final double DRIVE_K = 0.04;                    // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 7.2;        // Area of the target when the robot reaches the wall
        final double MAX_DRIVE = 0.35;                   // Simple speed limit so we don't drive too fast
        final double desiredDistInch = 9.25;
        final double DRIVE_K_Inch = 0.01;                    // how hard to drive fwd toward the target


        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        double ts = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);

        double dist = Robot.driveTrain.ultraSonicSensor.getRangeInches();   

        if (tv < 1.0){
          m_LimelightHasValidTarget = false;
          return;
        }

        m_LimelightHasValidTarget = true;

        // Start with proportional steering
        double steer_cmd = tx * STEER_K;
        m_LimelightSteerCommand = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = Math.max((DESIRED_TARGET_AREA - ta) * DRIVE_K, 0);
        double drive_cmd_inch = Math.max((dist - desiredDistInch) * DRIVE_K_Inch, 0);
        

        // don't let the robot drive too fast into the goal
        if (drive_cmd > MAX_DRIVE){
          drive_cmd = MAX_DRIVE;
        }

        if (drive_cmd_inch > MAX_DRIVE){
            drive_cmd_inch = MAX_DRIVE;
        }
        
        // m_LimelightDriveCommand = drive_cmd;
        m_LimelightDriveCommand = drive_cmd_inch;
        SmartDashboard.putString("DB/String 7", "DRIVE:   "+drive_cmd_inch);
        SmartDashboard.putString("DB/String 6", "STEER:   "+steer_cmd);

    }

}

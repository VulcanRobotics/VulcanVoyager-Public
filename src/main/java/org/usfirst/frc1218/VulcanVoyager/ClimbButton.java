package org.usfirst.frc1218.VulcanVoyager;

import edu.wpi.first.wpilibj.buttons.Button;

public class ClimbButton extends Button {
    public boolean get() {
        return Robot.oi.driverJoystick.getRawButton(9) && Robot.oi.driverJoystick.getRawButton(10);
    }
}
package org.usfirst.frc1218.VulcanVoyager.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbProcedure extends CommandGroup {
    public ClimbProcedure() {
	addSequential(new ClimbOnPlatform());
    }
}
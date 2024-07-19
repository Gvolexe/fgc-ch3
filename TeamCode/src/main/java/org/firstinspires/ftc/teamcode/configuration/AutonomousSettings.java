package org.firstinspires.ftc.teamcode.configuration;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

public class AutonomousSettings {
    public enum AllianceType {
        BLUE, RED
    }

    public static final Vector2d RED_BACKBOARD_LEFT = new Vector2d(52, -25);
    public static final Vector2d RED_BACKBOARD_MIDDLE = new Vector2d(52, -35);
    public static final Vector2d RED_BACKBOARD_RIGHT = new Vector2d(52, -42);

    public static final Vector2d BLUE_BACKBOARD_RIGHT = new Vector2d(55, 25);
    public static final Vector2d BLUE_BACKBOARD_MIDDLE = new Vector2d(55, 35);
    public static final Vector2d BLUE_BACKBOARD_LEFT = new Vector2d(55, 42);
    
    public static final Vector2d RED_PARK = new Vector2d(48,-65);
    public static final Vector2d BLUE_PARK = new Vector2d(48,65);

    public static final Pose2d BORDER_UPPER_LEFT = new Pose2d(-46, -72, 0);
    public static final Pose2d BORDER_DOWN_RIGHT = new Pose2d(24, 72, 0);

    //* STACKS

    //! RED
    public static final Vector2d RED_STACK_LEFT = new Vector2d(-36,6);
    public static final Vector2d RED_STACK_MIDDLE = new Vector2d(-36,12);
    public static final Vector2d RED_STACK_RIGHT = new Vector2d(-36,18);


    //? BLUE
    public static final Vector2d BLUE_STACK_LEFT = new Vector2d(-36,-6);
    public static final Vector2d BLUE_STACK_MIDDLE = new Vector2d(-36,-12);
    public static final Vector2d BLUE_STACK_RIGHT = new Vector2d(-36,-18);








}

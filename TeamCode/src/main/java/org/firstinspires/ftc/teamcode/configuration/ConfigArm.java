package org.firstinspires.ftc.teamcode.configuration;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ConfigArm {

    public static final double degPerTick = 1.25;
    public static double intakePosAngle = 0;
    public static double scoringLowAngle = 150;
    public static double scoringHighAngle = 135 ;
    public static double scoringMiddleAngle = -0;
    public static double driveAngle =  0;
    public static double powerGain = 1;
    public static double Kcos = 0.14;
    public static double kA = -0;
    public static double gndAngleOffset = -40;

    public static double UPPER_LIMIT = 90;

    public static int transitionDownMs = 600;
    public static double homingDegrees = 40.0;
    public static double transitionDownPower = 0.01;
    public static double transitionUpPower = 0.6;
    public static double degForZeroPower = 10;
    public static double degForPositionSnap = 30;

    public static double kP = 0.005;
    public static double kI = 0;
    public static double kD = -0;

}

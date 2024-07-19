package org.firstinspires.ftc.teamcode.configuration;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import java.util.Arrays;

@Config
public class ConfigDrivetrain {


    /*
     * These are motor constants that should be listed online for our motors.
     */
    public static final double TICKS_PER_REV = 529;
    public static final double MAX_RPM = 317.4;


    /*
     * Set RUN_USING_ENCODER to true to enable built-in hub velocity control using drive encoders.
     * Set this flag to false if drive encoders are not present and an alternative localization
     * method is in use (e.g., tracking wheels).
     *
     * If using the built-in motor velocity PID, update MOTOR_VELO_PID with the tuned coefficients
     * from DriveVelocityPIDTuner.
     */
    public static final boolean RUN_USING_ENCODERS = true;

    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(37, 0, 14.9, 20.5);

//    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(0, 0, 0, 0);

    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(4, 0, 0);
    public static PIDCoefficients HEADING_PID = new PIDCoefficients(8, 0, 0);
//    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(0, 0, 0);
//    public static PIDCoefficients HEADING_PID = new PIDCoefficients(0, 0, 0);

    /*
     * These are physical constants that can be determined from your robot (including the track
     * width; it will be tune empirically later although a rough estimate is important). Users are
     * free to chose whichever linear distance unit they would like so long as it is consistently
     * used. The default values were selected with inches in mind. Road runner uses radians for
     * angular distances although most angular parameters are wrapped in Math.toRadians() for
     * convenience. Make sure to exclude any gear ratio included in MOTOR_CONFIG from GEAR_RATIO.
     */
    public static double WHEEL_DIAM = 1.8898 * 2; // in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (motor) speed

    //? Effective track width = 7.39  ||  SE = 2.431
    public static double TRACK_WIDTH = 2.431; // in



    /*
     * These are the feedforward parameters used to model the drive motor behavior. If you are using
     * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
     * motor encoders or have elected not to use them for velocity control, these values should be
     * empirically tuned.
     */
    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;


    /*
     * These values are used to generate the trajectories for you robot. To ensure proper operation,
     * the constraints should never exceed ~80% of the robot's actual capabilities. While Road
     * Runner is designed to enable faster autonomous motion, it is a good idea for testing to start
     * small and gradually increase them later after everything is working. All distance units are
     * inches.
     */
    /*
     * Note from LearnRoadRunner.com:
     * The velocity and acceleration constraints were calculated based on the following equation:
     * ((MAX_RPM / 60) * GEAR_RATIO * WHEEL_RADIUS * 2 * Math.PI) * 0.85
     * Resulting in 96.7401498872079 in/s.
     * This is only 85% of the theoretical maximum velocity of the bot, following the recommendation above.
     * This is capped at 85% because there are a number of variables that will prevent your bot from actually
     * reaching this maximum velocity: voltage dropping over the game, bot weight, general mechanical inefficiencies, etc.
     * However, you can push this higher yourself if you'd like. Perhaps raise it to 90-95% of the theoretically
     * max velocity. The theoretically maximum velocity is 113.81194104377401 in/s.
     * Just make sure that your bot can actually reach this maximum velocity. Path following will be detrimentally
     * affected if it is aiming for a velocity not actually possible.
     *
     * The maximum acceleration is somewhat arbitrary and it is recommended that you tweak this yourself based on
     * actual testing. Just set it at a reasonable value and keep increasing until your path following starts
     * to degrade. As of now, it simply mirrors the velocity, resulting in 96.7401498872079 in/s/s
     *
     * Maximum Angular Velocity is calculated as: maximum velocity / trackWidth * (180 / Math.PI) but capped at 360°/s.
     * You are free to raise this on your own if you would like. It is best determined through experimentation.
     *
     * WARNING: LearnRoadRunner.com's constant generator has capped the calculated recommended velocity at 90in/s.
     * This message is showing because your gear ratio/motor RPM/etc. configuration, results in a recommended
     * velocity (85% of max velocity) exceeding 90in/s.
     * (Your recommended velocity was 96.7401498872079in/s)
     * This is simply insanely fast for an FTC bot and chances are your bot cannot properly reach these speeds.
     *
     * Just to be safe, LearnRoadRunner.com has arbitrarily limited your velocity to 90in/s.
     * You are free to increase it yourself. If you do run into issues, please lower the maximum velocity.
     *
     * A documented case of a similar error which served as an impetus for this reasoning can be found here:
     * https://github.com/acmerobotics/road-runner-quickstart/issues/91

     */
    public static double MAX_VEL = 35;
    public static double MAX_ACCEL = 20;
    public static double MAX_ANG_VEL = Math.toRadians(280);
    public static double MAX_ANG_ACCEL = Math.toRadians(250);

    public static Pose2d ADMISSIBLE_ERROR = new Pose2d(1, 1, Math.toRadians(3.0));

    public static PIDCoefficients xCoeffs =  new PIDCoefficients(0.06, 0, 0.02);
    public static PIDCoefficients yCoeffs =  new PIDCoefficients(0.06, 0, 0.02);
    public static PIDCoefficients turnCoeffs = new PIDCoefficients(0.0091, 0, 0.04);

    public static TrajectoryVelocityConstraint velocityConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
            new TranslationalVelocityConstraint(MAX_VEL)
    ));
    public static TrajectoryAccelerationConstraint accelConstraint = new ProfileAccelerationConstraint(MAX_ACCEL);

    public static double getMotorVelocityF(double ticksPerSecond) {
        // see https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#heading=h.61g9ixenznbx
        return 32767 / ticksPerSecond;
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_DIAM * Math.PI * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * Math.PI * WHEEL_DIAM / 60.0;
    }
}


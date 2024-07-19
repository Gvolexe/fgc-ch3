package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.BallBox;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.BallPush;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.DriveBase;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Lift;
import org.firstinspires.ftc.teamcode.hardware.sensors.Battery;
import org.firstinspires.ftc.teamcode.hardware.sensors.Gyro;
import org.firstinspires.ftc.teamcode.utilities.RingBuffer;

public class HardwareDevices {

    public static Gyro gyro;
//    public static MecanumChassis robot;
    //public static Base robot;
    //public static Door door;
    //public static Grabber grabber;

    public static Lift lift;
    public static DriveBase driveBase;
    public static Battery battery;
    public static BallBox ballBox;
    public static BallPush ballPush;

    private static long currentTimeMillis;
    private static Pose2d currentPose;


    private static double armPreviousVelocity;
    private static double armAcceleration;




    static RingBuffer<Long> timeRing = new RingBuffer<>(3, System.currentTimeMillis());

    public static void init() {
        //robot = new Base();
        //door = new Door();
        //grabber = new Grabber();


        battery = new Battery();
        gyro = new Gyro();

        lift = new Lift();
        ballBox = new BallBox();

        driveBase = new DriveBase();
        ballPush = new BallPush();



        gyro.init();
        battery.init();
        lift.init();
        driveBase.init();
        ballBox.init();
        ballPush.init();
        //door.init();
        //grabber.init();
        //robot.init();

    }



    public static void update() {
;
            gyro.update();
//            robot.updatePoseEstimate();
            battery.update();
//            lift.updatePosition();


//            currentPose = robot.getPoseEstimate();

    }

    public static double getArmAcceleration() {
        return armAcceleration;
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }



    public static Pose2d getCurrentPose() {
        return currentPose;
    }

//    public static DriveSignal getCurrentDriveSignal() {
//        return robot.follower.update(currentPose);
//    }
//
//    public static List<Double> getWheelPositions() {
//        return robot.getWheelPositions();
//    }

    @NonNull
    public static String debugString() {
        return "=== Hardware Devices Readings ===\n" + "AH YES DEBUG";
    }

}

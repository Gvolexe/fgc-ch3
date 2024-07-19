package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Base;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Door;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Grabber;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.HolonicChasis;
import org.firstinspires.ftc.teamcode.hardware.sensors.Battery;
import org.firstinspires.ftc.teamcode.hardware.sensors.Gyro;
import org.firstinspires.ftc.teamcode.utilities.RingBuffer;

public class HardwareDevices {

    public static Gyro gyro;
//    public static MecanumChassis robot;
    public static Base robot;
    public static Door door;
    public static Grabber grabber;


    public static Battery battery;

    private static long currentTimeMillis;
    private static Pose2d currentPose;


    private static double armPreviousVelocity;
    private static double armAcceleration;




    static RingBuffer<Long> timeRing = new RingBuffer<>(3, System.currentTimeMillis());

    public static void init() {
        robot = new Base();
        door = new Door();
        grabber = new Grabber();


        battery = new Battery();
        gyro = new Gyro();



        gyro.init();
        battery.init();
        door.init();
        grabber.init();
        robot.init();

    }



    public static void update() {
;
            gyro.update();
//            robot.updatePoseEstimate();
            battery.update();


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

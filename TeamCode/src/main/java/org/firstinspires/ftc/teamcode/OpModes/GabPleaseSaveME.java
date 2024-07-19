package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.configuration.ConfigHardware;


@TeleOp(name="GabHelp", group="Pushbot")
public class GabPleaseSaveME extends LinearOpMode {
    float rotate_angle = 0;
    double reset_angle = 0;

    private DcMotor front_left_wheel = null;
    private DcMotor back_left_wheel = null;
    private DcMotor back_right_wheel = null;
    private DcMotor front_right_wheel = null;

    BHI260IMU imu;
    @Override
    public void runOpMode() {
        front_left_wheel = hardwareMap.dcMotor.get(ConfigHardware.front_left_motor_id);
        back_left_wheel = hardwareMap.dcMotor.get(ConfigHardware.back_left_motor_id);
        back_right_wheel = hardwareMap.dcMotor.get(ConfigHardware.back_right_motor_id);
        front_right_wheel = hardwareMap.dcMotor.get(ConfigHardware.front_right_motor_id);

        front_left_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        front_right_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

//        front_left_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        back_left_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        front_right_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        back_right_wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        imu = hardwareMap.get(BHI260IMU.class, "imu");

        BHI260IMU.Parameters parameters = new BHI260IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

//        parameters.mode                = BHI260IMU.SensorMode.IMU;
//        parameters.angleUnit           = BHI260IMU.AngleUnit.DEGREES;
//        parameters.accelUnit           = BHI260IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.loggingEnabled      = false;
        imu.initialize(parameters);

        while(!opModeIsActive()){}

        while(opModeIsActive()){
            drive();
            resetAngle();
            //driveSimple();
            telemetry.update();
        }
    }
    public void driveSimple(){
        double power = 1;
        if(gamepad1.dpad_down){ //Forward
            front_left_wheel.setPower(-power);
            back_left_wheel.setPower(-power);
            back_right_wheel.setPower(-power);
            front_right_wheel.setPower(-power);
        }
        else if(gamepad1.dpad_left){ //Left
            front_left_wheel.setPower(power);
            back_left_wheel.setPower(-power);
            back_right_wheel.setPower(power);
            front_right_wheel.setPower(-power);
        }
        else if(gamepad1.dpad_up){ //Back
            front_left_wheel.setPower(power);
            back_left_wheel.setPower(power);
            back_right_wheel.setPower(power);
            front_right_wheel.setPower(power);
        }
        else if(gamepad1.dpad_right){ //Right
            front_left_wheel.setPower(-power);
            back_left_wheel.setPower(power);
            back_right_wheel.setPower(-power);
            front_right_wheel.setPower(power);
        }
        else if(Math.abs(gamepad1.right_stick_x) > 0){ //Rotation
            front_left_wheel.setPower(-gamepad1.right_stick_x);
            back_left_wheel.setPower(-gamepad1.right_stick_x);
            back_right_wheel.setPower(gamepad1.right_stick_x);
            front_right_wheel.setPower(gamepad1.right_stick_x);
        }
        else{
            front_left_wheel.setPower(0);
            back_left_wheel.setPower(0);
            back_right_wheel.setPower(0);
            front_right_wheel.setPower(0);
        }
    }
    public void drive() {
        double Protate = gamepad1.right_stick_x/4;
        double stick_x = gamepad1.left_stick_x * Math.sqrt(Math.pow(1-Math.abs(Protate), 2)/2); //Accounts for Protate when limiting magnitude to be less than 1
        double stick_y = gamepad1.left_stick_y * Math.sqrt(Math.pow(1-Math.abs(Protate), 2)/2);
        double theta = 0;
        double Px = 0;
        double Py = 0;

        double gyroAngle = getHeading() * Math.PI / 180; //Converts gyroAngle into radians
        if (gyroAngle <= 0) {
            gyroAngle = gyroAngle + (Math.PI / 2);
        } else if (0 < gyroAngle && gyroAngle < Math.PI / 2) {
            gyroAngle = gyroAngle + (Math.PI / 2);
        } else if (Math.PI / 2 <= gyroAngle) {
            gyroAngle = gyroAngle - (3 * Math.PI / 2);
        }
        gyroAngle = -1 * gyroAngle;

        if(gamepad1.right_bumper){ //Disables gyro, sets to -Math.PI/2 so front is defined correctly.
            gyroAngle = -Math.PI/2;
        }

        //Linear directions in case you want to do straight lines.
        if(gamepad1.dpad_right){
            stick_x = 0.5;
        }
        else if(gamepad1.dpad_left){
            stick_x = -0.5;
        }
        if(gamepad1.dpad_up){
            stick_y = -0.5;
        }
        else if(gamepad1.dpad_down){
            stick_y = 0.5;
        }


        //MOVEMENT
        theta = Math.atan2(stick_y, stick_x) - gyroAngle - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        telemetry.addData("Stick_X", stick_x);
        telemetry.addData("Stick_Y", stick_y);
        telemetry.addData("Magnitude",  Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)));
        telemetry.addData("Front Left", Py - Protate);
        telemetry.addData("Back Left", Px - Protate);
        telemetry.addData("Back Right", Py + Protate);
        telemetry.addData("Front Right", Px + Protate);

        front_left_wheel.setPower(Py - Protate);
        back_left_wheel.setPower(Px - Protate);
        back_right_wheel.setPower(Py + Protate);
        front_right_wheel.setPower(Px + Protate);
    }
    public void resetAngle(){
        if(gamepad1.a){
            reset_angle = getHeading() + reset_angle;
        }
    }
    public double getHeading(){
        Orientation angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = angles.firstAngle;
        if(heading < -180) {
            heading = heading + 360;
        }
        else if(heading > 180){
            heading = heading - 360;
        }
        heading = heading - reset_angle;
        return heading;
    }
}

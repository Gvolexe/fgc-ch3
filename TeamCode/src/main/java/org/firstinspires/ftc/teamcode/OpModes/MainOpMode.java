package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Base;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Door;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Grabber;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.HolonicChasis;
import org.firstinspires.ftc.teamcode.utilities.Controller;
import org.firstinspires.ftc.teamcode.hardware.HardwareDevices;
import org.firstinspires.ftc.teamcode.utilities.SlewRateLimiter;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.setHardwareMap;
import static org.firstinspires.ftc.teamcode.utilities.Utilities.setTelemetry;
import static java.lang.Math.max;



@TeleOp(group = "TESTING" ,name="TEST OpMode")
public class MainOpMode extends OpMode {


    Controller driver, operator;
    Base robot;
    Door door;
    Grabber grabber;



    final SlewRateLimiter filterX = new SlewRateLimiter(0.03);
    final SlewRateLimiter filterY = new SlewRateLimiter(0.03);
    final SlewRateLimiter filterTurn = new SlewRateLimiter(0.03);
    double maxAcceleration = 0;

    public static final double POWER = 0.75;

    @Override
    public void init() {
        setHardwareMap(hardwareMap);
        setTelemetry(telemetry);

        HardwareDevices.init();


        robot = HardwareDevices.robot;
        grabber = HardwareDevices.grabber;
        door = HardwareDevices.door;




//        intake.init(HardwareDevices.colorSensor);
//        climb.init();


        driver = new Controller(gamepad1);
    }



    @Override
    public void loop() {
        maxAcceleration = max(maxAcceleration, HardwareDevices.getArmAcceleration());
        driver.update();  HardwareDevices.update(); telemetry.update();
//        currentPose = robot.getPoseEstimate();

        double drive = filterX.calculate(driver.leftStick.Y());

        double turn = filterX.calculate(-driver.rightStick.X());
        boolean trigger = driver.rightTrigger.toggle();
        boolean sqare = driver.square.toggle();

        robot.drive(drive,turn,trigger ? .4 : .7);
//        grabber.grabberController(trigger);
        door.coloseDoor(sqare);
        telemetry.addData("LDATA -->",robot.getDataL());
        telemetry.addData("RDATA -->",robot.getDataR());



}
}
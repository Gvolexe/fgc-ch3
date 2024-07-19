package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.BallBox;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.BallPush;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.DriveBase;
import org.firstinspires.ftc.teamcode.hardware.hardwareDevices.Lift;
import org.firstinspires.ftc.teamcode.utilities.Controller;
import org.firstinspires.ftc.teamcode.hardware.HardwareDevices;
import org.firstinspires.ftc.teamcode.utilities.SlewRateLimiter;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.setHardwareMap;
import static org.firstinspires.ftc.teamcode.utilities.Utilities.setTelemetry;
import static java.lang.Math.max;



@TeleOp(group = "TESTING" ,name="TEST OpMode")
public class MainOpMode extends OpMode {


    Controller driver, operator;

    Lift lift;
    BallBox ballBox;
    BallPush ballPush;

    DriveBase driveBase;


    final SlewRateLimiter filterX = new SlewRateLimiter(0.03);
    final SlewRateLimiter filterY = new SlewRateLimiter(0.03);
    //final SlewRateLimiter filterTurn = new SlewRateLimiter(0.03);
    double maxAcceleration = 0;

    public static final double POWER = 0.6;

    @Override
    public void init() {
        setHardwareMap(hardwareMap);
        setTelemetry(telemetry);

        HardwareDevices.init();

        lift = HardwareDevices.lift;

        driveBase = HardwareDevices.driveBase;
        ballBox = HardwareDevices.ballBox;
        ballPush = HardwareDevices.ballPush;


//        intake.init(HardwareDevices.colorSensor);
//        climb.init();


        driver = new Controller(gamepad1);
    }



    @Override
    public void loop() {
        maxAcceleration = max(maxAcceleration, HardwareDevices.getArmAcceleration());
        driver.update();  HardwareDevices.update(); telemetry.update();
//        currentPose = robot.getPoseEstimate();

        double drive = filterY.calculate(driver.leftStick.Y());
        double turn = filterX.calculate(-driver.rightStick.X());

        boolean liftDown = driver.down.hold();
        boolean liftLow = driver.right.hold();
        boolean liftMed = driver.left.hold();
        boolean liftHigh = driver.up.hold();
        boolean ball = driver.square.toggle();
        boolean isBallPushed = driver.circle.toggle();
        boolean higher = driver.leftBumper.hold();
        boolean lower = driver.rightBumper.hold();

//        if (liftDown){
//            lift.newState(Lift.SlidesState.DOWN);
//        } else if (liftLow){
//            lift.newState(Lift.SlidesState.SCORE_LOW);
//        } else if(liftMed){
//            lift.newState(Lift.SlidesState.SCORE_MED);
//        } else if (liftHigh){
//            lift.newState(Lift.SlidesState.SCORE_HIGH);
//        }
        telemetry.addData("Lift" ,lift.getPosition());
        telemetry.addData("State", lift.getCurrentLiftState());
        telemetry.addData("Down", Lift.liftDown());
        ballBox.isDroped(ball);
        ballPush.isPushed(isBallPushed);


        driveBase.drive(drive, turn, 0.6);

        if (higher) {
            lift.setPower(POWER);
        }
        else if (lower){
            lift.setPower(-POWER);
        }
        else {
            lift.setPower(0);
        }


//        lift.sliderStateMachine();
    }
}
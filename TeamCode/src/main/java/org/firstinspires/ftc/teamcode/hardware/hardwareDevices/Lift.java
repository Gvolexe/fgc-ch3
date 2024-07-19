package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;
import static org.firstinspires.ftc.teamcode.utilities.Utilities.telemetry;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.configuration.ConfigHardware;
import org.firstinspires.ftc.teamcode.configuration.ConfigSlider;
import org.firstinspires.ftc.teamcode.utilities.Controller;
import org.firstinspires.ftc.teamcode.utilities.PID;

public class Lift {
    private final DcMotorEx leftMotor = hardwareMap.get(DcMotorEx.class, ConfigHardware.sliderLeftMotorId);
    private final DcMotorEx rightMotor = hardwareMap.get(DcMotorEx.class, ConfigHardware.sliderRightMotorId);
    private final TouchSensor lowerIndicatorSensor = hardwareMap.get(TouchSensor.class, ConfigHardware.magneticLimitSwichId);
    private static Controller.Button lowerIndicator;
    double currentPos = 0;
    final double[] possitionEdge = {0,100};
    private static final double ENCODER_TICKS_PER_REVOLUTION = 288;
    private static final double SPROCKET_RADIUS = 3.35; //centimeters
    private static final double METERS_PER_TICKS = 2 * PI * SPROCKET_RADIUS / ENCODER_TICKS_PER_REVOLUTION;
    private static PID liftPID;
    private double previousLeft = 0, previousRight = 0;
    private double currentLiftPos = 0;
    public double staticFriction = 0;
    public double resetPower = 0.3;
    private boolean reset = false;
    public double target = 0;


    public SlidesState currentLiftState = SlidesState.DOWN;


    public enum SlidesState {
        DOWN(0), SCORE_LOW(25),  SCORE_MED(50), SCORE_HIGH(75);
        final double CM;

        SlidesState(double position) {
            this.CM = position;
        }
    }

    public void init() {
        liftPID = new PID(ConfigSlider.KP, ConfigSlider.KI, ConfigSlider.KD);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        stopAndResetEncoders();

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        lowerIndicator = new Controller.Button();
        currentLiftPos *= 0;

        enableBrakes(true);
        telemetry.addData("State: ", "Initialized");
    }
    public void setPower(double power){
        if(abs(power) < 0.05) power = 0;
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }



    public void setTarget(double target) {
        this.target = target;
    }

    public void seekTarget() {
        gotoTarget(target);
    }

    public void gotoTarget(double target) {
        double error = target - currentLiftPos;
        double result = liftPID.update(error);

        double sign = Math.signum(error);

        double power = sign * staticFriction + result;

        setPower(power);
    }
    public static boolean liftDown() {
        return lowerIndicator.hold();
    }
    public void stopAndResetEncoders() {
        currentLiftPos *= 0;
        setTarget(currentLiftPos);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    public void enableBrakes(boolean on) {
        leftMotor.setZeroPowerBehavior(on ? DcMotor.ZeroPowerBehavior.BRAKE : DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setZeroPowerBehavior(on ? DcMotor.ZeroPowerBehavior.BRAKE : DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void runWithoutEncoders() {
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void updatePosition() {
        double currentPositionLeft = leftMotor.getCurrentPosition();
        double currentPositionRight = rightMotor.getCurrentPosition();

        int deltaTicksLeft = (int) (currentPositionLeft - previousLeft);
        int deltaTicksRight = (int) (currentPositionRight - previousRight);

        // Calculate the absolute value of the average delta ticks
        int deltaTicksAbs = Math.abs((deltaTicksLeft + deltaTicksRight) / 2);

        double deltaY = deltaTicksAbs * METERS_PER_TICKS;

        // Update position only if the lift is not down
        if (!liftDown()) {
            currentLiftPos += deltaY;
        } else {
            currentLiftPos = 0;
        }

        previousLeft = currentPositionLeft;
        previousRight = currentPositionRight;
    }


    public void sliderStateMachine() {
        lowerIndicator.update(lowerIndicatorSensor.isPressed());
        updatePosition();

        if (reset) {
            reset = false;
            runWithoutEncoders();
        }

        switch (currentLiftState) {
            case SCORE_LOW:
                gotoTarget(SlidesState.SCORE_LOW.CM);
                break;

            case SCORE_MED:
                gotoTarget(SlidesState.SCORE_MED.CM);
                break;

            case SCORE_HIGH:
                gotoTarget(SlidesState.SCORE_HIGH.CM);
                break;

            case DOWN:
                if (!liftDown()) {
                    setPower(resetPower);
                } else {
                    setPower(0);
                    if (!reset) {
                        reset = true;
                        stopAndResetEncoders();
                    }
                }
                break;
        }
    }

    public double getPosition(){
        return currentLiftPos;
    }

    public SlidesState getCurrentLiftState(){
        return currentLiftState;
    }
    public void newState(SlidesState newState) {
        currentLiftState = newState;
    }
}

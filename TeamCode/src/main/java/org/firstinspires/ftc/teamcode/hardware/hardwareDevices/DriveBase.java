package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DriveBase {
    DcMotor left = hardwareMap.get(DcMotor.class,"left");
    DcMotor right = hardwareMap.get(DcMotor.class, "right");

    public void init(){
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double lp , double rp){
        left.setPower(lp);
        right.setPower(rp);
    }
    public void setPower(double p){
        setPower(p,p);
    }

    public int getDataL(){
        return left.getCurrentPosition();
    }
    public int getDataR(){
        return right.getCurrentPosition();
    }

    public void drive (double drive , double turn , double power){
        double denominator = Math.max(Math.abs(drive) + Math.abs(turn), 1);
        double leftPower = (drive + turn) * power;
        double rightPower = (drive - turn) * power;

        leftPower /= denominator;
        rightPower /= denominator;

        double maxPower = Math.abs(Math.max(Math.abs(leftPower),Math.abs(rightPower)));


        if (maxPower < 0.005) {
            leftPower  = rightPower = 0;
        }


        setPower(leftPower,rightPower);
    }
}

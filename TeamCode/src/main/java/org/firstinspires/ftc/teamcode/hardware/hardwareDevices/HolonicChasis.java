package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.configuration.ConfigHardware;
import org.firstinspires.ftc.teamcode.hardware.HardwareDevices;

public class HolonicChasis {
    private final DcMotorEx frontLeftMotor = hardwareMap.get(DcMotorEx.class , ConfigHardware.front_left_motor_id);
    private final DcMotorEx frontRightMotor = hardwareMap.get(DcMotorEx.class, ConfigHardware.front_right_motor_id);
    private final DcMotorEx backLeftMotor = hardwareMap.get(DcMotorEx.class, ConfigHardware.back_left_motor_id);
    private final DcMotorEx backRightMotor = hardwareMap.get(DcMotorEx.class , ConfigHardware.back_right_motor_id);


    public void init(){
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDriveBasePowers(double drive, double strafe, double turn, double power) {
        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        double flPower = (drive - strafe - turn) * power;
        double frPower = (drive + strafe + turn) * power;
        double blPower = (drive + strafe - turn) * power;
        double brPower = (drive - strafe + turn) * power;

        flPower /= denominator;
        blPower /= denominator;
        brPower /= denominator;
        frPower /= denominator;

        double maxPower = Math.abs(
                Math.max(
                        Math.max(
                                Math.abs(flPower), Math.abs(frPower)
                        )
                        , Math.max(
                                Math.abs(blPower), Math.abs(brPower)
                        )
                )
        );
        if (maxPower < 0.005) {
            flPower = blPower = frPower = brPower = 0;
        }
        setPower(flPower, frPower, blPower, brPower);
    }


    public void setPowerFieldCentric(double X, double Y, double rX, double vel) {

        double botHeading = HardwareDevices.gyro.IMUAngleRads();
        double rotX = X * Math.cos(-botHeading) - Y * Math.sin(-botHeading);
//?        rotX = rotX * 1.1; //! imperfect Strafing counter don't know if we need it too scared to delete
        double rotY = X * Math.sin(-botHeading) + Y * Math.cos(-botHeading);

        setDriveBasePowers(rotX, rotY, rX, vel);
    }

    public void setPower(double power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public void setPower(double flPower,double frPower,double blPower,double brPower) {
        frontLeftMotor.setPower(flPower);
        frontRightMotor.setPower(frPower);
        backLeftMotor.setPower(blPower);
        backRightMotor.setPower(brPower);
    }

    public double[] getPower() {
        return new double[]{frontLeftMotor.getPower(),frontRightMotor.getPower(),backLeftMotor.getPower() , backRightMotor.getPower()};
    }

    public double getPowerAvg() {
        return (frontLeftMotor.getPower() + frontRightMotor.getPower()+ backLeftMotor.getPower() + backRightMotor.getPower())/4;
    }
}

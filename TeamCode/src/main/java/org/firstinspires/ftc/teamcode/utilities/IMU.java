package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

public class  IMU {
    private final BHI260IMU imu;
    private Double previousAngle;
    private double deltaAngle;

    public IMU(String deviceName) {

        imu = hardwareMap.get(BHI260IMU.class, deviceName);
        BHI260IMU.Parameters parameters = new BHI260IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP , RevHubOrientationOnRobot.UsbFacingDirection.RIGHT));
        imu.initialize(parameters);

        previousAngle = null;
        deltaAngle = 0;
    }

    /**
     * @return the wrapped angle
     */
    public double getAngle() {
        // Get the current angle
        Orientation angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double currentAngle = angles.firstAngle;

        // Update how many times we have wrapped
        deltaAngle = updateWraps(previousAngle, currentAngle, deltaAngle);

        // Update the previous angle
        previousAngle = currentAngle;
//        System.out.println(currentAngle + deltaAngle);
        return currentAngle + deltaAngle;
    }

    /**
     * @param previousAngle {double}
     * @param currentAngle {double}
     * @param deltaAngle {double}
     * @return {double} the value to add to the currentAngle to get the currentWrappedAngle
     */
    static double updateWraps(Double previousAngle, double currentAngle, double deltaAngle) {
        if (previousAngle != null){
            if (currentAngle - previousAngle >= 180){
                deltaAngle -= 360;
            }
            else if (currentAngle - previousAngle <= -180){
                deltaAngle += 360;
            }
        }
        return deltaAngle;
    }

    public double getRawAngle() {

        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
}
package org.firstinspires.ftc.teamcode.hardware.sensors;

import static org.firstinspires.ftc.teamcode.configuration.ConfigHardware.imuSensor;

import org.firstinspires.ftc.teamcode.utilities.IMU;

public class Gyro {
    private IMU imu;
    private double datum;
    private double imuAngle = 0;
    private double rawAngle = 0;
    private double rateOfChange = 0;
    private long previousTime = 0;
    //TODO: config
    public void init() {
        imu = new IMU(imuSensor);
    }

    public void update() {
        imuAngle = imu.getAngle() - datum;
        rawAngle = imu.getRawAngle();

        long currentTime = System.currentTimeMillis();
        long deltaMili = currentTime - previousTime;
        double deltaSeconds = deltaMili / 1000.0;

        double currentAngle = imuAngle;
        double deltaAngle = currentAngle - previousTime;


        rateOfChange = deltaAngle/deltaSeconds;
        previousTime = currentTime;
    }

    public void setImu(IMU imu) {
        this.imu = imu;
    }

    public void setDatum(double datum) {
        this.datum = datum;
    }

    public void reset() {
        setDatum(imu.getAngle());
    }

    public double rawAngle() {
        return rawAngle;
    }

    public double IMUAngle() {
        return imuAngle;
    }
    public double IMUAngleRads(){
        return Math.toRadians(imuAngle);
    }

    public double getRateOfChange() {
        return rateOfChange;
    }
}

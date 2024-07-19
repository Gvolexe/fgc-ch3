package org.firstinspires.ftc.teamcode.utilities;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.telemetry;

import com.acmerobotics.roadrunner.control.PIDCoefficients;

/** @noinspection unused*/
public class PID {
    public double integralSum = 0;
    private final double proportional;
    private final double integral;
    private final double derivative;
    private double fComponent;
    private double result = 0;

    private final double integralLength;
    private final RingBuffer<Double> integralBuffer;
    private final RingBuffer<Double> derivativeBuffer;
    private final RingBuffer<Double> timeBuffer;

    private final boolean debugMode;


    public PID(double proportional, double integral, double derivative) {
        this(proportional, integral, derivative, 0, 0, false);
    }

    public PID(double proportional, double integral, double derivative, double fComponent) {
        this(proportional, integral, derivative, fComponent, 0, false);
    }

    public PID(double proportional, double integral, double derivative, int integralLength) {
        this(proportional, integral, derivative, 0, integralLength, false);
    }

    public PID(double proportional, double integral, double derivative, boolean debugMode) {
        this(proportional, integral, derivative, 0, 0, debugMode);
    }

    public PID(double proportional, double integral, double derivative, double fComponent, int integralLength) {
        this(proportional, integral, derivative, fComponent, integralLength, false);
    }

    public PID(double proportional, double integral, double derivative, int integralLength, boolean debugMode) {
        this(proportional, integral, derivative, 0, integralLength, debugMode);
    }

    public PID(double proportional, double integral, double derivative, double fComponent, boolean debugMode) {
        this(proportional, integral, derivative, fComponent, 0, debugMode);
    }

    public PID(PIDCoefficients coefficients, int integralLength) {
        this(coefficients.kP, coefficients.kI, coefficients.kD, integralLength);
    }

    public PID(PIDCoefficients coefficients, int integralLength, boolean debug) {
        this(coefficients.kP, coefficients.kI, coefficients.kD, integralLength, debug);
    }
    public PID(double proportional, double integral, double derivative, double fComponent, int integralLength, boolean debugMode) {
        this.proportional = proportional;
        this.integral = integral;
        this.derivative = derivative;
        this.fComponent = fComponent;
        this.debugMode = debugMode;
        this.integralLength = integralLength;
        derivativeBuffer = new RingBuffer<>(3, 0.0);
        timeBuffer = new RingBuffer<>(3, (double) System.currentTimeMillis());
        integralBuffer = new RingBuffer<>(integralLength, 0.0);
    }

    public double update(double error) {
        double currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - timeBuffer.getValue(currentTime)) ;

        integralSum += error * (deltaTime / 100000);
        if (integralLength != 0) {
            integralSum -= integralBuffer.getValue(error * deltaTime);
        }

        double deltaError = error - derivativeBuffer.getValue(error);
        double rateOfChange = (deltaError / deltaTime) / 3;


        double pComponent = error * proportional;
        double iComponent = integralSum * integral;
        double dComponent = (rateOfChange * derivative);


        if (debugMode) {
            telemetry.addData("Proportional", pComponent);
            telemetry.addData("Integral", iComponent);
            telemetry.addData("Derivative", dComponent);
        }

        this.result = pComponent + iComponent + dComponent + fComponent;
        return result;
    }

    public double getResult() {
        return result;
    }

    public void setFComponent(double fComponent) {
        this.fComponent = fComponent;
    }

    public void resetIntegralSum() {
        integralSum = 0;
    }

    public void setIntegralSum(double integralSum) {
        this.integralSum = integralSum;
    }
}

package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Math.floorMod;

public class MathUtils {

    public static double sigmoid(double rawNumber, double range, double outputRange) {
        return (outputRange / (1 + Math.pow(range, -rawNumber))) - outputRange / 2;
    }
    public static double floorModDouble(double dividend, double divisor) {
        return floorMod(Math.round(dividend * 1e6), Math.round(divisor * 1e6)) / 1e6;
    }

    public static double floorModDouble(double dividend, int divisor) {
        return floorMod(Math.round(dividend * 1e6), divisor) / 1e6;
    }

    public static double floorModDouble(int dividend, double divisor) {
        return floorMod(dividend, Math.round(divisor * 1e6)) / 1e6;

    }

    public static double shiftX(double x, double y, double theta) {
        return (x * Math.cos(Math.toRadians(theta)) - (y * Math.sin(Math.toRadians(theta))));
    }

    public static double shiftY(double x, double y, double theta) {
        return (x * Math.sin(Math.toRadians(theta)) + (y * Math.cos(Math.toRadians(theta))));
    }

}

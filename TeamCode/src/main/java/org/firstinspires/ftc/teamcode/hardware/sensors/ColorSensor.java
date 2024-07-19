package org.firstinspires.ftc.teamcode.hardware.sensors;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.configuration.ConfigColorSensor;
import org.firstinspires.ftc.teamcode.configuration.ConfigHardware;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;


public class ColorSensor {


    public enum PixelContainerFullnessState {
        LEFT, RIGHT, NONE, BOTH;
    }
    private int previousPixels = 0;
    public boolean pixelIntakeSucsess = false;

    public PixelContainerFullnessState currentFullnessState = PixelContainerFullnessState.NONE;
    private final ColorRangeSensor colorSensor1 = hardwareMap.get(ColorRangeSensor.class, ConfigHardware.colorSensor1);
    private final ColorRangeSensor colorSensor2 = hardwareMap.get(ColorRangeSensor.class, ConfigHardware.colorSensor2);

    public void update() {
        boolean isPixel1Present = isPixelPresent1();
        boolean isPixel2Present = isPixelPresent2();

        if (isPixel1Present && isPixel2Present) {
            newState(PixelContainerFullnessState.BOTH);
        } else if (isPixel1Present) {
            newState(PixelContainerFullnessState.LEFT);
        } else if (isPixel2Present) {
            newState(PixelContainerFullnessState.RIGHT);
        } else {
            newState(PixelContainerFullnessState.NONE);
        }

        pixelIntakeSucsess = false;
//                = ((isPixel1Present ? 1 : 0) + (isPixel2Present ? 1 : 0)) > previousPixels;
    }


    public double getDistance1() {
        return colorSensor1.getDistance(DistanceUnit.CM);
    }

    public double getDistance2() {
        return colorSensor2.getDistance(DistanceUnit.CM);
    }

    public boolean isPixelPresent1() {
        double distance1 = getDistance1();
        return distance1 < ConfigColorSensor.detectionThresshold;
    }

    public boolean isPixelPresent2() {
        double distance2 = getDistance2();
        return distance2 < ConfigColorSensor.detectionThresshold;
    }

    public boolean isBothPixelPresent() {
        return isPixelPresent1() && isPixelPresent2();
    }




    public void newState(PixelContainerFullnessState state) {
        currentFullnessState = state;
    }

}

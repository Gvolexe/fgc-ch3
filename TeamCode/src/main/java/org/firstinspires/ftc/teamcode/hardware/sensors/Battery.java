package org.firstinspires.ftc.teamcode.hardware.sensors;

import com.qualcomm.robotcore.hardware.VoltageSensor;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

public class Battery {
    private VoltageSensor voltageSensor;
    private double voltage;

    public void init() {
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
    }

    public void update() {
        voltage = voltageSensor.getVoltage();
    }

    public double getVoltage() {
        return voltage;
    }
}

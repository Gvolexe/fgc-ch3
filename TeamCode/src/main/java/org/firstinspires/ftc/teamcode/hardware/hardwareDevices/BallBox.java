package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class BallBox {
    Servo servo = hardwareMap.get(Servo.class,"ball");
    double droppedPos = 1;
    double upPos = 0.1;

    public void init() {
        isDroped(false);
    }
    public void isDroped(boolean dropped) {
        servo.setPosition(dropped ? droppedPos : upPos);
    }
}

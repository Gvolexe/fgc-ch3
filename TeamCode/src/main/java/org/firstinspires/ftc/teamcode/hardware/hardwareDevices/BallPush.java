package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class BallPush {
    Servo servo = hardwareMap.get(Servo.class,"push");
    double droppedPos = 1;
    double upPos = 0;

    public void init() {
        isPushed(false);
    }
    public void isPushed(boolean dropped) {
        servo.setPosition(dropped ? droppedPos : upPos);
    }
}

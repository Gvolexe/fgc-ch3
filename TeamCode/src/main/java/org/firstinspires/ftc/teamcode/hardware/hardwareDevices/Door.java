package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class Door {
    Servo servo = hardwareMap.get(Servo.class, "servoDoor");
    double servoClosedPos = 0.4;
    double servoOpenPos = 0.75;

    public void init(){
        servo.setDirection(Servo.Direction.REVERSE);

    }


    public void coloseDoor(boolean isClosed) {
        servo.setPosition(isClosed ? servoClosedPos : servoOpenPos);
    }
}

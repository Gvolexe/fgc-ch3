package org.firstinspires.ftc.teamcode.hardware.hardwareDevices;

import static org.firstinspires.ftc.teamcode.utilities.Utilities.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    Servo grabberLeft = hardwareMap.get(Servo.class, "grabLeft");
    Servo grabberRight = hardwareMap.get(Servo.class, "grabRight");
    double[] leftPos = {0,1};
    double[] rightPos = {0,1};


    public void init(){
        grabberRight.setDirection(Servo.Direction.REVERSE);
    }
    public void grabberController (boolean isOppen) {
        grabberLeft.setPosition(isOppen ? leftPos[0] : leftPos[1]);
        grabberRight.setPosition(isOppen ? rightPos[0] : rightPos[1]);
    }


}

package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.configuration.ConfigGamepad;

import java.util.ArrayList;
import java.util.Comparator;

/** @noinspection ALL*/
public class Controller {

    private final Gamepad gamepad;
    public final RumbleManager rumbleManager;

    public final Thumbstick rightStick;
    public final Thumbstick leftStick;
    public final Button cross;
    public final Button circle;
    public final Button triangle;
    public final Button square;
    public final Button up;
    public final Button down;
    public final Button left;
    public final Button right;
    public final Button rightBumper;
    public final Button leftBumper;
    public final Button rightPush;
    public final Button leftPush;
    public final Button share;
    public final Button touchpad;
    public final Trigger rightTrigger;
    public final Trigger leftTrigger;

    boolean hold = false;

    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        rightStick = new Thumbstick(); leftStick = new Thumbstick();
        rumbleManager = new RumbleManager();

        cross = new Button(); circle = new Button(); triangle = new Button(); square = new Button();
        up = new Button(); down = new Button(); left = new Button(); right = new Button();
        rightBumper = new Button(); leftBumper = new Button(); rightPush = new Button(); leftPush = new Button();
        share = new Button(); touchpad = new Button();

        leftTrigger = new Trigger(); rightTrigger = new Trigger();
    }

    public void update() {
        rightStick.update(gamepad.right_stick_x, gamepad.right_stick_y); leftStick.update(gamepad.left_stick_x, gamepad.left_stick_y);
        rumbleManager.applyQueuePriority();
        cross.update(gamepad.a); circle.update(gamepad.b); triangle.update(gamepad.y); square.update(gamepad.x);
        up.update(gamepad.dpad_up); down.update(gamepad.dpad_down); left.update(gamepad.dpad_left); right.update(gamepad.dpad_right);
        rightBumper.update(gamepad.right_bumper); leftBumper.update(gamepad.left_bumper); rightPush.update(gamepad.right_stick_button); leftPush.update(gamepad.left_stick_button);
        share.update(gamepad.share);

        rightTrigger.update(gamepad.right_trigger); leftTrigger.update(gamepad.left_trigger);
    }

    /** @noinspection InnerClassMayBeStatic*/
    public static class Button {
        private boolean hold = false; private boolean press = false; private boolean toggle = false;

        public void update(boolean button) {
            boolean wasHeld = hold;
            hold = button;
            press = button && !wasHeld;
        }

        public boolean hold() {
            return hold;
        }

        public boolean press() {
            return press;
        }

        public boolean toggle() {
            if (press()) {
                toggle = !toggle;
                press = false;
            }
            return (toggle);
        }
    }

    public class Trigger {
        private final Button trigger = new Button();
        private float value;

        private void update(float value) {
            this.value = value;
            trigger.update(hold());
        }

        public float value(){
            return value;
        }

        public boolean hold() {
            return value > .7;
        }

        public boolean press() {
            return trigger.press();
        }

        public boolean toggle() {
            return trigger.toggle();
        }

        public double range(double pressed, double released) {
            double range = pressed - released;
            return (value() * range) + released;
        }

    }


    public class Thumbstick {
        private double rawX, rawY, shiftedX, shiftedY;

        private void update(Float x, Float y) { rawX = x; rawY = y; }

        public double X() { return rawX * -1; }

        public double Y() { return rawY * -1; }


        public void setShift(double shiftAngle) {
            this.shiftedX = (this.rawX * Math.cos(Math.toRadians(shiftAngle))) - (this.rawY * Math.sin(Math.toRadians(shiftAngle)));
            this.shiftedY = (this.rawX * Math.sin(Math.toRadians(shiftAngle))) + (this.rawY * Math.cos(Math.toRadians(shiftAngle)));
        }

        public double shiftedX() {
            return shiftedX * -1;
        }

        public double shiftedY() {
            return shiftedY * -1;
        }


        public double getAngle() {
            return ((270 - (Math.atan2(0 - Y(), 0 - X())) * 180 / Math.PI) % 360);
        }
    }

    public abstract class Notification {
        final Gamepad.RumbleEffect rumbleEffect;

        public Notification(String sequenceString, int repeat) throws ArithmeticException {
            //TODO: Add a W<wait time> parameter to the sequence string and utilize it with the RumbleBuilder.wait() method
            int sequenceDuration = 0;

            String rumbleEffectChangeDelay = ConfigGamepad.rumbleEffectChangeDelay;
            Gamepad.RumbleEffect.Builder rumbleBuilder = new Gamepad.RumbleEffect.Builder();
            //TODO: reference from correct config class
            String[] segmentsString = ("0/0/"+ rumbleEffectChangeDelay+",").concat(sequenceString).split(","); // ["1/1/100", "0/0/100", "1/0/100"]
            int[][] sequence = new int[segmentsString.length][3];


            for (int i = 0; i < segmentsString.length; i++) {
                String[] segmentParamsString = segmentsString[i].split("/"); // ["1", "1", "100"]




                for (int j = 0; j < segmentParamsString.length; j++) {
                    sequence[i][j] = Integer.parseInt(segmentParamsString[j]); // [1, 1, 100]


                }
                sequenceDuration += sequence[1][i];

            }

            if (repeat == 0) {
                repeat = 150 / sequenceDuration + 1;
            }

            for (int i = 0; i < repeat; i++) {
                for (int[] segment : sequence) {

                    rumbleBuilder.addStep(segment[0], segment[1], segment[2]);
                    rumbleBuilder.build();

                }

            }
            rumbleEffect = rumbleBuilder.build();
            rumbleBuilder.build();
        }
    }

    public class ContinuousNotification extends Notification {
        public int id;
        public ContinuousNotification(int id, String sequenceString, int repeat) throws ArithmeticException {
            super(sequenceString, repeat);
            if (id < 0) {
                throw new ArithmeticException("id cannot a negative number");
            }
            this.id = id;
        }
    }

    public class OneTimeNotification extends Notification {
        public OneTimeNotification(String sequenceString, int repeat) throws ArithmeticException {
            super(sequenceString, repeat);
        }
    }

    public class RumbleManager {
        private final ArrayList<ContinuousNotification> queue = new ArrayList<>();

        private final ArrayList<OneTimeNotification> otnQueue = new ArrayList<>();

        public void enable(ContinuousNotification notification) {
            queue.add(notification);
            queue.sort(Comparator.comparingInt(ContinuousNotification -> ContinuousNotification.id));
        }

        public void disable(ContinuousNotification notification) {
            queue.remove(notification);
        }

        public void add(OneTimeNotification notification) {
            otnQueue.add(notification);
        }

        public void remove(OneTimeNotification notification) {
            otnQueue.remove(notification);
        }

        public void applyQueuePriority() {
            if (otnQueue.isEmpty()) {
                if (!queue.isEmpty() && !gamepad.isRumbling()) {
                    gamepad.runRumbleEffect(queue.get(0).rumbleEffect);
                    queue.remove(0);
                }
            } else if (!gamepad.isRumbling()) {
                gamepad.runRumbleEffect(otnQueue.get(0).rumbleEffect);
                otnQueue.remove(0);

            }
        }
    }
}
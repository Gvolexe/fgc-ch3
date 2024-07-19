package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

/** @noinspection unused*/
public class Utilities {
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;

    private static final double DEFAULT_RESOLUTION = 2.0;
    public static final double ROBOT_RADIUS = 9;
    public static void setHardwareMap(HardwareMap hardwareMap) {
        Utilities.hardwareMap = hardwareMap;
    }

    public static void setTelemetry(Telemetry telemetry) {
        Utilities.telemetry = telemetry;
    }

    public static void drawPoseHistory(Canvas canvas, List<Pose2d> poseHistory) {
        double[] xPoints = new double[poseHistory.size()];
        double[] yPoints = new double[poseHistory.size()];
        for (int i = 0; i < poseHistory.size(); i++) {
            Pose2d pose = poseHistory.get(i);
            xPoints[i] = pose.getX();
            yPoints[i] = pose.getY();
        }
        canvas.strokePolyline(xPoints, yPoints);
    }

    public static void drawSampledPath(Canvas canvas, Path path, double resolution) {
        int samples = (int) Math.ceil(path.length() / resolution);
        double[] xPoints = new double[samples];
        double[] yPoints = new double[samples];
        double dx = path.length() / (samples - 1);
        for (int i = 0; i < samples; i++) {
            double displacement = i * dx;
            Pose2d pose = path.get(displacement);
            xPoints[i] = pose.getX();
            yPoints[i] = pose.getY();
        }
        canvas.strokePolyline(xPoints, yPoints);
    }

    public static void drawSampledPath(Canvas canvas, Path path) {
        drawSampledPath(canvas, path, DEFAULT_RESOLUTION);
    }

    public static void drawRobot(Canvas canvas, Pose2d pose) {
        canvas.setStroke("green");
        canvas.strokeCircle(pose.getX(), pose.getY(), ROBOT_RADIUS);
        Vector2d v = pose.headingVec().times(ROBOT_RADIUS);
        double x1 = pose.getX() + v.getX() / 2, y1 = pose.getY() + v.getY() / 2;
        double x2 = pose.getX() + v.getX(), y2 = pose.getY() + v.getY();
        canvas.strokeLine(x1, y1, x2, y2);
    }
    public boolean isWithinBounds(Pose2d pos, Pose2d lowerBound, Pose2d upperBound){
        double targetX = pos.getX();
        double targetY = pos.getY();
        double lowerX = lowerBound.getX();
        double lowerY = lowerBound.getY();
        double upperX = upperBound.getX();
        double upperY = upperBound.getY();

        boolean withinXBounds = lowerX <= targetX && targetX <= upperX;
        boolean withinYBounds = lowerY <= targetY && targetY <= upperY;

        return withinXBounds && withinYBounds;
    }
}

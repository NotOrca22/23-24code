package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.Locale;

@Autonomous(name="Red Far")
public class RedLeftAuto extends LinearOpMode {
    private VisionPortal portal;
    private RedPropSearchThreshold redPropSearchThreshold;
    private boolean captured = false;

    @Override
    public void runOpMode() throws InterruptedException {
        redPropSearchThreshold = new RedPropSearchThreshold();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(redPropSearchThreshold)
                .build();




        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.05)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.74);
                })
                .back(28)
                .turn(Math.toRadians(90*178/180))
                .back(0.5)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.45)
                .addTemporalMarker(() -> {
                    drive.stoptake();
                })
                .forward(2.5)
//                .strafeLeft(22.5)
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.95);
//                })
//                .addTemporalMarker(() -> {
//                    drive.intake();
//                })
//                .waitSeconds(0.25)
//                .back(24.74)
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.88);
//                })
//                .waitSeconds(0.5)
//
//                .forward(99)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
//                .waitSeconds(0.1)
//                .addTemporalMarker(() -> {
//                    drive.boxOut();
//                })
//                .strafeRight(22.75)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(950);
//                })
//                .waitSeconds(0.4)
//                .forward(11.75)
//                .waitSeconds(0.2)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(1300);
//                })
//                .waitSeconds(0.2)
//                .back(4)
//                .addTemporalMarker(() -> {
//                    drive.boxIn();
//                })
//                .waitSeconds(0.1)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(0);
//                })
//                .waitSeconds(0.2)
//                .strafeRight(29.25)
//                .forward(2.5)
                .build();
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .back(27.5)
//                .turn(Math.toRadians(180))
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.45)
                .addTemporalMarker(() -> {
                    drive.stoptake();
                })
                .forward(4)
//                .turn(Math.toRadians(-90))
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.95);
//                })
//                .addTemporalMarker(() -> {
//                    drive.intake();
//                })
//                .waitSeconds(0.25)
//                .back(20)
//                .strafeRight(1)
//                .back(5.5)
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.88);
//                })
//                .waitSeconds(0.5)
//
//                .forward(99)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
//                .waitSeconds(0.1)
//                .addTemporalMarker(() -> {
//                    drive.boxOut();
//                })
//                .strafeRight(22.75)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(950);
//                })
//                .waitSeconds(0.4)
//                .forward(11.75)
//                .waitSeconds(0.2)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(1300);
//                })
//                .waitSeconds(0.2)
//                .back(4)
//                .addTemporalMarker(() -> {
//                    drive.boxIn();
//                })
//                .waitSeconds(0.1)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(0);
//                })
//                .waitSeconds(0.2)
//                .strafeRight(29.25)
//                .forward(2.5)
                .build();


//
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.05)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.74);
                })
                .back(28)
                .turn(Math.toRadians(-90*180/180))
                .back(1)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.45)
                .addTemporalMarker(() -> {
                    drive.stoptake();
                })
                .forward(1.5)
//                .strafeRight(22.5)
//                .turn(Math.toRadians(180*179/180))
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.95);
//                })
//                .addTemporalMarker(() -> {
//                    drive.intake();
//                })
//                .waitSeconds(0.25)
//                .back(24.74)
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.88);
//                })
//                .waitSeconds(0.5)
//
//                .forward(99)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
//                .waitSeconds(0.1)
//                .addTemporalMarker(() -> {
//                    drive.boxOut();
//                })
//                .strafeRight(29)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(950);
//                })
//                .waitSeconds(0.4)
//                .forward(12.25)
//                .waitSeconds(0.2)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(1300);
//                })
//                .waitSeconds(0.2)
//                .back(4)
//                .addTemporalMarker(() -> {
//                    drive.boxIn();
//                })
//                .waitSeconds(0.1)
//                .addTemporalMarker(() -> {
//                    drive.raiseSlider(0);
//                })
//                .waitSeconds(0.2)
//                .strafeLeft(29)
//                .forward(2.5)
                .build();
        drive.raiseIntake(0.74);
        waitForStart();
        String position = redPropSearchThreshold.getPropPosition();
        telemetry.addData("Prop Position", position);
        telemetry.update();
        if(isStopRequested()) return;
        if (position.equals("center")) {
            drive.followTrajectorySequence(center);
        } else if (position.equals("left")) {
            drive.followTrajectorySequence(left);
        } else {
            drive.followTrajectorySequence(right);
        }
    }
}
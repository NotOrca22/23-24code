package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="BlueNearCycle")
public class BlueLeftCycle extends LinearOpMode {
    private VisionPortal portal;
    private BluePropSearchThreshold bluePropSearchThreshold;
    private boolean captured = false;

    @Override
    public void runOpMode() throws InterruptedException {
        bluePropSearchThreshold = new BluePropSearchThreshold();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(bluePropSearchThreshold)
                .build();




        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d())
                .back(18)
                .strafeRight(11.5)
                .back(4)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(2)
                .turn(Math.toRadians(-90))
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1000);
                })
                .waitSeconds(0.2)
                .forward(8)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.15)
                .forward(20.25)
                .waitSeconds(0.2)
//                .waitSeconds(0.1)
                .back(2)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.15)
                .strafeRight(30)
                .back(100)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.64);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.intake();
                })
                .waitSeconds(0.05)
                .strafeLeft(11.7)
                .back(9.15)
                .waitSeconds(0.2)
//                .forward(5)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.71);
                })
//                .waitSeconds(0.1)
//                .back(4)
                .waitSeconds(0.2)
                .forward(5)
                .strafeRight(11.7)
                .forward(97)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .strafeLeft(25)

                .waitSeconds(0.025)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1100);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.adjustBox();
                })
                .waitSeconds(0.2)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.3)
                .forward(9)
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.2)
                .back(3)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(1.5)
                .build();
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .back(27.5)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(5)
                .turn(Math.toRadians(-90))
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1000);
                })
                .waitSeconds(0.4)
                .forward(20)
                .strafeRight(4)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.3)
                .forward(21)
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1200);
                })
                .waitSeconds(0.25)
                .back(5)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.5)
                .strafeLeft(22)
//                                                                                                                                                                        .forward(10)
                .build();

//
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d())
                .back(28)
                .turn(Math.toRadians(-90))
//                                        .back(4)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(30)
//                                                .turn(Math.toRadians(-90))
//                                                        .forward(30)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1000);
                })
                .waitSeconds(0.5)
                .strafeRight(3)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.5)
                .forward(11.5)
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1200);
                })
                .waitSeconds(0.3)
                .back(2)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.6)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.2)
                .strafeLeft(30.5)
                .build();

        waitForStart();
        String position = bluePropSearchThreshold.getPropPosition();
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
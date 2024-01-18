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

@Autonomous(name="Blue Near")
public class BlueLeftAuto extends LinearOpMode {
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
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.74);
                })
                .back(4)
                .strafeRight(10.5)
                .back(20)
//                .strafeTo(new Vector2d(-24, -10.5))
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.45)
                .addTemporalMarker(() -> {
                    drive.stoptake();
                })
                .forward(6)
//                .addTemporalMarker(() -> {

//                })
//                .addTemporalMarker(() -> {
//                    drive.raiseIntake(0.5)
//                })

//                .lineToSplineHeading(new Pose2d(-26.5, -27,  Math.toRadians(-90)))
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.15)


                .turn(Math.toRadians(-90))
                .forward(7.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(800);
                })
                .waitSeconds(0.4)
                .strafeRight(3)
                .forward(18)
                .waitSeconds(0.1)
                .forward(4.5)
                .waitSeconds(0.35)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1150);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.1)
                .back(3)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.25)
                .strafeLeft(17.75)
                .forward(8)
                .waitSeconds(1)
//                .strafeRight(15)
                .build();
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .back(27.5)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.45)
                .addTemporalMarker(() -> {
                    drive.stoptake();
                })
                .forward(6)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.05)
                .turn(Math.toRadians(-90))
                .addTemporalMarker(() -> {
                    drive.raiseSlider(800);
                })
                .waitSeconds(0.3)
                .forward(30)
                .strafeRight(5)

                .waitSeconds(0.3)
                .forward(8.75)
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
                .waitSeconds(0.25)
                .strafeLeft(22)
                .forward(9)
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
                .waitSeconds(0.45)
                .addTemporalMarker(() -> {
                    drive.stoptake();
                })
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.15)
                .forward(30.25)
//                                                .turn(Math.toRadians(-90))
//                                                        .forward(30)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(775);
                })
                .waitSeconds(0.5)
                .strafeRight(5.75)

                .forward(9)
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1100);
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
                .strafeLeft(29.5)
                .forward(8)
                .build();
        drive.raiseIntake(0.74);
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
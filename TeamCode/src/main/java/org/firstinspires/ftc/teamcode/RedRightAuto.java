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

@Autonomous(name="Red Near")
public class RedRightAuto extends LinearOpMode {
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
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.74);
                })
                .back(4)
                .strafeLeft(11)
                .back(20)
//                .strafeTo(new Vector2d(-24, -10.5))
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.91);
                })
                .waitSeconds(0.45)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
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


                .turn(Math.toRadians(90))
                .forward(7.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(725);
                })
                .waitSeconds(0.4)
//                .strafeLeft(1)
                .forward(17.5)
                .waitSeconds(0.1)
                .forward(4.5)
                .waitSeconds(0.35)

//                .strafeRight(15)
                .build();

        TrajectorySequence rightAlreadyDropped = drive.trajectorySequenceBuilder(right.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1150);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.1)
                .back(6)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.25)
                .strafeRight(17.75)
                .forward(12.5)
                .waitSeconds(1)
                .build();
        TrajectorySequence rightNotDropped = drive.trajectorySequenceBuilder(right.end())
                .forward(5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1150);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.1)
                .back(8)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.25)
                .strafeRight(17.75)
                .forward(12)
                .waitSeconds(1)
                .build();
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .back(28.75)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.91);
                })
                .waitSeconds(0.45)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
                .forward(7.25)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.05)
                .turn(Math.toRadians(90))
                .addTemporalMarker(() -> {
                    drive.raiseSlider(725);
                })
                .waitSeconds(0.3)
                .forward(31.25)
                .strafeLeft(3.35)

                .waitSeconds(0.3)
                .forward(8)
                .waitSeconds(0.25)

//                .forward(9)
//                                                                                                                                                                        .forward(10)
                .build();


//
        TrajectorySequence centerAlreadyDropped = drive.trajectorySequenceBuilder(center.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1200);
                })
                .waitSeconds(0.25)
                .back(7)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.25)
                .strafeRight(23.25)
                .forward(12.5)
                .build();
        TrajectorySequence centerNotDropped = drive.trajectorySequenceBuilder(center.end())
                .forward(5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1200);
                })
                .waitSeconds(0.25)
                .back(9)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.25)
                .strafeRight(23.25)
                .forward(13.75)
                .build();
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d())
                .back(28)
                .turn(Math.toRadians(90))
                .back(4.25)
//                                        .back(4)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.91);
                })
                .waitSeconds(0.45)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.25)
                .forward(34.75)
//                                                .turn(Math.toRadians(-90))
//                                                        .forward(30)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(725);
                })
                .waitSeconds(0.5)
                .strafeLeft(4)

                .forward(9.25)
                .waitSeconds(0.25)

//                .forward(8)
                .build();
        TrajectorySequence leftAlreadyDropped = drive.trajectorySequenceBuilder(left.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1100);
                })
                .waitSeconds(0.3)
                .back(5)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.6)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.2)
                .strafeRight(32)
                .forward(12)
                .build();
        TrajectorySequence leftNotDropped = drive.trajectorySequenceBuilder(left.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1100);
                })
                .waitSeconds(0.3)
                .back(5)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.6)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.2)
                .strafeRight(32)
                .forward(12)
                .build();
        drive.raiseIntake(0.74);
        waitForStart();
        String position = redPropSearchThreshold.getPropPosition();
        telemetry.addData("Prop Position", position);
        telemetry.update();
        if(isStopRequested()) return;
        if (position.equals("center")) {
            drive.followTrajectorySequence(center);
            telemetry.addData("yellowDropped", !drive.checkLowerPixel());
            telemetry.update();
            if (drive.checkLowerPixel()) {
                drive.followTrajectorySequence(centerNotDropped);
            } else {
                drive.followTrajectorySequence(centerAlreadyDropped);
            }
        } else if (position.equals("left")) {
            drive.followTrajectorySequence(left);
            telemetry.addData("yellowDropped", !drive.checkLowerPixel());
            telemetry.update();
            if (drive.checkLowerPixel()) {
                drive.followTrajectorySequence(leftNotDropped);
            } else {
                drive.followTrajectorySequence(leftAlreadyDropped);
            }
        } else {
            drive.followTrajectorySequence(right);
            telemetry.addData("yellowDropped", !drive.checkLowerPixel());
            telemetry.update();
            if (drive.checkLowerPixel()) {
                drive.followTrajectorySequence(rightNotDropped);
            } else {
                drive.followTrajectorySequence(rightAlreadyDropped);
            }
        }
    }
}
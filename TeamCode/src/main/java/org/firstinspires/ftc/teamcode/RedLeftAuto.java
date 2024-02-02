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
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.05)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.74);
                })
                .back(28)
                .turn(Math.toRadians(-90))
                .back(2.5)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.91);
//                    drive.outtake();
                })
                .waitSeconds(0.45)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
                .forward(7)
                .turn(Math.toRadians(-90))
                .forward(24.5)
                .turn(Math.toRadians(-90))
                .forward(72)
                .turn(Math.toRadians(-90))
                .forward(29.5)
                .turn(Math.toRadians(90))
                .addTemporalMarker(() -> {
                    drive.raiseSlider(850);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.25)
                .forward(17.5)
                .waitSeconds(0.1)
//                .forward(5.5)
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
        TrajectorySequence rightAlreadyDropped = drive.trajectorySequenceBuilder(right.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.25)
                .back(10)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.3)
                .strafeLeft(29)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .forward(4)
                .waitSeconds(0.7)
                .build();
        TrajectorySequence rightNotDropped = drive.trajectorySequenceBuilder(right.end())
                .forward(5.5)
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.25)
                .back(10)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .waitSeconds(0.3)
                .strafeLeft(29)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.7)
                .forward(5)
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .back(48)
                .turn(Math.toRadians(180))
                .back(3)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.91);
                })
                .waitSeconds(0.45)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
                .forward(6)
                .turn(Math.toRadians(-90))
                .forward(73)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.15)


                .turn(Math.toRadians(-90))
                .forward(28)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(850);
                })
                .waitSeconds(0.5)
                .turn(Math.toRadians(90))

                .forward(15)
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

        TrajectorySequence centerAlreadyDropped = drive.trajectorySequenceBuilder(center.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1250);
                })
                .waitSeconds(0.25)
                .back(6)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                    drive.boxIn();
                })
                .strafeLeft(18)
                .forward(3)
                .build();

        TrajectorySequence centerNotDropped = drive.trajectorySequenceBuilder(center.end())
                .forward(5.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1250);
                })
                .waitSeconds(0.25)
                .back(8)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                    drive.boxIn();
                })
                .strafeLeft(18)
                .forward(4.5)
                .build();
//
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.05)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.74);
                })
                .back(48)
                .turn(Math.toRadians(180))
                .strafeLeft(11.75)
                .back(4)
                .addTemporalMarker(() -> {
//                    drive.outtake();
                    drive.raiseIntake(0.91);
                })
                .waitSeconds(0.45)
//                .addTemporalMarker(() -> {
//                    drive.stoptake();
//                })
//                .forward(2)
//                .turn(Math.toRadians(-90))
                .forward(8)
                .turn(Math.toRadians(-90))
                .forward(85.5)
                .turn(Math.toRadians(-90))
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .forward(23.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(850);
                })
                .waitSeconds(0.5)
                .turn(Math.toRadians(90))
                .forward(14)
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
        TrajectorySequence leftAlreadyDropped = drive.trajectorySequenceBuilder(left.end())
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1250);
                })
                .waitSeconds(0.25)
                .back(5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                    drive.boxIn();
                })
                .strafeLeft(18.5)
                .forward(4.5)
                .build();
        TrajectorySequence leftNotDropped = drive.trajectorySequenceBuilder(left.end())
                .forward(5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1250);
                })
                .waitSeconds(0.25)
                .back(7.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                    drive.boxIn();
                })
                .strafeLeft(18.5)
                .forward(5.75)
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
//            sleep(5000);
            if (drive.checkLowerPixel()) {
                drive.followTrajectorySequence(leftNotDropped);
            } else {
                drive.followTrajectorySequence(leftAlreadyDropped);
            }
        } else {
            drive.followTrajectorySequence(right);
            telemetry.addData("yellowDropped", !drive.checkLowerPixel());
            telemetry.update();
//            sleep(5000);
            if (drive.checkLowerPixel()) {
                drive.followTrajectorySequence(rightNotDropped);
            } else {
                drive.followTrajectorySequence(rightAlreadyDropped);
            }
        }
    }
}
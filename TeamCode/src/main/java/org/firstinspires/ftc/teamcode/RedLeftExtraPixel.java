package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
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

@Autonomous(name="Red Far Cycle")
public class RedLeftExtraPixel extends LinearOpMode {
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
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d())
                .back(32.25)
                .turn(Math.toRadians(-90))
//                                        .back(4)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(6)
                .strafeRight(18.5)
                .turn(Math.toRadians(-180))
//                .back(23)
//                .strafeLeft(11)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.63);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.intake();
                })
                .waitSeconds(0.025)
                .back(18.5)
                .waitSeconds(0.55)

//                .turn(Math.toRadians(-90))
                .forward(15)
                .strafeRight(1)
                .forward(84)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .waitSeconds(0.025)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1050);
                })
                .waitSeconds(0.5)
                .strafeRight(30)
                .addTemporalMarker(() -> {
                    drive.adjustBox();
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.4)
                .forward(12)
                .waitSeconds(0.2)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.3)
                .back(2.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .strafeLeft(23)
                .build();
        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .back(27.25)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(4)
                .strafeRight(16)
                .back(27.5)
                .turn(Math.toRadians(90))
//                .strafeLeft(29.4)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.63);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.intake();
                })
                .waitSeconds(0.05)
                .back(8)
                .waitSeconds(0.55)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .waitSeconds(0.025)
                .forward(15)
                .strafeLeft(0.25)
                .forward(84)

                .addTemporalMarker(() -> {
                    drive.raiseSlider(1030);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.3)
                .strafeRight(26)
                .forward(10.75)
                .waitSeconds(0.2)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.3)
                .back(4)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .strafeLeft(18)
                .build();


//
        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d())
                .back(32.25)
                .turn(Math.toRadians(90))
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(1)
                .strafeLeft(18)
//                .turn(Math.toRadians(-90))
//                .strafeLeft(9)
                .addTemporalMarker(() -> {
                    drive.raiseIntake(0.63);
                })
                .waitSeconds(0.15)
                .addTemporalMarker(() -> {
                    drive.intake();
                })
                .waitSeconds(0.025)
                .back(26.5)
                .waitSeconds(0.55)
                .forward(15)
                .strafeLeft(0.5)
                .forward(85.5)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .waitSeconds(0.025)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1040);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.3)
                .strafeRight(21.2)
                .forward(10.75)
                .waitSeconds(0.2)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                })
                .waitSeconds(0.3)
                .back(4)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(0);
                })
                .strafeLeft(15)
                .build();

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
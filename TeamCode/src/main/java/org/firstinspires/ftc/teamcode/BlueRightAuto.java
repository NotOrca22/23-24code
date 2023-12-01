package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

@Autonomous(name="BlueRightAuto")
public class BlueRightAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence center = drive.trajectorySequenceBuilder(new Pose2d())
                .back(48)
//                .forward(10)
                .turn(Math.toRadians(-187))
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.4)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
                .forward(7)
                .turn(Math.toRadians(90))
                .forward(78)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1000);
                })
                .waitSeconds(0.5)
                .turn(Math.toRadians(90))
                .forward(31)
                .turn(Math.toRadians(-90))

                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.25)
                .forward(14.5)
                .build();
        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d())
                .back(32)
                .turn(-Math.toRadians(90))
//                .back(3)
                .forward(4)
                .addTemporalMarker(() -> {
                    drive.outtake();
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    drive.stopIntake();
                })
//                .forward(3)
                .turn(Math.toRadians(-90))
                .forward(27)
                .turn(Math.toRadians(90))
//                .turn(Math.toRadians(-12))
                .forward(80)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1000);
                })
                .waitSeconds(0.3)
                .turn(Math.toRadians(90))
                .forward(23)
                .turn(Math.toRadians(-90))
                .addTemporalMarker(() -> {
                  drive.boxOut();
                })
                .forward(14.5)
                .addTemporalMarker(() -> {
                    drive.raiseSlider(1300);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    drive.boxIn();
                    drive.raiseSlider(0);
                })
                .waitSeconds(2)
                .build();
        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectorySequence(right);
    }
}
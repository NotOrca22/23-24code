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

        TrajectorySequence auto = drive.trajectorySequenceBuilder(new Pose2d())
                .back(48)

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
                .strafeLeft(31)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .waitSeconds(0.25)
                .forward(14.5)
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectorySequence(auto);
    }
}
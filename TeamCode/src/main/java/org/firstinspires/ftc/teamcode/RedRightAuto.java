package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

public class RedRightAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPos = new Pose2d(-62, -28, 0);
        drive.setPoseEstimate(startPos);
        TrajectorySequence trajSeq;
        TrajectorySequenceBuilder trajSeqBuilder = drive.trajectorySequenceBuilder(startPos)
                .back(30)
                        .turn(Math.toRadians(90*187/180))
                                .addTemporalMarker(() -> {
                                    drive.raiseSlider(600);
                                })
                                        .waitSeconds(0.1)
                .forward(20)
                .addTemporalMarker(() -> {
                    drive.boxOut();
                })
                .forward(5);
        waitForStart();

        if(isStopRequested()) return;

//        drive.followTrajectory(trajSeqBuilder.build());
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//drop behind 0.66 upper 0.55 lower
//pickup front 0.135 lower 0.41 upper
@Disabled
public class Tele extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        Servo lowerLeft = hardwareMap.servo.get("lowLeft");
//        Servo lowerRight = hardwareMap.servo.get("lowRight");
        Servo highLeft = hardwareMap.servo.get("highLeft");
        Servo claw1 = hardwareMap.servo.get("claw1");
        Servo claw2 = hardwareMap.servo.get("claw2");
//        Servo highRight = hardwareMap.servo.get("highRight");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        // HOME POSITIONS!!! (SUPER IMPORTANT STUFF)

        double lowerPosition = 0.2;
        double upperPosition = 0;
//        lowerRight.setPosition(lowerPosition);

        lowerLeft.setPosition(lowerPosition);
//        highRight.setPosition(1-upperPosition);
//        highLeft.setPosition(upperPosition);
        waitForStart();
//    Servo claw = hardwareMap.servo.get("claw");
        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
//            if (Math.abs(y) > Math.abs(x)) {
//                frontLeft.setPower(0.7 * y);
//                frontRight.setPower(0.7 * y);
//                backLeft.setPower(0.7 * y);
//                backRight.setPower(0.7 * y);
////            } else {
////                frontLeft.setPower(0);
////                frontRight.setPower(0);
////                backLeft.setPower(0);
////                backRight.setPower(0);
////            }
//            } else {
//                frontLeft.setPower(0.7 * x);
//                frontRight.setPower(-0.7 * x);
//                backLeft.setPower(0.7 * x);
//                backRight.setPower(-0.7 * x);
//            }
            if (gamepad1.y) {
                lowerPosition = 0.135;
//                upperPosition = 0;
            }
            if (gamepad1.a) {
                upperPosition = 0.38;

            } else if (gamepad1.x) {
                upperPosition = 0.55;
            }
            if (gamepad1.b) {
                lowerPosition = 0.66;
            }
            telemetry.addData("lowerLeft", lowerLeft.getPosition());
            telemetry.addData("upperLeft", highLeft.getPosition());

//            telemetry.addData("lowerRight", lowerRight.getPosition());
//            lowerRight.setPosition(lowerPosition);
            lowerLeft.setPosition(lowerPosition);
            highLeft.setPosition(upperPosition);
//            highRight.setPosition(1-upperPosition);
//            highLeft.setPosition(upperPosition);
            telemetry.update();
        }
    }
}
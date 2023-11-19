package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
// PWD Orca1234
@TeleOp(name="1P_Tele")
public class Tele1 extends LinearOpMode {
    DcMotorEx leftSlide;
    DcMotorEx rightSlide;
    DcMotor frontLeftWheel;
    DcMotor frontRightWheel;
    DcMotor backLeftWheel;
    DcMotor backRightWheel;
    Servo box;
    Servo in;
    CRServo rollers;
    public void raiseSlider(int position) {
        leftSlide.setTargetPosition(position);
        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setVelocity(ARM_FULL_SPEED_IN_COUNTS);
        rightSlide.setTargetPosition(position);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setVelocity(ARM_FULL_SPEED_IN_COUNTS);
    }
    public void straight(double power) {
        frontLeftWheel.setPower(power*0.8*0.7);
        frontRightWheel.setPower(power*0.82*0.7);
        backLeftWheel.setPower(power*0.8*0.7);
        backRightWheel.setPower(power*0.82*0.7);
    }
    public void turn(double power) { // positive is CW turn, negative is CCW
        frontLeftWheel.setPower(-power*0.8*0.7);
        frontRightWheel.setPower(power*0.8*0.7);
        backLeftWheel.setPower(-power*0.8*0.7);
        backRightWheel.setPower(power*0.8*0.7);
    }
    public void strafe(double power) { // positive is right, negative is left
        frontLeftWheel.setPower(power*0.8*0.7);
        frontRightWheel.setPower(power*0.82*0.7);
        backLeftWheel.setPower(-power*0.8*0.7);
        backRightWheel.setPower(-power*0.8*0.7);
    }
    public void boxIn() {
        box.setPosition(0.6);
    }
    public void boxOut() {
        if (leftSlide.getCurrentPosition() > 450) {
//            box.setPosition(1);
//            sleep(500);
            box.setPosition(0.3);
        }

    }
    public static final double ARM_GEAR_RATIO = 13.7;
    public static final int ARM_MOTOR_SPEED_IN_RPM = 435;
    public static final double PULLEY_DIAMETER_IN_MM = 35.65;
    public static final double COUNTS_PER_ENCODER_REV = 28;
    public static final int ARM_COUNTS_PER_MILLIMETER = (int) ((COUNTS_PER_ENCODER_REV * ARM_GEAR_RATIO) / (PULLEY_DIAMETER_IN_MM * Math.PI));
    public static final double ARM_FULL_SPEED_IN_COUNTS = COUNTS_PER_ENCODER_REV * ARM_GEAR_RATIO * ARM_MOTOR_SPEED_IN_RPM / 60;
    boolean intakeOn = true;
    // LEFT MAX 2000
    @Override
    public void runOpMode() throws InterruptedException {
        leftSlide = (DcMotorEx) hardwareMap.dcMotor.get("leftSlide");
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide = (DcMotorEx) hardwareMap.dcMotor.get("rightSlide");
        rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        frontLeftWheel = hardwareMap.dcMotor.get("frontLeft");
        frontRightWheel = hardwareMap.dcMotor.get("frontRight");
        frontRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftWheel = hardwareMap.dcMotor.get("backLeft");
        backRightWheel = hardwareMap.dcMotor.get("backRight");
        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers = hardwareMap.crservo.get("rollers");
        box = hardwareMap.servo.get("box");
        in = hardwareMap.servo.get("in");
//        box.setDirection(Servo.Direction.REVERSE);
        in.setPosition(1);
        waitForStart();
        boxIn();
        while (opModeIsActive()) {
            in.setPosition(1);
            leftSlide.setTargetPositionTolerance(100);
            rightSlide.setTargetPositionTolerance(100);
            int currentPosition = leftSlide.getCurrentPosition();
            int raiseStep = 0;
            if(gamepad1.dpad_down) {
                raiseStep = -100;
            } else if (gamepad1.dpad_up) {
                raiseStep = 100;
            } else {
                raiseStep = 0;
            }
            int targetPosition = currentPosition;
            targetPosition += raiseStep;
            if (targetPosition > 2000) {
                targetPosition = 2000;
            }
//            else if (targetPosition < 0) {
//                targetPosition = 0;

            if (gamepad1.x) {
                boxIn();
            } else if (gamepad1.a) {
                boxOut();
            }
            raiseSlider(targetPosition);
            if (intakeOn) {
                rollers.setPower(1);
                intake.setPower(-1);
            } else {
                rollers.setPower(0);
                intake.setPower(0);
            }
            if (gamepad1.left_bumper) {
                intakeOn = false;
            } else if (gamepad1.right_bumper) {
                intakeOn = true;
            }
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (Math.abs(y) > 0.1 && Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(rx)) {
                straight(y);
            } else if (Math.abs(x) > 0.1 && Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(rx)) {
                strafe(x);
            } else if (Math.abs(rx) > 0.1 && Math.abs(rx) > Math.abs(x) && Math.abs(rx) > Math.abs(y)) {
                turn(rx);
            } else {
                straight(0);
            }
            telemetry.addData("leftPosition", leftSlide.getCurrentPosition());
            telemetry.addData("rightPosition", rightSlide.getCurrentPosition());
            telemetry.addData("intakeOn", intakeOn);
            telemetry.update();
        }
    }
}
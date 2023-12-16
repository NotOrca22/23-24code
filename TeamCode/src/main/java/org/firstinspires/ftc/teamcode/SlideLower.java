package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// PWD Orca1234
@TeleOp(name="LowerSlide")
public class SlideLower extends LinearOpMode {
    OuttakeStatus status;
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
        frontLeftWheel.setPower(power*0.8*0.8);
        frontRightWheel.setPower(power*0.82*0.8);
        backLeftWheel.setPower(power*0.8*0.8);
        backRightWheel.setPower(power*0.82*0.8);
    }
    public void turn(double power) { // positive is CW turn, negative is CCW
        frontLeftWheel.setPower(-power*0.8*0.8);
        frontRightWheel.setPower(power*0.8*0.8);
        backLeftWheel.setPower(-power*0.8*0.8);
        backRightWheel.setPower(power*0.8*0.8);
    }
    public void strafe(double power) { // positive is right, negative is left
        frontLeftWheel.setPower(power*0.8*0.9);
        frontRightWheel.setPower(power*0.82*0.9);
        backLeftWheel.setPower(-power*0.8*0.9);
        backRightWheel.setPower(-power*0.8*0.9);
    }
    public void boxIn() {
        box.setPosition(0.625);
        status = OuttakeStatus.OUTAKE_RECEIVE;
    }

    public void adjustBox() {
        if (leftSlide.getCurrentPosition() > 450) {
            box.setPosition(1);
//            sleep(500);
            status = OuttakeStatus.OUTTAKE_ADJUST;
        }

    }
    public void boxOut() {
        if (leftSlide.getCurrentPosition() > 450) {
            box.setPosition(0.3);
            status = OuttakeStatus.OUTTAKE_DROP;
        }
    }
    //    public void boxUp() {
//
//    }
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
        status = OuttakeStatus.OUTAKE_RECEIVE;
        leftSlide = (DcMotorEx) hardwareMap.dcMotor.get("leftSlide");
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide = (DcMotorEx) hardwareMap.dcMotor.get("rightSlide");
        rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        frontLeftWheel = hardwareMap.dcMotor.get("frontLeft");
        frontLeftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
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
        in.setPosition(0.935);
        waitForStart();
        boxIn();
        while (opModeIsActive()) {
            leftSlide.setTargetPositionTolerance(100);
            rightSlide.setTargetPositionTolerance(100);
            int currentPosition = leftSlide.getCurrentPosition();
            int raiseStep = 0;
            if(gamepad2.dpad_down) {
                raiseStep = -80;
            } else if (gamepad2.dpad_up) {
                raiseStep = 80;
            } else if (gamepad2.y) {
                raiseStep = -180;
            } else {
                raiseStep = 0;
            }
            int targetPosition = currentPosition;
            targetPosition += raiseStep;
            raiseSlider(targetPosition);
        }
    }
}
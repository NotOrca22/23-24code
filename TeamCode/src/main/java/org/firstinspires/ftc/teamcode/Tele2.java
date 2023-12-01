package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// PWD Orca1234
@TeleOp(name="2P_Tele")
public class Tele2 extends LinearOpMode {
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
    Servo plane;
    int launches;
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
        frontLeftWheel.setPower(-power*0.8*0.66);
        frontRightWheel.setPower(power*0.8*0.66);
        backLeftWheel.setPower(-power*0.8*0.66);
        backRightWheel.setPower(power*0.8*0.66);
    }
    public void strafe(double power) { // positive is right, negative is left
        frontLeftWheel.setPower(-power*0.8*0.9);
        frontRightWheel.setPower(power*0.82*0.9);
        backLeftWheel.setPower(power*0.8*0.9);
        backRightWheel.setPower(-power*0.8*0.9);
    }
    public void boxIn() {
        box.setPosition(0.625);
        status = OuttakeStatus.OUTAKE_RECEIVE;
    }

    public void adjustBox() {
        if (leftSlide.getCurrentPosition() > 450) {
            status = OuttakeStatus.ADJUSTING;
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
        launches = 0;
        boolean hasLaunched = false;
        status = OuttakeStatus.OUTAKE_RECEIVE;
        leftSlide = (DcMotorEx) hardwareMap.dcMotor.get("leftSlide");
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide = (DcMotorEx) hardwareMap.dcMotor.get("rightSlide");
        rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        frontLeftWheel = hardwareMap.dcMotor.get("frontLeft");
//        frontLeftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
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
        plane = hardwareMap.servo.get("plane");
//        box.setDirection(Servo.Direction.REVERSE);
        in.setPosition(0.935);
        waitForStart();
        boxIn();
        while (opModeIsActive()) {
            in.setPosition(1);
            leftSlide.setTargetPositionTolerance(100);
            rightSlide.setTargetPositionTolerance(100);
            int currentPosition = leftSlide.getCurrentPosition();
            int raiseStep = 0;
            if(gamepad2.dpad_down) {
                raiseStep = -120;
            } else if (gamepad2.dpad_up) {
                raiseStep = 120;
            } else if (gamepad2.y) {
               raiseStep = -180;
            } else {
                raiseStep = 0;
            }
            int targetPosition = currentPosition;
            targetPosition += raiseStep;
            if (targetPosition > 2100) {
                targetPosition = 2100;
            }
            else if (targetPosition < 10) {
                targetPosition = 10;
            }
            if (status == OuttakeStatus.OUTTAKE_ADJUST && Math.abs(box.getPosition()-1) < 0.005) {
                boxOut();
            }
            if (gamepad2.x) {
                boxIn();
            } else if (gamepad2.a) {
                adjustBox();

            }
            raiseSlider(targetPosition);
            if (intakeOn) {
                rollers.setPower(1);
                intake.setPower(-1);
            } else {
                rollers.setPower(0);
                intake.setPower(0);
            }
            if (gamepad2.left_bumper) {
                intakeOn = false;
            } else if (gamepad2.right_bumper) {
                intakeOn = true;
            }
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (Math.abs(rx) > 0.1 && Math.abs(rx) > Math.abs(x) && Math.abs(rx) > Math.abs(y)) {
                turn(rx);
            } else if (Math.abs(y) > 0.1 && Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(rx)) {
                straight(y);
            } else if (Math.abs(x) > 0.1 && Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(rx)) {
                strafe(x);
            } else {
                straight(0);
            }

            if (gamepad2.b) {
                launches += 1;
            }
            if (launches >= 50) {
                hasLaunched = true;
            }
            if (hasLaunched) {
                plane.setPosition(0.4);
            } else {
                plane.setPosition(0.5);
            }
            telemetry.addData("leftPosition", leftSlide.getCurrentPosition());
            telemetry.addData("rightPosition", rightSlide.getCurrentPosition());
            telemetry.addData("intakeOn", intakeOn);
            telemetry.addData("launches", launches);
            telemetry.update();
        }
    }
}
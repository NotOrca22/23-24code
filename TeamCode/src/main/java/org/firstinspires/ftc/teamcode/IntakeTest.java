package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Tele")
public class IntakeTest extends LinearOpMode {
    DcMotor intake;
    Servo box;
    DcMotor rollers;
    DcMotorEx leftSlide;
    DcMotorEx rightSlide;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    Servo intakeRaise;
    Servo plane;
    int intakePosition = 1;
    public static final double ARM_GEAR_RATIO = 13.7;
    public static final int ARM_MOTOR_SPEED_IN_RPM = 435;
    public static final double PULLEY_DIAMETER_IN_MM = 35.9007007;
    public static final double COUNTS_PER_ENCODER_REV = 28;
    public static final int ARM_COUNTS_PER_MILLIMETER = (int) ((COUNTS_PER_ENCODER_REV * ARM_GEAR_RATIO) / (PULLEY_DIAMETER_IN_MM * Math.PI));
    public static final double ARM_FULL_SPEED_IN_COUNTS = COUNTS_PER_ENCODER_REV * ARM_GEAR_RATIO * ARM_MOTOR_SPEED_IN_RPM*0.85 / 60;

    //    boolean intakeOn = false;
    public void raiseSlider(int position) {
        leftSlide.setTargetPosition(position);
        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setVelocity(ARM_FULL_SPEED_IN_COUNTS);
        rightSlide.setTargetPosition(position);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setVelocity(ARM_FULL_SPEED_IN_COUNTS);
    }
    boolean boxIn = true;
    int launches = 0;

    // 0.82 for 2 pixels
    @Override
    public void runOpMode() throws InterruptedException {
        intake = hardwareMap.dcMotor.get("intake");
        box = hardwareMap.servo.get("box");
        intakeRaise = hardwareMap.servo.get("intakeRaise");
        rollers = hardwareMap.dcMotor.get("rollers");
        leftSlide = (DcMotorEx) hardwareMap.dcMotor.get("leftSlide");
        rightSlide = (DcMotorEx) hardwareMap.dcMotor.get("rightSlide");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        plane = hardwareMap.servo.get("plane");
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        plane.setDirection(Servo.Direction.REVERSE);
        plane.setPosition(0.47);
//        box.setPosition(1);
        int slideHeight = 0;
        box.setPosition(1);
        waitForStart();
        while (opModeIsActive()) {
            if (boxIn) {
                box.setPosition(0.885);
            } else {
                box.setPosition(0.05);
            }
            if (gamepad1.a) {
                boxIn = false;
            } else if (gamepad1.x) {
                boxIn = true;
            }
            if (gamepad1.b) {
                launches += 1;
            }
            if (launches >= 50) {
                plane.setPosition(0.39);
            } else {
                plane.setPosition(0.47);
            }
            intake.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
//            box.setPosition(0.8125);
            rollers.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
            if (intakePosition == 1) {
                intakeRaise.setPosition(0.74);
            }
            leftSlide.setTargetPositionTolerance(100);
            rightSlide.setTargetPositionTolerance(100);
//            int currentPosition = leftSlide.getCurrentPosition();
            int raiseStep = 0;
            if(gamepad1.dpad_down) {
                slideHeight += -15;
            } else if (gamepad1.dpad_up) {
                slideHeight += 15;
            } else if (gamepad1.y) {
                slideHeight += -40;
            } else {
                slideHeight += 0;
            }
//            targetPosition += raiseStep;
            if (slideHeight > 2800) {
                slideHeight = 2800;
            }
            else if (slideHeight < -25) {
                slideHeight = -25;
            }
            raiseSlider(slideHeight);
            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            frontLeft.setPower(y + x + rx);
            backLeft.setPower(y - x + rx);
            frontRight.setPower(y - x - rx);
            backRight.setPower(y + x - rx);
            telemetry.addData("launches", launches);
            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.time.Instant;

@TeleOp(name="!Tele2")
public class Tele22 extends LinearOpMode {
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
    DistanceSensor frontSensor;
    NormalizedColorSensor topColor;
    NormalizedColorSensor bottomColor;
    int intakePosition = 1;
    long twoDetected = Long.MAX_VALUE-1;
    boolean outtakeStopped = false;
    public static final double ARM_GEAR_RATIO = 13.7;
    public static final int ARM_MOTOR_SPEED_IN_RPM = 435;
    public static final double PULLEY_DIAMETER_IN_MM = 35.9007007;
    public static final double COUNTS_PER_ENCODER_REV = 28;
    public static final int ARM_COUNTS_PER_MILLIMETER = (int) ((COUNTS_PER_ENCODER_REV * ARM_GEAR_RATIO) / (PULLEY_DIAMETER_IN_MM * Math.PI));
    public static final double ARM_FULL_SPEED_IN_COUNTS = COUNTS_PER_ENCODER_REV * ARM_GEAR_RATIO * ARM_MOTOR_SPEED_IN_RPM*0.85 / 60;

    //    boolean intakeOn = false;
    public void raiseSlider(int position) {
//        leftSlide.setTargetPosition(position);
//        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftSlide.setPower(ARM_FULL_SPEED_IN_COUNTS);
        rightSlide.setTargetPosition(position);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setVelocity(ARM_FULL_SPEED_IN_COUNTS);
    }
    boolean boxIn = true;
    int launches = 0;
    boolean intakeOn = false;
    final float[] hsvValues = new float[3];
    final float[] hsvValues2 = new float[3];
    // 0.82 for 2 pixels
    boolean outtaking = false;
    boolean previouslyOuttaking = false;
    int slideHeight = 0;
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
        frontSensor = hardwareMap.get(DistanceSensor.class, "frontSensor");
        topColor = hardwareMap.get(NormalizedColorSensor.class, "topColor");
        bottomColor = hardwareMap.get(NormalizedColorSensor.class, "bottomColor");
        plane.setPosition(0.47);
//        box.setPosition(1);

        waitForStart();
        while (opModeIsActive()) {
            if (boxIn) {
                box.setPosition(0.9575);
            } else {
                box.setPosition(0.1475);
            }
            if (gamepad2.a) {
                boxIn = false;
            } else if (gamepad2.x) {
                boxIn = true;
                slideHeight = 0;
            }
            if (gamepad2.b) {
                launches += 1;
            }
            if (launches >= 50) {
                plane.setPosition(0.39);
            } else {
                plane.setPosition(0.47);
            }
            if (((!(((DistanceSensor) bottomColor).getDistance(DistanceUnit.CM) < 1)) || (!(((DistanceSensor) topColor).getDistance(DistanceUnit.CM) < 1)))) {
                outtaking = false;
                previouslyOuttaking = false;
                twoDetected = Long.MAX_VALUE-1;
            } else {
                if (!previouslyOuttaking) {
//                    wait(250);

                    long millis = System.currentTimeMillis();
                    twoDetected = millis;

                }
            }
            if (!previouslyOuttaking && System.currentTimeMillis()-twoDetected>=250) {
                outtaking = true;
                previouslyOuttaking = true;
                outtakeStopped = false;
            }
            if (gamepad2.right_bumper && boxIn && intakePosition < 150 && ((!(((DistanceSensor) bottomColor).getDistance(DistanceUnit.CM) < 1)) || (!(((DistanceSensor) topColor).getDistance(DistanceUnit.CM) < 1)))) {
                intakeOn = true;
                outtaking = false;
                outtakeStopped = true;
                previouslyOuttaking = false;
            } else if (gamepad2.left_bumper || !boxIn || intakePosition > 150 || !((!(((DistanceSensor) bottomColor).getDistance(DistanceUnit.CM) < 1)) || (!(((DistanceSensor) topColor).getDistance(DistanceUnit.CM) < 1)))) {
                intakeOn = false;
                if (gamepad2.left_bumper) {
                    outtaking = false;
                    outtakeStopped = true;
                    previouslyOuttaking = false;
                }
            }
            if (outtaking) {
                rollers.setPower(-1);
                intake.setPower(-1);
            } else if (intakeOn) {
                rollers.setPower(1);
                intake.setPower(1);
            } else {
                rollers.setPower(gamepad2.right_trigger-gamepad2.left_trigger);
                intake.setPower(gamepad2.right_trigger-gamepad2.left_trigger);
            }
            if (intakePosition == 1) {
                intakeRaise.setPosition(0.74);
            } else if (intakePosition == 2) {
                intakeRaise.setPosition(0.78);
            } else if (intakePosition == 3) {
                intakeRaise.setPosition(0.819);
            } else if (intakePosition == 4) {
                intakeRaise.setPosition(0.8435);
            } else {
                intakeRaise.setPosition(0.884);
            }
            if (gamepad1.x) {
                intakePosition = 1;
            } else if (gamepad1.a) {
                intakePosition = 2;
            } else if (gamepad1.b) {
                intakePosition = 3;
            } else if (gamepad1.y) {
                intakePosition = 4;
            } else if (gamepad1.right_bumper) {
                intakePosition = 5;
            }
            leftSlide.setTargetPositionTolerance(100);
            rightSlide.setTargetPositionTolerance(100);
//            int currentPosition = leftSlide.getCurrentPosition();
            int raiseStep = 0;
            if(gamepad2.dpad_down) {
                slideHeight += -70;
            } else if (gamepad2.dpad_up) {
                slideHeight += 70;
            } else if (gamepad2.y) {
                slideHeight += -120;
            } else {
                slideHeight += 0;
            }
//            targetPosition += raiseStep;
            if (slideHeight > 3100) {
                slideHeight = 3100;
            }
            else if (slideHeight < -25) {
                slideHeight = -25;
            }
            raiseSlider(slideHeight);
            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x * 0.85;

            frontLeft.setPower((y + x + rx)*0.9);
            backLeft.setPower((y - x + rx)*0.9);
            frontRight.setPower((y - x - rx)*0.9);
            backRight.setPower((y + x - rx)*0.9);
            telemetry.addData("launches", launches);
            NormalizedRGBA colors = topColor.getNormalizedColors();

            /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
             * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
             * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
             * for an explanation of HSV color. */

            // Update the hsvValues array by passing it to Color.colorToHSV()
            Color.colorToHSV(colors.toColor(), hsvValues);

            telemetry.addLine()
                    .addData("Red", "%.3f", colors.red)
                    .addData("Green", "%.3f", colors.green)
                    .addData("Blue", "%.3f", colors.blue);
            telemetry.addLine()
                    .addData("Hue", "%.3f", hsvValues[0])
                    .addData("Saturation", "%.3f", hsvValues[1])
                    .addData("Value", "%.3f", hsvValues[2]);
            telemetry.addData("Alpha", "%.3f", colors.alpha);
//            telemetry.addData("bottom R", bottomColor.red());
//            telemetry.addData("bottom G", bottomColor.green());
//            telemetry.addData("bottom B", bottomColor.blue());
            NormalizedRGBA colors2 = bottomColor.getNormalizedColors();
            telemetry.addData("Top Distance (cm)", "%.3f", ((DistanceSensor) topColor).getDistance(DistanceUnit.CM));

            /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
             * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
             * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
             * for an explanation of HSV color. */

            // Update the hsvValues array by passing it to Color.colorToHSV()
            Color.colorToHSV(colors2.toColor(), hsvValues2);

            telemetry.addLine()
                    .addData("Red Bottom", "%.3f", colors2.red)
                    .addData("Green", "%.3f", colors2.green)
                    .addData("Blue", "%.3f", colors2.blue);
            telemetry.addLine()
                    .addData("Hue", "%.3f", hsvValues2[0])
                    .addData("Saturation", "%.3f", hsvValues2[1])
                    .addData("Value", "%.3f", hsvValues2[2]);
            telemetry.addData("Alpha", "%.3f", colors.alpha);
            telemetry.addData("front distance", frontSensor.getDistance(DistanceUnit.INCH));
            telemetry.addData("Bottom Distance (cm)", "%.3f", ((DistanceSensor) bottomColor).getDistance(DistanceUnit.CM));
            telemetry.addData("slideHeight", slideHeight);
            telemetry.update();
        }
    }
}
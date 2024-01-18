package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class BluePropThreshold implements VisionProcessor {
    Mat testMat = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();
    double redThreshold = 0.5;

    double averagedLeftBoxH;
    double averagedLeftBoxS;
    double averagedMidBoxH;
    double averagedMidBoxS;
    String outStr = "right"; //Set a default value in case vision does not work

    static final Rect LEFT_RECTANGLE = new Rect(
            new Point(40, 340),
            new Point(70, 370)
    );

    static final Rect MID_RECTANGLE = new Rect(
            new Point(460, 330),
            new Point(490, 360)
    );

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        int avg = (int) Core.mean(frame).val[0];
        if (avg > 0) {
            Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);


//        Scalar lowHSVRedLower = new Scalar(0, 100, 20);  //Beginning of Color Wheel
//        Scalar lowHSVRedUpper = new Scalar(10, 255, 255);
//
//        Scalar redHSVRedLower = new Scalar(160, 100, 20); //Wraps around Color Wheel
//        Scalar highHSVRedUpper = new Scalar(180, 255, 255);
//
//        Core.inRange(testMat, lowHSVRedLower, lowHSVRedUpper, lowMat);
//        Core.inRange(testMat, redHSVRedLower, highHSVRedUpper, highMat);

//        testMat.release();
//
//        Core.bitwise_or(lowMat, highMat, finalMat);
//
//        lowMat.release();
//        highMat.release();


            Scalar leftBox = Core.sumElems(testMat.submat(LEFT_RECTANGLE));
            Scalar rightBox = Core.sumElems(testMat.submat(MID_RECTANGLE));

            averagedLeftBoxH = leftBox.val[2] / LEFT_RECTANGLE.area() / 1;
            averagedMidBoxH = rightBox.val[2] / MID_RECTANGLE.area() / 1; //Makes value [0,1]
            averagedLeftBoxS = leftBox.val[1] / LEFT_RECTANGLE.area() / 1;
            averagedMidBoxS = rightBox.val[1] / MID_RECTANGLE.area() / 1;


//        if(averagedLeftBox > redThreshold){        //Must Tune Red Threshold
//            outStr = "left";
//        }else if(averagedRightBox> redThreshold){
//            outStr = "center";
//        }else{
//            outStr = "right";
//        }

            testMat.submat(LEFT_RECTANGLE).copyTo(frame); /*This line should only be added in when you want to see your custom pipeline
                                  on the driver station stream, do not use this permanently in your code as
                                  you use the "frame" mat for all of your pipelines, such as April Tags*/
        }
            return null;            //You do not return the original mat anymore, instead return null




    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
    public double[] getAverage() {
        double[] result = {averagedLeftBoxH, averagedLeftBoxS, averagedMidBoxH, averagedMidBoxS};
        return result;
    }

    public String getPropPosition(){  //Returns postion of the prop in a String
        return outStr;
    }
}
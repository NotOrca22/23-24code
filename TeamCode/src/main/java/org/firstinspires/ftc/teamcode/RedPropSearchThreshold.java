package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.File;

/**
 * Blue is represented in HSV at a hue of around 240 degrees out of 360. The Hue range in OpenCV-HSV is 0-180, to store the value in 8 bits. Thus, blue is represented in OpenCV-HSV as a value of H around 240 / 2 = 120.
 *
 * To detect blue correctly, the following values could be chosen:
 *
 * blue_lower=np.array([100,150,0],np.uint8)
 * blue_upper=np.array([140,255,255],np.uint8)
 *
 * https://cvexplained.wordpress.com/2020/04/28/color-detection-hsv/#:~:text=The%20HSV%20values%20for%20true,10%20and%20160%20to%20180.
 */
public class RedPropSearchThreshold implements VisionProcessor {
    private int captureCounter = 0;
    private File captureDirectory = AppUtil.FIRST_FOLDER;
    Mat testMat = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();
    Mat saved = new Mat();
    double redThreshold = 0.5;
    Boolean captured = Boolean.FALSE;

    String outStr = "unknown"; //Set a default value in case vision does not work

    static final Rect LEFT_SEARCH_ZONE = new Rect(
            new Point(0, 370),
            new Point(120, 460)
    );

    static final Rect CENTER_SEARCH_ZONE = new Rect(
            new Point(270, 320),
            new Point(620, 410)
    );

    static final Rect SEARCH_WINDOW = new Rect(
            new Point(0, 0),
            new Point(60, 90)
    );

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        if(true) {
            double redAvg = Core.mean(frame).val[0];
            if(redAvg > 0) {
                captured = Boolean.TRUE;
                Imgproc.cvtColor(frame, saved, Imgproc.COLOR_BGR2RGB);

//                File file = new File(captureDirectory, String.format(Locale.getDefault(), "webcam-frame-%d.jpg", System.currentTimeMillis()));

//                Imgcodecs.imwrite(file.getAbsolutePath(), saved);
                Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);


                Scalar lowHSVRedLower = new Scalar(0, 100, 20);  //Beginning of Color Wheel
                Scalar lowHSVRedUpper = new Scalar(10, 255, 255);

                Scalar redHSVRedLower = new Scalar(160, 100, 20); //Wraps around Color Wheel
                Scalar highHSVRedUpper = new Scalar(180, 255, 255);

                Core.inRange(testMat, lowHSVRedLower, lowHSVRedUpper, lowMat);
                Core.inRange(testMat, redHSVRedLower, highHSVRedUpper, highMat);

                testMat.release();

                Core.bitwise_or(lowMat, highMat, finalMat);

                lowMat.release();
                highMat.release();
                double maxLeftBox = 0.0;
                for(int offset = 0; offset < LEFT_SEARCH_ZONE.width- SEARCH_WINDOW.width; offset+=5) {
                    Rect used_rectangle = new Rect(LEFT_SEARCH_ZONE.x + offset, LEFT_SEARCH_ZONE.y, SEARCH_WINDOW.width, SEARCH_WINDOW.height);
                    double leftBox = Core.sumElems(finalMat.submat(used_rectangle)).val[0];

                    double averagedLeftBox = leftBox / used_rectangle.area() / 255;
                    if (averagedLeftBox > maxLeftBox) {
                        maxLeftBox = averagedLeftBox;
                    }
                }
                double maxCenterBox = 0.0;
                for(int offset = 0; offset < CENTER_SEARCH_ZONE.width- SEARCH_WINDOW.width; offset+=5) {
                    Rect used_rectangle = new Rect(CENTER_SEARCH_ZONE.x + offset, CENTER_SEARCH_ZONE.y, SEARCH_WINDOW.width, SEARCH_WINDOW.height);
                    double centerBox = Core.sumElems(finalMat.submat(used_rectangle)).val[0];

                    double averagedCenterBox = centerBox / used_rectangle.area() / 255;
                    if (averagedCenterBox > maxCenterBox) {
                        maxCenterBox = averagedCenterBox;
                    }
                }

//                file = new File(captureDirectory, String.format(Locale.getDefault(), "left-box-%d.jpg", System.currentTimeMillis()));
//
//                Imgcodecs.imwrite(file.getAbsolutePath(), saved.submat(LEFT_RECTANGLE));
//                file = new File(captureDirectory, String.format(Locale.getDefault(), "center-box-%d.jpg", System.currentTimeMillis()));
//
//                Imgcodecs.imwrite(file.getAbsolutePath(), saved.submat(CENTER_RECTANGLE));

                if (maxLeftBox > redThreshold) {        //Must Tune Red Threshold
                    outStr = "left";
                } else if (maxCenterBox > redThreshold) {
                    outStr = "center";
                } else {
                    outStr = "right";
                }

                 /*This line should only be added in when you want to see your custom pipeline
                   on the driver station stream, do not use this permanently in your code as
                   you use the "frame" mat for all of your pipelines, such as April Tags*/
//                finalMat.copyTo(frame);
            }

        }
        return null;            //You do not return the original mat anymore, instead return null




    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public String getPropPosition(){  //Returns postion of the prop in a String
        return outStr;
    }
    public boolean isCaptured(){
        return captured;
    }
}
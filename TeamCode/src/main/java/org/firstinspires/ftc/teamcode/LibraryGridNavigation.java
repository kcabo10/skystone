package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Beep Patrol
 * <p>
 * This library contains the grid navigation program, which utilizes a virtual grid
 * with origin (0, 0) starting the center of the field.  The angle fo 0 degrees
 * begins on the positive X axis and moves counterclockwise
 */
public class LibraryGridNavigation {

    HardwareBeep robot;// = new HardwareBeep();
    //LibraryGyro gyro;// = new LibraryGyro();
    LibraryGyroDrive gyroDrive = new LibraryGyroDrive();
    Telemetry telemetry;
    int i = 0;
    double xOrigin = 0;
    //X1 is starting X coordinate
    double xDestination;
    //X2 is X destination
    double yOrigin = 0;
    //Y1 is starting Y coordinate
    double yDestination;
    //Y2 is Y destination
    float StartingAngle = 0;
    //X1 is starting X coordinate
    //Y1 is starting Y coordinate
    double Distance;
    float turnAngle = 0f;
    double GEAR_RATIO_SCALING_FACTOR = 2;//(35/45);
    double Direction;
    double GEAR_RATIO_SCALING_FACTOR_TileRunner = 1;//(35/45); //12604 tile base has a 1 to 1 ratio
    //    SensorMB1242 leftUS = robot.leftSonic;
//    SensorMB1242 rightUS = robot.rightSonic;
    private ElapsedTime runtime = new ElapsedTime();

    //The angle 0 degrees starts on the positive X axis and moves counterclockwise

    /**
     * @param xPosition
     * @param yPosition
     * @param angle
     */

    double previousXPosition;

    public void setGridPosition(double xPosition, double yPosition, float angle) {
        xOrigin = xPosition;
        yOrigin = yPosition;
        previousXPosition = getDriveDistance(xPosition, yPosition);
        StartingAngle = angle;
        System.out.println("setGridPos to (" + xPosition + ", " + yPosition + ") angle " + angle);

    }

    /**
     * @param xDestination
     * @param yDestination
     * @return
     */
    public float getTurnAngleValuesOnly(double xDestination, double yDestination) {

        float tanAngle = 0; // comment

        // This section creates the legs of the triangle formed by the rectangular coordinates
        // inputted by the programing team.
        double xLeg = xDestination - xOrigin;

        double yLeg = yDestination - yOrigin;

        // The theta variable is found by taking the inverse tangent function of the y and x legs
        // of the triangle. Theta is the angle the robot needs to turn to align with the new
        // destination.
        double theta = Math.atan2(yLeg, xLeg);
        //atan2 automatically corrects for the limited domain of the inverse tangent function


        // This converts the radians given by the atan2 funtion to degrees.
        tanAngle = (float) Math.toDegrees(theta);
        if (tanAngle > 180) {
            tanAngle = tanAngle - 360;
        } else if (tanAngle < -180) {
            tanAngle = tanAngle + 360;
        }

        // These set the previous destination coordinates to the next starting coordinates.
        xOrigin = xDestination;
        yOrigin = yDestination;

        turnAngle = tanAngle - StartingAngle;
//        if (turnAngle > 180) {
//            turnAngle = turnAngle - 360;
//        } else if (turnAngle < -180) {
//            turnAngle = turnAngle + 360;
//        } else {
//
//        }
        System.out.println("xLeg is " + xLeg);
        System.out.println("yLeg is " + yLeg);
        System.out.println("Start Angle is " + StartingAngle);
        System.out.println("Tangent Angle is " + tanAngle);
        System.out.println("Turn angle " + turnAngle);
        StartingAngle = tanAngle;

        return turnAngle;

    }

    public float getTurnAngleBackwardsValuesOnly(double xDestination, double yDestination) {

        float tanAngle = 0; // comment

        // This section creates the legs of the triangle formed by the rectangular coordinates
        // inputted by the programing team.
        double xLeg = xDestination - xOrigin;

        double yLeg = yDestination - yOrigin;

        // The theta variable is found by taking the inverse tangent function of the y and x legs
        // of the triangle. Theta is the angle the robot needs to turn to align with the new
        // destination.
        double theta = Math.atan2(yLeg, xLeg);
        //atan2 automatically corrects for the limited domain of the inverse tangent function


        // This converts the radians given by the atan2 funtion to degrees.
        tanAngle = (float) Math.toDegrees(theta) + 180;
        if (tanAngle > 180) {
            tanAngle = tanAngle - 360;
        } else if (tanAngle < -180) {
            tanAngle = tanAngle + 360;
        }

        // These set the previous destination coordinates to the next starting coordinates.
        xOrigin = xDestination;
        yOrigin = yDestination;

        turnAngle = tanAngle - StartingAngle;
        if (turnAngle > 180) {
            turnAngle = turnAngle - 360;
        } else if (turnAngle < -180) {
            turnAngle = turnAngle + 360;
        }

        System.out.println("xLeg is " + xLeg);
        System.out.println("yLeg is " + yLeg);
        System.out.println("Start Angle is " + StartingAngle);
        System.out.println("Tangent Angle is " + tanAngle);
        System.out.println("Turn angle " + turnAngle);
        StartingAngle = tanAngle;

        return turnAngle;

    }

    /**
     * @param xDestination
     * @param yDestination
     * @return
     */
    public float getTurnAngle(double xDestination, double yDestination) {

        float tanAngle = 0;

        double xLeg = xDestination - xOrigin;

        double yLeg = yDestination - yOrigin;

        double theta = Math.atan2(yLeg, xLeg);
        //atan2 automatically corrects for the limited domain of the inverse tangent function
        System.out.println(xLeg);
        telemetry.addData("X pos", xLeg);
        System.out.println(yLeg);
        telemetry.addData("Y pos", yLeg);
        // This converts the radians naturally given by atan2 to degrees.
        tanAngle = (float) Math.toDegrees(theta);
        while ((tanAngle > 180) || (tanAngle < -180)) {
            if (tanAngle > 180) {
                tanAngle = tanAngle - 360;
            } else if (tanAngle < -180) {
                tanAngle = tanAngle + 360;
            }
        }
        // Prints telemetry to the phone.
        System.out.println("Start Angle is " + StartingAngle);
        telemetry.addData("Start Angle is ", StartingAngle);
        System.out.println("Tangent Angle is " + tanAngle);
        telemetry.addData("Tangent Angle is ", tanAngle);
        // Sets the previous destination coordinates to the new starting coordinates.
        xOrigin = xDestination;
        yOrigin = yDestination;
        // Corrects the turn angle to account for the robot's current heading.
        turnAngle = tanAngle - StartingAngle;
//            if(180 > turnAngle){
//                turnAngle = turnAngle - 360;
//            }
//            else if(turnAngle < -180){
//                turnAngle = turnAngle + 360;
//            }
//            else{
//
//            }
        System.out.println("Turn angle " + turnAngle);
        StartingAngle = tanAngle;
        telemetry.update();

        return turnAngle;

    }

    /**
     * @param xDestination
     * @param yDestination
     * @return
     */

    public float getTurnAngleBackwards(double xDestination, double yDestination) {

        float tanAngle = 0; // comment

        // This section creates the legs of the triangle formed by the rectangular coordinates
        // inputted by the programing team.
        double xLeg = xDestination - xOrigin;

        double yLeg = yDestination - yOrigin;

        // The theta variable is found by taking the inverse tangent function of the y and x legs
        // of the triangle. Theta is the angle the robot needs to turn to align with the new
        // destination.
        double theta = Math.atan2(yLeg, xLeg);
        //atan2 automatically corrects for the limited domain of the inverse tangent function


        // This converts the radians given by the atan2 funtion to degrees.
        tanAngle = (float) Math.toDegrees(theta) + 180;
        if (tanAngle > 180) {
            tanAngle = tanAngle - 360;
        } else if (tanAngle < -180) {
            tanAngle = tanAngle + 360;
        }

        // These set the previous destination coordinates to the next starting coordinates.
        xOrigin = xDestination;
        yOrigin = yDestination;

        turnAngle = tanAngle - StartingAngle;
        if (turnAngle > 180) {
            turnAngle = turnAngle - 360;
        } else if (turnAngle < -180) {
            turnAngle = turnAngle + 360;
        }

        System.out.println("xLeg is " + xLeg);
        System.out.println("yLeg is " + yLeg);
        System.out.println("Start Angle is " + StartingAngle);
        System.out.println("Tangent Angle is " + tanAngle);
        System.out.println("Turn angle " + turnAngle);
        StartingAngle = tanAngle;

        return turnAngle;

    }

    /**
     * @param xDestination
     * @param yDestination
     * @return
     */
    public double getDriveDistance(double xDestination, double yDestination) {

        double xLeg = xDestination - xOrigin;

        double yLeg = yDestination - yOrigin;

        Distance = ((Math.hypot(xLeg, yLeg) * 24) / 12.5663) * 145.6 * GEAR_RATIO_SCALING_FACTOR;

//        Distance = ((Math.hypot(xLeg, yLeg) * 24) / 12.5663) * 1120 * GEAR_RATIO_SCALING_FACTOR_TileRunner;

        //3.94 is 100cm circumference. 1120 is the encoder ticks for Neverrest 40.
        /** The input for each grid coordiante is one tile, so first we multiply the input
         * by size of one tile, which is 24 inches.  Then we divide that value by the distance
         * covered by one rotation of our wheels, which is 12.57 inches.  We then multiply that
         * value by the number of encoder ticks per rotation of the motors we are using.  Finally,
         * we multiply that encoder value by the gear ratio that is set up for the wheel assembly we use.
         */
        System.out.println("Drive Distance is " + Distance);

        return Distance;
    }

    //The grid is set such as that the origin (0, 0) is at the center and each grid point is 2 feet
    // from the next point

    /**
     * @param xDestination When you call this method in another function you insert the x destination
     *                     * you want to go to on the grid
     * @param yDestination When you call this method in another function you insert the y destination
     *                     you want to go to on the grid
     * @param power        Input the power you want to run the robot at
     */
    public void driveToPositionNonBlocking(double xDestination, double yDestination, double power) {

        getDriveDistance(xDestination, yDestination);
        getTurnAngle(xDestination, yDestination);

        gyroDrive.gyro.turnGyro(turnAngle);

        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftFront.setTargetPosition((int) (Distance));
        robot.leftBack.setTargetPosition((int) (Distance));
        robot.rightFront.setTargetPosition((int) (Distance));
        robot.rightBack.setTargetPosition((int) (Distance));

        robot.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);

    }

    //The grid is set such as that the origin (0, 0) is at the center and each grid point is 2 feet from the next point

    /**
     * @param xDestination When you call this method in another function you insert the x destination
     *                     * you want to go to on the grid
     * @param yDestination When you call this method in another function you insert the y destination
     *                     you want to go to on the grid
     * @param power        Input the power you want to run the robot at
     */
    public void driveToPositionValuesOnly(double xDestination, double yDestination, double power) {

        System.out.println("%n%ndriveToPosValuesOnly to (" + xDestination + ", " + yDestination + ")");

        getDriveDistance(xDestination, yDestination);
        getTurnAngleValuesOnly(xDestination, yDestination);
    }

    //The grid is set such as that the origin (0, 0) is at the center and each grid point is 2 feet from the next point

    /**
     * @param xDestination When you call this method in another function you insert the x destination
     *                     * you want to go to on the grid
     * @param yDestination When you call this method in another function you insert the y destination
     *                     you want to go to on the grid
     * @param power        Input the power you want to run the robot at
     */
    public void driveToPosition(double xDestination, double yDestination, double power) {

        // Encoder count is 145.6 rasius of wheel is 2
        // 2 * R means one ration of a wheel is 12.56 inches
        // Since its a 2-1 ratio that means it takes two times the distance to make it one rotation
        // Based on that, if we take away 1 motor rotation than that reduces the targed position my 6.28 inches
        // Which is approximately out error


        double PCoeff = .03;
        // ALERT THIS VALUE IS NOT USED BECAUSE WE SET IT MANUALLY IN GYRO DRIVE

        getDriveDistance(xDestination, yDestination);
        previousXPosition = getDriveDistance(xDestination, yDestination);
        getTurnAngle(xDestination, yDestination);

        gyroDrive.gyro.turnGyro(turnAngle);


        //THE HACK

        double myEncoderTicksPerInch = 145.6/6;


        if (power >= .3){
            Distance = Distance - (12 * myEncoderTicksPerInch);
        }

        gyroDrive.gyroDriveVariableP(power, (int) Distance, 0, PCoeff);
        //
        telemetry.addData("Get drive distance", getDriveDistance(xDestination, yDestination));
        telemetry.addData("Distance parameter", (int) Distance);
        telemetry.addData("What's my angle", StartingAngle);
        telemetry.addData("Turn Angle", turnAngle);
        telemetry.addData("xOrigin", xOrigin);
        telemetry.addData("yOrigin", yOrigin);
        telemetry.update();

    }

    public void strafeToPosition(double xDestination, double yDestination, double power, double direction) {

        getDriveDistance(xDestination, yDestination);

        if (direction == 0) {
            gyroDrive.gyroStrafeRight(power, (int) Distance, turnAngle);
        } else {
            gyroDrive.gyroStrafeLeft(power, (int) Distance, turnAngle);
        }

        telemetry.addData("Get drive distance", getDriveDistance(xDestination, yDestination));
        telemetry.addData("Distance parameter", (int) Distance);
        telemetry.addData("Turn Angle", (int) turnAngle);
        telemetry.update();

        xOrigin = xDestination;
        yOrigin = yDestination;
    }

    public void strafeToPositionBackwards(double xDestination, double yDestination, double power, double direction) {

        getDriveDistance(xDestination, yDestination);

        // Strafing Right
        if (direction == 0) {
            gyroDrive.gyroStrafeLeft(power, (int) Distance, turnAngle);
        }
        // Strafing Left
        else {
            gyroDrive.gyroStrafeRight(power, (int) Distance, turnAngle);
        }

        telemetry.addData("Get drive distance", getDriveDistance(xDestination, yDestination));
        telemetry.addData("Distance parameter", (int) Distance);
        telemetry.addData("Turn Angle", (int) turnAngle);
        telemetry.update();


        xOrigin = xDestination;
        yOrigin = yDestination;
    }

    /**
     * This is the initialize method
     *
     * @param myRobot     Call your hardware map
     * @param myGyro      Insert the gyro you want to ues. In our case we use the REV IMU
     * @param myTelemetry Call the telemetry
     */
    public void init(HardwareBeep myRobot, LibraryGyro myGyro, Telemetry myTelemetry) {
        // this is our robot
        robot = myRobot;
        // calling telemetry
        telemetry = myTelemetry;
        // Called the gyro drive library for us to use in the drive to position method
        gyroDrive.init(robot, telemetry, robot.leftFront);
    }

    /**
     * @param xDestination When you call this method in another function you insert the x destination
     *                     * you want to go to on the grid
     * @param yDestination When you call this method in another function you insert the y destination
     *                     you want to go to on the grid
     * @param power        Input the power you want to run the robot at
     */
    public void driveToPositionBackwards(double xDestination, double yDestination, double power) {
        double PCoeff = .03;
        getDriveDistance(xDestination, yDestination);
        previousXPosition = getDriveDistance(xDestination, yDestination);
        getTurnAngleBackwards(xDestination, yDestination);

        gyroDrive.gyro.turnGyro(turnAngle);



        //THE HACK

        double myEncoderTicksPerInch = 145.6/6;

        if (power >= .3){
            Distance = Distance - (12 * myEncoderTicksPerInch);
        }

        gyroDrive.gyroDriveVariableP(-power, (int) -Distance, 0, PCoeff);

        //


        telemetry.addData("What's my angle", StartingAngle);
        telemetry.addData("Turn Angle", turnAngle);
        telemetry.addData("xOrigin", xOrigin);
        telemetry.addData("yOrigin", yOrigin);
        telemetry.update();
    }

    public void driveToPositionCalcFindX(double xDestination, double yDestination, double power) {
        double PCoeff = .03;
        getDriveDistance(xDestination, yDestination);
        previousXPosition = getDriveDistance(xDestination, yDestination);

        //THE HACK

        double myEncoderTicksPerInch = 145.6/6;

        if (power >= .3){
            Distance = Distance - (12 * myEncoderTicksPerInch);
        }

        gyroDrive.gyroDriveVariableP(-power, (int) -Distance, 0, PCoeff);

        //


        telemetry.addData("What's my angle", StartingAngle);
        telemetry.addData("Turn Angle", turnAngle);
        telemetry.addData("xOrigin", xOrigin);
        telemetry.addData("yOrigin", yOrigin);
        telemetry.update();
    }
    /**
     * @param xDestination When you call this method in another function you insert the x destination
     *                     * you want to go to on the grid
     * @param yDestination When you call this method in another function you insert the y destination
     *                     you want to go to on the grid
     * @param power        Input the power you want to run the robot at
     */
    public void driveToPositionBackwardsValuesOnly(double xDestination, double yDestination, double power) {
        getDriveDistance(xDestination, yDestination);
        getTurnAngleBackwardsValuesOnly(xDestination, yDestination);
        System.out.println("driveToPositionBackwardsValueOnly to with turn angle " + turnAngle + " and Starting Angle " + StartingAngle);

    }


    public void strafeToPositionBackwardsValuesOnly(double xDestination, double yDestination, double power, double direction) {
        getDriveDistance(xDestination, yDestination);

        // Strafing Right
        if (direction == 0) {
            //gyroDrive.gyroStrafeLeft(power, (int) Distance, turnAngle);
        }
        // Strafing Left
        else {
            //gyroDrive.gyroStrafeRight(power, (int) Distance, turnAngle);
        }
        System.out.println("driveToPositionStrafingValueOnly to with turn angle " + turnAngle + " and Starting Angle " + StartingAngle);

        xOrigin = xDestination;
        yOrigin = yDestination;
    }
}


/*
    public void angleCorrection(double L1, double L2, double tanAngle) {
        double getTurnAngle = 0;
        if((L1 < 0) && (L2 < 0)){
            getTurnAngle = tanAngle - StartingAngle + 180;
        }
        else if(L1 < 0){
            getTurnAngle = tanAngle - StartingAngle + 180;
        }
        else{
            getTurnAngle = tanAngle - StartingAngle;
        }
        System.out.println(getTurnAngle);
        StartingAngle = getTurnAngle;
        }
}
*/
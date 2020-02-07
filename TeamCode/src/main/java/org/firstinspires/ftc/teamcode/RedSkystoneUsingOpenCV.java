package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.sensors.LibraryColorSensor;

/**
 * @author Beep Patrol
 * <p>
 * <b>Summary:</b>
 * <p>
 * This is our autonomous program for the depot side on the blue side of the field. This program runs
 * without the phone light for Tensor Flow. This is the go to program. This program... .
 */
@Autonomous(name = "Red Side Skystone Delivery", group = "Beep")
public class RedSkystoneUsingOpenCV extends LinearOpMode {

    // Declaring a timer
    public ElapsedTime runtime = new ElapsedTime();
    public String foundTargetName = "";
    String SkystonePosition = "";
    float StoneColor = 1;
    //Calling our hardware map
    HardwareBeep robot = new HardwareBeep();
    // Calling the Library Gyro program to use the gyro turn function
    LibraryGyro gyroTurn = new LibraryGyro();
    // Calling the Library Gyro Drive program to use the gyro drive function
    LibraryGyroDrive gyroDrive = new LibraryGyroDrive();

    LibraryGridNavigation gridNavigation = new LibraryGridNavigation();

    LibraryOpenCV opencv;

    private int readColorSensor = 0;

    double offset = .31;

    /**
     * This method is the main body of our code which contains the set of commands carried out in our crater side autonomous program.
     */
    @Override
    public void runOpMode() {
        telemetry.addData("Telemetry", "robot initializing");
        telemetry.update();
        //initializing the hardware map
        robot.init(hardwareMap);
        //initializing the gyro turn function
        gyroTurn.init(robot, telemetry);
        //initializing the gyro drive function
        gyroDrive.init(robot, telemetry, robot.rightBack);

        opencv = new LibraryOpenCV(robot, telemetry);

        gridNavigation.init(robot, gyroTurn, telemetry);

        telemetry.addData("Telemetry", "run opMode start");
        telemetry.update();


        int X = 0;
        int Y = 1;

        double[] TO_SKYSTONE_1 = {1.9, 2.34};
        double[] TO_SKYSTONE_2 = {2, .3645};
        double[] SKYSTONE_POS_1 = {2, 2.4};
        double[] SKYSTONE_POS_2 = {2, 2};
        double[] SKYSTONE_POS_3 = {1.7, 2};
        double[] GRAB_SKYSTONE_POS_1 = {1.8, 2};
        double[] GRAB_SKYSTONE_POS_2 = {1.5, 2};
        double[] GRAB_SKYSTONE_POS_3 = {1.1, 2};
        double[] BACKING_UP_1 = {1.81, 1.5};
        double[] BACKING_UP_2 = {1.5, 1.5};
        double[] BACKING_UP_3 = {1.1, 1.5};

        // Start position for foundation hooks
        robot.foundation1.setPosition(1);
        robot.foundation2.setPosition(-1);

        waitForStart();

        getSkystonePos();

        gridNavigation.setGridPosition(1.7, .3645, 270);


        switch (SkystonePosition) {

            default: {

                telemetry.addData("Telemetry", "Skystone Pos = 1");
                telemetry.update();

                robot.rightIntake.setPower(1);
                robot.leftIntake.setPower(-1);
                gridNavigation.driveToPositionBackwards(SKYSTONE_POS_1[X], SKYSTONE_POS_1[Y], .2);
                robot.rightIntake.setPower(0);
                robot.leftIntake.setPower(0);
                gridNavigation.driveToPosition(BACKING_UP_1[X], BACKING_UP_1[Y], .5);
//
//                moveFoundationPlaceSkystone();
            }

                break;

//            //Middle Skystone position
//            case "Pos 2":
//
//                telemetry.addData("Telemetry", "Skystone Pos = 2");
//                telemetry.update();
//
//                gridNavigation.driveToPositionBackwards(TO_SKYSTONE_2[X], TO_SKYSTONE_2[Y], .2);
//                robot.rightIntake.setPower(1);
//                robot.leftIntake.setPower(-1);
//                gridNavigation.strafeToPositionBackwards(SKYSTONE_POS_2[X], SKYSTONE_POS_2[Y], .7, 0);
//                gridNavigation.driveToPositionBackwards(GRAB_SKYSTONE_POS_2[X], GRAB_SKYSTONE_POS_2[Y], .3);
//                robot.rightIntake.setPower(0);
//                robot.leftIntake.setPower(0);
//                gridNavigation.strafeToPositionBackwards(BACKING_UP_2[X], BACKING_UP_2[Y], .7,1);
//                moveFoundationPlaceSkystone();
//
//                break;
//
//            //Position closest to skybridge
//            case "Pos 3":
//
//                telemetry.addData("Telemetry", "Skystone Pos = 3");
//                telemetry.update();
//
//                // Turning so we can strafe into stones
//                gyroTurn.turnGyro(90);
//                gridNavigation.setGridPosition(1.6, .3645, 360);
//
//                robot.rightIntake.setPower(1);
//                robot.leftIntake.setPower(-1);
//                gridNavigation.strafeToPositionBackwards(SKYSTONE_POS_3[X], SKYSTONE_POS_3[Y], .7, 0);
//                gridNavigation.driveToPositionBackwards(GRAB_SKYSTONE_POS_3[X], GRAB_SKYSTONE_POS_3[Y], .3);
//                robot.rightIntake.setPower(0);
//                robot.leftIntake.setPower(0);
//
//                gridNavigation.strafeToPositionBackwards(BACKING_UP_3[X], BACKING_UP_3[Y], .7, 1);
//
//                moveFoundationPlaceSkystone();
//
//                break;
//
//            // should never get to this case but in case it can't find the skystone position
//            // it goes to this default case
//            default:
//
//                // Turning so we can strafe into stones
//                telemetry.addData("No pos found","");
//                telemetry.update();
//                gyroTurn.turnGyro(90);
//                gridNavigation.setGridPosition(1.6, .3645, 360);
//
//                robot.rightIntake.setPower(1);
//                robot.leftIntake.setPower(-1);
//                gridNavigation.strafeToPositionBackwards(SKYSTONE_POS_3[X], SKYSTONE_POS_3[Y], .7, 0);
//                gridNavigation.driveToPositionBackwards(GRAB_SKYSTONE_POS_3[X], GRAB_SKYSTONE_POS_3[Y], .3);
//                robot.rightIntake.setPower(0);
//                robot.leftIntake.setPower(0);
//
//                gridNavigation.strafeToPositionBackwards(BACKING_UP_3[X], BACKING_UP_3[Y], .7, 1);
//
//                moveFoundationPlaceSkystone();
//                break;
        }

    }

    int X = 0;
    int Y = 1;

    //Same end to each case
    double[] DELIVERING_SKYSTONE = {5.3, 1.5};
    double[] GRAB_FOUNDATION = {5.3, 1.75};
    double[] BACK_UP = {5.3, .9};
    double[] PARKING_POS = {3.3, 1.6};

        public void moveFoundationPlaceSkystone (){

            /**
             * Setting up the claw for Delivering Stones
             */
            robot.claw.setPosition(0); //open claw
            robot.clawTurner.setPosition(1); //ensure claw turner is straight

            gridNavigation.driveToPosition(DELIVERING_SKYSTONE[X], DELIVERING_SKYSTONE[Y], .5);

            robot.clawAid.setPosition(1); //move the claw aid up

            robot.claw.setPosition(1); //close claw
            robot.clawAid.setPosition(0); //reset claw aid

            gridNavigation.driveToPosition(GRAB_FOUNDATION[X], GRAB_FOUNDATION[Y], .5);
            robot.foundation1.setPosition(.5);
            robot.foundation2.setPosition(.5);

            /**
             * Place Stone on Foundation
             */
            runtime.reset();
            while (runtime.milliseconds() == .3) {
                robot.outExtrusion.setPower(.7); //Extend out extrusions over foundation
            }

            robot.outExtrusion.setPower(0);

            robot.claw.setPosition(0); // open claw to drop stone

            gridNavigation.driveToPositionBackwards(BACK_UP[X], BACK_UP[Y], .5);

            /**
             * Bring back out extrusions
             */
            runtime.reset();
            while (runtime.milliseconds() == .2) {
                robot.outExtrusion.setPower(-.7);
            }
            robot.outExtrusion.setPower(0);

            /**
             * Turn foundation into building site
             */

            robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.leftBack.setTargetPosition(3656);
            robot.rightBack.setTargetPosition(-3656);

            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.leftBack.setPower(1);
            robot.rightBack.setPower(1);

            while (robot.rightBack.isBusy() && robot.leftBack.isBusy()) {
            }

            robot.leftBack.setPower(0);
            robot.rightBack.setPower(0);
        }

        public void placeSecondSkystone () {


        }


        public void getSkystonePos () {
            int debounceCount = 0;
            long startTime = 0;
            String previousPosition;
            /*
             * UPDATE WITH NEW REPOSITORY
             */
            SkystonePosition = opencv.findSkystone();

            // Switch block that indicated which skystone position it reads
            switch (SkystonePosition) {
                case ("Pos 1"):
                    telemetry.addData("Telemetry", "left");
                    telemetry.update();
                    SkystonePosition = "Pos 1";
                    break;
                case ("Pos 2"):
                    telemetry.addData("Telemetry", "Middle");
                    telemetry.update();
                    SkystonePosition = "Pos 2";
                    break;
                case ("Pos 3"):
                    telemetry.addData("Telemetry", "right");
                    telemetry.update();
                    SkystonePosition = "Pos 3";
                    break;

                // If it reads unknown than it goes to this default case
                default:
                    telemetry.addData("Telemetry", "Unknown Position");
                    telemetry.update();
                    // sets skystone pos to center as default
                    SkystonePosition = "Pos 1";
                    break;
            }

            telemetry.update();
        }
    }
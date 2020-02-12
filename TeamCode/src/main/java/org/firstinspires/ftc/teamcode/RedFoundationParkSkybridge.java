package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Red Foundation Park Skybridge", group = "Beep")
public class RedFoundationParkSkybridge extends LinearOpMode {

    // Declaring a timer
    public ElapsedTime runtime = new ElapsedTime();
    // Calling hardware map
    HardwareBeep robot = new HardwareBeep();

    LibraryGyroDrive gyroDrive = new LibraryGyroDrive();

    LibraryGyro gyroTurn = new LibraryGyro();
    LibraryGridNavigation gridNavigation = new LibraryGridNavigation();

    @Override
    public void runOpMode() {

        telemetry.addData("Telemetry", "robot initializing");
        telemetry.update();
        //initializing the hardware map
        robot.init(hardwareMap);
        gyroDrive.init(robot, telemetry, robot.rightBack);
        gyroTurn.init(robot, telemetry);
        gridNavigation.init(robot, gyroTurn, telemetry);
        telemetry.addData("Telemetry", "run opMode start");
        telemetry.update();
        telemetry.update();

        //wait for start
        waitForStart();

        robot.foundation1.setPosition(1);
        robot.foundation2.setPosition(-1);
        //Grid nav set in perspective on positive x,y and blue build site

        gridNavigation.setGridPosition(4.6, 0.4,90);

        gridNavigation.strafeToPosition(5.1, 0.4,.5, 0);

        gridNavigation.driveToPosition(5.1, 1.7,.3);
        gridNavigation.driveToPosition(5.1, 1.9,.2);

        sleep(1000);
        robot.foundation1.setPosition(.5);
        robot.foundation2.setPosition(.5);
        sleep(800);

        gridNavigation.driveToPositionBackwards(5.1, .8,.3);

        /**
         * Turn foundation into building site
         */

        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftBack.setTargetPosition(1200);
        robot.rightBack.setTargetPosition(-1200);

        robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftBack.setPower(1);
        robot.rightBack.setPower(1);

        while (robot.rightBack.isBusy() && robot.leftBack.isBusy()) {
        }

        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);

        gyroDrive.gyroDriveVariableP(.5,(int)(145.6*1.6),0,.03);
        robot.foundation1.setPosition(1);
        robot.foundation2.setPosition(-1);
        sleep(1000);

        gyroDrive.gyroDriveVariableP(-.29,-(int)(145.6* (44/12.56) + 450),0,.03);


//        gridNavigation.setGridPosition();
    }
}
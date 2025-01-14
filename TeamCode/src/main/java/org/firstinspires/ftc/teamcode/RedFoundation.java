package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Red Foundation", group = "Beep")
public class RedFoundation extends LinearOpMode {

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

        robot.foundation1.setPosition(-1);
        robot.foundation2.setPosition(1);
        //Grid nav set in perspective on positive x,y and blue build site

        gridNavigation.setGridPosition(0.296,  2.1041, 0);

        gridNavigation.driveToPosition(2.375, 2.1041,.8);
        gridNavigation.driveToPosition(2.375, 1.6,.8);

        robot.foundation1.setPosition(.5);
        robot.foundation2.setPosition(.5);
        sleep(500);

        gyroDrive.gyroDriveVariableP(0.5, -500, 0,.01);

        gyroTurn.turnGyro(-45);
//
        gyroDrive.gyroDriveVariableP(0.5, 1500, 0,.01);

        robot.foundation1.setPosition(-1);
        robot.foundation2.setPosition(1);
        sleep(1000);

        gyroTurn.turnGyro(70);

        gyroDrive.gyroDriveVariableP(0.5, -4100, 0,.01);

        gyroDrive.gyroStrafeRight(.7, 2200, 0);


    }
}
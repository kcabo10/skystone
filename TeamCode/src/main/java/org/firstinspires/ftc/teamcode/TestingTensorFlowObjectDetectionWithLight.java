package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * @author Beep Patrol
 * <p>
 * <b>Summary:</b>
 * <p>
 * This is our autonomous program for the depot side on the blue side of the field. This program runs
 * without the phone light for Tensor Flow. This is the go to program. This program... .
 */
@Disabled
@Autonomous(name = "Testing Tensor Flow Object Detection With Light", group = "Beep")
public class TestingTensorFlowObjectDetectionWithLight extends LinearOpMode {

    //Calling our hardware map
    HardwareBeep robot = new HardwareBeep();

    // Calling the Library Tensor Flow No Light to use the Tensor Flow function without
    LibraryTensorFlowObjectDetectionWithLight tensorFlow =
            new LibraryTensorFlowObjectDetectionWithLight(robot, telemetry);
    // Declaring skystone position value to read what position Tensor Flow sees the skystone position
    String SkystonePosition = "";

    /**
     * This method is the main body of our code which contains the set of commands carried out in our crater side autonomous program.
     */
    @Override
    public void runOpMode() {

        telemetry.addData("Telemetry", "robot initializing");
        telemetry.update();
        //initializing the hardware map
        robot.init(hardwareMap);
        telemetry.addData("Telemetry", "run opMode start");
        telemetry.update();


        //wait for start
        waitForStart();

        // Start up Tensor Flow to read skystone position after start
        getSkystonePos();

        // This is a switch block that plays the program in relation to the skystone position
        // Tensor Flow reads
        switch (SkystonePosition) {

            // If Tensor Flow reads the first skystone position then it plays this case
            case "Pos 3":
                telemetry.addData("Telemetry", "Skystone Pos = right");
                printTelemetry(20);
                if (SkystonePosition == "Pos 3") {

                } else {
                    telemetry.addData("Telemetry", "No Position Found");
                    printTelemetry(30);
                }
                break;

            // If Tensor Flow reads the second skystone position then it plays this case
            case "Pos 2":
                telemetry.addData("Telemetry", "Skystone Pos = middle");
                printTelemetry(40);
                if (SkystonePosition == "Pos 2") {

                } else {
                    telemetry.addData("Telemetry", "No Position Found");
                    printTelemetry(50);
                }
                break;
// If Tensor Flow reads the third skystone position then it plays this case
            case "Pos 1":
                telemetry.addData("Telemetry", "Skystone Pos = left");
                printTelemetry(60);
                if (SkystonePosition == "Pos 1") {

                } else {
                    telemetry.addData("Telemetry", "No Position Found");
                    printTelemetry(70);
                }
                break;
            // should never get to this case but in case it can't find the skystone position
            // it goes to this default case
            default:
                telemetry.addData("Telemetry", "Didn't see skystone pos");
                telemetry.update();
                break;
        }
    }

    /**
     * This method prints telemetry for our autonomous program
     *
     * @param codePos This is the value we use in telemetry to see where in the code we are
     */
    private void printTelemetry(int codePos) {
        telemetry.addData("skystone Pos", SkystonePosition);
        telemetry.addData("Code Position", codePos);
        telemetry.update();
    }

    /**
     * This method calls Tensor Flow in order to read the skystone position
     */
    public void getSkystonePos() {
        int debounceCount = 0;
        long startTime = 0;
        String previousPosition;
        /*
         * UPDATE WITH NEW REPOSITORY
         */
        SkystonePosition = tensorFlow.findSkystone();

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
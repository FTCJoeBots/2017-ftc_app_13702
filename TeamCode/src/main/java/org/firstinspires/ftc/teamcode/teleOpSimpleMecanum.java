package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 *import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 *import com.qualcomm.robotcore.hardware.DcMotor;
 *
 *
 */

/**
 *Notes For this TeleOp Code. This code is for Comp and all proggramers should review over this
 *code and understand this code for the possibility that a question may be asked related to TeleOp and
 *you should be able to explain in good detail everything in this code.
 *11/16/17-> Changed all gamepad's in code to correct gamepad (i.e some gamepad1's to gamepad2)
 ***11/18/17-> Competition Notes below
 *Notes-> Autonomous is incorrect, Not much was wrong from a software sandpoint but hardware issues were fixed
 *Autonomous issues included: Incorrect spinning causing us to move out of destination,
 *To much time on the down motion of the clamp and arm.
 *These issues are still not resolved
 * Recomendation for autonomous issues(Not Offical):Fine tune the timer on the clamp
 * Fine tune the movements and LOWER the TIME OF MOVEMENT in autonomous.
 * List of issues at Comp(1)-> https://docs.google.com/a/stjoebears.com/spreadsheets/d/1r_liipKBU7GHfONdxq9E6d4f7zikcCuXwDL2bsQfwm0/edit?usp=sharing
 *G-Sheet of time VS Heading for autonomous -> https://docs.google.com/a/stjoebears.com/spreadsheets/d/1pqv0iN94fFd5KvX1YIWP7z39HgpURXsscn0zPujs1q4/edit?usp=sharing
*/
@TeleOp(name="2017-13702 Simple Mecanum Drive", group="TeleOp")

public class teleOpSimpleMecanum extends LinearOpMode {

    DcMotor liftMotor;
    double forward;
    double clockwise;
    double right;
    double k;
    double power1;
    double power2;
    double power3;
    double power4;
    double liftpower;
    double max;

    HardwareJoeBot2017 robot = new HardwareJoeBot2017();

    @Override
    public void runOpMode() throws InterruptedException {




        robot.init(hardwareMap, this);

        boolean bPrevStateA = false;
        boolean bCurrStateA = false;
        boolean bPrevStateB = false;
        boolean bCurrStateB = false;
        boolean bPrevStateX = false;
        boolean bCurrStateX = false;
        boolean bPrevStateY = false;
        boolean bCurrStateY = false;



        waitForStart();



        //start of loop
        while (opModeIsActive()) {


            //Drive Via "Analog Sticks" (Not Toggle)
            //Set initial motion parameters to Gamepad1 Inputs
            forward = -gamepad1.left_stick_y;
            //right = gamepad1.left_stick_x;
            right = -gamepad1.left_trigger + gamepad1.right_trigger;
            clockwise = gamepad1.right_stick_x;

            // Add a tuning constant "K" to tune rotate axis sensitivity
            k = .6;
            clockwise = clockwise * k; //Make sure the "= Clockwise" is "= -clockwise"


           robot.moveRobot(forward, right, clockwise);

            // Operator Controls

            if(opModeIsActive() && gamepad2.dpad_up) {
                // raise lift
                robot.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.liftMotor.setPower(-robot.LIFT_MOTOR_POWER);
            } else if (opModeIsActive() && gamepad2.dpad_down) {
                //lower lift
                robot.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.liftMotor.setPower(robot.LIFT_MOTOR_POWER);
            } else {
                robot.liftMotor.setPower(0);
            }

            // Check for A button press
            bCurrStateA = gamepad2.a;
            if ((bCurrStateA == true) && (bCurrStateA != bPrevStateA)) {

                // When the "A" button is pressed, we want to enable search mode
                if(robot.rotatePosition !=2) {
                    robot.clampHorizontal();
                }
                if(robot.clampPosition !=3) {
                    robot.clampOpen();
                }
                robot.liftPositionToggle();

            }
            bPrevStateA = bCurrStateA;

            // Check for B Button Press
            bCurrStateB = gamepad2.b;
            if ((bCurrStateB == true) && (bCurrStateB != bPrevStateB)) {

                robot.clampToggle();

            }
            bPrevStateB = bCurrStateB;

            // Check for Y Button Press
            bCurrStateY = gamepad2.y;
            if ((bCurrStateY == true) && (bCurrStateY != bPrevStateY)) {

                robot.rotateClamp();

            }
            bPrevStateY = bCurrStateY;


            // Update Telemetry
            telemetry.addData(">", "Press Stop to end test.");
            updateDisplay();
            idle();

            //TODO: Fix "A" button -- It always goes to 2, then back down to 1. Never stops at 2
            //TODO: Fix "Y" Button -- It brings clamp vertical, but not back down to horizontal.
            //TODO: Add function to move back down to base position when clamp is vertical. Right now, that can't be done since pressing "A" is the only way to toggle the lift
            //TODO: Fix "B" Button -- Right now, it doesn't find the mid position... Only Close.




        }//end while
    }

    public void updateDisplay(){

        telemetry.addData("Lift Motor: ", robot.liftMotor.getCurrentPosition());
        telemetry.addData("Clamp Position: ", robot.clampPosition);
        telemetry.addData("Rotate Position: ", robot.rotatePosition);
        telemetry.addData("Lift Height: ", robot.liftHeight);
        telemetry.update();

    }
}
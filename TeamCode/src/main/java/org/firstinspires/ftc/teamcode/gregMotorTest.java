/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Sample code to test mapping of one motor to the gamepad.
 */
@TeleOp(name = "TEST Server & Encoder Values", group = "Testing")
//@Disabled
public class gregMotorTest extends LinearOpMode {


    // Define class members
    DcMotor liftMotor = null; // Lander Lift Motor
    public Servo clampServo;
    public Servo rotateServo;
    public Servo jewelServo;

    double liftpower = 0;

    double clampServoPosition = 0;
    double rotateServoPosition = 0;
    double jewelServoPosition = 0;


    @Override
    public void runOpMode() {

        liftMotor       = hardwareMap.dcMotor.get("liftmotor");

        rotateServo    = hardwareMap.get(Servo.class, "clamprotate");
        clampServo     = hardwareMap.get(Servo.class, "clampservo");
        jewelServo     = hardwareMap.get(Servo.class, "jewelservo");


        liftMotor.setDirection(DcMotor.Direction.FORWARD);

        liftMotor.setPower(0);


        // Wait for the start button
        telemetry.addData(">", "Press Start to run Test." );
        telemetry.update();
        waitForStart();

        // Set Servos to mid position
        rotateServoPosition = 0.5;
        jewelServoPosition = 0.5;
        clampServoPosition = 0.5;

        rotateServo.setPosition(rotateServoPosition);
        jewelServo.setPosition(jewelServoPosition);
        clampServo.setPosition(clampServoPosition);

        updateDisplay();

        while(opModeIsActive()) {

            // Display the current value


            liftpower = gamepad1.left_stick_y * 0.75;
            liftMotor.setPower(liftpower);


            // Clamp Servo
            while (gamepad1.dpad_up) {
                if (clampServoPosition >= 1){
                    clampServoPosition = 1;
                } else {
                    clampServoPosition += 0.01;
                }
                clampServo.setPosition(clampServoPosition);
                updateDisplay();
            }
            while (gamepad1.dpad_down) {

                if (clampServoPosition <= 0){
                    clampServoPosition = 0;
                } else {
                    clampServoPosition -= 0.01;
                }
                clampServo.setPosition(clampServoPosition);
                updateDisplay();
            }

            // Rotate Servo

            while (gamepad1.dpad_right) {
                if (rotateServoPosition >= 1){
                    rotateServoPosition = 1;
                } else {
                    rotateServoPosition += 0.01;
                }
                rotateServo.setPosition(rotateServoPosition);
                updateDisplay();
            }
            while (gamepad1.dpad_left) {
                if (rotateServoPosition <= 0){
                    rotateServoPosition = 0;
                } else {
                    rotateServoPosition -= 0.01;
                }
                rotateServo.setPosition(rotateServoPosition);
                updateDisplay();
            }

            // Jewel Servo

            while (gamepad1.right_bumper) {
                if (jewelServoPosition >= 1){
                    jewelServoPosition = 1;
                } else {
                    jewelServoPosition += 0.01;
                }
                jewelServo.setPosition(jewelServoPosition);
                updateDisplay();
            }
            while (gamepad1.left_bumper) {
                if (jewelServoPosition <= 1){
                    jewelServoPosition = 1;
                } else {
                    jewelServoPosition -= 0.01;
                }
                jewelServo.setPosition(jewelServoPosition);
                updateDisplay();
            }



            updateDisplay();

        }

    }

    public void updateDisplay(){

        telemetry.addLine("DPAD U/D: Clamp; DPAD L/R: Rotate");
        telemetry.addLine("Bumpers: jewelServo");
        telemetry.addData("Clamp Servo: ", clampServoPosition );
        telemetry.addData("Rotate Servo: ", rotateServoPosition );
        telemetry.addData("Jewel Servo: ", jewelServoPosition );
        telemetry.addData("Lift Motor: ", liftMotor.getCurrentPosition());
        telemetry.update();

    }
}

















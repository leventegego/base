package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;

	private boolean breaking = false;
	TrainSensor sensor;

	public void run(int timeUnit_ms, int iteration) throws InterruptedException {
		for (int i=0; i< iteration; i++){
			followSpeed();
			Thread.sleep(timeUnit_ms/10);
		}
	}


	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
		enforceBreaking();

		sensor.logSpeed(referenceSpeed, step);
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		sensor.logSpeed(referenceSpeed, step);
		
	}

	@Override
	public void setSensor(TrainSensor sensor)
	{
		this.sensor = sensor;
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	private void enforceBreaking()
	{
		if(breaking)
			referenceSpeed = 0;
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

	@Override
	public int getJoystickPosition()
	{
		return step;
	}

	@Override
	public void setEmergencyBreak(boolean val)
	{
		breaking = val;
		enforceBreaking();
		sensor.logSpeed(referenceSpeed, step);

	}

}
